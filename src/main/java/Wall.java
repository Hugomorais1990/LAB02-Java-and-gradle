import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall extends Element {

    public Wall(int x, int y) {
        super(x, y); // Chama o construtor da superclasse
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFF256")); // Cor branca
        graphics.putString(getPosition().getX(), getPosition().getY(), "#"); // Representação da parede
    }
}
