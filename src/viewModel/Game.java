/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewModel;

import viewModel.Controller;
import view.Display;
import model.Player;
import model.GameObject;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.Random;
import viewModel.Controller;
import viewModel.Handler;

/**
 *
 * @author Satria Ramadhani
 */
public class Game extends Canvas implements Runnable
{
    /**
     * 
     * Attribute declaration.
     */
    
    /* View-related attributes. */
    public static final int width = 640;
    public static final int height = 480;
    private Display display;
    
    /* Process-related attributes. */
    private boolean running;
    private Handler handler;
    private Thread thread;
    
    /* Animation-related attributes. */
    private boolean startCounting = false;
    private int score = 0;
    private int counter = 0;
    private int stateCounter = 0;
    private int direction = 0;
    private int finalScore = 0;
    private int totalFinish = 0;
    
    // Default constructor.
    public Game()
    {
        try
        {
            // Initialize display.
            display = new Display(width, height, "Synchronization Tutorial");
            display.open(this); 
            
            // Initialize game handler.
            handler = new Handler();
            
            // Initialize controller (keyboard input).
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(new Controller(this, handler));
            
            // Initialize all object.
            running = true;
            Random random = new Random();
            if(running)
            {
                handler.add(new Player(320, 160));
                int x = random.nextInt(480);
                int y = random.nextInt(320);
                handler.add(new Player(x, y, "Rintangan"));
            }
        } catch(Exception e)
        {
            System.err.println("Failed to instance data.");
        }
    }
    
    /**
     * 
     * Getter and Setter.
     */
    
    /* Game running status. */
    
    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }
    
    /* Game score. */
    
    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
    
    public void setFinalScore(){
        this.finalScore += 1;
    }
    
    /**
     * 
     * Public methods.
     */
    
    // Clamp, so player won't get offset the display bound.
    public static int clamp(int var, int min, int max)
    {
        if(var >= max)
        {
            return var = max;
        }
        else if(var <= min)
        {
            return var = min;
        }
        
        return var;
    }
    
    // Close display.
    public void close()
    {
        display.close();
    }
    
    /**
     * 
     * Game controller.
     */
    
    // Start threading.
    public synchronized void start()
    {
        thread = new Thread(this);
        thread.start(); running = true;
        
    }
    
    // Stop threading.
    public synchronized void stop()
    {
        try
        {
            thread.join();
            running = false;
        }
        catch(InterruptedException e)
        {
            System.out.println("Thread error : " + e.getMessage());
        }
    }
    
    // Initialize game when it run for the first time.
    public void render()
    {
        // Use buffer strategy.
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null)
        {
            this.createBufferStrategy(SOMEBITS);
            return;
        }
        
        // Initialize graphics.
        Graphics g = bs.getDrawGraphics();
        Image bg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/game.jpg"));
        g.drawImage(bg, 0, 0, null);
        
        if(running == true)
        {
            // Render handler.
            handler.render(g);
            
            // Render score.
            Font oldFont = g.getFont();
            Font newFont = oldFont.deriveFont(oldFont.getSize() * 1.3f);
            g.setFont(newFont);
            
//            g.setColor(Color.blue);
//            g.drawString("Score : " + Integer.toString(score), 20, 30);
            g.setColor(Color.white);
            g.drawString("Final Score : " + Integer.toString(finalScore), 20, 50);
            
        }
        
        // Loop the process so it seems like "FPS".
        g.dispose();
        bs.show();
    }
    
    // Main loop proccess.
    public void loop()
    {
        GameObject player = null;
        GameObject rintangan = null;
        Random random = new Random();
        
        handler.loop();
        if(this.running)
        {   
            counter++;
            if(startCounting)
            {
                stateCounter++;
            }
            
            if(stateCounter >= 40)
            {
                stateCounter = 0;
                startCounting = false;
            }
            
            if(counter >= 50)
            {
                direction = (direction == 0) ? 1 : 0;
                counter = 0;
            }
            
            
            for(int i = 0; i < handler.count(); i++)
            {
                if(handler.get(i).getType().equals("Player"))
                {
                    player = handler.get(i);
                }
                if(handler.get(i).getType().equals("Rintangan")){
                    rintangan = handler.get(i);
                }
            }

            if (rintangan.getBoundingBox().contains(player.getBoundingBox())) {
                int x = random.nextInt(480);
                int y = random.nextInt(320);
                rintangan.setX(x);
                rintangan.setY(y);
                System.out.println("masuk");
                this.finalScore += 10;
            }
        }
    }
    
    /**
     * 
     * Override interface.
     */
    
    @Override
    public void run()
    {
        double fps = 60.0;
        double ns = (1000000000 / fps);
        double delta = 0;
        
        // Timer attributes.
        long time = System.nanoTime();
        long now = 0;
        long timer = System.currentTimeMillis();
        
        int frames = 0;
        while(running)
        {
            now = System.nanoTime();
            delta += ((now - time) / ns);
            time = now;
            
            while(delta > 1)
            {
                loop();
                delta--;
            }
            
            if(running)
            {
                render();
                frames++;
            }
            
            if((System.currentTimeMillis() - timer) > 1000)
            {
                timer += 1000;
                frames = 0;
            }
        }
        
        stop();
    }
}
