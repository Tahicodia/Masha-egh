package packaging;

public class ColdPackagingFactory implements PackagingFactory {
    private static final String KIT = "Холодове";

    @Override public Box createBox() { return new ColdBox(); }
    @Override public Filler createFiller() { return new ColdFiller(); }
    @Override public Label createLabel() { return new ColdLabel(); }
    @Override public String kitName() { return KIT; }

    private static class ColdBox implements Box {
        public String kit() { return KIT; }
        public String describe() { return "термобокс"; }
    }
    private static class ColdFiller implements Filler {
        public String kit() { return KIT; }
        public String describe() { return "гелеві холодоелементи"; }
    }
    private static class ColdLabel implements Label {
        public String kit() { return KIT; }
        public String describe() { return "етикетка «Зберігати при +2…+8 °C»"; }
    }
}