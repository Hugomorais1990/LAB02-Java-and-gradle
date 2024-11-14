import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element {

    public Coin(int x, int y) {
        super(x, y); // Chama o construtor da classe Element
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFD700")); // Cor dourada
        graphics.putString(getPosition().getX(), getPosition().getY(), "C"); // Representação da moeda
    }
}
