package TankGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import TankGame.GameObject.MainObject.MyTank;

/**
 * KeyInputEvent Class
 * @author Christopher
 * 
 * This processes key events when user clicks any key.
 * */

public class KeyInputEvent extends Observable implements KeyListener {
    
    private final MyTank tank;

    private final int keyShoot;
    private final int keyUp;
    private final int keyDown;
    private final int keyLeft;
    private final int keyRight;

    public KeyInputEvent(MyTank tank) {
    	
        this.tank = tank;
        
        this.keyShoot = tank.getShootKey();
        this.keyUp = tank.getUpKey();
        this.keyDown = tank.getDownKey();
        this.keyLeft = tank.getLeftKey();
        this.keyRight = tank.getRightKey();
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == this.keyUp) {
            this.tank.switchUpOn();
        }
        if (key == this.keyDown) {
            this.tank.switchDownOn();
        }
        if (key == this.keyLeft) {
            this.tank.switchLeftOn();
        }
        if (key == this.keyRight) {
            this.tank.switchRightOn();
        }
        if (key == this.keyShoot) {
            this.tank.switchShootOn();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == this.keyUp) {
            this.tank.switchUpOff();
        }
        if (key == this.keyDown) {
            this.tank.switchDownOff();
        }
        if (key == this.keyLeft) {
            this.tank.switchLeftOff();
        }
        if (key == this.keyRight) {
            this.tank.switchRightOff();
        }
        if (key == this.keyShoot) {
            this.tank.switchShootOff();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
