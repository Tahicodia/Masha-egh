
### Legacy (variant3, один файл)

| Клас | CBO | Зв'язаний з |
|---|---|---|
| LegacyFitnessService | 3 | Member, MembershipPlan, Subscription |
| Member | 0 | — |
| MembershipPlan | 0 | — |
| Subscription | 2 | Member, MembershipPlan |

- Прямих звернень до полів чужих класів у LegacyFitnessService: **43**
- Ланцюгів доступу глибини ≥2 (порушення Закону Деметри): **8** — ['sub.member.hasDiscountCard', 'sub.member.name', 'sub.plan.baseMonthlyRate', 'sub.plan.code', 'sub.plan.monthsDuration']
- Цикломатична складність єдиного методу (if/else if): **5**
- Публічних мутабельних полів у класах Legacy: **17**
- LCOM4(LegacyFitnessService) = **1** (методи: ['registerSubscriptionConsole'])

### Task3

| Клас | Пакет | CBO | LCOM4 | Типи, з якими зв'язаний |
|---|---|---|---|---|
| ClientNotifier | application | 1 | — | Subscription |
| MemberRepository | application | 1 | — | Member |
| PlanCatalog | application | 1 | — | MembershipPlan |
| RegisterSubscriptionCommand | application | 0 | 1* | — |
| SubscriptionController | application | 4 | 1 | RegisterSubscriptionCommand, SubscriptionFactory, SubscriptionReceipt, SubscriptionRepository |
| SubscriptionFactory | application | 5 | 1 | AdditionalService, MemberRepository, PlanCatalog, RegisterSubscriptionCommand, Subscription |
| SubscriptionReceipt | application | 1 | 1* | Subscription |
| SubscriptionRepository | application | 1 | — | Subscription |
| AdditionalService | domain | 0 | 1* | — |
| Member | domain | 0 | 1 | — |
| MembershipPlan | domain | 1 | 1 | PlanType |
| PlanType | domain | 0 | 1* | — |
| Subscription | domain | 3 | 1* | AdditionalService, Member, MembershipPlan |
| ConsolePushNotifier | infrastructure | 2 | 1 | ClientNotifier, Subscription |
| InMemoryMemberRepository | infrastructure | 2 | 1 | Member, MemberRepository |
| InMemorySubscriptionRepository | infrastructure | 2 | 1 | Subscription, SubscriptionRepository |
| NotifyingSubscriptionRepository | infrastructure | 3 | 1 | ClientNotifier, Subscription, SubscriptionRepository |
| StandardPlanCatalog | infrastructure | 3 | 1 | MembershipPlan, PlanCatalog, PlanType |
| ConsoleCommandParser | ui | 1 | 1 | RegisterSubscriptionCommand |
| ConsoleUI | ui | 3 | 1 | ConsoleCommandParser, ContractFormatter, SubscriptionController |
| ContractFormatter | ui | 1 | 1 | SubscriptionReceipt |
| Main | variant3_fitnessmembership | 11 | 1* | ConsoleCommandParser, ConsolePushNotifier, ConsoleUI, ContractFormatter, InMemoryMemberRepository, InMemorySubscriptionRepository, NotifyingSubscriptionRepository, StandardPlanCatalog, SubscriptionController, SubscriptionFactory, SubscriptionRepository |

Публічних мутабельних полів: **0**

| Пакет | Ca | Ce | I |
|---|---|---|---|
| application | 8 | 4 | 0.33 |
| domain | 11 | 0 | 0.00 |
| infrastructure | 0 | 8 | 1.00 |
| ui | 0 | 3 | 1.00 |

### Task4

| Клас | Пакет | CBO | LCOM4 | Типи, з якими зв'язаний |
|---|---|---|---|---|
| ClientNotifier | application | 1 | — | Subscription |
| MemberRepository | application | 1 | — | Member |
| PlanCatalog | application | 1 | — | MembershipPlan |
| RegisterSubscriptionCommand | application | 0 | 1* | — |
| SubscriptionController | application | 4 | 1 | RegisterSubscriptionCommand, SubscriptionFactory, SubscriptionReceipt, SubscriptionRepository |
| SubscriptionFactory | application | 6 | 1 | AdditionalService, DiscountPolicy, MemberRepository, PlanCatalog, RegisterSubscriptionCommand, Subscription |
| SubscriptionReceipt | application | 1 | 1* | Subscription |
| SubscriptionRepository | application | 1 | — | Subscription |
| AdditionalService | domain | 0 | 1* | — |
| CardDiscountPolicy | domain | 2 | 1 | DiscountPolicy, Member |
| DayPricing | domain | 1 | 1 | PricingPolicy |
| DiscountPolicy | domain | 1 | — | Member |
| FullPricing | domain | 1 | 1* | PricingPolicy |
| Member | domain | 0 | 1* | — |
| MembershipPlan | domain | 1 | 1 | PlanType |
| PlanType | domain | 4 | 1 | DayPricing, FullPricing, PricingPolicy, VipPricing |
| PricingPolicy | domain | 0 | — | — |
| Subscription | domain | 4 | 1* | AdditionalService, DiscountPolicy, Member, MembershipPlan |
| VipPricing | domain | 1 | 1 | PricingPolicy |
| ConsolePushNotifier | infrastructure | 2 | 1 | ClientNotifier, Subscription |
| InMemoryMemberRepository | infrastructure | 2 | 1 | Member, MemberRepository |
| InMemorySubscriptionRepository | infrastructure | 2 | 1 | Subscription, SubscriptionRepository |
| NotifyingSubscriptionRepository | infrastructure | 3 | 1 | ClientNotifier, Subscription, SubscriptionRepository |
| StandardPlanCatalog | infrastructure | 3 | 1 | MembershipPlan, PlanCatalog, PlanType |
| ConsoleCommandParser | ui | 1 | 1 | RegisterSubscriptionCommand |
| ConsoleUI | ui | 3 | 1 | ConsoleCommandParser, ContractFormatter, SubscriptionController |
| ContractFormatter | ui | 1 | 1 | SubscriptionReceipt |
| Main | variant3_fitnessmembership | 12 | 1* | CardDiscountPolicy, ConsoleCommandParser, ConsolePushNotifier, ConsoleUI, ContractFormatter, InMemoryMemberRepository, InMemorySubscriptionRepository, NotifyingSubscriptionRepository, StandardPlanCatalog, SubscriptionController, SubscriptionFactory, SubscriptionRepository |

Публічних мутабельних полів: **0**

| Пакет | Ca | Ce | I |
|---|---|---|---|
| application | 8 | 5 | 0.38 |
| domain | 11 | 0 | 0.00 |
| infrastructure | 0 | 8 | 1.00 |
| ui | 0 | 3 | 1.00 |
