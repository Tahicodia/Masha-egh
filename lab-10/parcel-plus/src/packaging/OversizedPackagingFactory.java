package packaging;

public class OversizedPackagingFactory implements PackagingFactory {
    private static final String KIT = "Негабаритне";

    @Override public Box createBox() { return new OversizedBox(); }
    @Override public Filler createFiller() { return new OversizedFiller(); }
    @Override public Label createLabel() { return new OversizedLabel(); }
    @Override public String kitName() { return KIT; }

    private static class OversizedBox implements Box {
        public String kit() { return KIT; }
        public String describe() { return "дерев'яний ящик"; }
    }
    private static class OversizedFiller implements Filler {
        public String kit() { return KIT; }
        public String describe() { return "піноплаcт"; }
    }
    private static class OversizedLabel implements Label {
        public String kit() { return KIT; }
        public String describe() { return "етикетка «Негабарит»"; }
    }
}