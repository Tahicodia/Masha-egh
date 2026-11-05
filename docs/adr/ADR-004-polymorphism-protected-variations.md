# ADR-004: Поліморфні політики тарифів і знижок, захист від змін (Polymorphism + Protected Variations)

## Статус
Прийнято (Accepted) — 2026-11-01

## Контекст
У legacy-коді ціна абонементу залежить від рядкового коду тарифу (`"Day"`, `"Full"`, `"VIP"`)
та від булевого прапорця дисконтної картки:

```text
if (plan.Code == "VIP")      rawCost += 5000;     // шафка та СПА
else if (plan.Code == "Day") rawCost *= 0.8;      // денне відвідування
if (personalTrainerIncluded) rawCost += 3000;
if (member.HasDiscountCard)  discount = base * 0.10;
```

Проблеми:
* кожен новий тариф або тип знижки = правка каскаду `if/else` у сервісі (порушення Open/Closed);
* «магічні» рядки й числа розкидані по коду;
* невідомий код тарифу (`"Platinum"`) мовчки обчислюється як `Full` — прихована помилка;
* точки нестабільності (формули цін, знижки, сховище, канали сповіщень) жорстко зашиті в ядро.

## QOC-аналіз

### Q1. Як розв'язати залежність ціни від типу тарифу?
**Options**
* **A.** Залишити `if/else`, але перенести в `MembershipPlan.calculatePrice()`.
* **B.** `switch` / `enum` + метод `price()` з `switch` усередині.
* **C.** Інтерфейс `PlanPricingPolicy` + по класу на тариф + реєстр (dictionary dispatch).

**Criteria:** K1 (Open/Closed — новий тариф без правки існуючого коду), K2 (немає каскадів умов),
K3 (кожен тариф тестується окремо), K4 (простота).

| | K1 | K2 | K3 | K4 |
|---|---|---|---|---|
| A | ✗ | ✗ | ~ | ✓ |
| B | ✗ | ✗ | ~ | ✓ |
| C | ✓ | ✓ | ✓ | ~ (більше класів) |

**Conclusion:** обрано **C**. `FullPlanPricing`, `DayPlanPricing`, `VipPlanPricing` реалізують
`PlanPricingPolicy`; `PlanPricingPolicyRegistry` перетворює код на політику через словник
(без `switch`). `MembershipPlan` лишається **Information Expert**: сам рахує
`policy.adjust(monthlyRate × months)`.

### Q2. Як змоделювати знижку за дисконтною карткою?
**Options:** A) `bool HasDiscountCard` + `if` у `Subscription`; B) `DiscountPolicy` у `Member`.

**Conclusion:** **B**. `Member` володіє `DiscountPolicy` (`CardDiscountPolicy` — 10 %, `NoDiscountPolicy` — 0).
Метод `member.discountFor(base)` — Information Expert. `Subscription.activate()` не знає,
**чому** знижка така, лише питає у `Member`. Нова акція (наприклад, студентська) = новий клас-політика.

### Q3. Що саме треба захистити інтерфейсами (Protected Variations)?
| Точка нестабільності | Стабільний контракт | Реалізації зараз |
|---|---|---|
| Формула ціни за тарифом | `PlanPricingPolicy` | Full / Day / VIP |
| Алгоритм знижки | `DiscountPolicy` | Card / None |
| Сховище абонементів | `SubscriptionRepository` | `InMemorySubscriptionRepository` |
| Сховище клієнтів | `MemberRepository` | `InMemoryMemberRepository` |
| Джерело тарифів | `MembershipPlanCatalog` | `InMemoryMembershipPlanCatalog` |
| Побічні ефекти активації (БД, push) | `ActivationListener` | `PersistSubscriptionListener`, `PushNotificationListener` |

Ядро (`domain`) не імпортує нічого з `application`/`infrastructure`/`cli`.

## Рішення
* Усі умовні каскади за кодом тарифу та за карткою видалені з коду; залишилась одна точка
  вибору — `PlanPricingPolicyRegistry.forCode()` (словник).
* Додавання тарифу «Student»: створити `StudentPlanPricing` і додати його в Composition Root
  (`Main`). Жоден існуючий клас не змінюється.
* Додавання каналу сповіщень: новий `ActivationListener` у списку слухачів.

## Наслідки
**Позитивні**
* Open/Closed для тарифів, знижок, сховищ і сповіщень.
* Кожна політика має 1 метод і тестується ізольовано; математика ціни збігається з legacy
  (VIP+тренер+картка: 22 400 → знижка 2 240 → до сплати 20 160).
* Прихований дефект виправлено: невідомий код тарифу тепер дає зрозумілу помилку
  («Невідомий тип абонемента 'Platinum'. Доступні: Day/Full/VIP»).

**Негативні / змінена поведінка (свідомо)**
* Невідомий тариф більше не обраховується як `Full`, а відхиляється.
* Регістр коду нормалізований: `vip`, `VIP`, `Vip` — один тариф (у частині legacy порівняння було
  регістрозалежним, у Java — ні; тепер однаково).
* Гроші: `double` → `BigDecimal` (немає похибок округлення); у договорі суми
  показуються з двома знаками (`22400.00`).
* CLI тепер питає про персонального тренера (у legacy він був завжди `true`).
* Реєстр додає ще один клас і потребує реєстрації нових політик у Composition Root.
