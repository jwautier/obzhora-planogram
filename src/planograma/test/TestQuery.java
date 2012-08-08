package planograma.test;

import planograma.constant.data.*;
import planograma.data.*;
import planograma.model.*;
import planograma.model.history.RackWaresHModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 28.03.12
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public class TestQuery {
	public static void main(String args[]) throws SQLException {

		UserContext userContext = null;
		try {
			userContext = new UserContext("", "");
			System.out.println("-------------------------StateAllModel-------------------------");
			testStateAllModel(userContext);
			System.out.println("-------------------------ShopModel-------------------------");
			testShopModel(userContext);
			System.out.println("-------------------------SectorModel-------------------------");
			testSectorModel(userContext);
			System.out.println("-------------------------RackModel-------------------------");
			testRackModel(userContext);
			System.out.println("-------------------------RackShelfModel-------------------------");
			testRackShelfModel(userContext);
			System.out.println("-------------------------RackTemplateModel-------------------------");
			testRackTemplateModel(userContext);
			System.out.println("-------------------------RackShelfTemplateModel-------------------------");
			testRackShelfTemplateModel(userContext);
			System.out.println("-------------------------RackWaresModel-------------------------");
			testRackWaresModel(userContext);


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (userContext != null && userContext.getConnection() != null) {
				userContext.getConnection().rollback();
				userContext.getConnection().close();
			}
		}
	}

	public static void testStateAllModel(final UserContext userContext) throws SQLException {
		testStateAllModel(userContext, SectorConst.TABLE_NAME, SectorConst.STATE_SECTOR, SectorConst.STATE_ALL_PART_STATE_STATE_SECTOR);
		testStateAllModel(userContext, RackConst.TABLE_NAME, RackConst.STATE_RACK, RackConst.STATE_ALL_PART_STATE_STATE_RACK);
		testStateAllModel(userContext, RackConst.TABLE_NAME, RackConst.LOAD_SIDE, RackConst.STATE_ALL_PART_STATE_LOAD_SIDE);
		testStateAllModel(userContext, RackTemplateConst.TABLE_NAME, RackTemplateConst.STATE_RACK_TEMPLATE, RackTemplateConst.STATE_ALL_PART_STATE_STATE_RACK_TEMPLATE);
		testStateAllModel(userContext, RackTemplateConst.TABLE_NAME, RackTemplateConst.LOAD_SIDE, RackTemplateConst.STATE_ALL_PART_STATE_LOAD_SIDE);
		testStateAllModel(userContext, RackShelfConst.TABLE_NAME, RackShelfConst.TYPE_SHELF, RackShelfConst.STATE_ALL_PART_STATE_TYPE_SHELF);
		testStateAllModel(userContext, RackShelfTemplateConst.TABLE_NAME, RackShelfTemplateConst.TYPE_SHELF, RackShelfTemplateConst.STATE_ALL_PART_STATE_STATE_TYPE_SHELF);
	}

	private static void testStateAllModel(final UserContext userContext, final String tableName, final String columnName, final int starteAllPartState) throws SQLException {
		final StateAllModel stateAllModel = StateAllModel.getInstance();
		System.out.println(tableName + '.' + columnName);
		final List<StateAll> list = stateAllModel.list(userContext, starteAllPartState);
		System.out.println("list");
		for (final StateAll o : list) {
			System.out.println(o.toJsonObject());
		}
	}

	public static void testShopModel(final UserContext userContext) throws SQLException {
		final ShopModel shopModel = ShopModel.getInstance();
		final List<Shop> list = shopModel.list(userContext);
		System.out.println("list");
		for (final Shop o : list) {
			System.out.println(o.toJsonObject());
		}
	}

	public static void testSectorModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, null, "sector 1", 2000, 2000, 300, null, null, null, null, null, null);
		System.out.println(sector.toJsonObject());
		sectorModel.insert(userContext, sector);
		System.out.println("insert");
		List<Sector> list = sectorModel.list(userContext, 8);
		System.out.println("list");
		for (final Sector o : list) {
			System.out.println(o.toJsonObject());
		}
		sector = sectorModel.select(userContext, sector.getCode_sector());
		System.out.println("select");
		System.out.println(sector.toJsonObject());
		sector.setWidth(2500);
		sectorModel.update(userContext, sector);
		System.out.println("update");
		sector = sectorModel.select(userContext, sector.getCode_sector());
		System.out.println(sector.toJsonObject());
		//TODO changestate
		sectorModel.delete(userContext, sector.getCode_sector());
		System.out.println("delete");
		sector = sectorModel.select(userContext, sector.getCode_sector());
		if (sector != null)
			System.out.println("Ошибка удаления");
	}

	public static void testRackModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, null, "sector 1", 2000, 2000, 300, null, null, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final RackModel rackModel = RackModel.getInstance();
		Rack rack = new Rack(null, null, "rack 1", "bar code 1", 50, 200, 150, sector.getCode_sector(), 50, 50, 0, LoadSide.F, null, false, false, TypeRack.R, null, null, null, null, null, null);
		System.out.println(rack.toJsonObject());
		rackModel.insert(userContext, rack);
		System.out.println("insert");
		List<Rack> list = rackModel.list(userContext, sector.getCode_sector());
		System.out.println("list");
		for (final Rack o : list) {
			System.out.println(o.toJsonObject());
		}
		rack = rackModel.select(userContext, rack.getCode_rack());
		System.out.println("select");
		System.out.println(rack.toJsonObject());
		rack.setWidth(250);
		rackModel.update(userContext, rack);
		System.out.println("update");
		rack = rackModel.select(userContext, rack.getCode_rack());
		System.out.println(rack.toJsonObject());
		//TODO changestate
		rackModel.delete(userContext, rack.getCode_rack());
		System.out.println("delete");
		rack = rackModel.select(userContext, rack.getCode_rack());
		if (rack != null)
			System.out.println("Ошибка удаления");
	}

	public static void testRackShelfModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, null, "sector 1", 2000, 2000, 300, null, null, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final RackModel rackModel = RackModel.getInstance();
		final Rack rack = new Rack(null, null, "rack 1", "1", 50, 200, 150, sector.getCode_sector(), 1000, 1000, 0, LoadSide.F, null, false, false, TypeRack.R, null, null, null, null, null, null);
		rackModel.insert(userContext, rack);
		final RackShelfModel rackShelfModel = RackShelfModel.getInstance();
		RackShelf rackShelf = new RackShelf(rack.getCode_rack(), null, 10, 10, 50, 50, 50, 0, TypeShelf.DZ, null, null, null, null);
		System.out.println(rackShelf.toJsonObject());
		rackShelfModel.insert(userContext, rackShelf);
		System.out.println("insert");
		List<RackShelf> list = rackShelfModel.list(userContext, rack.getCode_rack());
		System.out.println("list");
		for (final RackShelf o : list) {
			System.out.println(o.toJsonObject());
		}
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		System.out.println("select");
		System.out.println(rackShelf.toJsonObject());
		rackShelf.setShelf_width(250);
		rackShelfModel.update(userContext, rackShelf);
		System.out.println("update");
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		System.out.println(rackShelf.toJsonObject());
		//TODO changestate
		rackShelfModel.delete(userContext, rackShelf.getCode_shelf());
		System.out.println("delete");
		rackShelf = rackShelfModel.select(userContext, rackShelf.getCode_shelf());
		if (rackShelf != null)
			System.out.println("Ошибка удаления");
	}

	public static void testRackTemplateModel(final UserContext userContext) throws SQLException {
		final RackTemplateModel rackTemplateModel = RackTemplateModel.getInstance();
		RackTemplate rackTemplate = new RackTemplate(null, null, "rack template 1", 50, 200, 150, LoadSide.F, null, null, null, null, null, null);
		System.out.println(rackTemplate.toJsonObject());
		rackTemplateModel.insert(userContext, rackTemplate);
		System.out.println("insert");
		List<RackTemplate> list = rackTemplateModel.list(userContext);
		System.out.println("list");
		for (final RackTemplate o : list) {
			System.out.println(o.toJsonObject());
		}
		rackTemplate = rackTemplateModel.select(userContext, rackTemplate.getCode_rack_template());
		System.out.println("select");
		System.out.println(rackTemplate.toJsonObject());
		rackTemplate.setWidth(250);
		rackTemplateModel.update(userContext, rackTemplate);
		System.out.println("update");
		rackTemplate = rackTemplateModel.select(userContext, rackTemplate.getCode_rack_template());
		System.out.println(rackTemplate.toJsonObject());
		//TODO changestate
		rackTemplateModel.delete(userContext, rackTemplate.getCode_rack_template());
		System.out.println("delete");
		rackTemplate = rackTemplateModel.select(userContext, rackTemplate.getCode_rack_template());
		if (rackTemplate != null)
			System.out.println("Ошибка удаления");
	}

	public static void testRackShelfTemplateModel(final UserContext userContext) throws SQLException {
		final RackTemplateModel rackTemplateModel = RackTemplateModel.getInstance();
		final RackTemplate rackTemplate = new RackTemplate(null, null, "rack template 1", 50, 200, 150, LoadSide.F, null, null, null, null, null, null);
		rackTemplateModel.insert(userContext, rackTemplate);
		final RackShelfTemplateModel rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
		RackShelfTemplate rackShelfTemplate = new RackShelfTemplate(rackTemplate.getCode_rack_template(), null, 10, 10, 50, 50, 50, 0, TypeShelf.DZ, null, null, null, null);
		System.out.println(rackShelfTemplate.toJsonObject());
		rackShelfTemplateModel.insert(userContext, rackShelfTemplate);
		System.out.println("insert");
		List<RackShelfTemplate> list = rackShelfTemplateModel.list(userContext, rackTemplate.getCode_rack_template());
		System.out.println("list");
		for (final RackShelfTemplate o : list) {
			System.out.println(o.toJsonObject());
		}
		rackShelfTemplate = rackShelfTemplateModel.select(userContext, rackShelfTemplate.getCode_shelf_template());
		System.out.println("select");
		System.out.println(rackShelfTemplate.toJsonObject());
		rackShelfTemplate.setShelf_width(250);
		rackShelfTemplateModel.update(userContext, rackShelfTemplate);
		System.out.println("update");
		rackShelfTemplate = rackShelfTemplateModel.select(userContext, rackShelfTemplate.getCode_shelf_template());
		System.out.println(rackShelfTemplate.toJsonObject());
		//TODO changestate
		rackShelfTemplateModel.delete(userContext, rackShelfTemplate.getCode_shelf_template());
		System.out.println("delete");
		rackShelfTemplate = rackShelfTemplateModel.select(userContext, rackShelfTemplate.getCode_shelf_template());
		if (rackShelfTemplate != null)
			System.out.println("Ошибка удаления");
	}

	public static void testRackWaresModel(final UserContext userContext) throws SQLException {
		final SectorModel sectorModel = SectorModel.getInstance();
		Sector sector = new Sector(8, null, null, "sector 1", 2000, 2000, 300, null, null, null, null, null, null);
		sectorModel.insert(userContext, sector);
		final RackModel rackModel = RackModel.getInstance();
		final Rack rack = new Rack(null, null, "rack 1", "1", 50, 200, 150, sector.getCode_sector(), 1000, 1000, 0, LoadSide.F, null, false, false, TypeRack.R, null, null, null, null, null, null);
		rackModel.insert(userContext, rack);
		final RackWaresModel rackWaresModel = RackWaresModel.getInstance();
		final RackWaresHModel rackWaresHModel = RackWaresHModel.getInstance();
		RackWares rackWares = new RackWares(rack.getCode_rack(), 10, 19, null, TypeRackWares.NA, 1, 10, 10, 50, 50, 50, 1, null, null,null,null, null, "waresTest", "unitTest", "barcodeTest");
		System.out.println(rackWares.toJsonObject());
		rackWaresModel.insert(userContext, rackWares);
		System.out.println("insert");
		List<RackWares> list = rackWaresModel.list(userContext, rack.getCode_rack());
		System.out.println("list");
		for (final RackWares o : list) {
			System.out.println(o.toJsonObject());
		}
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		System.out.println("select");
		System.out.println(rackWares.toJsonObject());
		rackWares.setWares_width(250);
		rackWaresModel.update(userContext, rackWares);
		System.out.println("update");
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		System.out.println(rackWares.toJsonObject());
		rackWaresModel.delete(userContext, rackWares.getCode_wares_on_rack());
		System.out.println("delete");
		rackWares = rackWaresModel.select(userContext, rackWares.getCode_wares_on_rack());
		if (rackWares != null)
			System.out.println("Ошибка удаления");
	}
}
