package planograma.utils.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 20.05.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class Point3D implements Cloneable {

	protected float x;
	protected float y;
	protected float z;

	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void move(float dx, float dy, float dz) {
		x += dx;
		y += dy;
		z += dz;
	}

	public void scale(float m) {
		y = y / m;
		x = x / m;
		z = z / m;
	}

	@Override
	public Point3D clone() {
		return new Point3D(x, y, z);
	}
}
