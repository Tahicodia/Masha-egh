package packaging;

public interface PackagingFactory {
    Box createBox();
    Filler createFiller();
    Label createLabel();
    String kitName();
}