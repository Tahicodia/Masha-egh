package variant3_shapegeometry;

// Завдання 3 (LSP), варіант 3: геометричний графічний редактор.
//
// Проблема: Square extends Rectangle з мутабельними сетерами ламав постумову
// setWidth(w)/setHeight(h) => area == w * h.
// Рішення: розділена ієрархія (Square більше НЕ нащадок Rectangle), спільний контракт
// Shape, незмінні Value Objects і Guard Clauses (fail-fast).
public class Task3_LSP_ShapeGeometry {

    /** Спільний контракт: лише те, що справді правдиве для будь-якої фігури. */
    public interface Shape {
        double getArea();
    }

    private static double requirePositive(double value, String name) {
        if (!(value > 0) || Double.isInfinite(value)) { // false також для NaN
            throw new IllegalArgumentException(name + " має бути скінченним числом > 0, отримано: " + value);
        }
        return value;
    }

    public static final class Rectangle implements Shape {
        private final double width;
        private final double height;

        public Rectangle(double width, double height) {
            this.width = requirePositive(width, "width");
            this.height = requirePositive(height, "height");
        }

        public double getWidth() { return width; }
        public double getHeight() { return height; }

        public Rectangle withWidth(double newWidth) { return new Rectangle(newWidth, height); }
        public Rectangle withHeight(double newHeight) { return new Rectangle(width, newHeight); }

        @Override public double getArea() { return width * height; }
    }

    public static final class Square implements Shape {
        private final double side;

        public Square(double side) {
            this.side = requirePositive(side, "side");
        }

        public double getSide() { return side; }

        public Square withSide(double newSide) { return new Square(newSide); }

        @Override public double getArea() { return side * side; }
    }

    public static class AreaCalculator {
        /** Клієнт працює лише з Rectangle, і контракт тепер виконується завжди. */
        public void resizeAndVerifyArea(Rectangle rect) {
            Rectangle resized = rect.withWidth(5).withHeight(10);

            double expectedArea = 50;
            double actualArea = resized.getArea();
            if (actualArea != expectedArea) {
                throw new IllegalStateException(
                        "Порушено інваріант: очікувана площа " + expectedArea + ", отримано " + actualArea);
            }
            System.out.println("Rectangle після зміни розмірів: площа = " + actualArea);
        }

        /** Для квадрата - власна коректна операція, без підміни Rectangle. */
        public void resizeSquare(Square square, double newSide) {
            Square resized = square.withSide(newSide);
            System.out.println("Square зі стороною " + newSide + ": площа = " + resized.getArea());
        }
    }

    public static void main(String[] args) {
        AreaCalculator calculator = new AreaCalculator();
        calculator.resizeAndVerifyArea(new Rectangle(1, 1));
        calculator.resizeSquare(new Square(2), 7);

        try {
            new Rectangle(-3, 4); // fail-fast
        } catch (IllegalArgumentException e) {
            System.out.println("Очікувана помилка: " + e.getMessage());
        }
    }
}
