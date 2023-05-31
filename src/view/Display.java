/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import viewModel.Game;

/**
 *
 * @author Satria Ramadhani
 */
public class Display extends Canvas
{
    // JFrame declaration.
    private JFrame frame;
    
    /**
     * Constructor.
     */
    
    // Default constructor.
    public Display()
    {
        this.frame = new JFrame();
    }
    
    // Constructor with Frame data.
    public Display(int width, int height, String title)
    {
        // Initialize frame and its dimension.
        this.frame = new JFrame(title);        
        this.frame.setPreferredSize(new Dimension(width, height));
        this.frame.setMinimumSize(new Dimension(width, height));
        this.frame.setMaximumSize(new Dimension(width, height));
        
        // Initialize additional options.
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        
        this.frame.setResizable(false);
    }
    
    // Open / show game display. 
    public void open(Game game)
    {
        this.frame.add(game);
        this.frame.setVisible(true);
        
        game.start();
    }
    
    // Close game display.
    public void close()
    {
        this.frame.setVisible(false);
        this.frame.dispose();
    }
}
