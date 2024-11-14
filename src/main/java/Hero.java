import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Hero extends Element {
    private int energy; // Adiciona o atributo energia

    public Hero(int x, int y) {
        super(x, y); // Chama o construtor da superclasse
        this.energy = 5; // Inicializa com energia máxima
    }

    public int getEnergy() {
        return energy;
    }

    public void loseEnergy() {
        if (energy > 0) {
            energy--;
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00")); // Cor amarela
        graphics.putString(getPosition().getX(), getPosition().getY(), "H"); // Representação do herói
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
