package TankGame.GameView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JPanel;

import TankGame.GameObject.MainObject.BWall;
import TankGame.GameObject.MainObject.HealthUp;
import TankGame.GameObject.MainObject.Bullet;
import TankGame.GameObject.MainObject.MyTank;
import TankGame.GameObject.MainObject.Wall;

/**
 * GameScene Class
 * @author Christopher
 * 
 * This shows all objects.
 * */

public class GameScene extends JPanel {

    private BufferedImage bgImg; // background image
    private BufferedImage lifeIcon1, lifeIcon2;

    private int mapWidth, mapHeight,
            windowWidth, windowHeight,
            minimapWidth, minimapHeight;

    private MyTank tank1, tank2;  
    
    /* Player Window */
    BufferedImage p1w, p2w;
    Image minimap;
    
    /* Game Objects */
    private ArrayList<Wall> walls;
    private ArrayList<BWall> bwalls;
    private ArrayList<HealthUp> pups;
    private ArrayList<Bullet> bullets;

    /* Player Bound Checking */
    private int p1WndBndX, p1WndBndY, p2WndBndX, p2WndBndY;
    
    public GameScene() {}

    public GameScene(int mWidth, int mHeight,
    		int wndWidth, int wndHeight,
            String pathBackground, String[] arrayPathImg) {
    	
        super();
        this.mapWidth = mWidth;
        this.mapHeight = mHeight;
        this.windowWidth = wndWidth;
        this.windowHeight = wndHeight;
        this.minimapWidth = 200;
        this.minimapHeight = 200;

        this.setSize(mWidth, mHeight);
        this.setPreferredSize(new Dimension(mWidth, mHeight));
        this.bgImg = setImage(pathBackground);

        walls = new ArrayList<>();
        bwalls = new ArrayList<>();
        pups = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
    	
        getGameImage();
        
        super.paintComponent(g);

        // draw player-1 window
        g.drawImage(p1w, 0, 0, this);
        
        // draw player-2 window
        g.drawImage(p2w, windowWidth / 2, -30, this);
        
        // old minimap place
        drawPlayerStatus(g);
        
        // borders
        g.setColor(Color.YELLOW);
        g.draw3DRect(0, 0, (windowWidth/2)-1, windowHeight-22, true);
        g.fillRect(windowWidth/2, 0, 3, windowHeight);
        g.draw3DRect(windowWidth/2, 0, (windowWidth/2)-1, windowHeight-2, true);
        
        // draw minimap
        g.drawImage(minimap, windowWidth / 2 - minimapWidth / 2, windowHeight - minimapHeight - 30, this);
        g.drawRect(windowWidth / 2 - minimapWidth / 2, windowHeight - minimapHeight - 30, minimapWidth, minimapHeight);
        
        // victory text
        if (tank1.getLife() == 0) {
            g.setFont(new Font(g.getFont().getFontName(), Font.CENTER_BASELINE, 84));
            g.drawString("PLAYER 2 WINS!", 64, windowHeight/2);
        }
        if (tank2.getLife() == 0) {
            g.setFont(new Font(g.getFont().getFontName(), Font.CENTER_BASELINE, 84));
            g.drawString("PLAYER 1 WINS!", 64, windowHeight/2);
        }
    }

    public void getGameImage() {
    	
        // create buffered image
        BufferedImage bimg = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bimg.createGraphics();

        // draw to graphics2D
        drawBackground(graphics2D);
        drawMapLayout(graphics2D);
        drawTanks(graphics2D);
        drawProjectiles(graphics2D);

        // create subimages from g2
        checkBound();
        p1w = bimg.getSubimage(this.p1WndBndX, this.p1WndBndY, windowWidth/2, windowHeight);
        p2w = bimg.getSubimage(this.p2WndBndX, this.p2WndBndY, windowWidth/2, windowHeight);
        minimap = bimg.getScaledInstance(minimapWidth, minimapHeight, Image.SCALE_SMOOTH);
    }

    private void checkBound() {
    	
        if ((this.p1WndBndX = tank1.getTankCenterX() - windowWidth / 4) < 0) {
            this.p1WndBndX = 0;
        } else if (this.p1WndBndX >= mapWidth - windowWidth / 2) {
            this.p1WndBndX = (mapWidth - windowWidth / 2);
        }

        if ((this.p1WndBndY = tank1.getTankCenterY() - windowHeight / 2) < 0) {
            this.p1WndBndY = 0;
        } else if (this.p1WndBndY >= mapHeight - windowHeight) {
            this.p1WndBndY = (mapHeight - windowHeight);
        }

        if ((this.p2WndBndX = tank2.getTankCenterX() - windowWidth / 4) < 0) {
            this.p2WndBndX = 0;
        } else if (this.p2WndBndX >= mapWidth - windowWidth / 2) {
            this.p2WndBndX = (mapWidth - windowWidth / 2);
        }

        if ((this.p2WndBndY = tank2.getTankCenterY() - windowHeight / 2) < 0) {
            this.p2WndBndY = 0;
        } else if (this.p2WndBndY >= mapHeight - windowHeight) {
            this.p2WndBndY = (mapHeight - windowHeight);
        }
    }

