package variant3_multifunctionprinter;

import java.util.Objects;

// Завдання 4 (ISP), варіант 3: офісна периферійна техніка.
//
// Ролі клієнтів: той, хто друкує; той, хто друкує двосторонньо; той, хто сканує;
// той, хто надсилає факси; той, хто зшиває. Кожна роль - окремий інтерфейс.
// Жодного UnsupportedOperationException: пристрій реалізує лише ті ролі, які реально вміє.
public class Task4_ISP_MultiFunctionPrinter {

    public interface PrintingDevice {
        void printDocument(String doc);
    }

    public interface DuplexPrintingDevice {
        void duplexPrint(String doc);
    }

    public interface ScanningDevice {
        void scanDocument();
    }

    public interface FaxingDevice {
        void sendFax(String number, String doc);
    }

    public interface StaplingDevice {
        void staplePages(int count);
    }

    public static class BasicLaserPrinter implements PrintingDevice, DuplexPrintingDevice {
        @Override public void printDocument(String doc) { System.out.println("Printing: " + doc); }
        @Override public void duplexPrint(String doc) { System.out.println("Duplex printing: " + doc); }
    }

    public static class DocumentScanner implements ScanningDevice {
        @Override public void scanDocument() { System.out.println("Scanning physical document..."); }
    }

    /** Справжній «комбайн» реалізує всі ролі свідомо, а не з примусу. */
    public static class OfficeMultiFunctionCenter
            implements PrintingDevice, DuplexPrintingDevice, ScanningDevice, FaxingDevice, StaplingDevice {
        @Override public void printDocument(String doc) { System.out.println("MFC printing: " + doc); }
        @Override public void duplexPrint(String doc) { System.out.println("MFC duplex printing: " + doc); }
        @Override public void scanDocument() { System.out.println("MFC scanning..."); }
        @Override public void sendFax(String number, String doc) { System.out.println("MFC fax to " + number + ": " + doc); }
        @Override public void staplePages(int count) { System.out.println("MFC stapling " + count + " pages"); }
    }

    // ---- Клієнти залежать лише від ролей, які викликають ----

    public static class PrintJobClient {
        private final PrintingDevice printer;

        public PrintJobClient(PrintingDevice printer) {
            this.printer = Objects.requireNonNull(printer, "printer");
        }

        public void printReport(String report) { printer.printDocument(report); }
    }

    public static class ArchiveClient {
        private final ScanningDevice scanner;

        public ArchiveClient(ScanningDevice scanner) {
            this.scanner = Objects.requireNonNull(scanner, "scanner");
        }

        public void digitize() { scanner.scanDocument(); }
    }

    public static class BookletClient {
        private final DuplexPrintingDevice duplexPrinter;
        private final StaplingDevice stapler;

        public BookletClient(DuplexPrintingDevice duplexPrinter, StaplingDevice stapler) {
            this.duplexPrinter = Objects.requireNonNull(duplexPrinter, "duplexPrinter");
            this.stapler = Objects.requireNonNull(stapler, "stapler");
        }

        public void makeBooklet(String doc, int pages) {
            duplexPrinter.duplexPrint(doc);
            stapler.staplePages(pages);
        }
    }

    public static void main(String[] args) {
        BasicLaserPrinter laser = new BasicLaserPrinter();
        DocumentScanner scanner = new DocumentScanner();
        OfficeMultiFunctionCenter mfc = new OfficeMultiFunctionCenter();

        new PrintJobClient(laser).printReport("Квартальний звіт");
        new ArchiveClient(scanner).digitize();
        new BookletClient(mfc, mfc).makeBooklet("Брошура", 12);

        // new PrintJobClient(scanner); // не скомпілюється: сканер не вміє друкувати
    }
}
