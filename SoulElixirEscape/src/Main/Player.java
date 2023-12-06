package Main;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;


public class Player {
    
    KeyHandler keyH;
    Random r = new Random();
    
    //Initial Values:
    int x,y;
    int width, height;
    final int speed = 5;
    final int scale = 5;
    
    BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    
    int spriteNum = 1;
    int spriteCounter = 0;
    String direction = "down";
    
    Rectangle hitbox = new Rectangle(x,y,12*scale-1,14*scale-1);
    SoulPotion soulp;
    int score = 0;
    UI ui;
    ArrayList<Ghost> ghost;
    boolean alive = true;
    boolean win = false;
    
    
    public Player(KeyHandler keyH, int width, int height, SoulPotion soulp, UI ui, ArrayList<Ghost> ghost) {
        this.ghost = ghost;
        this.ui = ui;
        this.soulp = soulp;
        this.width = width;
        this.height = height;
        x = width/2;
        y = height/2;
        this.keyH = keyH;
        loadImages();
    } // END constructor
    
    public void loadImages() {
        
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/right2.png"));
        } catch(IOException e) {
            System.out.println("Image Loading Failed");
        }
    } // END loadimages

    public void draw(Graphics2D g2) {
        
        BufferedImage image = null;
        
        
        switch(direction) {
            
            case "up" -> {
                if(spriteNum == 1) {
                    image = up1;
                }
                
                if(spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if(spriteNum == 1) {
                    image = down1;
                }
                
                if(spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if(spriteNum == 1) {
                    image = left1;
                }
                
                if(spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if(spriteNum == 1) {
                    image = right1;
                }
                
                if(spriteNum == 2) {
                    image = right2;
                }
            }
        }
        
        
        
        g2.drawImage(image, x , y, 12*scale, 14*scale, null);       
        soulp.drawScore(g2, score);
    }
    
    public void drawHitbox(Graphics2D g2) {
        g2.draw(hitbox);
    }
    
    public void update() {
        
        hitbox.x = x;
        hitbox.y = y;
        if(keyH.upPressed == true) {    // method checks for the key input and the collisions for the player
            direction = "up";
            if(y>0) {
                y-=speed;
            }
        }
        if (keyH.downPressed == true) {
            direction = "down";
            if(y+speed<height-21*scale) {
                y+=speed;
            }
            
        }
        if (keyH.leftPressed == true) {
            direction = "left";
            if(x-speed>0) {
                x-=speed;
            }
        }
        if (keyH.rightPressed == true) {
            direction = "right";
            if(x+speed<width-12*scale) {
                x+=speed;
            }
        }            
        
        spriteCounter++; // this is for the sprite animation change
        if(spriteCounter > 15) { // changes the sprite every 15 fps
            if(spriteNum == 1) {
                spriteNum = 2;
            } 
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        
        if(hitbox.intersects(soulp.hitbox)) {
            System.out.println("Soul potion hit!");
            score++;
            int type = r.nextInt(3)+1;
            ui.addGhost(type);
            
            soulp.reposition();
            
            if(score == 10) {
                win();
            }
        }
        
        for(int i=0; i<ghost.size(); i++) {
            
            if(hitbox.intersects(ghost.get(i).hitbox)) {
                dead();
            }
            
        }
        
        
       
    }
    
    public void dead() {
        alive = false;
        
    }
    
    public void win() {
        win = true;
    }
    
} // END class
