import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class Game {
    private Screen screen;
    private Hero hero;

    public Game() throws IOException {
        TerminalSize terminalSize = new TerminalSize(40, 20);
        var terminal = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        hero = new Hero(10, 10);
    }

    private void draw() throws IOException {
        screen.clear();
        hero.draw(screen);
        screen.refresh();
    }

    public void run() {
        try {
            while (true) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);

                if (key.getKeyType().toString().equalsIgnoreCase("q")) {
                    screen.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processKey(KeyStroke key) {
        Position newPosition;
        switch (key.getKeyType()) {
            case ArrowUp:
                newPosition = hero.moveUp();
                break;
            case ArrowDown:
                newPosition = hero.moveDown();
                break;
            case ArrowLeft:
                newPosition = hero.moveLeft();
                break;
            case ArrowRight:
                newPosition = hero.moveRight();
                break;
            default:
                return; // Nenhuma ação para outras teclas
        }
        moveHero(newPosition);
    }


    private void moveHero(Position position) {
        hero.setPosition(position);
    }
}
