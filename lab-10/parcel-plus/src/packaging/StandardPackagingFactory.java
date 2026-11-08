package packaging;

public class StandardPackagingFactory implements PackagingFactory {
    private static final String KIT = "Звичайне";

    @Override public Box createBox() { return new StandardBox(); }
    @Override public Filler createFiller() { return new StandardFiller(); }
    @Override public Label createLabel() { return new StandardLabel(); }
    @Override public String kitName() { return KIT; }

    private static class StandardBox implements Box {
        public String kit() { return KIT; }
        public String describe() { return "картонна коробка"; }
    }
    private static class StandardFiller implements Filler {
        public String kit() { return KIT; }
        public String describe() { return "паперовий наповнювач"; }
    }
    private static class StandardLabel implements Label {
        public String kit() { return KIT; }
        public String describe() { return "етикетка «Стандарт»"; }
    }
}