/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import viewModel.Game;

/**
 *
 * @author Satria Ramadhani
 */
public class Player extends GameObject
{
    /**
     * Constructor.
     */
    
    // Default constructor.
    public Player()
    {
        super(0, 0, "Player");
        super.setHeight(30);
    }
    
    // Constructor with player position.
    public Player(int x, int y)
    {
        super(x, y, "Player");
        super.setHeight(30);
    }
    
    // Constructor with player position.
    public Player(int x, int y, String type)
    {
        super(x, y, type);
        super.setHeight(30);
    }
    
    /**
     * Override interface.
     */
    
    @Override
    public void render(Graphics object)
    {
        // Set player shape.
        object.setColor(Color.decode("#87CEEB"));
        if(this.type.equals("Player")){
            object.fillOval(x, y, 30, 30);
            this.height = 30;
            this.width = 30;
        }
        if(this.type.equals("Rintangan")){
            object.fillRect(x, y, 100, 200);
            this.height = 200;
            this.width = 100;
        }
        
    }
    
    @Override
    public void loop()
    {
        // Initialize velocity, so object can move.
        this.x += this.velX;
        this.y += this.velY;
        
        // Initialize player bound, so it won't get offset the display.
        x = Game.clamp(x, 0, (Game.width - 50));
        y = Game.clamp(y, 0, (Game.height - 70));
    }
}
