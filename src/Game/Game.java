package Game;

import Game.Move.TYPE;

import Sokoban.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable {

    private int id;
    private Thread thread;
    private volatile boolean running = false;
    private Handler handler;

    private Board board;

    private Highscore highscore = null;
    //counter gameObjects
    private Counter moveCount;
    private Counter pushCount;
    private Timer timer;
    private Solution solution;
    //player gameObject
    private Player player;

    //pixels
    private int fieldSize;
    private int startWidth;
    private int startHeight;
    private int panelWidth;
    private int panelHeight;

    private boolean playerInTheRightPosition;
    private boolean boxesInTheRightPosition;

    public Game(int id) {
        playerInTheRightPosition = boxesInTheRightPosition = false;
        this.id = id;
        this.fieldSize = 32;
        this.highscore = new Highscore(id);
        this.board = new Board(id);
        handler = new Handler();
            this.addKeyListener(new KeyInput(handler));
        setAppearance();
        createGameObjects();
        
        Main.frame.WINDOW.setFocusable(true);
        Main.frame.WINDOW.setFocusTraversalKeysEnabled(true);
        Main.frame.WINDOW.requestFocus();
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);

        playerInTheRightPosition = boxesInTheRightPosition = true;
        System.out.println(this.board.toString());
    }

    private void setAppearance() {
        panelWidth = 800;
        panelHeight = 600;

        if (fieldSize * (board.getHeight() + 2) + 88 + 30 <= Main.frame.maxHeight && fieldSize * (board.getWidth() + 2) <= Main.frame.maxWidth) {
            panelHeight = Math.max(fieldSize * (board.getHeight() + 2) + 88 + 30, panelHeight);
            panelWidth = Math.max(fieldSize * (board.getWidth() + 2), panelWidth);
        } else { //the screen is too small/the map is too big for 32pixel fields
            int requiredFieldWidth = Main.frame.maxWidth / (board.getWidth() + 2);
            int requiredFieldHeight = (Main.frame.maxHeight - 88) / (board.getHeight() + 2);
            fieldSize = Math.min(requiredFieldWidth, requiredFieldHeight);
            panelHeight = Math.max(fieldSize * (board.getHeight() + 2) + 88 + 30, panelHeight);
            panelWidth = Math.max(fieldSize * (board.getWidth() + 2), panelWidth);
        }

        Main.frame.setSize(panelWidth, panelHeight);        
        startWidth = (panelWidth - fieldSize * board.getWidth()) / 2;
        startHeight = (panelHeight - fieldSize * board.getHeight() - 30) / 2;
    }

    private void createGameObjects() {
        // add map
        handler.addObject(new Map(startWidth, startHeight, ID.Map, fieldSize, board));

        //add boxes
        Box.setSize(fieldSize);
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.getBoard()[y][x] == FieldType.BOX) {
                    handler.addObject(new Box(startWidth + x * fieldSize, startHeight + y * fieldSize, ID.Box, board, x, y));
                }
                if (board.getBoard()[y][x] == FieldType.BOX_ON_TARGET) {
                    handler.addObject(new Box(startWidth + x * fieldSize, startHeight + y * fieldSize, ID.Box, board, x, y));
                }
            }
        }

        //add player
        this.player = new Player(startWidth + board.getPlayerX() * fieldSize, startHeight + board.getPlayerY() * fieldSize, ID.Player, fieldSize, 0);
        handler.addObject(player);

        //add countes
        int counterFontSize = 32;
        int counterY = (panelHeight - 44 - fieldSize) + (fieldSize + 44 - counterFontSize) / 2;
        moveCount = new Counter(counterFontSize, counterY, ID.Counter, "move", counterFontSize);
        handler.addObject(moveCount);
        pushCount = new Counter(panelWidth * 2 / 5, counterY, ID.Counter, "push", counterFontSize);
        handler.addObject(pushCount);

        // add timer
        timer = new Timer(0, counterY, ID.Counter, counterFontSize);
        handler.addObject(timer);

        //add solution
        solution = new Solution(30, 30, ID.Counter, counterFontSize);
        handler.addObject(solution);

    }

    public void move(char c, boolean undo) {
        playerInTheRightPosition = false;
        Move move = new Move(c, undo);
        if (move.getType() == TYPE.STAY) {
            playerInTheRightPosition = true;
            return;
        }
        player.setImageY(move.getDirection(), undo);
        board.move(move);

        if (!undo) {
            moveCount.increase();
            if (move.getType() == TYPE.PUSH) {
                pushCount.increase();
                boxesInTheRightPosition = false;
            }
            solution.append(move.toChar());
            checkIfOver();
        } else {
            solution.removeLast();
            moveCount.decrease();
            if (move.getType() == TYPE.PULL) {
                pushCount.decrease();
                boxesInTheRightPosition = false;
            }
        }
       // System.out.println(board.toString());
    }

    public void checkIfOver() {
        if (board.isOver()) {
            Highscore probablyNewHs = new Highscore(id, moveCount.getCount(), pushCount.getCount(), timer.getElapsedSeconds(), solution.getSolution());
            if (highscore == null || highscore.isBetter(probablyNewHs)) {
                probablyNewHs.write();
            }
            this.nextlevel(this.id + 1);
        }
    }

    public void nextlevel(int id) {
       // handler.clear();
        this.running = false;

        Main.frame.WINDOW.remove(Main.game);
        //Main.game = null;
        
        Main.game = new Game(id);
        Main.frame.WINDOW.add(Main.game);

        Main.frame.refresh();
        Main.game.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, Main.frame.WINDOW.getWidth(), Main.frame.WINDOW.getHeight());
        handler.render(g);

        g.dispose();
        bs.show();
    }
    
    public int clamp(int num, int min, int max){
        if(min > num){
            return min;
        }
        if(max < num){
            return max;
        }
        return num;
    }
    
    
    public int getId() {
        return id;
    }
    
    public int getFieldSize() {
        return fieldSize;
    }

    public Player getPlayer() {
        return player;
    }

    public Board getBoard() {
        return board;
    }

    public int getStartWidth() {
        return startWidth;
    }

    public int getStartHeight() {
        return startHeight;
    }

    public Counter getMoveCount() {
        return moveCount;
    }

    public Counter getPushCount() {
        return pushCount;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public Highscore getHighscore() {
        return highscore;
    }

    public Solution getSolution() {
        return solution;
    }

    public boolean isReadyForInput() {
        return boxesInTheRightPosition == true && playerInTheRightPosition == true;
    }

    public void setBoxesInTheRightPosition(boolean boxesInTheRightPosition) {
        this.boxesInTheRightPosition = boxesInTheRightPosition;
    }

    public void setPlayerInTheRightPosition(boolean playerInTheRightPosition) {
        this.playerInTheRightPosition = playerInTheRightPosition;
    }
}
