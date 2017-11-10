package hu.oe.nik.szfmv.visualisation;

import hu.oe.nik.szfmv.environment.model.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class CourseDisplay {

    private static final Logger logger = LogManager.getLogger();
    private JFrame frame = new JFrame("OE NIK Automated Car Project");

    private static final int maxHeight = 700, maxWidth = 1100;
    private static final double idealRatio = (double) maxWidth / (double) maxHeight;
    private static double scale = 1;

    public void refreshFrame() {
        //frame.invalidate();
        //frame.validate();
        frame.repaint();
    }

    public void init(World world) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFrameSize(world);

        GameDisplayJPanel displayPanel = new GameDisplayJPanel(
                world,
                scale,
                frame.getWidth(),
                frame.getHeight());

        frame.add(displayPanel);

        frame.validate();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void setFrameSize(World world) {
        //if we need to scale the game field
        if (world.getHeight() > maxHeight || world.getWidth() > maxWidth) {
            scaleGameFieldToFrame(world);
        } else {
            frame.setSize(world.getWidth(), world.getHeight());
        }
    }

    private void scaleGameFieldToFrame(World world) {
        //ratio > 1: horizontal
        //ratio < 1: vertical
        double worldRatio = (double) world.getWidth() / (double) world.getHeight();
        int scaledHeight, scaledWidth;
        //worldRatio > idealRatio: we have to scale height
        //else we have to scale width

        if (worldRatio > idealRatio) {
            scaledWidth = maxWidth;
            calculateScale(scaledWidth, world.getWidth());
            scaledHeight = (int) Math.round(
                    world.getHeight() * ((double) maxWidth / (double) world.getWidth()));

        } else {
            scaledHeight = maxHeight;
            calculateScale(scaledHeight, world.getHeight());
            scaledWidth = (int) Math.round(
                    world.getWidth() * ((double) maxHeight / (double) world.getHeight()));
        }
        frame.setSize(scaledWidth, scaledHeight);
    }

    private void calculateScale(int sizeFrom, int sizeTo) {
        scale = ((double) sizeFrom) / sizeTo;
    }

}