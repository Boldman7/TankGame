package TankGame.GameView;

import java.awt.event.KeyEvent;
import java.util.Observable;

import TankGame.GameObject.BaseObject.GObject;

/**
 * GameObservable Class
 * @author Christopher
 * 
 * */

public class GameObservable extends Observable{
    private int type;
    private Object caller, target;

    public void setCollision(GObject caller, GObject target)
    {
        type = 1;
        this.caller = caller;
        this.target = target;

        setChanged();

        this.notifyObservers(this);
    }

    public void setKeys(KeyEvent key)
    {
        type = 2;
        this.target = key;
        setChanged();

        notifyObservers(this);
    }
    
    @Override
	public synchronized void setChanged() {
        super.setChanged();
    }
       
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }

    public Object getCaller()
    {
        return caller;
    }

    public Object getTarget()
    {
        return target;
    }
     
}
