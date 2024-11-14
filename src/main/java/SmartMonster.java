import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class SmartMonster extends Monster {

    public SmartMonster(int x, int y) {
        super(x, y); // Chama o construtor da classe Monster
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF8800")); // Cor laranja
        graphics.putString(getPosition().getX(), getPosition().getY(), "S"); // Representação do SmartMonster
    }

    public Position moveTowards(Position heroPosition) {
        // Calcula a direção do movimento com base na posição do herói
        int deltaX = Integer.compare(heroPosition.getX(), getPosition().getX());
        int deltaY = Integer.compare(heroPosition.getY(), getPosition().getY());
        return new Position(getPosition().getX() + deltaX, getPosition().getY() + deltaY);
    }
}
