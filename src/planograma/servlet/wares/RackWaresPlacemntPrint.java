package planograma.servlet.wares;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.data.*;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.model.*;
import planograma.utils.geometry.Point2D;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 08.06.12
 * Time: 4:25
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_RACK_WARES_PLACEMENT_PRINT + "*")
public class RackWaresPlacemntPrint extends HttpServlet {
	public static final String URL = UrlConst.URL_RACK_WARES_PLACEMENT_PRINT;

	private static final float marginLeft = 28;
	private static final float marginRight = 28;
	private static final float marginTop = 28;
	private static final float marginTitle = 28;
	private static final float marginBottom = 28;

	private ShopModel shopModel;
	private SectorModel sectorModel;
	private RackModel rackModel;
	private RackShelfModel rackShelfModel;
	private RackWaresModel rackWaresModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		shopModel = ShopModel.getInstance();
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
		rackWaresModel = RackWaresModel.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		try {
			int code_rack = Integer.parseInt(request.getPathInfo().substring(1));
			final HttpSession session = request.getSession(false);
			final UserContext userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);

			final Rack rack = rackModel.select(userContext, code_rack);
			if (rack.getLoad_side() == LoadSide.U) {
				final Integer t = rack.getWidth();
				rack.setWidth(rack.getLength());
				rack.setLength(rack.getHeight());
				rack.setHeight(t);
			} else {
				final Integer t = rack.getWidth();
				rack.setWidth(rack.getLength());
				rack.setLength(t);
			}
			final Sector sector = sectorModel.select(userContext, rack.getCode_sector());
			final Shop shop = shopModel.select(userContext, sector.getCode_shop());
			final List<RackShelf> rackShelfList = rackShelfModel.list(userContext, code_rack);
			final List<RackShelf2D> rackShelf2DList = new ArrayList<RackShelf2D>(rackShelfList.size());
			for (final RackShelf rackShelf : rackShelfList) {
				rackShelf2DList.add(new RackShelf2D(rackShelf));
			}
			final List<RackWares> rackWaresList = rackWaresModel.list(userContext, code_rack);
			final List<RackWares2D> rackWares2DList = new ArrayList<RackWares2D>(rackWaresList.size());
			for (final RackWares rackWares : rackWaresList) {
				rackWares2DList.add(new RackWares2D(rackWares));
			}

			final BaseFont baseFont = BaseFont.createFont(getServletContext().getRealPath("font/FreeSerif.ttf"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			final Font font = new Font(baseFont, Font.DEFAULTSIZE, Font.NORMAL);
			final Rectangle pageSize;
			if (rack.getWidth() > rack.getHeight()) {
				pageSize = PageSize.A4.rotate();
			} else {
				pageSize = PageSize.A4;
			}
			final Document document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
			final PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());
			document.open();
			final PdfContentByte cb = writer.getDirectContent();
			final float m = Math.max(rack.getWidth() / (pageSize.getWidth() - marginLeft - marginRight), rack.getHeight() / (pageSize.getHeight() - marginTop - marginTitle - marginBottom));
			final float y0 = pageSize.getHeight() - marginTop - marginTitle - rack.getHeight() / m;

			Paragraph p = new Paragraph(shop.getName_shop() + " (" + sector.getName_sector() + ") стеллаж " + rack.getRack_barcode(), font);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			// стеллаж
			drawRack(cb, rack, m, y0);
			// полки
			for (final RackShelf2D rackShelf2D : rackShelf2DList) {
				drawRackShelf2D(cb, rackShelf2D, m, y0);
			}
			// товары
			for (final RackWares2D rackWares2D : rackWares2DList) {
				drawRackWares2D(cb, rackWares2D, m, y0, baseFont);
			}

			document.setPageSize(PageSize.A4);
			document.newPage();

			final PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
//			int widths[] = new int[8];
//			table.setWidths(widths);
			PdfPCell cell;
			cell = new PdfPCell(new Paragraph("№", font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			for (final RackWares2D rackWares2D : rackWares2DList) {
				table.addCell(new PdfPCell(new Paragraph(String.valueOf(rackWares2D.getRackWares().getOrder_number_on_rack()), font)));
			}
			document.add(table);
			document.close();
			resp.setContentType("application/pdf");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void drawRack(final PdfContentByte cb, final Rack rack, final float m, final float y0) {
		cb.saveState();
		cb.setColorStroke(GrayColor.BLACK);
		cb.rectangle(marginLeft, y0, rack.getWidth() / m, rack.getHeight() / m);
		cb.stroke();
		cb.restoreState();
	}

	private void drawRackShelf2D(final PdfContentByte cb, final RackShelf2D rackShelf2D, final float m, final float y0) {
		cb.saveState();
		cb.setColorFill(GrayColor.GRAY);
		final Point2D p1 = rackShelf2D.getP1();
		p1.scale(m);
		p1.move(marginLeft, y0);
		final Point2D p2 = rackShelf2D.getP2();
		p2.scale(m);
		p2.move(marginLeft, y0);
		final Point2D p3 = rackShelf2D.getP3();
		p3.scale(m);
		p3.move(marginLeft, y0);
		final Point2D p4 = rackShelf2D.getP4();
		p4.scale(m);
		p4.move(marginLeft, y0);
		cb.moveTo(p1.getX(), p1.getY());
		cb.lineTo(p2.getX(), p2.getY());
		cb.lineTo(p3.getX(), p3.getY());
		cb.lineTo(p4.getX(), p4.getY());
		cb.fill();
		cb.restoreState();
	}

	private void drawRackWares2D(final PdfContentByte cb, final RackWares2D rackWares2D, final float m, final float y0, final BaseFont baseFont) {
		cb.saveState();
		cb.setColorStroke(GrayColor.BLACK);
		final Point2D p1 = rackWares2D.getP1();
		p1.scale(m);
		p1.move(marginLeft, y0);
		final Point2D p2 = rackWares2D.getP2();
		p2.scale(m);
		p2.move(marginLeft, y0);
		final Point2D p3 = rackWares2D.getP3();
		p3.scale(m);
		p3.move(marginLeft, y0);
		final Point2D p4 = rackWares2D.getP4();
		p4.scale(m);
		p4.move(marginLeft, y0);
		cb.moveTo(p1.getX(), p1.getY());
		cb.lineTo(p2.getX(), p2.getY());
		cb.lineTo(p3.getX(), p3.getY());
		cb.lineTo(p4.getX(), p4.getY());
		cb.closePathStroke();

		cb.beginText();
		float minSize = Math.min(rackWares2D.getRackWares().getWares_width(), rackWares2D.getRackWares().getWares_length()) / m;
		final String indexStr = String.valueOf(rackWares2D.getRackWares().getOrder_number_on_rack());
		float wM = minSize / baseFont.getWidthPoint(indexStr, Font.DEFAULTSIZE);
		float x = marginLeft + rackWares2D.getRackWares().getPosition_x() / m;
		float y = y0+ rackWares2D.getRackWares().getPosition_y() / m;
		cb.moveText(x - minSize / 2, y - minSize / 2.5F);
		cb.setFontAndSize(baseFont, Font.DEFAULTSIZE * wM);
		cb.showText(indexStr);
		cb.endText();

		cb.restoreState();
	}
}
