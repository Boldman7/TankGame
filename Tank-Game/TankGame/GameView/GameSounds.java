package TankGame.GameView;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * GameSounds Class
 * @author Christopher
 * 
 * This plays all sounds when it is needed.
 * */

public class GameSounds {

	
	private AudioInputStream soundStream; 
	private Clip clip;
   
   /* 1 : for sounds that needs to be played all the time
	  2 : for sounds that only need to be played once */
   private int type;
   
   public GameSounds(int type, String soundFile){
  
	   this.type = type;
       try{
           //soundStream = AudioSystem.getAudioInputStream(GameSounds.class.getResource(this.soundFile));
           soundStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource(soundFile));
           clip = AudioSystem.getClip();
           clip.open(soundStream);
       }
       catch(Exception e){
    	   e.printStackTrace();
       }
       
       if(this.type == 1){
           Runnable myRunnable = new Runnable(){
               @Override
               public void run(){
                   while(true){
                       clip.start();
                       clip.loop(clip.LOOP_CONTINUOUSLY);
                       try {
                           Thread.sleep(10000);
                       } catch (InterruptedException ex) {
                           Logger.getLogger(GameSounds.class.getName()).log(Level.SEVERE, null, ex);
                       }
                    }
               }
           };
           Thread thread = new Thread(myRunnable);
           thread.start();
       }
   }
   
   public void play(){
       clip.start();
   }
   public void stop(){
       clip.stop();
   }
   
   public Clip getClip() {
       return this.clip;
   }
}
