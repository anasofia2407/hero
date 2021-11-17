import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private boolean gameOver=false;
    Hero hero = new Hero(10, 10);
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public boolean isGameOver() {
        return gameOver;
    }

    public Arena(int width, int height){
        this.width=width;
        this.height=height;
        this.walls=createWalls();
        this.coins=createCoins();
        this.monsters=createMonters();
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

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }
    private List<Monster> createMonters() {
        Random random = new Random();
        ArrayList<Monster> monsters= new ArrayList<>();
        for (int i = 0; i < 5; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        for (Wall wall : walls)
            wall.draw(graphics);
        hero.draw(graphics);
        for (int i=0;i<coins.size();i++){
            boolean j =true;
            for (Wall wall : walls) {
                if (coins.get(i).getPosition().getX()==wall.getPosition().getX() && coins.get(i).getPosition().getY()== wall.getPosition().getY())
                    j= false;
            }
            if (coins.get(i).getPosition().getX()==hero.getPosition().getX() && coins.get(i).getPosition().getY()==hero.getPosition().getY()){
                j= false;
            }
            if(j)
                coins.get(i).draw(graphics);
        }
        for (int i=0;i<monsters.size();i++){
            boolean j =true;
            for (Wall wall : walls) {
                if (monsters.get(i).getPosition().getX()==wall.getPosition().getX() && monsters.get(i).getPosition().getY()== wall.getPosition().getY())
                    j= false;
            }
            if(j)
                monsters.get(i).draw(graphics);
        }


    }
    private void retrieveCoins(Position position){
        for(int i = 0; i<coins.size();i++){
            if (coins.get(i).getPosition().getX()==position.getX() && coins.get(i).getPosition().getY()==position.getY()){
                coins.remove(i);
            }
        }

    }
    public void verifyMonstersCollisions(){
        for(int i=0;i<monsters.size();i++){
            if (monsters.get(i).getPosition().getY()==hero.getPosition().getY() && monsters.get(i).getPosition().getX()==hero.getPosition().getX() )
                this.gameOver=true;
        }
    }
    private boolean canHeroMove(Position position){
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }
    private void moveMonsters(){
        for(int i=0; i<monsters.size();i++){
            monsters.get(i).setPosition(monsters.get(i).move(hero.getPosition()));
        }
    }
    private void moveHero(Position position) {
        if (canHeroMove(position)){
            hero.setPosition(position);
            retrieveCoins(hero.getPosition());
            moveMonsters();
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
