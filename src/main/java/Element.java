import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    private Position position;

    // Construtor
    public Element(int x, int y) {
        this.position = new Position(x, y);
    }

    // Obtém a posição
    public Position getPosition() {
        return position;
    }

    // Define uma nova posição
    public void setPosition(Position position) {
        this.position = position;
    }

    // Método abstrato para desenhar o elemento
    public abstract void draw(TextGraphics graphics);
}
