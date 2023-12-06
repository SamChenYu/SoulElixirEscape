package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class UI extends JPanel implements Runnable {
    
    private KeyHandler keyH = new KeyHandler();
    private Player player;
    private SoulPotion soulp;
    Map map = new Map();
    ArrayList<Ghost> ghost = new ArrayList<>();
    boolean deathMessage = false;
    boolean winMessage = false;
    
    final int FPS = 60;
    private Thread gameThread;
    
    int width, height;
    
    public UI(int width, int height) {
        this.width = width;
        this.height = height;
        soulp = new SoulPotion(width, height);
        player = new Player(keyH, width, height, soulp, this, ghost);
        
        
        setBackground(Color.BLACK);
        addKeyListener(keyH);
        setFocusable(true);
        requestFocus();
        startThread();
        
    }
    
    public void startThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void update() {
        player.update();
        for(int i=0; i<ghost.size(); i++) {
            ghost.get(i).update();
        }
        
    }
    
    public void addGhost(int type) {
        Ghost tempGhost = new Ghost(player, width, height, type);
        ghost.add(tempGhost);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        map.draw(g2);
        soulp.draw(g2);
        player.draw(g2);
        for(int i=0; i<ghost.size(); i++) {
            ghost.get(i).draw(g2);
        }

        
        if(deathMessage) {
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.setColor(Color.GREEN);

            // Draw text
            g2.drawString("YOU ARE DEAD", width/2-200, height/2);
        }
        
        if(winMessage) {
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.setColor(Color.GREEN);

            // Draw text
            g2.drawString("YOU WIN!!!", width/2-140, height/2);
        }
        
        
        if(keyH.hitboxes) {
            player.drawHitbox(g2);
            soulp.drawHitbox(g2);
            for(int i=0; i<ghost.size(); i++) {
                ghost.get(i).drawHitbox(g2);
            }
            
        }
        
        g2.dispose();
    }
    
    public void reset() {
        ghost = new ArrayList<Ghost>();
        player = new Player(keyH, width, height, soulp, this, ghost);
        deathMessage = false;
        soulp.reposition();
        winMessage = false;
        
    }

    @Override
    public void run() {
        int fpsCount = 0; // counter
        double time = 0;
        double totalTime = 0;

        double drawInterval = 1000000000/FPS; //0.01667 seconds for 60 fps
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(true) {

            time = System.nanoTime();
            //This is the how the CPU perceives time per se
            //What this function does:
            //Update information such as character position
            update();
            //Draw the screen with the updated information
            repaint(); // for some reason this is how you call the paintComponent() function

            //CODE BELOW ENSURES FPS
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

                time = System.nanoTime() - time;
                totalTime = totalTime + time;
                fpsCount++;
                if(totalTime >= 1000000000) {
                    System.out.println("FPS: "+ fpsCount);
                    fpsCount = 0;
                    totalTime = 0;
                }
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            
            
            if(!player.alive) {
                deathMessage = true;
                repaint();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }

                reset();
            }
            
            if(player.win) {
                winMessage = true;
                repaint();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }

                reset();
            }

        } // End game loop

    }
    
}