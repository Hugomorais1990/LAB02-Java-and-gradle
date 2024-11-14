import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private Hero hero;
    private List<Element> walls; // Paredes da arena
    private List<Coin> coins; // Moedas na arena
    private List<Monster> monsters; // Monstros na arena

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10, 10); // Inicializa o herói
        this.walls = createWalls(); // Cria as paredes ao redor da arena
        this.coins = createCoins(); // Cria moedas aleatórias
        this.monsters = createMonsters(); // Cria monstros aleatórios
    }

    private List<Element> createWalls() {
        List<Element> walls = new ArrayList<>();

        // Paredes horizontais
        for (int x = 0; x < width; x++) {
            walls.add(new Wall(x, 1)); // Parede superior (ajuste para linha 1)
            walls.add(new Wall(x, height - 1)); // Parede inferior
        }

        // Paredes verticais
        for (int y = 2; y < height - 1; y++) {
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
            int y = random.nextInt(height - 3) + 2; // Evita a linha do cabeçalho
            coins.add(new Coin(x, y));
        }

        return coins;
    }

    private List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
        Random random = new Random();

        // Gera monstros normais
        for (int i = 0; i < 2; i++) { // Dois monstros normais
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 3) + 2;
            monsters.add(new Monster(x, y));
        }

        // Gera SmartMonsters
        for (int i = 0; i < 1; i++) { // Um SmartMonster
            int x = random.nextInt(width - 2) + 1;
            int y = random.nextInt(height - 3) + 2;
            monsters.add(new SmartMonster(x, y));
        }

        return monsters;
    }

    public void processKey(KeyStroke key) {
        Position newPosition = null; // Inicializa newPosition como null
        switch (key.getKeyType()) {
            case ArrowUp -> newPosition = hero.moveUp();
            case ArrowDown -> newPosition = hero.moveDown();
            case ArrowLeft -> newPosition = hero.moveLeft();
            case ArrowRight -> newPosition = hero.moveRight();
            case Character -> {
                // Reinicia o jogo ao pressionar 'R' (ignorando maiúsculas/minúsculas)
                if (key.getCharacter() != null && key.getCharacter().toString().equalsIgnoreCase("r")) {
                    restartGame();
                    return; // Sai do método após reiniciar
                }
            }
            default -> {
                return; // Ignora outras teclas
            }
        }
        moveHero(newPosition);
        moveMonsters(); // Move os monstros após o movimento do herói
        verifyMonsterCollisions(); // Verifica colisões entre o herói e os monstros
    }

    private void restartGame() {
        System.out.println("Reiniciando o jogo...");

        // Redefine o herói na posição inicial
        this.hero = new Hero(10, 10);

        // Recria as moedas
        this.coins = createCoins();

        // Recria os monstros
        this.monsters = createMonsters();

        // Opcional: Se necessário, pode redefinir outras variáveis do jogo
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
                hero.addScore(10); // Adiciona 10 pontos
                break; // Sai do loop, pois só pode coletar uma moeda por movimento
            }
        }
    }

    private void moveMonsters() {
        for (Monster monster : monsters) {
            Position newPosition;
            if (monster instanceof SmartMonster) {
                // Movimento do SmartMonster
                newPosition = ((SmartMonster) monster).moveTowards(hero.getPosition());
            } else {
                // Movimento do monstro normal
                newPosition = monster.move();
            }

            if (canMonsterMove(newPosition)) {
                monster.setPosition(newPosition);
            }
        }
    }


    private boolean canMonsterMove(Position position) {
        // Verifica se o monstro colide com alguma parede
        for (Element wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private void verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(hero.getPosition())) {
                hero.loseEnergy(); // Reduz a energia do herói
                if (hero.getEnergy() == 0) {
                    System.out.println("O herói perdeu toda a energia! Fim do jogo.");
                    System.exit(0); // Encerra o jogo
                }
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

        // Mostra informações de energia e pontuação
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF")); // Cor branca para o texto
        graphics.putString(1, 0, "Energia: " + hero.getEnergy() + " | Pontuação: " + hero.getScore());

        // Desenha as paredes
        for (Element wall : walls) {
            wall.draw(graphics);
        }

        // Desenha as moedas
        for (Coin coin : coins) {
            coin.draw(graphics);
        }

        // Desenha os monstros
        for (Monster monster : monsters) {
            monster.draw(graphics);
        }

        // Desenha o herói
        hero.draw(graphics);
    }
}
