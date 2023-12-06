

package Main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;
 

public class Ghost {
    
    Random r = new Random();
    BufferedImage left, right;
    int x,y,type; // type determines the type of ghost: follow or wander or random
    int width, height;
    Rectangle hitbox = new Rectangle(x,y,53,64);
    String direction = "right";
    
    Player player;
    
    public Ghost(Player player, int width, int height, int type) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.player = player;
        
        if(type == 1 || type == 3) {
            x=r.nextInt(width);
            y=r.nextInt(height);
        } else if(type == 2) {
            x = r.nextInt(width);
            y = r.nextInt(14)*50;
        }
        loadImage();
    }
    
    public void loadImage() {
        try {
            left = ImageIO.read(getClass().getResourceAsStream("/res/leftGhost.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/res/rightGhost.png"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        

    }
    
    
    public void update() {
        
        if(type == 1) {
            if(player.x > x) {
                x+=1;
                direction = "right";
            } else if(player.x < x) {
                x-=1;
                direction = "left";
            }

            if(player.y > y) {
                y+=2;
            } else if(player.y < y) {
                y-=2;
            }
            
            
        } else if(type == 2) {
            
            if(x > 750) {
                direction = "left";
            }
            
            if(x < 50) {
                direction = "right";
            }
            
            if(direction.equals("right")) {
                x+=5;
            }
            
            if(direction.equals("left")) {
                x-=5;
            }
            
            
        } else if (type == 3) {
            int chance = r.nextInt(10);
                if(chance < 5) {
                               if(x > 750) {
                    direction = "left";
                }

                if(x < 50) {
                    direction = "right";
                }

                if(direction.equals("right")) {
                    x+=2;
                }

                if(direction.equals("left")) {
                    x-=2;
                }
            }
            
            else {
               if(player.x > x) {
                   x+=1;
                   direction = "right";
               } else if(player.x < x) {
                   x-=1;
                   direction = "left";
               }

               if(player.y > y) {
                   y+=1;
               } else if(player.y < y) {
                   y-=1;
               }
           }
        }
        
        
        
        
        hitbox.x =x;
        hitbox.y =y;
        
    }
    
    
    
    public void draw(Graphics2D g2) {
        

        
        if(direction.equals("left")) {
            g2.drawImage(left,x,y,53,64,null);
        } else {
            g2.drawImage(right,x,y,53,64,null);
        }
    }

    public void drawHitbox(Graphics2D g2) {
        g2.draw(hitbox);
    }
    
    
    
}