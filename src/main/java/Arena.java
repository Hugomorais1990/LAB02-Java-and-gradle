import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

public class Arena {
    private int width;
    private int height;
    private Hero hero;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10); // Inicializa o herói na posição (10,10)
    }

    public void processKey(KeyStroke key) {
        Position newPosition;
        switch (key.getKeyType()) {
            case ArrowUp -> newPosition = hero.moveUp();
            case ArrowDown -> newPosition = hero.moveDown();
            case ArrowLeft -> newPosition = hero.moveLeft();
            case ArrowRight -> newPosition = hero.moveRight();
            default -> {
                return; // Ignora outras teclas
            }
        }
        moveHero(newPosition);
    }

    private void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
    }

    private boolean canHeroMove(Position position) {
        return position.getX() >= 0 && position.getX() < width &&
                position.getY() >= 0 && position.getY() < height;
    }

    public void draw(TextGraphics graphics) {
        // Configura o fundo da arena
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699")); // Azul escuro
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                new TerminalSize(width, height),
                ' ' // Preenche com espaços
        );

        // Desenha o herói
        hero.draw(graphics);
    }
}
