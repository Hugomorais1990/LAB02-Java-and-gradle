import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Hero extends Element {

    public Hero(int x, int y) {
        super(x, y); // Chama o construtor da superclasse
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFF400")); // Cor amarela
        graphics.putString(getPosition().getX(), getPosition().getY(), "X"); // Desenha o herói na posição atual
    }

    public Position moveUp() {
        return new Position(getPosition().getX(), getPosition().getY() - 1);
    }

    public Position moveDown() {
        return new Position(getPosition().getX(), getPosition().getY() + 1);
    }

    public Position moveLeft() {
        return new Position(getPosition().getX() - 1, getPosition().getY());
    }

    public Position moveRight() {
        return new Position(getPosition().getX() + 1, getPosition().getY());
    }
}
