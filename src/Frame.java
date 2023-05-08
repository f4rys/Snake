import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Frame extends JFrame {
    Frame(){
        this.add(new Panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage();
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void setIconImage() {
        // Try loading the icon.png file from the file system
        Path imagePath = Paths.get("icon.png");

        try {
            if (Files.exists(imagePath)) {
                ImageIcon icon = new ImageIcon(imagePath.toString());
                this.setIconImage(icon.getImage());
            } else {
                // Load the icon.png file from the JAR resources
                InputStream inputStream = Frame.class.getResourceAsStream("/icon.png");
                if (inputStream != null) {
                    try {
                        ImageIcon icon = new ImageIcon(inputStream.readAllBytes());
                        this.setIconImage(icon.getImage());
                    } finally {
                        inputStream.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}