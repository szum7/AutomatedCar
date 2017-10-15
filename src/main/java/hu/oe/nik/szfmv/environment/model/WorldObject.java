package hu.oe.nik.szfmv.environment.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.oe.nik.szfmv.environment.util.ModelShape;

/**
 * Világ elemeinek ős osztálya
 * 
 * @author hunkak
 *
 */
public abstract class WorldObject {

	private static final Logger log = LogManager.getLogger(WorldObject.class);

	// objektum helyzete a síkon
	// TODO meghatározni, hogy az objektum melyik pontja - vizualizációs csapattal
	int x, y;
	// objektum forgatása
	// TODO meghatározni a bázis helyzetet és a mértékegységet (szög,radián)
	float rotation;

	// objektum kiterjedése
	// TODO befoglaló négyszög? implementációs függő jelentés?
	private final int width, height;

	// objektum formája
	private final ModelShape shape;

	private final String imageFileName;

	public WorldObject(int x, int y, float rotation, int width, int height, String imageName, ModelShape shape) {
		super();

		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.width = width;
		this.height = height;
		this.imageFileName = imageName;
		this.shape = shape;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getRotation() {
		return rotation;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @return the shape
	 */
	public ModelShape getShape() {
		return shape;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorldObject [x=" + x + ", y=" + y + ", rotation=" + rotation + ", width=" + width + ", height=" + height
				+ ", shape=" + shape + ", imageFileName=" + imageFileName + "]";
	}

}