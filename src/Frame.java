import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class Frame extends JFrame {
    Frame(){
        this.add(new Panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        ImageIcon icon = new ImageIcon("icon.png");
        this.setIconImage(icon.getImage());

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}