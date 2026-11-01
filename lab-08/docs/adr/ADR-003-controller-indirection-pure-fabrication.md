# ADR-003: Тонкий CLI, Use-Case Controller, інтерфейси-посередники та штучні класи

## Статус
Прийнято (Accepted)

## Контекст
`LegacyFitnessService` одночасно читав `Scanner`, рахував, друкував договір і «писав у БД/push» (Bloated Controller).

## Рішення
Чотири шари (пакети): `ui → application → domain ← infrastructure`.

| Патерн | Реалізація |
|---|---|
| **Thin Console UI** | `ConsoleUI` — лише `Scanner`/`PrintStream`: питає, делегує, друкує. Жодних обчислень, жодних доменних типів. |
| **Controller** | `SubscriptionController.register(RegisterSubscriptionCommand) → SubscriptionReceipt` — перший об'єкт за межами UI; приймає DTO, створює й зберігає агрегат, повертає DTO. Без вводу/виводу. |
| **Indirection** | Порти `SubscriptionRepository`, `MemberRepository`, `PlanCatalog`, `ClientNotifier`. Контролер знає лише інтерфейси. `NotifyingSubscriptionRepository` — Decorator: зберігає й сповіщає, контролер про сповіщення не знає. |
| **Pure Fabrication** | `ConsoleCommandParser`, `ContractFormatter`, `SubscriptionFactory`, `InMemory*Repository`, `ConsolePushNotifier`, `StandardPlanCatalog` — класів немає в бізнес-домені, вони існують, щоб сутності лишалися згуртованими. |
| **DTO** | `RegisterSubscriptionCommand`, `SubscriptionReceipt` — `record`-и (імутабельні, Закон Деметри не порушують). |

### QOC
* **Question:** Хто має знати, що після збереження абонемента треба надіслати сповіщення?
* **Options:** A — контролер викликає `notifier` явно; B — `Subscription` сам сповіщає; C — Decorator над репозиторієм; D — доменні події.
* **Criteria:** Low Coupling контролера (≤ 4); чистота домену (без I/O); простота.
  A: контролер матиме 5 залежностей ✗; B: домен із I/O ✗✗; C: ✔ (CBO контролера = 4); D: ✔✔, але надмірно для 1 сценарію.
* **Conclusion:** Option C. Для зростання системи (кілька сценаріїв) — перейти на D.

## Наслідки
* **Позитивні:** CLI, контролер і домен замінюються незалежно; CBO(`SubscriptionController`) = 4, CBO(`ConsoleUI`) = 3; домен має Instability = 0.
* **Негативні / компроміси:** сповіщення як побічний ефект `save()` — неочевидний зв'язок (описано тут); `SubscriptionFactory` має CBO = 5–6 (Creator-збирач, виходить за норматив ≤ 4, який стосується сутностей і контролерів);
  порядок рядків у консолі змінився: логи БД/push друкуються **до** тексту договору.
* **Зміна поведінки відносно Legacy:** невідомий тип пакету — помилка замість мовчазного `Full`; персональний тренер запитується в користувача (у Legacy було жорстко `true`).
