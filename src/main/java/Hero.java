import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {
    private int x;
    private int y;
    public Hero(int hx, int hy) {
        this.x = hx;
        this.y = hy;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void moveUp() {
        this.y = this.getY() - 1;
    }
    public void moveRight() {
        this.x = this.getX() + 1;
    }
    public void moveDown() {
        this.y = this.getY() + 1;
    }
    public void moveLeft() {
        this.x = this.getX() - 1;
    }
    public void draw(Screen screen) {
        screen.setCharacter(this.x, this.y, TextCharacter.fromCharacter('X')[0]);
    }

}