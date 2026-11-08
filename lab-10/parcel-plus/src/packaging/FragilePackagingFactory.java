package packaging;

public class FragilePackagingFactory implements PackagingFactory {
    private static final String KIT = "Крихке";

    @Override public Box createBox() { return new FragileBox(); }
    @Override public Filler createFiller() { return new FragileFiller(); }
    @Override public Label createLabel() { return new FragileLabel(); }
    @Override public String kitName() { return KIT; }

    private static class FragileBox implements Box {
        public String kit() { return KIT; }
        public String describe() { return "посилена коробка з подвійними стінками"; }
    }
    private static class FragileFiller implements Filler {
        public String kit() { return KIT; }
        public String describe() { return "бульбашкова плівка"; }
    }
    private static class FragileLabel implements Label {
        public String kit() { return KIT; }
        public String describe() { return "етикетка «Крихке»"; }
    }
}