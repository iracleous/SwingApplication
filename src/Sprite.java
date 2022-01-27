import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Sprite {

    private int x  ;
    private int y  ;
    private int width  ;
    private int height ;
    private Color color  ;
    private File file;
    private Image bi;
    private Image bg;

    public Sprite() {
        x = 100;
        y = 50;

        height = 80;
        color = Color.BLUE;
        file = new File("images//bee.png");
        try {
            bi = ImageIO.read(file);
            bg=  ImageIO.read(new File("images//background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = bi.getWidth(null);
    }

    public void move(int increment){
        if(x>=0 && x<=500-width)
        x += increment;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public File getFile() {
        return file;
    }

    public Image getBi() {
        return bi;
    }

    public Image getBg() {
        return bg;
    }
}
