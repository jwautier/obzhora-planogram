package planograma.utils.geometry;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 18.06.12
 * Time: 3:15
 * Утилитный класс определяющий пересечение обектов
 */
public class Intersection2DUtils {

	/**
	 * Пересечение двух отрезков
	 * @param a отчезок
	 * @param b отчезок
	 * @return точка пересечения
	 */
	public static Point2D intersection(final Segment2D a, final Segment2D b) {
		float x21 = a.p2.x - a.p1.x;
		float y21 = a.p2.y - a.p1.y;
		float x43 = b.p2.x - b.p1.x;
		float y43 = b.p2.y - b.p1.y;
		// отрезки не параллельны
		if (x21 * y43 != x43 * y21) {
			// точка пересечения линий
			final float y = ((a.p2.x * a.p1.y - a.p1.x * a.p2.y) * y43 + y21 * (b.p1.x * b.p2.y - b.p2.x * b.p1.y)) / (x21 * y43 - x43 * y21);
			final float x;
			if (y21 == 0) {
				x = (y * x43 + b.p1.x * b.p2.y - b.p2.x * b.p1.y) / y43;
			} else {
				x = (y * x21 + a.p1.x * a.p2.y - a.p2.x * a.p1.y) / y21;
			}
			// точка пересерения линий в пределах отрезков
			if (((a.p1.x <= x && x <= a.p2.x) || (a.p1.x >= x && x >= a.p2.x)) &&
					((b.p1.x <= x && x <= b.p2.x) || (b.p1.x >= x && x >= b.p2.x)) &&
					((a.p1.y <= y && y <= a.p2.y) || (a.p1.y >= y && y >= a.p2.y)) &&
					((b.p1.y <= y && y <= b.p2.y) || (b.p1.y >= y && y >= b.p2.y)))
				return new Point2D(x, y);
		}
		return null;
	}

	/**
	 * Пересечение двух прямоугольников (угол поворота = 0)
	 * @param a прямоугольник (угол поворота = 0)
	 * @param b прямоугольник (угол поворота = 0)
	 * @return a∩b
	 */
	public static Rectangle2D intersection(Rectangle2D a, Rectangle2D b) {
		float x1 = Math.max(a.getMinX(), b.getMinX());
		float x2 = Math.min(a.getMaxX(), b.getMaxX());
		float y1 = Math.max(a.getMinY(), b.getMinY());
		float y2 = Math.min(a.getMaxY(), b.getMaxY());
		if (x1 <= x2 && y1 <= y2)
			return new Rectangle2D(x1, y1, x2, y2);
		else
			return null;
	}

	public static boolean isIntersection(ConvexQuadrilateral2D a, ConvexQuadrilateral2D b)
	{
		boolean intersect=false;
		if (a.getMaxX()>=b.getMinX() && a.getMinX()<=b.getMaxX() &&
			a.getMaxY()>=b.getMinY() && a.getMinY()<=b.getMaxY()){
			// четырехугольники возможно пересекаются необходима детализация
		}
		return intersect;
	}
}
