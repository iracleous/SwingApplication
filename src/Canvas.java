import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;

public class Canvas  extends JPanel implements Runnable {

    private int x;
    private int direction = 1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Ball");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        Canvas canvas = new Canvas();
        Thread thread = new Thread(canvas);
        thread.start();
        frame.setContentPane(canvas);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
       super.paintComponent(g); // clears the background
        g.setColor(Color.BLUE);
        g.fillOval(100 + x, 100, 50, 50);
        g.fillOval(200 + x, 200, 50, 50);
        g.drawRect(300 + x, 300, 100, 100);
    }

    public void run() {
        while (true) {
            x += direction;
            if(x>600 || x<0) {
                direction *= -1;
            }

            repaint();
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException ex) {
            }
            System.out.println("x= "+ x);
        }
    }
}


