package planograma.servlet.rack;

import planograma.PlanogramMessage;
import planograma.constant.VerificationConst;
import planograma.constant.data.RackConst;
import planograma.data.LoadSide;
import planograma.data.Rack;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * User: poljakov
 * Date: 09.01.13
 * Time: 14:45
 * Проверка параметров стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
 */
public class RackMinDimensionsValidation {
	/**
	 * Проверка параметров стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
	 * @param fieldExceptionList список ошибок
	 * @param rack стеллаж
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final Rack rack) {
		final float w = rack.getLength();
		final float rw = rack.getReal_length();
		final float h;
		final float rh;
		final float l;
		final float rl;
		if (rack.getLoad_side() == LoadSide.F) {
			h = rack.getHeight();
			rh = rack.getReal_height();
			l = rack.getWidth();
			rl = rack.getReal_width();
		} else {
			l = rack.getHeight();
			rl = rack.getReal_height();
			h = rack.getWidth();
			rh = rack.getReal_width();
		}

		if (h < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_HEIGHT_TOO_LITTLE(), Rack.class, 0, rack.getCode_rack_template(), RackConst.HEIGHT));
		}
		if (l < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_LENGTH_TOO_LITTLE(), Rack.class, 0, rack.getCode_rack_template(), RackConst.LENGTH));
		}
		if (w < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WIDTH_TOO_LITTLE(), Rack.class, 0, rack.getCode_rack_template(), RackConst.WIDTH));
		}
		if (rh < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_HEIGHT_TOO_LITTLE(), Rack.class, 0, rack.getCode_rack_template(), RackConst.REAL_HEIGHT));
		}
		if (rl < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_LENGTH_TOO_LITTLE(), Rack.class, 0, rack.getCode_rack_template(), RackConst.REAL_LENGTH));
		}
		if (rw < VerificationConst.MIN_RACK_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_WIDTH_TOO_LITTLE(), Rack.class, 0, rack.getCode_rack_template(), RackConst.REAL_WIDTH));
		}

	}
}
