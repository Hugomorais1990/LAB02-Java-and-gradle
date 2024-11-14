import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {

    public Monster(int x, int y) {
        super(x, y); // Chama o construtor da superclasse Element
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000")); // Cor vermelha
        graphics.putString(getPosition().getX(), getPosition().getY(), "M"); // Representação do monstro
    }

    public Position move() {
        Random random = new Random();
        int deltaX = random.nextInt(3) - 1; // Gera -1, 0 ou 1
        int deltaY = random.nextInt(3) - 1; // Gera -1, 0 ou 1
        return new Position(getPosition().getX() + deltaX, getPosition().getY() + deltaY);
    }
}
