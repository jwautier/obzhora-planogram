package planograma.constant;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.02.12
 * Time: 2:18
 * To change this template use File | Settings | File Templates.
 */
public interface UrlConst {
	public static final String URL_LOGIN = "login";
	public static final String URL_LOGOUT = "logout";

	public static final String URL_SHOP_LIST = "servlet/shop/list";

	public static final String URL_SECTOR_LIST = "servlet/sector/list";
	public static final String URL_SECTOR_REMOVE = "servlet/sector/remove";
	public static final String URL_SECTOR_PRINT = "servlet/sector/print/";
	public static final String URL_SECTOR_PRINT_WITH_EDITOR = "servlet/sector/print_with_editor/";
	public static final String URL_SECTOR_ALL_RACK_SET_STATE_A = "servlet/sector/all_rack_set_state_active";
	public static final String URL_SECTOR_ALL_RACK_SET_STATE_PC = "servlet/sector/all_rack_set_state_complete";
	public static final String URL_SECTOR_FIND_WARES = "servlet/sector/find_wares";

	public static final String URL_SECTOR_EDIT = "servlet/sector/edit";
	public static final String URL_SECTOR_SAVE = "servlet/sector/save";

	public static final String URL_SECTOR_HISTORY_GET_MARK = "servlet/sector/history/get_mark";
	public static final String URL_SECTOR_HISTORY_VIEW = "servlet/sector/history/view";

	public static final String URL_RACK_TEMPLATE_LIST = "servlet/rack_template/list";
	public static final String URL_RACK_TEMPLATE_EDIT = "servlet/rack_template/edit";
	public static final String URL_RACK_TEMPLATE_SAVE = "servlet/rack_template/save";
	public static final String URL_RACK_TEMPLATE_REMOVE = "servlet/rack_template/remove";

	public static final String URL_RACK_EDIT = "servlet/rack/edit";
	public static final String URL_RACK_SAVE = "servlet/rack/save";
	public static final String URL_RACK_CAN_SET_STATE = "servlet/rack/can_set_state";
	public static final String URL_RACK_SET_STATE_IN_SECTOR_A = "servlet/rack/set_state_in_sector_active";
	public static final String URL_RACK_SET_STATE_IN_SECTOR_PC = "servlet/rack/set_state_in_sector_complete";
	public static final String URL_RACK_SET_STATE_A = "servlet/rack/set_state_active";
	public static final String URL_RACK_SET_STATE_PC = "servlet/rack/set_state_complete";
	
	public static final String URL_WARES_GROUP_TREE = "servlet/wares/group_tree";
	public static final String URL_WARES_LIST = "servlet/wares/list";
	public static final String URL_WARES_LIST_SEARCH = "servlet/wares/list/search";
	public static final String URL_RACK_WARES_PLACEMENT_PRINT = "servlet/rackWaresPlacement/print/";
	public static final String URL_RACK_WARES_PLACEMENT_EDIT = "servlet/rackWaresPlacement/edit";
	public static final String URL_RACK_WARES_PLACEMENT_SAVE = "servlet/rackWaresPlacement/save";
	public static final String URL_RACK_WARES_PLACEMENT_SET_BASKET = "servlet/rackWaresPlacement/setBasket";

	public static final String URL_IMAGE_LOAD = "image/";
	public static final String URL_IMAGE_CLEAN_CACHE = "servlet/image/clean_cache";

	public static final String URL_BUFFER_SET = "servlet/buffer/set";
	public static final String URL_BUFFER_GET = "servlet/buffer/get";

}
