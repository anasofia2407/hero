import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width;
    private int height;
    Hero hero = new Hero(10, 10);
    private List<Wall> walls;
    public Arena(int width,int height){
        this.width=width;
        this.height=height;
        this.walls=createWalls();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }


    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls)
            wall.draw(graphics);
        hero.draw(graphics);

    }
    private boolean canHeroMove(Position position){
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }
    private void moveHero(Position position) {
        if (canHeroMove(position)){
            hero.setPosition(position);
        }
    }
    public void processKey(KeyStroke key) throws IOException {
        if (key.getKeyType() == KeyType.ArrowUp){
            moveHero(hero.moveUp());
        }
        else if (key.getKeyType() == KeyType.ArrowLeft){
            moveHero(hero.moveLeft());
        }
        else if (key.getKeyType() == KeyType.ArrowRight){
            moveHero(hero.moveRight());
        }
        else if (key.getKeyType() == KeyType.ArrowDown){
            moveHero(hero.moveDown());
        }


    }

}
