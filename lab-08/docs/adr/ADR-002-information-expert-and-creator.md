# ADR-002: Перенесення розрахунків у домен та контрольоване створення агрегату

## Статус
Прийнято (Accepted)

## Контекст
У Legacy-коді сервіс читав і записував поля `Member`, `MembershipPlan`, `Subscription` напряму
(43 звернення), а `Subscription` створювався порожнім і заповнювався покроково ззовні — об'єкт довго
перебував у невалідному стані.

## Рішення
**Information Expert**
* `MembershipPlan.calculatePrice()` — план знає ставку, термін і тип, тому сам рахує ціну пакету.
* `Subscription` сам підсумовує вартість плану й послуг, рахує знижку та до сплати (`baseAmount`, `discountAmount`, `finalCost`).
* `Member` знає про наявність дисконтної картки (спочатку `discountRate()`, у ADR-004 — через `DiscountPolicy`).
* Усі поля `private final`; колекція послуг — `List.copyOf(...)` (імутабельна).

**Creator**
* `Subscription` — агрегат, що **містить** `AdditionalService` і **використовує** `Member`/`MembershipPlan`,
  тому лише він створюється через статичну фабрику `Subscription.create(...)`.
  Конструктор приватний, `null`-аргументи відхиляються, статус одразу `ACTIVE`.
* `MembershipPlan.of(...)` перевіряє, що ставка й термін додатні.

**Закон Деметри:** замість `sub.getMember().getName()` — `sub.memberName()`, `sub.planCode()`, `sub.memberPhone()`.

### QOC
* **Question:** Хто відповідає за підрахунок підсумкової вартості абонемента?
* **Options:** A — `LegacyFitnessService`; B — `Subscription`; C — окремий `PricingService`, якому передають `Subscription`.
* **Criteria:** Information Expert (дані про план, послуги, учасника є саме в `Subscription`); Low Coupling (сервіс не мусить знати структуру полів); інваріанти (`finalCost = baseAmount − discountAmount` має бути завжди істинним).
* **Conclusion:** Option B.

## Наслідки
* **Позитивні:** інваріант фінансових полів гарантований конструктором; поля не можна змінити ззовні; домен тестується без консолі.
* **Негативні / компроміси:** `Subscription` залежить від `Member`, `MembershipPlan`, `AdditionalService` (CBO = 3–4);
  грошові суми — `double` (для навчальної роботи прийнятно; у продакшні — `BigDecimal` / `long` у копійках).
