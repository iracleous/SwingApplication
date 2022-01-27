import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingGame extends JPanel implements Runnable {

   private Sprite sprite;

   public  SwingGame(){
       sprite = new Sprite();
   }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // clears the background
        Graphics2D graphic2d = (Graphics2D) g;
        act();
        itemDraw1(graphic2d);
    }


    public void itemDraw(Graphics2D graphic2d) {
        graphic2d.setColor(sprite.getColor());
        graphic2d.fillRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void itemDraw1(Graphics2D graphic2d) {
       graphic2d.drawImage(sprite.getBg(), 0, 0 ,null);
       graphic2d.drawImage(sprite.getBi(), sprite.getX(), sprite.getY(),null);

    }


    public void act() {
        sprite.move(6);
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }

        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo");
        SwingGame swingGame = new SwingGame();
        frame.add(swingGame);
       // frame.setSize(500, 500);
int width = 500;
int height =500;
int xx= (frame.getInsets().left + frame.getInsets().right);
int yy=  (frame.getInsets().top + frame.getInsets().bottom);
xx=17; yy=37;
        frame.setSize(new Dimension(width + xx, height +yy));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(swingGame).start();
    }
}

