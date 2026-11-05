#!/usr/bin/env python3
"""
Відтворюваний розрахунок метрик ЛР №8 (CBO, LCOM4, Ca/Ce/I) для варіанта 3.

Запуск (з теки lab-08):
    python3 docs/metrics/compute_metrics.py

Угоди підрахунку (див. docs/05-metrics.md):
  * CBO: кількість ІНШИХ типів проєкту, які явно названі у вихідному коді класу
    (поле, параметр, повернення, локальна змінна, new, статичний виклик, extends/implements, throws/catch).
    Типи java.* та примітиви не враховуються. Коментарі й рядкові літерали ігноруються.
  * LCOM4: вершини — нестатичні методи, крім конструкторів і тривіальних акцесорів
    (`return field;` / `return field.method();`). Ребро — спільне поле екземпляра
    або прямий виклик одного методу іншим. LCOM4 = кількість компонент зв'язності.
    Якщо після виключення методів лишилось 0 — клас є незмінним носієм даних → позначаємо «1*».
  * Ca/Ce/I рахуються на рівні пакетів; composition root (Main) виключено.
"""
import re, sys, pathlib, itertools
from collections import defaultdict

ROOT = pathlib.Path(__file__).resolve().parents[2]

def strip(src):
    src = re.sub(r'/\*.*?\*/', ' ', src, flags=re.S)
    src = re.sub(r'//[^\n]*', ' ', src)
    src = re.sub(r'"(?:\\.|[^"\\])*"', '""', src)
    return src

def top_level_types(src):
    return re.findall(r'^\s*(?:public\s+|final\s+|abstract\s+)*(?:class|interface|enum|record)\s+(\w+)', src, flags=re.M)

def load_tree(task):
    files = sorted((ROOT/task/'src').rglob('*.java'))
    classes = {}
    for f in files:
        raw = f.read_text(encoding='utf-8')
        pkg = re.search(r'package\s+([\w.]+);', raw).group(1).split('.')[-1]
        name = f.stem
        classes[name] = dict(pkg=pkg, src=strip(raw), raw=raw, file=f)
    return classes

def cbo(classes):
    names = set(classes)
    out = {}
    for n, c in classes.items():
        body = re.sub(r'^\s*(package|import)[^\n]*', '', c['src'], flags=re.M)
        deps = {m for m in names if m != n and re.search(r'\b%s\b' % m, body)}
        out[n] = deps
    return out

# ---------- LCOM4 ----------
def match_brace(s, i):
    d = 0
    for j in range(i, len(s)):
        if s[j] == '{': d += 1
        elif s[j] == '}':
            d -= 1
            if d == 0: return j
    return len(s)

def class_body(src, name):
    m = re.search(r'\b(?:class|enum|record)\s+%s\b[^{]*\{' % name, src)
    if not m: return None
    i = m.end() - 1
    return src[i+1:match_brace(src, i)]

def lcom4(name, src):
    body = class_body(src, name)
    if body is None: return None, []
    fields = set(re.findall(r'^\s*private\s+(?!static)(?:final\s+)?[\w<>\[\], ]+?\s+(\w+)\s*(?:=[^;]*)?;', body, flags=re.M))
    # record-компоненти не вважаємо полями з логікою
    methods = {}
    for m in re.finditer(r'^\s*(?:public\s+|private\s+|protected\s+)?(static\s+)?(?:final\s+)?[\w<>\[\], .]+?\s+(\w+)\s*\(([^)]*)\)\s*(?:throws [\w, ]+)?\{', body, flags=re.M):
        static, mname = m.group(1), m.group(2)
        if static or mname in ('if','for','while','switch','catch','return') or mname == name:
            continue
        i = m.end() - 1
        mb = body[i+1:match_brace(body, i)]
        if re.fullmatch(r'\s*return\s+(?:this\.)?\w+(?:\.\w+\(\))?\s*;\s*', mb):   # тривіальний акцесор
            continue
        params = set(re.findall(r'(\w+)\s*(?:,|$)', m.group(3)))
        methods[mname] = (mb, params)
    if not methods: return 0, []
    uses = {k: {f for f in fields if re.search(r'\b%s\b' % f, v[0]) and f not in v[1]} for k, v in methods.items()}
    calls = {k: {o for o in methods if o != k and re.search(r'\b%s\s*\(' % o, v[0])} for k, v in methods.items()}
    parent = {k: k for k in methods}
    def find(x):
        while parent[x] != x: parent[x] = parent[parent[x]]; x = parent[x]
        return x
    for a, b in itertools.combinations(methods, 2):
        if uses[a] & uses[b] or b in calls[a] or a in calls[b]:
            parent[find(a)] = find(b)
    return len({find(k) for k in methods}), sorted(methods)

