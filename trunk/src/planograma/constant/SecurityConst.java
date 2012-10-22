package planograma.constant;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 01.06.12
 * Time: 4:30
 * список разграничений в приложении
 * select * from mz.security_list where parent_object_code = -139
 */
public interface SecurityConst {
	/**
	 * SEV Модуль Планограммы ТП (WEB приложение)
	 */
	public static final int ACCESS_MODULE = -139;
	/**
	 * Доступ к редактированию шаблонов стелажей
	 */
	public static final int ACCESS_RACK_TEMPLATE_EDIT = -140;
	/**
	 * Доступ к редактированию стелажей
	 */
	public static final int ACCESS_RACK_EDIT = -141;
	/**
	 * Доступ к редактированию зала
	 */
	public static final int ACCESS_SECTOR_EDIT = -142;
	/**
	 * Доступ к расстановке товара
	 */
	public static final int ACCESS_RACK_WARES_PLACEMENT = -143;
	/**
	 * Доступ к очистке кеша изображений
	 */
	public static final int ACCESS_IMAGE_CLEAN_CACHE = -144;
	/**
	 * Доступ к принудительному снятию блокировки зала
	 */
	public static final int ACCESS_SECTOR_STATE_SET_A = -145;
	/**
	 * Доступ к принудительному снятию блокировки со стелажа
	 */
	public static final int ACCESS_RACK_STATE_SET_A = -146;
	/**
	 * Доступ к редактированию полок при расстановке товара
	 */
	public static final int ACCESS_RACK_WARES_PLACEMENT_AND_RACK_SHELF_EDIT = -150;
}