    /********************  Draw Part  ***********************/
    
    private void drawBackground(Graphics2D g) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 14; j++) {
                g.drawImage(this.bgImg, this.bgImg.getWidth() * i, this.bgImg.getHeight() * j, this);
            }
        } // end loops
    }

    private void drawMapLayout(Graphics2D g) {
        walls.forEach((curr) -> {
            curr.draw(g);
        });
        bwalls.forEach((curr) -> {
            curr.draw(g);
        });
        pups.forEach((curr) -> {
            curr.draw(g);
        });
    }

    private void drawTanks(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;

        this.tank1.draw(g2);
        this.tank2.draw(g2);
    }

    private synchronized void drawProjectiles(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;

        try {
            bullets.forEach((curr) -> {
                if (curr.isVisible()) {
                    curr.draw(this, g2);
                }

            });
        } catch (ConcurrentModificationException e) {
        }
    }

    private void drawPlayerStatus(Graphics g) {

        int p1_health = this.tank1.getHealth() * 2;
        int p2_health = this.tank2.getHealth() * 2;

        int p1_lives = this.tank1.getLife();
        int p2_lives = this.tank2.getLife();

        int p1_health_x = 22;
        int p1_health_y = 658;

        int p2_health_x = 778;
        int p2_health_y = 658;

        int health_width = 200;
        int health_height = 20;

        int coord_offset = 3;
        int size_offset = 6;

        // HEALTH FRAME
        g.setColor(Color.DARK_GRAY);
        g.fillRect(p1_health_x, p1_health_y, health_width, health_height); // p1
        g.fillRect(p2_health_x, p2_health_y, health_width, health_height); // p2

        // HEALTH DEPLIETED
        g.setColor(Color.GRAY);
        g.fillRect(p1_health_x + coord_offset, p1_health_y + coord_offset,
                health_width - size_offset, health_height - size_offset); // p1
        g.fillRect(p2_health_x + coord_offset, p2_health_y + coord_offset,
                health_width - size_offset, health_height - size_offset); // p2

        // HEALTH AVAILABLE
        g.setColor(Color.GREEN);
        g.fillRect(p1_health_x + coord_offset, p1_health_y + coord_offset,
                p1_health - size_offset, health_height - size_offset); // p1
        g.fillRect(p2_health_x + (health_width - p2_health) + coord_offset, p2_health_y + coord_offset,
                p2_health - size_offset, health_height - size_offset); // p2

        // Player 1 lives
        int p1_life_x = 230;
        int p1_life_y = 648;
        int p1_life_offset = 40;
        for (int i = 0; i < p1_lives; i++) {
            g.drawImage(lifeIcon1, p1_life_x + (i * p1_life_offset), p1_life_y, this);
        }

        // Player 2 lives
        int p2_life_x = 738;
        int p2_life_y = 648;
        int p2_life_offset = 40;
        for (int i = 0; i < p2_lives; i++) {
            g.drawImage(lifeIcon2, p2_life_x - (i * p2_life_offset), p2_life_y, this);
        }
    }

    private BufferedImage setImage(String filepath) {
        BufferedImage img = null;
        try {
        	img = ImageIO.read(this.getClass().getResourceAsStream("/" + filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public void setBackgroundImage(BufferedImage img) {
        this.bgImg = img;
    }

    public void setMapObjects(ArrayList<Wall> w, ArrayList<BWall> b, ArrayList<HealthUp> p) {
        this.walls = w;
        this.bwalls = b;
        this.pups = p;
    }

    public void setTanks(MyTank tank1, MyTank tank2) {
        this.tank1 = tank1;
        this.tank2 = tank2;
    }

    public void setBullet(ArrayList<Bullet> p) {
        this.bullets = p;
    }

    public void setLifeIcons(BufferedImage img1, BufferedImage img2) {
        this.lifeIcon1 = img1;
        this.lifeIcon2 = img2;
    }

    public BufferedImage getBackgroundImage() {
        return this.bgImg;
    }
}