package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.ImageConst;
import planograma.data.UserContext;
import planograma.utils.FileUtils;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 26.04.12
 * Time: 5:33
 * To change this template use File | Settings | File Templates.
 */
public class ImageModel {

	private static final Logger LOG = Logger.getLogger(ImageModel.class);

	private final File tempDir;
	private static final String Q_SELECT = "select" +
			" " + ImageConst.IMAGE_THUMBNAIL + " " +
			"from " + ImageConst.TABLE_NAME + " " +
			"where " + ImageConst.CODE_IMAGE + " = ?";

	public InputStream select(final UserContext userContext, final int code_image) throws SQLException, IOException {
		long time = System.currentTimeMillis();
		InputStream in = null;
		File file = new File(tempDir, String.valueOf(code_image));
		if (!file.isFile()) {
			final Connection connection = userContext.getConnection();
			final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
			ps.setInt(1, code_image);
			final ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				in = resultSet.getBinaryStream(1);
				FileUtils.copy(in, new FileOutputStream(file));
			}
		}
		in = new FileInputStream(file);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return in;
	}

	public void clearCache() {
		long time = System.currentTimeMillis();
		if (tempDir != null) {
			for (final File f : tempDir.listFiles())
				FileUtils.delete(f);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static ImageModel instance = new ImageModel();

	public static ImageModel getInstance() {
		return instance;
	}

	private ImageModel() {
		final String path = System.getProperty("java.io.tmpdir");
		tempDir = new File(path, "planogram/image/");
		tempDir.mkdirs();
	}

	@Override
	protected void finalize() throws Throwable {
		FileUtils.delete(tempDir);
	}
}
