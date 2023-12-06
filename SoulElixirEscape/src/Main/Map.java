package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Map {
    
    BufferedImage bricks;
    
    
    
    public Map() {
        loadImage();
    }
    
    public void loadImage() {
        try {
            bricks = ImageIO.read(getClass().getResourceAsStream("/res/bricks.png"));
        } catch(IOException ex) {
            System.out.println(ex);
        }
            
    }
    
    public void draw(Graphics2D g2) {
        
        for(int i=0;i<800;i+=40) {
            g2.drawImage(bricks,i,0,8*5,8*5,null);
        }
        
        for(int i=0; i<800; i+=40) {
            g2.drawImage(bricks,0,i,8*5,8*5,null);
        }
        
        for(int i=0; i<800; i+=40) {
            g2.drawImage(bricks,745,i,8*5,8*5,null);
        }
        
        for(int i=0;i<800;i+=40) {
            g2.drawImage(bricks,i,800-75,8*5,8*5,null);
        }
        
        
    }
    
}