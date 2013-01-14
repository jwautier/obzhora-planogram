package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.data.LoadSide;
import planograma.data.Rack;
import planograma.data.RackWares;
import planograma.data.geometry.RackWares2D;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * User: poljakov
 * Date: 14.01.13
 * Time: 9:13
 * товар не должен выходить за полезную зону стеллажа
 */
public class RackWaresOutsideRackValidation {
	/**
	 * товар не должен выходить за полезную зону стеллажа
	 *
	 * @param fieldExceptionList
	 * @param rack
	 * @param rackWares2DList
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Rack rack, final List<RackWares2D> rackWares2DList) {
		float dx;
		float dy;
		float dz;
		// относительно стороны загрузки
		if (rack.getLoad_side() == LoadSide.F) {
			dx = rack.getReal_length();
			dy = rack.getReal_height();
			dz = rack.getReal_width();
		} else {
			dx = rack.getReal_length();
			dy = rack.getReal_width();
			dz = rack.getReal_height();
		}
		//	полезная зона не может стать меньше чем расположеные на нем товары
		if (rackWares2DList != null) {
			for (int j = 0; j < rackWares2DList.size(); j++) {
				final RackWares2D rackWares2D = rackWares2DList.get(j);
				final RackWares rackWares = rackWares2D.getRackWares();
				if (rackWares2D.getMinX() < 0 ||
						rackWares2D.getMaxX() > dx ||
						rackWares2D.getMinY() < 0 ||
						rackWares2D.getMaxY() > dy ||
						rackWares.getWares_length() * rackWares.getCount_length_on_shelf() < 0 ||
						rackWares.getWares_length() * rackWares.getCount_length_on_shelf() > dz) {
					fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WARES_OUTSIDE_RACK(), RackWares.class, j, rackWares.getCode_wares_on_rack(), "wares_outside_rack"));
				}
			}
		}
	}
}
