/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModel;

import model.GameObject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Satria Ramadhani
 */
public class Controller extends KeyAdapter implements KeyListener
{
    /**
     * Attribute declaration.
     */
    
    private Game game;
    private Handler handler;
    
    /**
     * Constructor.
     */
    
    // Default constructor.
    public Controller()
    {
        this.game = new Game();
        this.handler = new Handler();
    }
    
    // Constructor with controller data.
    public Controller(Game game, Handler handler)
    {
        this.game = game;
        this.handler = handler;
    }
    
    /**
     * Getter and Setter.
     */

    /* Controller's game. */
    
    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    /* Controller's handler. */
    
    public Handler getHandler()
    {
        return handler;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }
    
    /**
     * Public methods.
     */
    
    // Override trait when key is pressed.
    @Override
    public synchronized void keyPressed(KeyEvent e)
    {
//        System.out.println("Pressed");
        
        // Get key code (what key that pressed?).
        int key = e.getKeyCode();
        if(game.isRunning())
        {
            // Searching for player object.
            int i = 0; boolean found = false;
            while((found == false) && (i < handler.count()))
            {
                if(handler.get(i).getType().equals("Player"))
                {
                    found = true;
                }
                else
                {
                    i++;
                }
            }
            int scoreBefore = game.getScore();
            // Set the object and do the handling.
            GameObject temp = handler.get(i);
            if((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP))
            {
                // Move up.
                temp.setVelY(-5);
                game.setScore(+1);
            }
            if((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT))
            {
                // Move left.
                temp.setVelX(-5);
                game.setScore(+2);
            }
            if((key == KeyEvent.VK_S) || (key == KeyEvent.VK_DOWN))
            {
                // Move down.
                temp.setVelY(+5);
                game.setScore(+3);
            }
            if((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT))
            {
                // Move right.
                temp.setVelX(+5);
                game.setScore(+4);
            }
            
            if(scoreBefore != game.getScore()){
                game.setFinalScore();
            }
        }
    }
    
    // Override trait when key is released from being pressed.
    @Override
    public synchronized void keyReleased(KeyEvent e)
    {
//        System.out.println("Released");
        
        // Get key code (what key that released?).
        int key = e.getKeyCode();
        if(game.isRunning())
        {
            // Searching for player object.
            int i = 0; boolean found = false;
            while((found == false) && (i < handler.count()))
            {
                if(handler.get(i).getType() == "Player")
                {
                    found = true;
                }
                else
                {
                    i++;
                }
            }
            
            // Set the object and do the handling.
            GameObject temp = handler.get(i);            
            if(key == KeyEvent.VK_SPACE)
            {
                // Close the game.
                game.setRunning(false);
                game.close();
            }
            if((key == KeyEvent.VK_W) || (key == KeyEvent.VK_UP))
            {
                // Stop from being moved up.
                temp.setVelY(0);
            }
            if((key == KeyEvent.VK_A) || (key == KeyEvent.VK_LEFT))
            {
                // Stop from being moved left.
                temp.setVelX(0);
            }
            if((key == KeyEvent.VK_S) || (key == KeyEvent.VK_DOWN))
            {
                // Stop from being moved down.
                temp.setVelY(0);
            }
            if((key == KeyEvent.VK_D) || (key == KeyEvent.VK_RIGHT))
            {
                // Stop from being moved right.
                temp.setVelX(0);
            }
        }
    }
}
