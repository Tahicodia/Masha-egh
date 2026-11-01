# ADR-004: Поліморфні політики ціни та знижки, ізоляція нестабільних точок

## Статус
Прийнято (Accepted)

## Контекст
Розрахунок ціни містив каскад за типом пакету:
```java
if ("VIP".equalsIgnoreCase(code)) rawCost += 5000.0;
else if ("Day".equalsIgnoreCase(code)) rawCost *= 0.8;
```
У Task3 він перетворився на `switch (type)` усередині `MembershipPlan` — формально краще (enum замість рядка),
але додавання нового пакету (напр. `Student`) вимагає правки `MembershipPlan` (порушення Open/Closed).
Знижка була зашита в `Member` як константа 10%.

## Рішення
**Polymorphism**
* `PricingPolicy.price(double rawCost)` + реалізації `DayPricing` (×0.8), `FullPricing` (без змін), `VipPricing` (+5000).
* Кожна константа `PlanType` містить власну політику; `MembershipPlan.calculatePrice()` = `type.priceFor(rate × months)` — жодних `if/switch`.
* Розбір тексту користувача — `PlanType.parse(code)` (регістр не важливий, невідоме значення → `IllegalArgumentException`).

**Protected Variations** — нестабільні точки сховано за стабільними інтерфейсами:

| Нестабільна точка | Стабільний контракт | Поточна реалізація |
|---|---|---|
| Алгоритм ціни за пакетом | `PricingPolicy` | `DayPricing`, `FullPricing`, `VipPricing` |
| Алгоритм знижки | `DiscountPolicy` | `CardDiscountPolicy` (10% за карткою) |
| Сховище абонементів | `SubscriptionRepository` | `InMemorySubscriptionRepository` |
| Сховище клієнтів | `MemberRepository` | `InMemoryMemberRepository` |
| Канал сповіщення | `ClientNotifier` | `ConsolePushNotifier` (SMS/e-mail — нові класи) |
| Каталог тарифів | `PlanCatalog` | `StandardPlanCatalog` |

`DiscountPolicy` передається в `Subscription.create(...)` — домен не знає конкретного алгоритму.

### QOC
* **Question:** Як додати новий тип пакету, не змінюючи існуючий код розрахунку?
* **Options:** A — ще одна гілка в `switch`; B — `PricingPolicy` у кожної константи `PlanType`; C — окремий реєстр `Map<PlanType, PricingPolicy>` ззовні.
* **Criteria:** Open/Closed; локальність змін; ризик «забути» обробити новий тип.
  A: ✗ (правка в існуючому коді, компілятор не змусить розширити всі місця, якщо `default`); B: ✔ (новий тип без політики не скомпілюється); C: ✔, але політики відокремлені від типу, ризик розсинхрону.
* **Conclusion:** Option B.

## Наслідки
* **Позитивні:** новий пакет = нова константа + новий клас; `MembershipPlan` не змінюється; політики тестуються окремо.
* **Негативні / компроміси:** більше дрібних класів; `PlanType` (CBO = 4) знає всі політики — це «каталог» типів, при значному зростанні варто винести реєстр (Option C).