def lcom_str(name, c):
    v, ms = lcom4(name, c['src'])
    if v is None: return '—'
    return '1*' if v == 0 else str(v)

# ---------- Ca / Ce / I ----------
def instability(classes, deps):
    pk = defaultdict(set)
    for n, c in classes.items():
        if n != 'Main': pk[c['pkg']].add(n)
    res = {}
    for p, cs in pk.items():
        ce = {d for n in cs for d in deps[n] if d not in cs and d != 'Main'}
        ca = {n2 for n2 in classes if n2 not in cs and n2 != 'Main' for d in deps[n2] if d in cs}
        res[p] = (len(ca), len(ce), len(ce) / (len(ca) + len(ce)) if (ca or ce) else 0.0)
    return res

def report(task):
    classes = load_tree(task)
    deps = cbo(classes)
    print(f'\n### {task}\n')
    print('| Клас | Пакет | CBO | LCOM4 | Типи, з якими зв\'язаний |')
    print('|---|---|---|---|---|')
    for n in sorted(classes, key=lambda x: (classes[x]['pkg'], x)):
        print(f"| {n} | {classes[n]['pkg']} | {len(deps[n])} | {lcom_str(n, classes[n])} | {', '.join(sorted(deps[n])) or '—'} |")
    pub = sum(len(re.findall(r'^\s*public\s+(?!class|static|final|enum|interface|record)[\w<>\[\]]+\s+\w+\s*;', c['src'], flags=re.M)) for c in classes.values())
    print(f'\nПублічних мутабельних полів: **{pub}**')
    print('\n| Пакет | Ca | Ce | I |')
    print('|---|---|---|---|')
    for p, (ca, ce, i) in sorted(instability(classes, deps).items()):
        print(f'| {p} | {ca} | {ce} | {i:.2f} |')

def legacy():
    raw = (ROOT/'docs/metrics/LegacyVariant3.java.txt').read_text(encoding='utf-8')
    src = strip(raw)
    names = set(top_level_types(src))
    i = src.index('class LegacyFitnessService')
    svc = src[i:]
    deps = {m for m in names if m != 'LegacyFitnessService' and re.search(r'\b%s\b' % m, svc)}
    print('\n### Legacy (variant3, один файл)\n')
    print('| Клас | CBO | Зв\'язаний з |')
    print('|---|---|---|')
    for n in sorted(names):
        part = src[src.index('class ' + n):]
        if n != 'LegacyFitnessService':
            part = part[:match_brace(part, part.index('{')) + 1]
        d = {m for m in names if m != n and re.search(r'\b%s\b' % m, part)}
        print(f"| {n} | {len(d)} | {', '.join(sorted(d)) or '—'} |")
    foreign = re.findall(r'\b(?:member|plan|sub)(?:\.\w+)+', svc)
    accesses = sum(m.count('.') for m in foreign)
    chains = [m for m in foreign if m.count('.') >= 2]
    ifs = len(re.findall(r'\bif\b', svc))
    print(f'\n- Прямих звернень до полів чужих класів у LegacyFitnessService: **{accesses}**')
    print(f'- Ланцюгів доступу глибини ≥2 (порушення Закону Деметри): **{len(chains)}** — {sorted(set(chains))}')
    print(f'- Цикломатична складність єдиного методу (if/else if): **{1 + ifs}**')
    pub = re.findall(r'^\s*public\s+(?!class|static|final|enum|interface|record)[\w<>\[\]]+\s+\w+\s*;', src, flags=re.M)
    print(f'- Публічних мутабельних полів у класах Legacy: **{len(pub)}**')
    v, ms = lcom4('LegacyFitnessService', svc)
    print(f'- LCOM4(LegacyFitnessService) = **{v}** (методи: {ms})')

if __name__ == '__main__':
    legacy()
    for t in ('Task3', 'Task4'):
        report(t)
