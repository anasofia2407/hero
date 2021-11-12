import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    Hero hero = new Hero(10, 10);
    public Game() throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
        TerminalSize terminalSize = new TerminalSize(40, 20);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);


    }
    private void draw() throws IOException {
        screen.clear();
        screen.setCharacter(hero.getX(), hero.getY(), TextCharacter.fromCharacter('X')[0]);
        screen.refresh();
    }
    private void processKey(KeyStroke key) {
        System.out.println(key);
    }
    public void run(){
        try {
            while (true) {
                draw();
                KeyStroke key = screen.readInput();
                processKey(key);
                if (key.getKeyType() == KeyType.ArrowUp){
                    hero.moveUp();
                }
                else if (key.getKeyType() == KeyType.ArrowLeft){
                    hero.moveLeft();
                }
                else if (key.getKeyType() == KeyType.ArrowRight){
                    hero.moveRight();
                }
                else if (key.getKeyType() == KeyType.ArrowDown){
                    hero.moveDown();
                }
                else if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q'){
                    screen.close();
                }
                else if (key.getKeyType()==KeyType.EOF){
                    break;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
