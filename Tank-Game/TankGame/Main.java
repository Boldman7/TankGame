package TankGame;

import TankGame.GameObject.MainObject.BWall;
import TankGame.GameObject.MainObject.HealthUp;
import TankGame.GameObject.MainObject.Bullet;
import TankGame.GameObject.MainObject.MyTank;
import TankGame.GameObject.MainObject.Wall;
import TankGame.GameView.GameObservable;
import TankGame.GameView.GameScene;
import TankGame.GameView.GameSounds;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Main Class
 * @author Christopher
 * 
 * This shows the game scene and initialize all objects.
 * */

public class Main implements Runnable {
	
	/******************************* Declare Variables *********************************/
	
    // Game window
    private JFrame frame;
	
	// JFrame properties
    private String jTitle;
    private int jWidth, jHeight;
    
    // Size of Scene
    private int mapWidth, mapHeight;
    
    // For Sounds
    private GameSounds gameSounds;
    private ArrayList<GameSounds> soundsList;
    
    // Map(Scene) properties
    private final int cntRow = 25;
    private final int cntCol = 25;
    private int[][] mapLayout;
    
    // Swing parts
    private GameScene gameScene;
    
    // Observable
    private final GameObservable gameObs;
    
    // Active fields
    private Thread thread;
    private boolean running = false;
    
    // Game objects
    private ArrayList<Wall> walls;
    private ArrayList<BWall> bwalls;
    private ArrayList<HealthUp> pups;
    private ArrayList<Bullet> bullets;
    
    // Player objects
    private static MyTank tankOne;
    private static MyTank tankTwo;
    
    // Key Event
    private KeyInputEvent keyEventOne;
    private KeyInputEvent keyEventTwo;
    
    // Resource paths
    private String pathDIR;
    private String pathBackground;
    private String pathWall;
    private String pathBWall;
    private String pathHealth;
    private String pathLife;
    private String pathLifeIC1;
    private String pathLifeIC2;
    private String pathTank1, pathTank2;
    private String pathTree;
    private String pathBullet;
    private String arrayPathImg[];
    
    private String pathSound;
    private String pathSndExpSmall;
    private String pathSndExpLarge;
    private String pathSndBreak;
    private String pathSndUnBreak;
    
    private String arrayPathSound[];
    
    /*************************************** End Declare *************************************/
    
    /* Main Process */
    public static void main(String args[]) throws MalformedURLException {
    	    	
        Main main = new Main();
        main.start();
    }
    
    public Main() {
        this.gameObs = new GameObservable();
    }
    
    @Override
    public void run() {
    	
        /********************************** initialize ***********************************/
        // World Properties
    	initWorldProperties();
    	
    	// Init Resource paths
        setResourcePaths();
        
        // Create scene
        this.gameScene = new GameScene(mapWidth, mapHeight, jWidth, jHeight,
                                pathBackground, arrayPathImg);
        // Map
        setMapLayout();
        createMapObjects();
        // Players
        setupPlayers();
        // Sounds
        setupSounds();
        // Frame
        setupFrame();

        /***********************************************************************************/
        
        try {
        	
	        while(running) {
	        	
	            this.gameObs.setChanged();
	            this.gameObs.notifyObservers();
	            this.gameScene.setBullet(bullets);
	            render();
	            
	            Thread.sleep(7);
	        }
        } catch (InterruptedException e) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
	        }
        
