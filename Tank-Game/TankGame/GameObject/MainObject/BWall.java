package TankGame.GameObject.MainObject;

import TankGame.Main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

/**
 * BWall Class
 * @author Christopher
 * 
 * This is for the breakable wall. (UnMovable)
 * */

public class BWall extends Wall implements Observer {
	
    private boolean flagBreak = false;
    Rectangle wallRect;
    
    public BWall(int x, int y, int width, int height, BufferedImage img) {
    	
        super(x, y, width, height, img);
        wallRect = new Rectangle(x, y, width, height);
    }
    
    public void breakWall(){
        flagBreak = true;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        update();
    }
    
    @Override
    public void update(){
    	
        if(!flagBreak){
        	
	        MyTank p1 = Main.getMyTank(1);
	        MyTank p2 = Main.getMyTank(2);
	
	        if (p1.collision(this)) {
	            if (p1.x > (x)) { 
	                p1.x += 3;
	            } else if (p1.x < (this.x)) {
	                p1.x -= 3;
	            }
	            if (p1.y > (this.y)) {
	                p1.y += 3;
	            } else if (p1.y < this.y) {
	                p1.y -= 3;
	            }
	        }
	        
	        if (p2.collision(this)) {
	            if (p2.x > (x)) {
	                p2.x += 3;
	            } else if (p2.x < (this.x)) {
	                p2.x -= 3;
	            }
	            if (p2.y > (this.y)) {
	                p2.y += 3;
	            } else if (p2.y < this.y) {
	                p2.y -= 3;
	            }
	        }
        }
    }
    
    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(this.img, this.x, this.y, obs);
    }
}
