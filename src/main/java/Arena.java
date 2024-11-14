import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Wall> walls;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10); // Inicializa o herói
        this.walls = createWalls(); // Cria as paredes ao redor da arena
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        // Paredes horizontais
        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 0)); // Parede superior
            walls.add(new Wall(x, height - 1)); // Parede inferior
        }

        // Paredes verticais
        for (int y = 1; y < height - 1; y++) {
            walls.add(new Wall(0, y)); // Parede esquerda
            walls.add(new Wall(width - 1, y)); // Parede direita
        }

        return walls;
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
        // Verifica se colide com alguma parede usando equals()
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }

        // Retorna verdadeiro se não houver colisão com as paredes
        return true;
    }

    public void draw(TextGraphics graphics) {
        // Configura o fundo da arena
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699")); // Azul escuro
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                new TerminalSize(width, height),
                ' ' // Preenche com espaços
        );

        // Desenha as paredes
        for (Wall wall : walls) {
            wall.draw(graphics);
        }

        // Desenha o herói
        hero.draw(graphics);
    }
}