        stop();
    }
       
    private void initWorldProperties() {
    	
        this.jTitle = "Christoper's Tank Game";
        this.jWidth = 1000;
        this.jHeight = 722;
        this.mapWidth = 1600;
        this.mapHeight = 1600;
    }
    
    private void createMapObjects() {
    	
        BufferedImage objectImage;
        
        int cell_size = 64;
        int extra = 32;
        
    	walls = new ArrayList<>();
        bwalls = new ArrayList<>();
        pups  = new ArrayList<>();

        for (int row = 0; row < cntRow; row++) {
            for (int col = 0; col < cntCol; col++) {
            	
                // Wall
                if (this.mapLayout[row][col] == 2) {
                	
                    objectImage = setImage(arrayPathImg[2]);
                    
                    walls.add(new Wall(
                    		col*cell_size, 
                    		row*cell_size,
                            objectImage.getWidth(), 
                            objectImage.getHeight(), 
                            objectImage));
                    
                    walls.add(new Wall(
                    		col*cell_size + extra, 
                    		row*cell_size,
                            objectImage.getWidth(), 
                            objectImage.getHeight(),
                            objectImage));
                    
                    walls.add(new Wall(
                    		col*cell_size,
                    		row*cell_size + extra,
                            objectImage.getWidth(), 
                            objectImage.getHeight(),
                            objectImage));
                    
                    walls.add(new Wall(
                    		col*cell_size + extra, 
                    		row*cell_size + extra,
                            objectImage.getWidth(),
                            objectImage.getHeight(), 
                            objectImage));
                }
                // Breakable Wall
                if (this.mapLayout[row][col] == 3) {
                	
                    objectImage = setImage(arrayPathImg[3]);
                    
                    bwalls.add(new BWall(
                    		col*cell_size,
                    		row*cell_size,
                            objectImage.getWidth(),
                            objectImage.getHeight(),
                            objectImage));
                    
                    bwalls.add(new BWall(
                    		col*cell_size + extra, 
                    		row*cell_size,
                            objectImage.getWidth(),
                            objectImage.getHeight(),
                            objectImage));
                    
                    bwalls.add(new BWall(
                    		col*cell_size,
                    		row*cell_size + extra,
                            objectImage.getWidth(),
                            objectImage.getHeight(), 
                            objectImage));
                    
                    bwalls.add(new BWall(
                    		col*cell_size + extra, 
                    		row*cell_size + extra,
                            objectImage.getWidth(),
                            objectImage.getHeight(),
                            objectImage));
                }
                // Health
                if (this.mapLayout[row][col] == 4) {
                	
                    objectImage = setImage(arrayPathImg[4]);

                        pups.add(new HealthUp(
                    		col*cell_size + extra/2,
                    		row*cell_size + extra/2,
                            objectImage.getWidth(),
                            objectImage.getHeight(),
                            objectImage));
                }
                // Tree
                if (this.mapLayout[row][col] == 7) {
                	
                    objectImage = setImage(arrayPathImg[7]);
                    walls.add(new Wall(
                    		col*cell_size, 
                    		row*cell_size,
                            objectImage.getWidth(), 
                            objectImage.getHeight(), 
                            objectImage));
                }
            }
        }
        
        // Add each object to the Observable
        walls.forEach((curr) -> {
            this.gameObs.addObserver(curr);
        });
        bwalls.forEach((curr) -> {
            this.gameObs.addObserver(curr);
        });
        pups.forEach((curr) -> {
            this.gameObs.addObserver(curr);
        });
        
        // Add each object to the Scene
        this.gameScene.setMapObjects(this.walls, this.bwalls, this.pups);
    }
    

    private void setupPlayers() {
    	
        BufferedImage t1img = setImage(arrayPathImg[0]);
        BufferedImage t2img = setImage(arrayPathImg[1]);
        
        int tank1_x = 80,
            tank1_y = 50,
            tank2_x = 1500,
            tank2_y = 1500,
            tank2_angle = 180, // face tank2 inwards
            tank_speed = 1;
        
        tankOne = new MyTank(this, t1img, tank1_x, tank1_y, tank_speed,
                KeyEvent.VK_A, KeyEvent.VK_D,
                KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_SPACE);
        
        tankTwo = new MyTank(this, t2img, tank2_x, tank2_y, tank_speed,
                KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER);
        tankTwo.setAngle(tank2_angle); // face tank2 inwards
        
        // other tank has to be set after instantions
        tankOne.setOtherTank(tankTwo);
        tankTwo.setOtherTank(tankOne);
        
        // connect key inputs with tanks
        this.keyEventOne = new KeyInputEvent(tankOne);
        this.keyEventTwo = new KeyInputEvent(tankTwo);
        
        // add tanks to observer list
        gameObs.addObserver(tankOne);
        gameObs.addObserver(tankTwo);
        
        this.gameScene.setTanks(tankOne, tankTwo);
        
        // instantiate bullets list
        this.bullets = new ArrayList<>();
        
        // set life icons
        this.gameScene.setLifeIcons(setImage(this.pathLifeIC1), setImage(this.pathLifeIC2));
    }
    
    private void setupSounds() {
    	
        // music; for looped sound, it will play once instantiated 
        this.gameSounds = new GameSounds(1, this.pathSound);
        
        // game sounds
        GameSounds small_explosion = new GameSounds(2, this.arrayPathSound[0]);
        GameSounds large_explosion = new GameSounds(2, this.arrayPathSound[1]);
        GameSounds unbreakable_hit = new GameSounds(2, this.arrayPathSound[2]);
        GameSounds breakable_hit = new GameSounds(2, this.arrayPathSound[3]);
        this.soundsList = new ArrayList<>();
        this.soundsList.add(small_explosion); // 0
        this.soundsList.add(large_explosion); // 1
        this.soundsList.add(unbreakable_hit); // 2
        this.soundsList.add(breakable_hit); // 3
    }

    private void setupFrame() {
        frame = new JFrame();
        
        // Game Window
        this.frame.setTitle(jTitle);
        this.frame.setSize(jWidth, jHeight);
        this.frame.setPreferredSize(new Dimension(jWidth, jHeight));
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.add(this.gameScene);
        this.frame.setLocationRelativeTo(null);
        
        this.frame.addKeyListener(keyEventOne);
        this.frame.addKeyListener(keyEventTwo);
        
        // finalize the frame
        this.frame.pack();
        this.frame.setVisible(true);
    }
    
    private void setResourcePaths() {
    	
        /******************** Image Paths *********************/
    	pathDIR = "res/";
    	
        pathBackground = pathDIR + "img_background.png";
        pathWall = pathDIR + "img_break_wall.gif";
        pathBWall = pathDIR + "img_unbreak_wall.gif";
        pathTree = pathDIR + "img_tree.png";
        
        pathHealth = pathDIR + "img_health_up.png";
        pathLife = pathDIR + "img_life.gif";
        pathLifeIC1 = pathDIR + "img_life_p1.gif";
        pathLifeIC2 = pathDIR + "img_life_p2.gif";
        
        pathTank1 = pathDIR + "img_tank_1.gif";
        pathTank2 = pathDIR + "img_tank_2.gif";
        pathBullet = pathDIR + "img_bullet.gif";
        
        pathSound = pathDIR + "snd_background.wav";
        pathSndExpSmall = pathDIR + "snd_explosion_small.wav";
        pathSndExpLarge = pathDIR + "snd_explosion_large.wav";
        pathSndUnBreak = pathDIR + "snd_unbreak.wav";
        pathSndBreak = pathDIR + "snd_break.wav";
        
        
        // 0: empty, 1: empty, 2: wall, 3: breakable wall, 4: health, 5: life, 6: projectile 7: tree
        arrayPathImg = new String[] {
        		pathTank1,
        		pathTank2,
        		pathBWall,
        		pathWall, 
        		pathHealth,
        		pathLife,
        		pathBullet,
        		pathTree};
        
        // Sounds        
        arrayPathSound = new String[] {
        		pathSndExpSmall,
        		pathSndExpLarge,
        		pathSndUnBreak,
        		pathSndBreak};
    }
    
    // 0 = empty, 1 = ?, 2 = wall, 3 = breakable wall, 4 = health, 5 = life, 7: tree
    private void setMapLayout() {
    	
        this.mapLayout = new int[][]
        { 
        	// 25x25
            {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7}, // 0
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 1
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 2
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 3
            {7, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 2, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 7}, // 4
            {7, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 7}, // 5
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 6
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 7
            {7, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 7}, // 8
            {7, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 7}, // 9
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 10
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 11
            {7, 2, 2, 2, 2, 3, 0, 0, 3, 2, 3, 3, 4, 3, 3, 2, 3, 0, 0, 3, 2, 2, 2, 2, 7}, // 12
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 13
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 14
            {7, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 7}, // 15
            {7, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 7}, // 16
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 17
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 18
            {7, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 3, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 7}, // 19
            {7, 0, 0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 2, 0, 0, 0, 3, 3, 0, 0, 0, 0, 0, 0, 7}, // 20
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 21
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 22
            {7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7}, // 23
            {7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7}, // 24
        };
    }
    
    public void addBulletToObservable(Bullet p) {
        this.gameObs.addObserver(p);
    }

    // SETTERS //
    private BufferedImage setImage(String filepath){
        BufferedImage img = null;
        
        try {
        	
        	img = ImageIO.read(this.getClass().getResourceAsStream("/" + filepath));
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return img;
    }
    
    // GETTERS //
    public Main getTankWorld() {
        return this;
    }
    
    public static MyTank getMyTank(int tankNumber) {
        switch (tankNumber) {
            case 1:
                return tankOne;
            case 2:
                return tankTwo;
            default:
                System.out.println("Tank not found!");
                return null;
        }
    }
    
    public static ArrayList<MyTank> getTanks() {
        ArrayList<MyTank> tanks = new ArrayList<>();
        tanks.add(tankOne);
        tanks.add(tankTwo);
        return tanks;
    }
    
    public ArrayList<Wall> getWalls() {
        return this.walls;
    }
    
    public int getWallSize(){
        return walls.size();
    }
    
    public ArrayList<BWall> getBWalls() {
        return this.bwalls;
    }
    
    public int getBWallSize() {
        return bwalls.size();
    }
    
    public ArrayList<HealthUp> getHealthUp() {
        return this.pups;
    }

    public ArrayList<Bullet> getProjectile() {
        return bullets;
    }
    
    public BufferedImage getProjectileImg(){
        BufferedImage projectile = setImage(arrayPathImg[6]);
        return projectile;
    }
    
    public int getWindowWidth () {
        return this.jWidth;
    }
    
    public int getWindowHeight() {
        return this.jHeight;
    }
    
    public int getMapWidth() {
        return this.mapWidth;
    }
    
    public int getMapHeight() {
        return this.mapHeight;
    }
    
    public GameSounds getSound(int sound_number) {
        return this.soundsList.get(sound_number);
    }
    
    public void playSound(int sound_number) {
        if (sound_number >= 0 && sound_number <= 3)
            this.soundsList.get(sound_number).play();
    }
    
    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void render() {
        this.gameScene.repaint();
    }
}