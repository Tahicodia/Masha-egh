package variant3_fitnessmembership.application;

/** DTO-запит від CLI до контролера. Не містить доменних типів. */
public record RegisterSubscriptionCommand(String memberName, String planCode, boolean personalTrainer) { }
