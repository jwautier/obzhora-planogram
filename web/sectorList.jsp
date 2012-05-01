<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.sector.SectorList" %>
<%@ page import="planograma.servlet.sector.SectorRemove" %>
<%@ page import="planograma.servlet.shop.ShopList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- TODO предпросмотр зала--%>
<html>
<head>
	<title>Редактирование залов торговой площадки</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
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
					<col width="25%"/>
					<col width="25%"/>
					<col width="50%"/>
				</colgroup>
				<tr>
					<td valign="top">
						<table class="menu">
							<tr>
								<td><a href="#" id="sectorAdd" onclick="return aOnClick(this, sectorAdd)" class="disabled"><%=JspUtils.toMenuTitle("Добавить зал")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorView" onclick="return aOnClick(this, sectorView)" class="disabled"><%=JspUtils.toMenuTitle("Просмотр зала")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorViewHistory" onclick="return aOnClick(this, sectorViewHistory)" class="disabled"><%=JspUtils.toMenuTitle("Просмотр истории зала")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorEdit" onclick="return aOnClick(this, sectorEdit)" class="disabled"><%=JspUtils.toMenuTitle("Редактировать зал")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorActive" onclick="return aOnClick(this, fSectorActive)" class="disabled"><%=JspUtils.toMenuTitle("Активировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorNotActive" onclick="return aOnClick(this, fSectorNotActive)" class="disabled"><%=JspUtils.toMenuTitle("Заблокировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="sectorRemove" onclick="return aOnClick(this, sectorRemove)" class="disabled"><%=JspUtils.toMenuTitle("Удалить зал")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td>
						<select id="shopList"
								size="20"
								style="width: 100%; height: 100%; min-width: 150px;"
								onchange="selectShop(value)">
						</select>
					</td>
					<td>
						<select id="sectorList"
								size="20"
								style="width: 100%; height: 100%; min-width: 150px;"
								onchange="selectSector(value)">
						</select>
					</td>
					<td style="min-width: 150px; background-color: #dcdcdc;">
						preview
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	function selectShop(code_shop) {
		if (code_shop != null && code_shop != '') {
			var shopList = $('#shopList');
			var option = shopList.find('option[value=' + code_shop + ']');
			if (option.length==1) {
				option.attr('selected', 'selected');
				$('#sectorAdd').removeClass('disabled');
				$('#sectorView').addClass('disabled');
				$('#sectorEdit').addClass('disabled');
				$('#sectorActive').addClass('disabled');
				$('#sectorNotActive').addClass('disabled');
				$('#sectorRemove').addClass('disabled');
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
		if (code_sector != null && code_sector != '') {
			var sectorList = $('#sectorList');
			var option = sectorList.find('option[value=' + code_sector + ']');
			if (option.length==1) {
				option.attr('selected', 'selected');
				$('#sectorView').removeClass('disabled');
				$('#sectorEdit').removeClass('disabled');
				//TODO for state view button
				$('#sectorActive').removeClass('disabled');
				$('#sectorNotActive').removeClass('disabled');
				$('#sectorRemove').removeClass('disabled');
				setCookie('code_sector', code_sector);
				//	TODO preview
			}
			else {
				setCookie('code_sector', '');
			}
		}
		else {
			setCookie('code_sector', '');
		}
	}
	function sectorAdd()
	{
		var code_shop=$('#shopList').val();
		if (code_shop!=null && code_shop.length>0)
		{
			setCookie('code_shop', code_shop);
			setCookie('code_sector', '');
			document.location='sectorEdit.jsp';
		}
	}
	function sectorView()
	{
		//TODO
		alert('Функционал на стадии разработки');
	}
	function sectorViewHistory()
	{
		//TODO
		alert('Функционал на стадии разработки');
	}
	function sectorEdit()
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
	function sectorRemove()
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