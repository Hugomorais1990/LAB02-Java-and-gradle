import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Element> walls; // Paredes da arena
    private List<Coin> coins; // Moedas na arena

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10); // Inicializa o herói
        this.walls = createWalls(); // Cria as paredes ao redor da arena
        this.coins = createCoins(); // Cria moedas aleatoriamente na arena
    }

    private List<Element> createWalls() {
        List<Element> walls = new ArrayList<>();

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

    private List<Coin> createCoins() {
        List<Coin> coins = new ArrayList<>();
        Random random = new Random();

        // Gera 5 moedas em posições aleatórias dentro da arena
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(width - 2) + 1; // Evita bordas
            int y = random.nextInt(height - 2) + 1; // Evita bordas
            coins.add(new Coin(x, y));
        }

        return coins;
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
            retrieveCoins(); // Verifica se o herói coleta uma moeda
        }
    }

    private boolean canHeroMove(Position position) {
        // Verifica se colide com alguma parede
        for (Element wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }

        return true;
    }

    private void retrieveCoins() {
        // Verifica se o herói está na mesma posição de uma moeda
        for (int i = 0; i < coins.size(); i++) {
            if (coins.get(i).getPosition().equals(hero.getPosition())) {
                coins.remove(i); // Remove a moeda da lista
                break; // Sai do loop, pois só pode coletar uma moeda por movimento
            }
        }
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
        for (Element wall : walls) {
            wall.draw(graphics);
        }

        // Desenha as moedas
        for (Coin coin : coins) {
            coin.draw(graphics);
        }

        // Desenha o herói
        hero.draw(graphics);
    }
}
