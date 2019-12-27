package TankGame.GameObject.MainObject;

import TankGame.Main;
import TankGame.GameObject.BaseObject.Movable;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

/**
 * Projectile Class
 * @author Christopher
 * 
 * This is for the projectile object. (Movable)
 * */

public class Bullet extends Movable implements Observer {

    private final MyTank p1 = Main.getMyTank(1);
    private final MyTank p2 = Main.getMyTank(2);

    public static MyTank currentTank;
    
    private final BufferedImage bullet;
    
    private int theta;
    private int damage;
    private Main obj;
    public int xSize;
    public int ySize;
    public boolean visible;

    public Bullet(Main tw, BufferedImage img, int speed, MyTank t, int dmg) {
    	
        super(img, t.getTankCenterX(), t.getTankCenterY(), speed);
        
        visible = true;
        
        bullet = img;
        damage = dmg;
        xSize = img.getWidth(null);
        ySize = img.getHeight(null);
        
        currentTank = t;
        theta = currentTank.getAngle();

        this.obj = tw;
    }

    public void setTankWorld(Main tw) {
        this.obj = tw;
    }

    public static MyTank getTank() {
        return currentTank;
    }

    @Override
    public void update(Observable o, Object arg) {
        update();
    }

    public void draw(ImageObserver iobs, Graphics2D g) {
    	
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(theta), 0, 0);
        g.drawImage(bullet, rotation, iobs);
    }

    public int getTheta() {
        return this.theta;
    }
    
    @Override
    public boolean isVisible () {
        return this.visible;
    }

    public void update() {
    	
        y += Math.round(speed * Math.sin(Math.toRadians(theta)));
        x += Math.round(speed * Math.cos(Math.toRadians(theta)));

        if (p1.collision(this) && visible && currentTank != p1 && visible && p1.coolDown <= 0) {
        	
            if (visible) {
            	
                obj.playSound(3);// breakable collision sound
                obj.getSound(3).getClip().setFramePosition(0);
            }
            
            visible = false;
            p1.bulletDamage(damage);
            
        } else if (p2.collision(this) && visible && currentTank != p2 && visible && p2.coolDown <= 0) {
        	
            if (visible) {
                obj.playSound(3);// breakable collision sound
                obj.getSound(3).getClip().setFramePosition(0);
            }
            visible = false;
            p2.bulletDamage(damage);
        } else 
            for (int i = 0; i < obj.getWallSize(); i++){
            	
	            Wall tempWall = obj.getWalls().get(i);
	            
	            if ((tempWall.getWallRectangle().intersects(this.x, this.y, this.width, this.height)) && visible) {
	            	
	                this.visible = false;
	                obj.playSound(2);// unbreakable collision sound
	                obj.getSound(2).getClip().setFramePosition(0);
	            }
	          
	            for (int j = 0; j < obj.getBWallSize(); j++){
	            	
	            BWall tempWall2 = obj.getBWalls().get(j); 
	            
	            if((tempWall2.getWallRectangle().intersects(this.x, this.y, this.width, this.height)) && visible){
	            	
	                obj.getBWalls().remove(j);
	                tempWall2.breakWall();
	                this.visible = false;
	                obj.playSound(3);// breakable collision sound
	                obj.getSound(3).getClip().setFramePosition(0);
	            }
            }
        }
    }
}
