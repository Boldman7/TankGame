package TankGame.GameObject.BaseObject;

import java.awt.image.BufferedImage;

/**
 * Movable Class
 * @author Christopher
 * 
 * This for the movable game objects.
 * */

public class Movable extends GObject {

    protected int speed; 
    
    public Movable(){}
    
    public Movable(BufferedImage img, int x, int y, int speed){
        super(x, y, img, null);
        this.speed = speed;
    }
    
}