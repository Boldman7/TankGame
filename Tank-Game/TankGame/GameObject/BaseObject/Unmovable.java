
package TankGame.GameObject.BaseObject;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Unmovable Class
 * @author Christopher
 * 
 * This is for the unmovable game object.
 * */

public class Unmovable extends GObject {
    
    public Unmovable() {}
    
    public Unmovable(BufferedImage img, ImageObserver observer) {
        super(img, observer);
    }
    
    public Unmovable(int x, int y, int width, int height, BufferedImage img) {
        super(x, y, width, height, img);
    }
    
    public Unmovable(int x, int y, BufferedImage img, ImageObserver observer) {
        super(x, y, img, observer);
    }
    
  }
