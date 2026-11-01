# ADR-003: Тонкий CLI, Use-Case Controller, Indirection та Pure Fabrication для реєстрації абонементу

## Статус
Прийнято (Accepted) — 2026-11-01

## Контекст
Варіант 3 — «Мережа фітнес-клубів та продаж клубних карт». У вихідному коді весь сценарій
«оформити абонемент» живе в одному методі `LegacyFitnessService.RegisterSubscriptionConsole()`
(~60 рядків). Один метод одночасно:

1. читає введення (`Console.ReadLine()` / `Scanner`);
2. вручну збирає `Member`, `MembershipPlan`, `Subscription` (порожні об'єкти + public setters);
3. рахує ціну (`if/else` за кодом тарифу), знижку та підсумок;
4. формує текст договору (рядкова конкатенація);
5. «пише в БД» та «шле push» (прямі `Console.WriteLine`);
6. друкує результат.

Це **Bloated Controller / Monolithic Main**: UI, оркестрація, бізнес-правила й інфраструктура
змішані; жоден фрагмент не можна протестувати без консолі; будь-яка зміна (новий канал сповіщень,
інше сховище) вимагає правки цього ж методу.

## QOC-аналіз

### Q1. Хто відповідає за координацію сценарію «зареєструвати абонемент»?
**Options**
* **A.** Залишити в CLI-класі, лише розбити на приватні методи.
* **B.** Один «сервіс» `FitnessService` із усіма залежностями (репозиторій, нотифікатор, форматер, …).
* **C.** Тонкий CLI + окремий Use-Case Controller, що координує доменні об'єкти й порти.

**Criteria**
* K1 (Controller): перший об'єкт за межами UI не повинен містити UI-операцій.
* K2 (High Cohesion): клас має одну причину для змін.
* K3 (Low Coupling): CBO контролера ≤ 4.
* K4: сценарій тестується без консолі.

| | K1 | K2 | K3 | K4 |
|---|---|---|---|---|
| A | ✗ | ✗ | ✗ | ✗ |
| B | ✓ | ~ | ✗ (накопичує всі залежності) | ✓ |
| C | ✓ | ✓ | ✓ | ✓ |

**Conclusion:** обрано **C**. `ConsoleUi` лише читає/друкує; `SubscriptionController.register()`
не містить `Console.*`, не обчислює гроші, лише: створює чернетку → `activate()` → сповіщає слухачів.

### Q2. Як утримати CBO контролера ≤ 4, коли після активації потрібно кілька побічних ефектів (БД, push, надалі email/аудит)?
**Options**
* **A.** Контролер напряму залежить від `SubscriptionRepository` і `NotificationSender`.
* **B.** Один абстрактний контракт `ActivationListener`; контролер ітерує список слухачів.
* **C.** Повноцінна шина доменних подій.

**Criteria:** K3 (CBO), K5 (Open/Closed: новий ефект без зміни контролера), K6 (простота — немає надлишкової інфраструктури).

* A: CBO контролера = 5 (Command, Factory, Subscription, Repository, Notifier), новий ефект = правка контролера. ✗ K3, ✗ K5.
* B: CBO = 4 (`RegisterSubscriptionCommand`, `SubscriptionDraftFactory`, `Subscription`, `ActivationListener`). ✓ K3, ✓ K5, ✓ K6.
* C: ✓ K3, ✓ K5, ✗ K6 (шина, підписки, реєстрація — надмірно для лабораторної).

**Conclusion:** обрано **B** — **Indirection**: між контролером та конкретними ефектами стоїть один інтерфейс.

### Q3. Куди винести технічні обов'язки, яких немає в предметній області?
**Options**
* **A.** Додати їх у сутності (`Subscription.toContractText()`, `Subscription.saveToDb()`).
* **B.** Створити штучні (Pure Fabrication) класи під кожну технічну роль.

**Criteria:** K2 (High Cohesion сутностей, LCOM4 = 1), K7 (змінність: формат договору/сховище змінюються незалежно від домену).

**Conclusion:** обрано **B**. Сутності залишаються «чистим» доменом.

| Pure Fabrication | Обов'язок |
|---|---|
| `ConsoleCommandParser` | сирий текст → `RegisterSubscriptionCommand` (без бізнес-правил) |
| `SubscriptionDraftFactory` | завантажити `Member` та `MembershipPlan`, видати id, поставити дату, створити `Subscription.draft(...)` |
| `ContractFormatter` | формування тексту договору |
| `PersistSubscriptionListener` | «зберегти після активації» |
| `SubscriptionQueryService` | читання готового договору за id |
| `InMemory*Repository`, `PushNotificationListener` | заглушки інфраструктури |

### Q4. Що повертає команда `register`?
**Options:** A) `Subscription` (CLI залежить від домену); B) окремий DTO-результат; C) лише `id` + окремий query-сервіс (CQS).

**Conclusion:** обрано **C**. CLI не бачить доменних типів (Indirection), а CBO контролера не росте на ще один тип результату.

## Рішення
Структура шарів (залежності спрямовані до стабільного ядра):

```
cli  ──►  application  ──►  domain
              ▲
infrastructure ┘ (реалізує порти application)
```

* `cli`: `ConsoleUi`, `ConsoleCommandParser`, `Main` (Composition Root).
* `application`: `SubscriptionController`, `SubscriptionDraftFactory`, `SubscriptionQueryService`,
  `ContractFormatter`, порти (`MemberRepository`, `MembershipPlanCatalog`, `SubscriptionRepository`,
  `ActivationListener`), `RegisterSubscriptionCommand`.
* `infrastructure`: `InMemory*`, `PushNotificationListener`.
* `domain`: `Member`, `MembershipPlan`, `Subscription`, політики (див. ADR-004).

## Наслідки
**Позитивні**
* `ConsoleUi` та `SubscriptionController` мають CBO = 4 (ціль ≤ 4); у контролері немає жодного `Console.*`.
* Бізнес-сценарій тестується без консолі (див. `SmokeTests`).
* Новий побічний ефект (email, аудит) = новий `ActivationListener`, контролер не змінюється.

**Негативні / компроміси**
* Кількість класів зросла (більше файлів, складніший Composition Root).
* `SubscriptionDraftFactory` має CBO = 7: вона зібрала в собі «брудну» координацію, що було в контролері.
  Це свідомий компроміс — норматив CBO ≤ 4 стосується доменних сутностей і контролерів, а фабрика —
  Pure Fabrication, єдина відповідальність якої — збірка чернетки.
* Порядок виводу змінився: рядки `[База даних]` і `[Push Notification]` друкуються інфраструктурою
  до того, як CLI надрукує договір (у legacy-коді договір ішов першим).
* Побічні ефекти виконуються синхронно й послідовно; обробка збою одного слухача не реалізована.
