package hu.oe.nik.szfmv.environment.model;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.common.Vector2D;
import hu.oe.nik.szfmv.environment.detector.IRadarSensor;
import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Mozgó objektumokat reprezentáló osztály TODO: maximális sebesség, min-max
 * gyorsulás beépítése
 * 
 * @author hunkak
 *
 */
public abstract class MovingObject extends CollidableObject implements IRadarSensor {

    protected static final Logger log = LogManager.getLogger(MovingObject.class);

    // objektum pillantnyi sebess�ge
    private Vector2D currentSpeed;

    /**
     * @deprecated
     * The width and height of the object must be based on the size of the <code>imageName</code> referenced in the constructor
     * <p>
     * Use the following constructor instead: {@link #MovingObject(double x, double y, double rotation, String imageName, ModelShape shape)}
     * 
     * 
     * @param x
     * @param y
     * @param rotation
     * @param width
     * @param height
     * @param imageFileName
     * @param weight
     * @param shape
     */
    @Deprecated
    public MovingObject(int x, int y, float rotation, int width, int height, String imageFileName, int weight,
            ModelShape shape) {
        super(x, y, rotation, width, height, imageFileName, weight, shape);
        // TODO: this must be 0,0 so the object is steady
        this.currentSpeed = new Vector2D(0, 0);
    }
    
    /**
     * width and height are set based on image size
     * @param x
     * @param y
     * @param rotation
     * @param imageFileName
     * @param weight
     * @param shape
     */
    public MovingObject(int x, int y, float rotation, String imageFileName, int weight,
            ModelShape shape) {
        super(x, y, rotation, imageFileName, weight, shape);
        // TODO: this must be 0,0 so the object is steady
        this.currentSpeed = new Vector2D(0, 0);
    }
    

    public void move() {
        if (log.isDebugEnabled()) {
            log.debug("move called");
        }

        Vector2D newPosition = new Vector2D(this.getX(), this.getY()).add(currentSpeed.div(24));
        this.setPosition(newPosition);
    };

    protected void chageDirection(List<Vector2D> vectors) {
        for (Vector2D vector2d : vectors) {
            currentSpeed = currentSpeed.add(vector2d);

        }
    }

    protected void chageDirection(Vector2D vector) {
        currentSpeed = currentSpeed.add(vector);

    }

    /**
     * Motgzó objeltum impulzusa
     * 
     * @return vektor melynek nagysága az impulzus nagysága, iránya egyezik az
     *         objektum sebességének irányával (szögben)
     */
    public Vector2D getImpulse() {
        return currentSpeed.mult(getWeight());
    }

    /**
     * Az objektum pillanatnyi sebessége
     * 
     * @return vektor melynek nagysága a pillanatnyi sebesség nagysága, iránya a
     *         pillanatnyi sebesség iránya (szögben)
     */
    public Vector2D getCurrentSpeed() {
        return currentSpeed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MovingObject [currentSpeed=" + currentSpeed + ", x=" + this.getX() + ", y=" + this.getY() + ", rotation=" + this.getRotation()
                + ", isCollided()=" + isCollided() + ", getWeight()=" + getWeight() + ", getWidth()=" + getWidth()
                + ", getHeight()=" + getHeight() + ", getImageFileName()=" + getImageFileName() + ", getShape()="
                + getShape() + "]";
    }

}
