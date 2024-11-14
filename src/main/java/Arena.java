import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Arena {
    private int width;
    private int height;
    private Hero hero;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10);
    }

    public void processKey(com.googlecode.lanterna.input.KeyStroke key) {
        Position newPosition;
        switch (key.getKeyType()) {
            case ArrowUp -> newPosition = hero.moveUp();
            case ArrowDown -> newPosition = hero.moveDown();
            case ArrowLeft -> newPosition = hero.moveLeft();
            case ArrowRight -> newPosition = hero.moveRight();
            default -> {
                return;
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
        graphics.setBackgroundColor(com.googlecode.lanterna.TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(
                new com.googlecode.lanterna.TerminalPosition(0, 0),
                new com.googlecode.lanterna.TerminalSize(width, height),
                ' '
        );

        // Desenha o herói
        hero.draw(graphics);
    }
}
