package planograma.servlet.validate;

import planograma.PlanogramMessage;
import planograma.constant.VerificationConst;
import planograma.constant.data.RackShelfTemplateConst;
import planograma.data.AbstractRackShelf;
import planograma.data.RackShelf;
import planograma.data.RackShelfTemplate;
import planograma.exception.EntityFieldException;

import java.util.List;

/**
 * User: poljakov
 * Date: 09.01.13
 * Time: 14:45
 * Проверка параметров полки стеллажа: высота, ширина, глубина должны быть больше 5мм
 */
public class RackShelfMinDimensionsValidation {
	/**
	 * Проверка параметров полки стеллажа: высота, ширина, глубина должны быть больше 5мм
	 *
	 * @param fieldExceptionList список ошибок
	 * @param rackShelf          полка стеллажа
	 */
	public static void validate(final List<EntityFieldException> fieldExceptionList, final AbstractRackShelf rackShelf, final int index) {
		Integer code = null;
		if (rackShelf instanceof RackShelf)
			code = ((RackShelf) rackShelf).getCode_shelf();
		else if (rackShelf instanceof RackShelfTemplate)
			code = ((RackShelfTemplate) rackShelf).getCode_shelf_template();

		if (rackShelf.getShelf_width() < VerificationConst.MIN_RACK_SHELF_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_WIDTH_TOO_LITTLE(), rackShelf.getClass(), index, code, RackShelfTemplateConst.SHELF_WIDTH));
		}
		if (rackShelf.getShelf_height() < VerificationConst.MIN_RACK_SHELF_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_HEIGHT_TOO_LITTLE(), rackShelf.getClass(), index, code, RackShelfTemplateConst.SHELF_HEIGHT));
		}
		if (rackShelf.getShelf_length() < VerificationConst.MIN_RACK_SHELF_DIMENSIONS) {
			fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_LENGTH_TOO_LITTLE(), rackShelf.getClass(), index, code, RackShelfTemplateConst.SHELF_LENGTH));
		}
	}
}
