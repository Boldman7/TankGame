package TankGame.GameObject.MainObject;

import TankGame.Main;
import TankGame.GameObject.BaseObject.Unmovable;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * HealthUp Class
 * @author Christopher
 * 
 * This is for the health up object.  (UnMovable)
 * It's located in the center of the scene.
 * */

public class HealthUp extends Unmovable implements Observer {
    
    boolean flag = false;
    
    public HealthUp(int x, int y, int width, int height, BufferedImage img) {
        super(x, y, width, height, img);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        update();
    }
    
    public void update(){
    	
		MyTank tank1 = Main.getMyTank(1);
		MyTank tank2 = Main.getMyTank(2);
        
		if (tank1.collision(this)) {
			
			flag = true;
			tank1.healthUp();
			
		} else if (tank2.collision(this)) {
	    	
	    	flag = true;
	    	tank2.healthUp();
		}
    }
    
    public void draw(Graphics g) {
    	
        if (!flag)
            g.drawImage(this.img, this.x, this.y, this);
    }
}
