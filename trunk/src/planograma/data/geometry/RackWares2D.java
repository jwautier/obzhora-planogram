package planograma.data.geometry;

import planograma.data.RackWares;
import planograma.utils.geometry.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 08.06.12
 * Time: 4:35
 * To change this template use File | Settings | File Templates.
 */
public class RackWares2D {
	private final RackWares rackWares;
	private Point2D p1;
	private Point2D p2;
	private Point2D p3;
	private Point2D p4;

	public RackWares2D(RackWares rackWares) {
		this.rackWares = rackWares;
		// поворот объекта
//		final float cos = (float) Math.cos(Math.toRadians(rack.getAngle()));
//		final float sin = (float) Math.sin(Math.toRadians(rack.getAngle()));
		// правый верхний угол
		float x = 0.5F * rackWares.getWares_width();
		float y = 0.5F * rackWares.getWares_height();
		// относительно сцены
//		p1=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p1=new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
		// правый нижний угол
		y = -y;
		// относительно сцены
//		p2=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p2=new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
		// левый нижний угол
		x = -x;
		// относительно сцены
//		p3=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p3=new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
		// левый верхний угол
		y = -y;
		// относительно сцены
//		p4=new Point2D(rackWares.getPosition_x() + x * cos - y * sin, rackWares.getPosition_y() + x * sin + y * cos);
		p4=new Point2D(rackWares.getPosition_x() + x, rackWares.getPosition_y() + y);
	}

	public RackWares getRackWares() {
		return rackWares;
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

	public Point2D getP3() {
		return p3;
	}

	public void setP3(Point2D p3) {
		this.p3 = p3;
	}

	public Point2D getP4() {
		return p4;
	}

	public void setP4(Point2D p4) {
		this.p4 = p4;
	}
}
