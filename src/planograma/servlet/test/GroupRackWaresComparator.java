package planograma.servlet.test;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 18.06.12
 * Time: 6:18
 * To change this template use File | Settings | File Templates.
 */
public class GroupRackWaresComparator implements Comparator<GroupRackWares> {
	float minWidth;
	float minHeight;

	public GroupRackWaresComparator(float minWidth, float minHeight) {
		this.minWidth = minWidth;
		this.minHeight = minHeight;
	}

	@Override
	public int compare(GroupRackWares o1, GroupRackWares o2) {
		int i = (int) ((o2.getMinY() - o1.getMinY()) / minHeight);
		if (i == 0)
			i = (int) ((o1.getMinX() - o2.getMinX()) / minWidth);
		return i;
	}
}
