package TankGame.GameObject.BaseObject;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * GameObject Class
 * @author Christopher
 * 
 * This is for the base game object.
 * All game objects are based on this.
 * */

public abstract class GObject extends JComponent {

    public int x;
    public int y;
    protected BufferedImage img;

    protected Rectangle objRtg;
    protected int width, height;
    
    public GObject(){}
    
    public GObject(int x, int y, int width, int height, BufferedImage img) {
    	
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.objRtg = new Rectangle(x, y, this.width, this.height);
    }
    
    public GObject(BufferedImage img, ImageObserver observer) {

        this.img = img;
        this.x = 0;
        this.y = 0;
        
        try {
            this.width = img.getWidth(observer);
            this.height = img.getHeight(observer);
        } catch (Exception e) {
        	
            this.width = 0;
            this.height = 0;
        }
        
        this.objRtg.setBounds(this.x, this.y, this.width, this.height);
    }
    
    public GObject(int x, int y, BufferedImage img, ImageObserver observer) {
    	
        this.img = img;
        this.x = x;
        this.y = y;
        
        try {
            this.width = img.getWidth(observer);
            this.height = img.getHeight(observer);
        } catch (Exception e) {

            this.width = 0;
            this.height = 0;
        }
        
        this.objRtg = new Rectangle(x, y, this.width, this.height);
    }
	    
    @Override
    public void setLocation(int newX, int newY) {
    	
        this.x = newX;
        this.y = newY;
        this.objRtg = new Rectangle(newX, newY);
    }
    
    @Override
    public void setLocation(Point newLocation){
    	
        this.x = newLocation.x;
        this.y = newLocation.y;
    	this.objRtg.setLocation(newLocation);
    }

    public void setWidth(int newWidth) {
        this.width = newWidth;
        this.objRtg.setSize(newWidth, this.height);
    }
    
    public void setHeight(int newHeight) {
        this.height = newHeight;
        this.objRtg.setSize(this.width, newHeight);
    }
    
    @Override
    public void setSize(int newWidth, int newHeight) {
        this.width = newWidth;
        this.height = newHeight;
        this.objRtg.setSize(newWidth, newHeight);
    }
    
    @Override
    public void setSize(Dimension dim) {
        this.objRtg.setSize(dim);
    }
    
    public void setObjectRectangle(int x, int y, int width, int height) {
        this.objRtg = new Rectangle(x, y, width, height);
    }
    
    public void setImage(BufferedImage img) {
        this.img = img;
    }
    
    public void setImage(BufferedImage img, ImageObserver observer){
    	
    	this.img = img;
        try {
            this.height = img.getWidth(observer);
            this.width = img.getHeight(observer);
		} catch (Exception e) {
	            this.height = 0;
		    this.width = 0;
		}
        this.objRtg.setSize(this.width, this.height);
    }
    
    public void setX(int newX) {
    	
        this.x = newX;
        this.objRtg.setLocation(newX, this.y);
    }
    
    public void setY(int newY) {
    	
        this.y = newY;
        this.objRtg.setLocation(this.x, newY);
    }
        
    @Override
    public Point getLocation() {
    	return new Point(this.x, this.y);
    }
    
    @Override
    public int getWidth() {
    	return this.width;
    }
    
    @Override
    public int getHeight() {
    	return this.height;
    }
    
    @Override
    public Dimension getSize() {
        return this.objRtg.getSize();
    }
    
    public Rectangle getObjectRectangle() {
    	return this.objRtg;
    }
    
    public Image getImage() {
        return this.img;
    }
    
    @Override
    public int getX() {
    	return this.x;
    }
    
    @Override
    public int getY() {
    	return this.y;
    }
}