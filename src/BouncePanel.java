import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class BouncePanel extends JPanel implements Runnable
{
    Image ball,ball2, bkg;//image of players ball and bkg = background image
    float current = 0F;
    float current2 = 0F;
    Thread runner;
    int xPosition = 550; //Position of players ball
    public static int xMove = 1;
    public static int xPlus = 0;
    int yPosition = 570; //Position of players ball
    int ballHeight = 20; //How tall the players ball is
    int ballWidth = 20; //How wide the players ball is
    int height;


    int xPosition2 = 55;
    int yPosition2 = 55;
    int ballHeight2 = 25;
    int ballWidth2 = 25;
    public BouncePanel()
    {
        super();
        Toolkit kit = Toolkit.getDefaultToolkit();
        ball = kit.getImage("images//ant.png");//image of players ball
        ball2 = kit.getImage("images/bee.png");
        bkg = kit.getImage("images//background.png");//Background image
        runner = new Thread(this);
        runner.start();
    }



    public void paintComponent(Graphics comp)
    {
        Graphics2D comp2D = (Graphics2D) comp;
        height = getSize().height - ballHeight;
        if (yPosition == -1)
        {
            yPosition = height - 20;
        }
        if ((bkg != null) && (ball != null))
        {
            comp2D.drawImage(bkg, 0, 0, this);//draws background to JPanel
            comp2D.drawImage(ball, (int) xPosition, (int) yPosition, this);//Draws player ball at whatever x and y position is
        }

        height = getSize().height - ballHeight2;
        if (yPosition2 == -1)
        {
            yPosition2 = height - 20;
        }
        if ((ball2 != null))
        {

            comp2D.drawImage(ball2, (int) xPosition2, (int) yPosition2, this);
        }
    }

    public void run()
    {
        Thread thisThread = Thread.currentThread();
        while (runner == thisThread)
        {
            current += (float) 0.1;
            if (current > 3)
            {
                current = (float) 0;
            }
            current2 += (float) 0.5;
            if (current2 > 3)
            {
                current2 = (float) 0.6;
            }
            xMove += xPlus;
            xPosition += xMove;
            if (xPosition > (getSize().width - ballWidth))
            {
                xMove *= -1;
            }
            else if (xPosition < 1)
            {
                xMove *= -1;
            }

            double bounce = Math.sin(current) * height;
            yPosition = (int) (height - bounce);
            repaint();//repaints image of ball everytime it moves.
            yPosition2 = (int) (height - bounce);
            repaint();
            try {
                Thread.sleep(100);
            } catch (Exception e) { }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Demo");
        BouncePanel game = new BouncePanel();
        frame.add(game);
        frame.setSize(550, 250);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(game).start();


    }
}


