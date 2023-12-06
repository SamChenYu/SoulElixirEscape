package Main;


import Main.UI;
import javax.swing.JFrame;


public class Main {
    
    public static void main(String[] args) {
        
        final int height = 800;
        final int width = 800;
        
        JFrame window = new JFrame("Something Missing");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Something Missing");
        
        UI panel = new UI(width, height);
        window.add(panel);
        window.pack();
        
        window.setSize(width,height);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    
}
