# Завдання 6. Крос-рев'ю та підготовка до захисту

> Крос-рев'ю — командна робота: коментарі рецензента мають залишитися в Git (Pull Request / Review comments).
> Нижче — готовий чек-лист і попередні знахідки, які можна використати як стартову точку.

## 1. Чек-лист peer review

| № | Перевірка | Де дивитися | Результат |
|---|---|---|---|
| 1 | У сутностях немає `public` мутабельних полів і сеттерів | `domain/*` | ☐ |
| 2 | `Subscription` створюється лише через `create(...)` | `Subscription`, `SubscriptionFactory` | ☐ |
| 3 | Колекції не «течуть» (`List.copyOf`) | `Subscription` | ☐ |
| 4 | У `ui` немає імпортів з `domain` і немає обчислень | `ConsoleUI`, `ContractFormatter` | ☐ |
| 5 | У `SubscriptionController` немає `Scanner`/`System.out` | `SubscriptionController` | ☐ |
| 6 | Контролер залежить лише від інтерфейсів та DTO | `SubscriptionController` | ☐ |
| 7 | Немає `if/switch` за типом пакету (єдине `equalsIgnoreCase` — розбір тексту в `PlanType.parse`) | `grep -R "switch\|equalsIgnoreCase" Task4/src` | ☐ |
| 8 | Нова політика додається без правки існуючих класів | `PlanType`, `PricingPolicy` | ☐ |
| 9 | Немає ланцюгів `a.getB().getC()` | `grep -R "get.*()\.get" Task4/src` | ☐ |
| 10 | ADR відповідають коду (посилання на реальні класи) | `docs/adr/*` | ☐ |
| 11 | Метрики відтворюються скриптом | `python3 docs/metrics/compute_metrics.py` | ☐ |
| 12 | Проєкт збирається та проходить `SmokeTest` | див. README | ☐ |

## 2. Попередні знахідки (self-review)

| Серйозність | Знахідка | Рекомендація |
|---|---|---|
| Середня | Гроші зберігаються як `double` | `BigDecimal` або `long` (копійки) |
| Середня | Сповіщення — побічний ефект `save()` у `NotifyingSubscriptionRepository` | Перейти на доменні події, якщо з'являться інші сценарії (ADR-003) |
| Низька | `SubscriptionFactory` має CBO = 6 | Розділити за потреби (п. 5 у `05-metrics.md`) |
| Низька | `Subscription.Status.DRAFT` не використовується | Видалити або додати метод `activate()` |
| Низька | ID абонементів генерує фабрика (`AtomicInteger`) | У реальній БД ID видає сховище |
| Низька | Тести — `SmokeTest` без JUnit | Додати JUnit 5, коли з'явиться доступ до Maven/Gradle |
| Інфо | Зміни поведінки відносно Legacy (невідомий пакет → помилка, запит про тренера, порядок логів) | Описано в ADR-003 |

## 3. Підготовка до захисту: 9 патернів GRASP

| Патерн | Де в коді | Аргумент «чому» | Альтернатива, яку відхилили |
|---|---|---|---|
| **Information Expert** | `MembershipPlan.calculatePrice()`, `Subscription` (суми) | Дані про ставку/послуги/учасника — всередині цих класів | `PricingService`, що читає чужі поля (ADR-002) |
| **Creator** | `Subscription.create(...)`, `MembershipPlan.of(...)` | Агрегат містить послуги й використовує учасника/план ⇒ він і створює себе, одразу валідним | Публічний конструктор + сеттери |
| **Controller** | `SubscriptionController` | Перший об'єкт за UI: DTO → агрегат → збереження → DTO, без I/O | Логіка в `ConsoleUI` |
| **Low Coupling** | CBO ≤ 4, Instability 1 → 0.38 → 0 | Залежності лише від інтерфейсів; домен ні від чого не залежить | Прямі виклики `InMemory*` з контролера |
| **High Cohesion** | LCOM4 = 1; 1 відповідальність на клас | Було 9 відповідальностей у одному методі | Один «сервіс-комбайн» |
| **Pure Fabrication** | `ConsoleCommandParser`, `ContractFormatter`, `SubscriptionFactory`, `InMemory*Repository` | Технічні класи, яких немає в домені, звільняють сутності від I/O | Форматування й парсинг у `Subscription` |
| **Indirection** | `SubscriptionRepository`, `PlanCatalog`, `ClientNotifier`, `NotifyingSubscriptionRepository` | Проміжний інтерфейс розриває прямий зв'язок контролера із сховищем та каналом | Контролер напряму пише в БД і шле push |
| **Polymorphism** | `PricingPolicy` + `DayPricing/FullPricing/VipPricing`; `PlanType.priceFor` | Поведінка за типом — у самому типі; без `if/switch` | `switch` у `MembershipPlan` (Task3) |
| **Protected Variations** | `PricingPolicy`, `DiscountPolicy`, порти репозиторіїв і нотифікатора | Нестабільне (тарифи, знижки, сховище, канал) сховане за стабільним контрактом | Хардкод значень у ядрі |

### Типові запитання викладача
1. **«Чому CBO контролера = 4, а в Legacy було 3 — де покращення?»** — див. п. 3 і 5 у `05-metrics.md`: 43 → 0 прямих звернень до чужих полів, 9 → 1 відповідальність, Instability 0 для домену.
2. **«Чому LCOM4 Legacy = 1?»** — один метод; метрика тут хибнопозитивна, тому додано відповідальності й цикломатичну складність.
3. **«Чому Decorator для сповіщення?»** — щоб контролер мав ≤ 4 залежності й не знав про канал; компроміс і альтернатива (доменні події) — в ADR-003.
4. **«Як додати пакет Student?»** — константа `STUDENT` у `PlanType` + клас `StudentPricing`; без змін у `MembershipPlan`.
5. **«Як замінити push на SMS?»** — новий клас `SmsNotifier implements ClientNotifier`, змінити одну стрічку в `Main`.
6. **«Закон Деметри?»** — `Subscription.memberName()` замість `getMember().getName()`; DTO-`record` вільно читаються.
7. **«Чому `double` для грошей?»** — навчальне спрощення; у продакшні `BigDecimal`/копійки (знахідка №1).
