package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


public class SoulPotion {
    
    
    Random r = new Random();
    int x=0;
    int y=0;
    int width,height;
    final int scale = 3;
    BufferedImage image;
    
    Rectangle hitbox = new Rectangle(x,y,16*scale,16*scale);
    
    
    public SoulPotion(int width, int height) {
        this.width = width;
        this.height = height;
        loadImage();
        reposition();
    }
    
    public void reposition() {
        x = r.nextInt(width-50);
        y = r.nextInt(height-50);
    }
    
    public void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/potion.png"));
        } catch(IOException ex) {
            System.out.println(ex);
        }
    }
    
    public void draw(Graphics2D g2) {
        g2.drawImage(image,x,y,16*scale,16*scale,null);
        g2.setColor(Color.GREEN);
        
        hitbox.x = x;
        hitbox.y = y;       
    }
    
    public void drawHitbox(Graphics2D g2) {
        g2.draw(hitbox);
    }
    
    public void drawScore(Graphics2D g2,int score) {
        g2.drawImage(image,-4,715,16*scale,16*scale,null);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        g2.setColor(Color.GREEN);

        // Draw text
        g2.drawString(": "+score + "/10", 40, 753);
    }
    
    
    
    
}