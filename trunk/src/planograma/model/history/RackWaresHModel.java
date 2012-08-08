package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.data.UserContext;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 15.06.12
 * Time: 4:38
 * To change this template use File | Settings | File Templates.
 */
public class RackWaresHModel {

	public static final Logger LOG = Logger.getLogger(RackWaresHModel.class);



	private static RackWaresHModel instance = new RackWaresHModel();

	public static RackWaresHModel getInstance() {
		return instance;
	}

	private RackWaresHModel(final UserContext userContext) {
	}
}
