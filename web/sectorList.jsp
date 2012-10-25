<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.sector.SectorList" %>
<%@ page import="planograma.servlet.sector.SectorRemove" %>
<%@ page import="planograma.servlet.shop.ShopList" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page import="planograma.servlet.sector.SectorEdit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_sector_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_SECTOR_EDIT);
%>
<html>
<head>
	<title>Редактирование залов торговой площадки</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/draw/drawRack.jsp"></script>
	<script type="text/javascript" src="js/draw/calcCoordinatesRack.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body>
<table class="frame">
	<tr>
		<td class="path">
			<table>
				<tr>
					<td><a href="menu.jsp">Меню</a></td>
					<td>&gt;</td>
					<td><i>Залы торговых площадок</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="logout()">Выход</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="frame">
				<colgroup>
					<col/>
					<col/>
					<col/>
					<col width="100%"/>
				</colgroup>
				<tr>
					<td valign="top">
						<table class="menu">
							<tr>
								<td><a href="#" id="sectorAdd" onclick="return aOnClick(this, fSectorAdd)" class="disabled"><%=JspUtils.toMenuTitle("Добавить зал")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorPrint" onclick="return aOnClick(this, fSectorPrint)" class="disabled"><%=JspUtils.toMenuTitle("Печать зала")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorViewHistory" onclick="return aOnClick(this, fSectorViewHistory)" class="disabled"><%=JspUtils.toMenuTitle("Просмотр истории зала")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorEdit" onclick="return aOnClick(this, fSectorEdit)" class="disabled"><%=JspUtils.toMenuTitle("Редактировать зал")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorActive" onclick="return aOnClick(this, fSectorActive)" class="disabled"><%=JspUtils.toMenuTitle("Активировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorNotActive" onclick="return aOnClick(this, fSectorNotActive)" class="disabled"><%=JspUtils.toMenuTitle("Заблокировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorRemove" onclick="return aOnClick(this, fSectorRemove)" class="disabled"><%=JspUtils.toMenuTitle("Удалить зал")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td>
						<select id="shopList"
								size="20"
								style="width: 200px; height: 100%; "
								onchange="selectShop(value)">
						</select>
					</td>
					<td>
						<select id="sectorList"
								size="20"
								style="width: 200px; height: 100%; "
								onchange="selectSector(value)">
						</select>
					</td>
					<td id="preview_td" valign="top">
						<canvas id="preview_canvas" style="display: none;" width="150" height="150">canvas not supported</canvas>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<jsp:include page="choiceSectorPrint.jsp"/>

</body>
<script type="text/javascript">

	var canSectorEdit='<%=access_sector_edit%>';

	function selectShop(code_shop) {
		if (code_shop != null && code_shop != '') {
			var shopList = $('#shopList');
			var option = shopList.find('option[value=' + code_shop + ']');
			if (option.length==1) {
				option.attr('selected', 'selected');
				if (canSectorEdit!='disabled')
				{
					$('#sectorAdd').removeClass('disabled');
				}
				$('#sectorPrint').addClass('disabled');
				choiceSectorPrintSetCodeSector(null);
				$('#sectorEdit').addClass('disabled');
				$('#sectorActive').addClass('disabled');
				$('#sectorNotActive').addClass('disabled');
				$('#sectorRemove').addClass('disabled');
				$('#preview_canvas').hide();
				setCookie('code_shop', code_shop);
				postJson('<%=SectorList.URL%>', {code_shop:code_shop}, function (data) {
					var sectorList = $('#sectorList');
					sectorList.empty();
					for (var i in data.sectorList) {
						var item = data.sectorList[i];
						sectorList.append('<option value="' + item.code_sector + '">' + item.name_sector + '</option>')
					}
					var code_sector = getCookie('code_sector');
					if (code_sector != null && code_sector.length>0) {
						selectSector(code_sector);
					}
				});
			}
			else {
				setCookie('code_shop', '');
			}
		}
		else {
			setCookie('code_shop', '');
		}
	}
	function selectSector(code_sector) {
		$('#preview_canvas').hide();
		if (code_sector != null && code_sector != '') {
			var sectorList = $('#sectorList');
			var option = sectorList.find('option[value=' + code_sector + ']');
			if (option.length==1) {
				option.attr('selected', 'selected');
				$('#sectorPrint').removeClass('disabled');
				choiceSectorPrintSetCodeSector(code_sector);
				$('#sectorEdit').removeClass('disabled');
				$('#sectorActive').removeClass('disabled');
				$('#sectorNotActive').removeClass('disabled');
				if (canSectorEdit!='disabled')
				{
					$('#sectorRemove').removeClass('disabled');
				}
				setCookie('code_sector', code_sector);

				postJson('<%=SectorEdit.URL%>', {code_sector:code_sector}, function (data) {
					window.sector=data.sector;
					window.rackList = data.rackList;
					selectSector2();
				}, 'preview_td');
			}
			else {
				setCookie('code_sector', '');
			}
		}
		else {
			setCookie('code_sector', '');
		}
	}
	function selectSector2()
	{
		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = window.preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		var width=preview_td.width() - 4;
		var height= preview_td.height() - 4;
		window.preview_m = Math.max(window.sector.length / width, window.sector.width / height);
		window.preview_canvas.width = window.sector.length/window.preview_m;
		window.preview_canvas.height = window.sector.width/window.preview_m;

		for (var i = 0; i < window.rackList.length; i++) {
			calcCoordinatesRack(window.rackList[i]);
			drawRack(window.rackList[i], window.preview_context, 0, 0, window.preview_m);
		}
		var title=window.sector.name_sector;
		title+='\n'+window.sector.length+'x'+window.sector.width+'x'+window.sector.height;
		$('#preview_canvas').attr('title', title);
		$('#preview_canvas').show();
	}
	function fSectorAdd()
	{
		var code_shop=$('#shopList').val();
		if (code_shop!=null && code_shop.length>0)
		{
			setCookie('code_shop', code_shop);
			setCookie('code_sector', '');
			document.location='sectorEdit.jsp';
		}
	}
	function fSectorPrint()
	{
		choiceSectorPrintShow();
	}
	function fSectorViewHistory()
	{
		//TODO
		alert('Функционал на стадии разработки');
	}
	function fSectorEdit()
	{
		var code_sector = $('#sectorList').val();
		if (code_sector!=null && code_sector.length>0)
		{
			setCookie('code_sector', code_sector);
			document.location='sectorEdit.jsp';
		}
	}
	function fSectorActive()
	{
		//TODO
		alert('Функционал на стадии разработки');
	}
	function fSectorNotActive()
	{
		//TODO
		alert('Функционал на стадии разработки');
	}
	function fSectorRemove()
	{
		var code_sector=$('#sectorList').val();
		if (code_sector!=null && code_sector.length>0)
		{
			postJson(
					'<%=SectorRemove.URL%>',
					{code_sector:code_sector},
					function () {
						var code_shop=$('#shopList').val();
						selectShop(code_shop);
					})
		}
	}

</script>
<script type="text/javascript">
	postJson('<%=ShopList.URL%>', null, function (data) {
		var shopList = $('#shopList');
		for (var i in data.shopList) {
			var item = data.shopList[i];
			shopList.append('<option value="' + item.code_shop + '">' + item.name_shop + '</option>')
		}
		var code_shop=getCookie('code_shop');
		if (code_shop!=null && code_shop.length>0)
		{
			selectShop(code_shop);
		}
	});
</script>
</html>