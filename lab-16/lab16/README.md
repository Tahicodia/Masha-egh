# ЛР №16. Поведінкові шаблони (Memento, Visitor, Iterator) — Варіант 3 «Посилка+»

| Завдання | Ситуація | Шаблон | Пакет |
|---|---|---|---|
| 1 | Відновлення стану сортувальника | Memento | `ua.parcelplus.memento` |
| 2 | Обхід відправлень у машині | Iterator | `ua.parcelplus.iterator` |
| 3 | Операції над вантажем | Visitor | `ua.parcelplus.visitor` |

Потрібна Java 17+ (record, pattern matching для instanceof).

## Збірка і запуск
```bash
mkdir out
javac -encoding UTF-8 -d out $(find src -name "*.java")      # Windows PowerShell: див. нижче
java -Dfile.encoding=UTF-8 -cp out ua.parcelplus.Main
```
PowerShell: `javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName`

Діаграми: `docs/diagrams/*.puml` (PlantUML). Приклад журналу: `docs/console-log.txt`.
Експорт у PNG: https://www.plantuml.com/plantuml або розширення PlantUML у VS Code / IntelliJ.
