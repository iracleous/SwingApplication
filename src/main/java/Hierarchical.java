/**
 * ============================================================================
 * HierarchicalModeling2D.java : Create a panel that displays a two-dimensional
 * animation that is constructed using hierarchical modeling.
 *
 * Scenario: A cart rolls down a road while a sun shines and three windmills
 * turn in the background.  This class also contains a main() routine that
 * simply opens a window that displays the animation.
 * ============================================================================
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class Hierarchical extends JPanel {

    // For animation, increases by 1 in each frame.

    private int frameNumber;

    // A path used for drawing the ground, with "hills".

    private GeneralPath       ground;

    // A path used for drawing one windmill blade.

    private GeneralPath windmillVane;

    // The size of a pixel in drawing coordinates.

    private float pixelSize;

    // Desired coordinate limits.

    private double[] limitsRequested = new double[] {0,7,4,-1};

    /**
     * =====================================================================
     * Constructor sets the preferred size of the panel to 700-by-500,
     * adds a gray border, and starts a timer that will drive the animation.
     * =====================================================================
     */

    public Hierarchical() {

        setPreferredSize( new Dimension(700,500));
        setBackground( Color.LIGHT_GRAY );
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        // Create ground pathway ....

        ground = new GeneralPath();
        ground.moveTo(0,-1);
        ground.lineTo(0,1);
        ground.curveTo(1,2,1,2,1.5F,2);
        ground.curveTo(2.5F,2,2.5F,1.5F,2,1.5F);
        ground.curveTo(2.5F,1.5F,2.5F,2.5F,3,2.5F);
        ground.curveTo(3.5F,2.5F,3.5F,1.8F,4,1.8F);
        ground.curveTo(5,1.8F,4,2.2F,5,2.2F);
        ground.curveTo(6,2.2F,6,2,7,2);
        ground.lineTo(7,-1);
        ground.closePath();

        // Create windmill vane pathway ....

        windmillVane = new GeneralPath();
        windmillVane.moveTo(0,0);
        windmillVane.lineTo(0.5F,0.1F);
        windmillVane.lineTo(1.5F,0);
        windmillVane.lineTo(0.5F,-0.1F);
        windmillVane.closePath();

        // Create timer and run animation ....

        new Timer(30,new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                frameNumber++;
                repaint();
            }
        }).start();
    }

    /**
     * Draw the current frame of the animation.
     */

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();

        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // =====================================================================
        // Apply coordinates with x ranging from 0 on the left to 7 on the right
        // and y ranging from -1 at the bottom to 4 at the top.
        // Note that the aspect ration is NOT preserved, so the aspect ratio
        // of the panel should closely match the aspect ratio of the
        // coordinate rectangle.
        // =====================================================================

        applyLimits(g2, getWidth(), getHeight(), limitsRequested, false);

        // ========================================
        // Draw the sky, ground, and road directly.
        // ========================================

        // Fill the sky, behind the hills

        g2.setColor( new Color(150,150,255) );
        g2.fillRect(0,0,7,4);

        // Fill the ground ....

        g2.setColor( new Color(0,150,30) );
        g2.fill(ground);

        // Draw the road ...

        g2.setColor(new Color(100,100,150));
        g2.fill(new Rectangle2D.Double(0,-0.4,7,0.8));

        // Draw stripes on the road as a dashed line ..

        g2.setStroke( new BasicStroke(0.1F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                new float[] { 0.25F, 0.2F }, 1) );
        g2.setColor(Color.WHITE);
        g2.drawLine(0,0,7,0);
        g2.setStroke( new BasicStroke(pixelSize) );

        /*
         *  =================================================================
         *  Keep a copy of the current transform, so that it can be restored
         *  after drawing each object.
         *  =================================================================
         */

        AffineTransform saveTr = g2.getTransform();

        // ========================================
        // Draw the sun.
        // ========================================

        g2.translate(5,3.3);
        drawSun(g2);
        g2.setTransform(saveTr);

        // ========================================
        // Draw three windmills.
        // ========================================

        g2.translate(0.75,1);
        g2.scale(0.6,0.6);
        drawWindmill(g2);
        g2.setTransform(saveTr);

        g2.translate(2.2,1.6);
        g2.scale(0.4,0.4);
        drawWindmill(g2);
        g2.setTransform(saveTr);

        g2.translate(3.7,0.8);
        g2.scale(1.0,1.0);
        drawWindmill(g2);
        g2.setTransform(saveTr);

        // ========================================
        // Draw the cart.
        // ========================================

        g2.translate(-3 + 13*(frameNumber % 300) / 300.0, 0);
        g2.scale(0.3,0.3);
        drawCart(g2);
    }

    /**
     * ==========================================================================
     * Applies a coordinate transform to a Graphics2D graphics context.
     *
     * The upper left corner of the viewport where the graphics context draws
     * is assumed to be (0,0).  (This method sets the global variable pixelSize.)
     *
     * @param              g2  The drawing context whose transform will be set.
     * @param           width  The width of the viewport where g2 draws.
     * @param          height  The height of the viewport where g2 draws.
     * @param limitsRequested  Specifies a rectangle that will be
     *                         visible in the viewport.
     *
     *  Under the transform, the rectangle with corners
     *          (limitsRequested[0],limitsRequested[1])
     *  and
     *          (limitsRequested[2],limitsRequested[3])
     * will just fit in the viewport.
     *
     * @param preserveAspect if preserveAspect is false, then the limitsRequested
     * rectangle will exactly fill the viewport; if it is true, then the limits
     * will be expanded in one direction, horizontally or vertically, to make the
     * aspect ratio of the displayed rectangle match the aspect ratio of the viewport.
     *
     * Note that when preserveAspect is false, the units of measure in the
     * horizontal and vertical directions will be different.
     * ==========================================================================
     */

    private void applyLimits(Graphics2D g2, int width, int height,
                             double[] limitsRequested, boolean preserveAspect) {

        double[] limits = limitsRequested;

        if (preserveAspect) {
            double displayAspect   = Math.abs((double)height / width);
            double requestedAspect = Math.abs(( limits[3] - limits[2] ) / ( limits[1] - limits[0] ));

            if (displayAspect > requestedAspect) {
                double excess = (limits[3] - limits[2]) * (displayAspect/requestedAspect - 1);
                limits = new double[] { limits[0], limits[1],
                        limits[2] - excess/2, limits[3] + excess/2 };
            }
            else if (displayAspect < requestedAspect) {
                double excess = (limits[1] - limits[0]) * (requestedAspect/displayAspect - 1);
                limits = new double[] { limits[0] - excess/2,
                        limits[1] + excess/2, limits[2], limits[3] };
            }
        }

        double pixelWidth  = Math.abs(( limits[1] - limits[0] ) / width);
        double pixelHeight = Math.abs(( limits[3] - limits[2] ) / height);
        pixelSize = (float) Math.min(pixelWidth,pixelHeight);

        g2.scale( width / (limits[1]-limits[0]), height / (limits[3]-limits[2]) );
        g2.translate( -limits[0], -limits[2] );
    }

    /**
     * ==========================================================================
     * Draw a sun with radius 0.5 centered at (0,0). There are also 13 rays which
     * extend outside from the sun for another 0.25 units.
     * ==========================================================================
     */

    private void drawSun(Graphics2D g2) {
        g2.setColor(Color.YELLOW);

        // Draw 13 rays, with different rotations.

        for (int i = 0; i < 13; i++) {
            g2.rotate( 2*Math.PI / 13 );
            g2.draw( new Line2D.Double(0,0,0.75,0) );
        }

        // Now draw the sun itself ....

        g2.fill( new Ellipse2D.Double(-0.5,-0.5,1,1) );
        g2.setColor( new Color(180,180,0) );
        g2.draw( new Ellipse2D.Double(-0.5,-0.5,1,1) );
    }

    /**
     * ==========================================================================
     * Draw a windmill, consisting of a pole and three vanes.
     *
     *   -- The pole extends from the point (0,0) to (0,3).
     *   -- The vanes radiate out from (0,3).
     *   -- A rotation that depends on the frame number is applied to the whole
     *      set of vanes, which causes the windmill to rotate.
     *
     * ==========================================================================
     */

    private void drawWindmill(Graphics2D g2) {

        g2.setColor(new Color(200,200,225));
        g2.fill(new Rectangle2D.Double(-0.05,0,0.1,3));

        // Translations to move the vanes to the top of the pole.

        g2.translate(0,3);

        // Apply an overall rotation to the set of vanes.

        g2.rotate(frameNumber/23.0);
        g2.setColor(new Color(100,100,200));

        // Draw wind vanes 60 degrees apart.....

        for (int i = 0; i < 6; i++) {
            g2.rotate(1*Math.PI/3);
            g2.fill(windmillVane);
        }
    }

    /**
     * ==========================================================================
     * Draw a cart consisting of a rectangular body and two wheels.
     *
     * The wheels are drawn by the drawWheel() method; a different translation
     * is applied to each wheel to move them into position under the body.
     * The body of the cart is a red rectangle with corner at (0,-2.5), width 5,
     * and height 2.  The center of the bottom of the rectangle is at (0,0).
     * ==========================================================================
     */

    private void drawCart(Graphics2D g2) {
        AffineTransform tr = g2.getTransform();  // save the current transform

        // Center of first wheel will be at (-1.5,-0.1)

        g2.translate(-1.5,-0.1);
        g2.scale(0.8,0.8);
        drawWheel(g2);

        // Restore the transform

        g2.setTransform(tr);

        // Center of second wheel will be at (1.5,-0.1)

        g2.translate(1.5,-0.1);
        g2.scale(0.8,0.8);
        drawWheel(g2);

        // Restore the transform

        g2.setTransform(tr);
        g2.setColor(Color.RED);

        // Draw the body of the cart

        g2.fill(new Rectangle2D.Double(-2.5,0,5,2) );
    }

    /**
     * ==========================================================================
     * Draw a wheel centered at (0,0) and with radius 1. The wheel has 15 spokes
     * that rotate in a clockwise direction as the animation proceeds.
     * ==========================================================================
     */

    private void drawWheel(Graphics2D g2) {

        // Draw outline of wheel ....

        g2.setColor(Color.BLACK);
        g2.fill( new Ellipse2D.Double(-1,-1,2,2) );
        g2.setColor(Color.LIGHT_GRAY);
        g2.fill( new Ellipse2D.Double(-0.8,-0.8,1.6,1.6) );
        g2.setColor(Color.BLACK);
        g2.fill( new Ellipse2D.Double(-0.2,-0.2,0.4,0.4) );
        g2.rotate( -frameNumber/30.0 );

        // Draw 15 spokes ....

        for (int i = 0; i < 15; i++) {
            g2.rotate(2*Math.PI/15);
            g2.draw( new Rectangle2D.Double(0,-0.1,1,0.2) );
        }
    }

    // ========================
    // Exercise simulation ....
    // ========================

    public static void main(String[] args) {

        Hierarchical demo = new Hierarchical();

        JFrame window = new JFrame("Windmills and Cart Animation in 2D");
        window.setContentPane( demo );
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}