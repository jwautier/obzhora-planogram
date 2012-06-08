package planograma.utils.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 20.05.12
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class Segment2D {
	private Point2D p1;
	private Point2D p2;

	public Segment2D(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Point2D getP1() {
		return p1;
	}

	public void setP1(Point2D p1) {
		this.p1 = p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public void setP2(Point2D p2) {
		this.p2 = p2;
	}

	public double distance(Point2D p) {
		double x21 = p2.x - p1.x;
		double y21 = p2.y - p1.y;
		return (x21 * p.y - y21 * p.x + (p1.x * p2.y - p2.x * p1.y)) / Math.sqrt(x21 * x21 + y21 * y21);
	}

	public boolean intersectsSegment(Segment2D s) {
		double x21 = this.p2.x - this.p1.x;
		double y21 = this.p2.y - this.p1.y;
		double x43 = s.p2.x - s.p1.x;
		double y43 = s.p2.y - s.p1.y;
		// отрезки параллельны
		if (x21 * y43 == x43 * y21)
			return false;
		// точка пересечения линий
		double y = ((this.p2.x * this.p1.y - this.p1.x * this.p2.y) * y43 + y21 * (s.p1.x * s.p2.y - s.p2.x * s.p1.y)) / (x21 * y43 - x43 * y21);
		double x;
		if (y21 == 0) {
			x = (y * x43 + s.p1.x * s.p2.y - s.p2.x * s.p1.y) / y43;
		} else {
			x = (y * x21 + this.p1.x * this.p2.y - this.p2.x * this.p1.y) / y21;
		}
		System.out.println("x: "+x+" y:"+y);
		// точка пересерения линий в пределах отрезков
		if (((this.p1.x <= x && x <= this.p2.x) || (this.p1.x >= x && x >= this.p2.x)) &&
				((s.p1.x <= x && x <= s.p2.x) || (s.p1.x >= x && x >= s.p2.x)) &&
				((this.p1.y <= y && y <= this.p2.y) || (this.p1.y >= y && y >= this.p2.y)) &&
				((s.p1.y <= y && y <= s.p2.y) || (s.p1.y >= y && y >= s.p2.y)))
			return true;
		else
			return false;
	}
}
