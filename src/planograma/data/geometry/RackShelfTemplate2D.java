package planograma.data.geometry;

import planograma.data.RackShelfTemplate;
import planograma.utils.geometry.ConvexQuadrilateral2D;
import planograma.utils.geometry.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 08.06.12
 * Time: 5:06
 */
public class RackShelfTemplate2D extends ConvexQuadrilateral2D {
	private final RackShelfTemplate rackShelfTemplate;

	public RackShelfTemplate2D(RackShelfTemplate rackShelfTemplate) {
		this.rackShelfTemplate = rackShelfTemplate;
		// поворот объекта
		final float cos = (float) Math.cos(Math.toRadians(rackShelfTemplate.getAngle()));
		final float sin = (float) Math.sin(Math.toRadians(rackShelfTemplate.getAngle()));
		// правый верхний угол
		float x = 0.5F * rackShelfTemplate.getShelf_width();
		float y = 0.5F * rackShelfTemplate.getShelf_height();
		// относительно сцены
		p1 = new Point2D(rackShelfTemplate.getX_coord() + x * cos - y * sin, rackShelfTemplate.getY_coord() + x * sin + y * cos);
		// правый нижний угол
		y = -y;
		// относительно сцены
		p2 = new Point2D(rackShelfTemplate.getX_coord() + x * cos - y * sin, rackShelfTemplate.getY_coord() + x * sin + y * cos);
		// левый нижний угол
		x = -x;
		// относительно сцены
		p3 = new Point2D(rackShelfTemplate.getX_coord() + x * cos - y * sin, rackShelfTemplate.getY_coord() + x * sin + y * cos);
		// левый верхний угол
		y = -y;
		// относительно сцены
		p4 = new Point2D(rackShelfTemplate.getX_coord() + x * cos - y * sin, rackShelfTemplate.getY_coord() + x * sin + y * cos);
	}

	public RackShelfTemplate getRackShelfTemplate() {
		return rackShelfTemplate;
	}
}
