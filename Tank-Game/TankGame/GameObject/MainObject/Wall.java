
package TankGame.GameObject.MainObject;

import TankGame.Main;
import TankGame.GameObject.BaseObject.Unmovable;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Wall Class
 * @author Christopher
 * 
 * This is for the normal wall. (UnMovable)
 * */

public class Wall extends Unmovable implements Observer {
	
    Rectangle wallRect;
    
    public Wall(int x, int y, int width, int height, BufferedImage img) {
    	
        super(x, y, width, height, img);
        wallRect = new Rectangle(x,y,width,height);
    }
    
    public void draw(Graphics g) {
        g.drawImage(this.img, this.x, this.y, this);
    }

    @Override
    public void update(Observable o, Object arg) {
        update();
    }
    
    public void update(){
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
    
    public Rectangle getWallRectangle() {
        return wallRect;
    }
}
