package planograma.data.geometry;

import planograma.data.RackShelf;
import planograma.utils.geometry.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 08.06.12
 * Time: 5:06
 * To change this template use File | Settings | File Templates.
 */
public class RackShelf2D {
	private final RackShelf rackShelf;
	private Point2D p1;
	private Point2D p2;
	private Point2D p3;
	private Point2D p4;

	public RackShelf2D(RackShelf rackShelf) {
		this.rackShelf = rackShelf;
		// поворот объекта
		final float cos = (float) Math.cos(Math.toRadians(rackShelf.getAngle()));
		final float sin = (float) Math.sin(Math.toRadians(rackShelf.getAngle()));
		// правый верхний угол
		float x = 0.5F * rackShelf.getShelf_width();
		float y = 0.5F * rackShelf.getShelf_height();
		// относительно сцены
		p1=new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
		// правый нижний угол
		y = -y;
		// относительно сцены
		p2=new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
		// левый нижний угол
		x = -x;
		// относительно сцены
		p3=new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
		// левый верхний угол
		y = -y;
		// относительно сцены
		p4=new Point2D(rackShelf.getX_coord() + x * cos - y * sin, rackShelf.getY_coord() + x * sin + y * cos);
	}

	public RackShelf getRackShelf() {
		return rackShelf;
	}

	public Point2D getP1() {
		return p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public Point2D getP3() {
		return p3;
	}

	public Point2D getP4() {
		return p4;
	}
}
