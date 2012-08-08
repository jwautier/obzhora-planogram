package planograma.model.history;

import java.util.Date;
import java.util.List;

/**
 * User: poljakov
 * Date: 08.08.12
 * Time: 11:19
 */
public class HistoryModel {
	private final String Q_HISTORYMARK =
			" (select DATE_INSERT from EUGENE_SAZ.SEV_PL_SHOP_SECTOR_H$) " +
			"union" +
			" (select DATE_INSERT from EUGENE_SAZ.SEV_PL_RACK_IN_SECTOR_H$) " +
			"union" +
			" (select DATE_INSERT from EUGENE_SAZ.SEV_PL_SHELF_H$) " +
			"union" +
			" (select DATE_INSERT from EUGENE_SAZ.SEV_PL_WARES_ON_RACK_H$) " +
			"order by DATE_INSERT desc";
	public List<Date> getHistoryMark()
	{

	}
}
