package planograma.utils.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 20.05.12
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public class Point2D implements Cloneable{
	protected float x;
	protected float y;

	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
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

	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public void scale(float m) {
		y = y / m;
		x = x / m;
	}

	public boolean inside(final Rectangle2D rectangle2D){
		return this.x >= rectangle2D.p1.x &&
				this.x <= rectangle2D.p2.x &&
				this.y >= rectangle2D.p1.y &&
				this.y <= rectangle2D.p2.y;
	}

	@Override
	public Point2D clone() {
		return new Point2D(x,y);
	}
}
