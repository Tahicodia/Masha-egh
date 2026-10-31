package variant3_financialtransactionprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Завдання 5 (DIP), варіант 3: еквайринг та фінансовий процесінг.
//
// Високорівневий FinancialTransactionProcessor залежить від абстракцій
// (PaymentGateway, TransactionLedger, PaymentNotifier), а не від Stripe/SQL Server/Twilio.
// Залежності передаються через конструктор. Збірка - у Composition Root (main).
public class Task5_DIP_FinancialTransactionProcessor {

    public static class PaymentRequest {
        private String transactionId;
        private String cardNumber;
        private double amount;
        private String payerPhone;

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
        public String getPayerPhone() { return payerPhone; }
        public void setPayerPhone(String payerPhone) { this.payerPhone = payerPhone; }
    }

    // ---- Абстракції (належать високорівневому модулю) ----

    public interface PaymentGateway {
        boolean executeCharge(String cardNumber, double amount);
    }

    public interface TransactionLedger {
        void recordTransaction(String transactionId, double amount, String status);
    }

    public interface PaymentNotifier {
        void sendReceipt(String phone, String transactionId, double amount);
    }

    // ---- Продакшн-реалізації (низький рівень) ----

    public static class StripePaymentGatewayClient implements PaymentGateway {
        @Override
        public boolean executeCharge(String cardNumber, double amount) {
            System.out.println("[Stripe API] Виконано списання " + amount + " UAH з картки " + cardNumber + ".");
            return true;
        }
    }

    public static class SqlServerTransactionLedger implements TransactionLedger {
        @Override
        public void recordTransaction(String transactionId, double amount, String status) {
            System.out.println("[SQL Server Ledger] Записано транзакцію " + transactionId
                    + ": сума=" + amount + ", статус=" + status + ".");
        }
    }

    public static class TwilioSmsNotifier implements PaymentNotifier {
        @Override
        public void sendReceipt(String phone, String transactionId, double amount) {
            System.out.println("[Twilio SMS] Повідомлення на " + phone + ": Платіж "
                    + transactionId + " на суму " + amount + " UAH успішно виконано.");
        }
    }

    // ---- Тестові/мок-реалізації для демонстрації підміни ----

    public static class MockPaymentGateway implements PaymentGateway {
        private final boolean result;

        public MockPaymentGateway(boolean result) { this.result = result; }

        @Override public boolean executeCharge(String cardNumber, double amount) { return result; }
    }

    public static class InMemoryTransactionLedger implements TransactionLedger {
        public final List<String> entries = new ArrayList<>();

        @Override
        public void recordTransaction(String transactionId, double amount, String status) {
            entries.add(transactionId + ":" + amount + ":" + status);
        }
    }

    public static class RecordingNotifier implements PaymentNotifier {
        public final List<String> sent = new ArrayList<>();

        @Override
        public void sendReceipt(String phone, String transactionId, double amount) {
            sent.add(phone + ":" + transactionId);
        }
    }

    // ---- Високорівнева бізнес-логіка ----

    public static class FinancialTransactionProcessor {
        private final PaymentGateway gateway;
        private final TransactionLedger ledger;
        private final PaymentNotifier notifier;

        public FinancialTransactionProcessor(PaymentGateway gateway, TransactionLedger ledger, PaymentNotifier notifier) {
            this.gateway = Objects.requireNonNull(gateway, "gateway");
            this.ledger = Objects.requireNonNull(ledger, "ledger");
            this.notifier = Objects.requireNonNull(notifier, "notifier");
        }

        public void processPayment(PaymentRequest request) {
            if (request == null || request.getAmount() <= 0) {
                throw new IllegalArgumentException("Сума транзакції повинна бути більшою за нуль.");
            }

            boolean success = gateway.executeCharge(request.getCardNumber(), request.getAmount());
            ledger.recordTransaction(request.getTransactionId(), request.getAmount(), success ? "SUCCESS" : "FAILED");

            // Зміна відносно оригіналу: квитанція "успішно виконано" лише при успішному списанні.
            if (success) {
                notifier.sendReceipt(request.getPayerPhone(), request.getTransactionId(), request.getAmount());
            }
        }
    }

    // ---- Composition Root ----

    public static void main(String[] args) {
        PaymentRequest request = new PaymentRequest();
        request.setTransactionId("TX-1001");
        request.setCardNumber("4111111111111111");
        request.setAmount(250.0);
        request.setPayerPhone("+380501234567");

        // 1) Production-збірка
        FinancialTransactionProcessor production = new FinancialTransactionProcessor(
                new StripePaymentGatewayClient(), new SqlServerTransactionLedger(), new TwilioSmsNotifier());
        production.processPayment(request);

        // 2) Тестова збірка: підміна реалізацій без змін у процесорі
        InMemoryTransactionLedger ledger = new InMemoryTransactionLedger();
        RecordingNotifier notifier = new RecordingNotifier();
        FinancialTransactionProcessor test = new FinancialTransactionProcessor(
                new MockPaymentGateway(false), ledger, notifier);
        test.processPayment(request);

        System.out.println("\n[Mock] Ledger: " + ledger.entries);
        System.out.println("[Mock] Надіслані квитанції: " + notifier.sent);
    }
}
