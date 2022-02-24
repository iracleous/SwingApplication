
import java.awt.*;
        import java.awt.event.*;
        import java.awt.image.BufferedImage;
        import javax.swing.*;

import java.io.File;
import java.net.URL;
        import javax.imageio.ImageIO;

public class ImageAnimation {

    public static void main(String[] args) throws Exception {
   //     File file = new File("images//bee.png");
     //   final BufferedImage bi = ImageIO.read(file);

        URL url = new URL("http://i.stack.imgur.com/i8UJD.jpg");
        final BufferedImage bi = ImageIO.read(url);
        Runnable r = new Runnable() {

            @Override
            public void run() {
                final BufferedImage canvas = new BufferedImage(
                        bi.getWidth(), bi.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                final JLabel animationLabel = new JLabel(new ImageIcon(canvas));
                ActionListener animator = new ActionListener() {

                    int x = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Graphics2D g = canvas.createGraphics();

                        // paint last part of image in left of canvas
                        g.drawImage(bi, x, 0, null);
                        // paint first part of image immediately to the right
                        g.drawImage(bi, x + bi.getWidth(), 0, null);

                        // reset x to prevent hitting integer overflow
                        if (x%bi.getWidth()==0) x = 0;

                        g.dispose();
                        animationLabel.repaint();
                        x--;
                    }
                };
                Timer timer = new Timer(40, animator);
                timer.start();
                JOptionPane.showMessageDialog(null, animationLabel);
                timer.stop();
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}