package TankGame.GameObject.MainObject;

import TankGame.Main;
import TankGame.GameObject.BaseObject.GObject;
import TankGame.GameObject.BaseObject.Movable;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Tank Class
 * @author Christopher
 * 
 * This is for the tank object. (Movable)
 * */

public class MyTank extends Movable implements Observer {

    private Main mainObj;
    private MyTank p1, p2;
    
    private boolean isDead;
    private boolean moveLeft, moveRight, moveUp, moveDown, shoot;
    private int left, right, up, down;
    
    protected int spawnPointX, spawnPointY;
    
    private int shootKey;
    private int shootCoolDown = 0;
    protected int coolDown = 0;
    protected int score = 0;
    protected int health = 100;
    protected int life = 3;
    private int angle = 0;
    
    private int mapSizeX, mapSizeY;

    public MyTank() {
    }

    public MyTank(Main obj, BufferedImage img, int x, int y, int speed, int left, int right, int up, int down, int shootKey) {
    	
        super(img, x, y, speed);
        
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.shootKey = shootKey;
        this.moveLeft = false;
        this.moveRight = false;
        this.moveUp = false;
        this.moveDown = false;
        this.shoot = false;
        this.isDead = false;
        this.spawnPointX = x;
        this.spawnPointY = y;
        this.mainObj = obj;
        this.setBounds(8, 10, 49, 44);
        this.mapSizeX = obj.getMapWidth();
        this.mapSizeY = obj.getMapHeight();
    }

    public boolean collision(GObject go) {
        objRtg = new Rectangle(this.x, this.y, this.width, this.height);
        Rectangle objectRectangle2 = new Rectangle(go.getX(), go.getY(), go.getWidth(), go.getHeight());
        if ((this.objRtg.intersects(objectRectangle2)) & (!isDead)) {
            return true;
        }
        return false;
    }

    public void setOtherTank(MyTank otherTank) {
        this.p1 = new MyTank();
        this.p1 = this;
        this.p2 = new MyTank();
        this.p2 = otherTank;
    }

    public void setAngle(int a) {
        this.angle = a;
    }
    
    public void setScore(int s) {
        this.score += s;
    }

    public void setHealthPoints(int hp) {
        this.health = hp;
    }
    

    public void bulletDamage(int dmg) {
        if (coolDown <= 0) // originally not used
            this.health -= dmg;
    }

    public void healthUp() {
        if (this.health < 100){
            this.health = 100;
        }
    }

    public void switchUpOn() {
        this.moveUp = true;
    }

    public void switchDownOn() {
        this.moveDown = true;
    }

    public void switchLeftOn() {
        this.moveLeft = true;
    }

    public void switchRightOn() {
        this.moveRight = true;
    }

    public void switchUpOff() {
        this.moveUp = false;
    }

    public void switchDownOff() {
        this.moveDown = false;
    }

    public void switchLeftOff() {
        this.moveLeft = false;
    }

    public void switchRightOff() {
        this.moveRight = false;
    }

    public void switchShootOn() {
        this.shoot = true;
    }

    public void switchShootOff() {
        this.shoot = false;
    }

    public int getTankCenterX() {
        return x + img.getWidth(null) / 2;
    }

    public int getTankCenterY() {
        return y + img.getHeight(null) / 2;
    }

    public int getHealth() {
        return health;
    }

    public int getAngle() {
        return this.angle;
    }

    public int getLife() {
        return this.life;
    }

    public int getScore() {
        return this.score;
    }

    public int getUpKey() {
        return this.up;
    }

    public int getDownKey() {
        return this.down;
    }

    public int getLeftKey() {
        return this.left;
    }

    public int getRightKey() {
        return this.right;
    }

    public int getShootKey() {
        return this.shootKey;
    }

    public boolean isDead() {
        if (this.life <= 0) {
            return true;
        }
        return false;
    }


    public void draw(Graphics2D g) {
    	
        p1 = Main.getMyTank(1);
        p2 = Main.getMyTank(2);
        
        this.shootCoolDown -= 1;
        if (this.health <= 0) {
            isDead = true;
            if (life <= 0)
                mainObj.playSound(1);
        }
        if ((health > 0) && (coolDown == 0) && (life > 0)) {
            isDead = false;
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), img.getWidth(null) / 2, img.getHeight(null) / 2);
            g.drawImage(img, rotation, null);
            if ((p1.collision(p2))) {
                if (p1.x > x) {
                    p1.x += speed * 5;
                    p2.x -= speed * 5;
                } else if (p1.x < x) {
                    p1.x -= speed * 5;
                    p2.x += speed * 5;
                }
                if (p1.y > y) {
                    p1.y += speed * 5;
                    p2.y -= speed * 5;
                } else if (p1.y < y) {
                    p1.y -= speed * 5;
                    p2.y += speed * 5;
                }
            }
        } else if ((isDead == true) && (coolDown == 0) && (life > 0)) {
            coolDown = 20; // original: 180
            if (life > 1) {
                mainObj.playSound(0); // normal death sound
                mainObj.getSound(0).getClip().setFramePosition(0);
            }
            if (--life >= 0) {
                if (life > 0) // FIXME: wasteful
                    health = 100; // 4
            }
            isDead = false;
            x = spawnPointX;
            y = spawnPointY;
        } else {
            coolDown -= 1;
        }
    }

    @Override
    public void update(Observable obj, Object arg) {
        shoot(this);
        update();
    }

    private void shoot(MyTank a) {
        if (shoot && shootCoolDown <= 0 && coolDown <= 0 && life > 0) { // added coolDown check => fixes shooting when spawning
            Bullet newBullet = new Bullet(this.mainObj, mainObj.getProjectileImg(), 5, this, 10);
            mainObj.getProjectile().add(newBullet);
            mainObj.addBulletToObservable(newBullet);
            this.shootCoolDown = 10;
        }
    }

    private void checkLimit() {
        if (x < 0) {
            x = 0;
        }
        if (x >= mapSizeX) {
            x = mapSizeX;
        }
        if (y < 0) {
            y = 0;
        }
        if (y >= mapSizeY) {
            y = mapSizeY;
        }
    }


    public void update() {
        if (moveLeft == true){
            angle -= 3;
        }
        if (moveRight == true){
            angle += 3;
        }
        if (moveUp == true){
            x = ((int) (x + Math.round(speed * Math.cos(Math.toRadians(angle)))));
            y = ((int) (y + Math.round(speed * Math.sin(Math.toRadians(angle)))));
            checkLimit();
        }
        if (moveDown == true){
            x = ((int) (x - Math.round(speed * Math.cos(Math.toRadians(angle)))));
            y = ((int) (y - Math.round(speed * Math.sin(Math.toRadians(angle)))));
            checkLimit();
        }

        if (angle == -1) {
            angle = 359;
        } else if (angle == 361) {
            angle = 1;
        }

        if (coolDown > 0) {
            moveLeft = false;
            moveRight = false;
            moveUp = false;
            moveDown = false;
        }

    }
}