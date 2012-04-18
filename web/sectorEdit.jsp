<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.data.Sector" %>
<%@ page import="planograma.data.Rack" %>
<%@ page import="planograma.servlet.sector.SectorSave" %>
<%@ page import="planograma.servlet.sector.SectorEdit" %>
<%@ page import="planograma.constant.data.SectorConst" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- TODO При выделении подсвечивать --%>
<%-- TODO Отслеживать изменния размера окна --%>
<%-- TODO отмена действий --%>
<%-- TODO флаг запрета изменения размера --%>
<%-- TODO флаг запрета изменения местоположения --%>
<%-- TODO перед отправкой на сервер ругаться на незаполненые поля --%>
<%-- TODO ctrl+c ctrl+v
	$(document).keypress("c",function(e) {
		if(e.ctrlKey)
			alert("Ctrl+C was pressed!!");
	});
--%>
<html>
<head>
	<title>Редактирование зала</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body onload="loadComplite();" style="overflow-x:hidden;">
<table class="frame">
	<tr>
		<td class="path">
			<table>
				<tr>
					<td><a href="menu.jsp">Меню</a></td>
					<td>&gt;</td>
					<td><a href="sectorList.jsp">Залы торговых площадок</a></td>
					<td>&gt;</td>
					<td><i>Редактирование зала</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="logout()">Выход</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="frame">
				<tr>
					<td>
						<table class="menu">
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fSectorSave)"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fSectorReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackAdd)"><%=JspUtils.toMenuTitle("Добавить объект")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateAdd)"><%=JspUtils.toMenuTitle("Добавить стандартный стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Просмотреть стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butEdit" onclick="return aOnClick(this, fRackEdit)" class="disabled"><%=JspUtils.toMenuTitle("Редактировать стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Расстановка товара")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCopy" onclick="return aOnClick(this,fRackCopy)" class="disabled"><%=JspUtils.toMenuTitle("Копировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCut" onclick="return aOnClick(this,fRackCut)" class="disabled"><%=JspUtils.toMenuTitle("Вырезать")%></a></td>
							</tr>
							<tr>
								<td><a  href="#" id="butPaste" onclick="return aOnClick(this,fRackPaste)" class="disabled"><%=JspUtils.toMenuTitle("Вставить")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td id="edit_td" width="100%">
						<canvas id='edit_canvas' width="150" height="150">canvas not supported</canvas>
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table class="frame">
										<tr>
											<td id="preview_td" colspan="2" align="center" valign="middle">
												<canvas id="preview_canvas" width="150" height="150">canvas not supported</canvas>
											</td>
										</tr>
										<tr>
											<td onclick="kMadd()" class="scale">
												-
											</td>
											<td onclick="kMsub()" class="scale">
												+
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<hr/>
								</td>
							</tr>
							<tr>
								<td>
									<table>
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства зала:</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td><input type="text" id="sectorName" onchange="changeSectorName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">длина</td>
											<td><input type="text" id="sectorLength"
													   onchange="changeSectorLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="sectorWidth" onchange="changeSectorWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="sectorHeight"
													   onchange="changeSectorHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<hr/>
								</td>
							</tr>
							<tr>
								<td>
									<table id="showcaseAttr">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства объекта:</td>
										</tr>
										<tr>
											<td align="right">стеллаж!!!</td>
											<td><input type="checkbox" id="showcaseIs"/></td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td><input type="text" id="rackName"
													   onchange="changeShowcaseName(this)"/></td>
										</tr>
										<tr>
											<td align="right">штрихкод</td>
											<td><input type="text" id="rackBarcode"
													   onchange="changeRackBarcode(this)"/></td>
										</tr>
										<tr>
											<td align="right">x</td>
											<td><input type="text" id="showcaseX" onchange="changeShowcaseX(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text" id="showcaseY" onchange="changeShowcaseY(this)" onkeydown="numberFieldKeyDown(event, this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">поворот</td>
											<td>
												<select id="showcaseAngle" onchange="changeShowcaseAngle(this)">
													<option>0</option>
													<option>10</option>
													<option>20</option>
													<option>30</option>
													<option>40</option>
													<option>45</option>
													<option>50</option>
													<option>60</option>
													<option>70</option>
													<option>80</option>
													<option>90</option>
												</select>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="showcaseWidth"
													   onchange="changeShowcaseWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">длина</td>
											<td><input type="text" id="showcaseLength"
													   onchange="changeShowcaseLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="showcaseHeight"
													   onchange="changeShowcaseHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">загрузка</td>
											<td>
												<select id="rackLoadSide" onchange="changeShowcaseOpen(this)">
													<option value="<%=LoadSide.U%>">Сверху</option>
													<option value="<%=LoadSide.N%>">С севера</option>
													<option value="<%=LoadSide.E%>">С востока</option>
													<option value="<%=LoadSide.S%>">С юга</option>
													<option value="<%=LoadSide.W%>">С запада</option>
												</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table id="rackTemplate">
	<tr>
		<td class="path">
			<table>
				<tr>
					<td><i>Выбор стандартного стеллажа</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="rackTemplateCancel()">&#x2715;</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="frame">
			<colgroup>
				<col width="25%"/>
				<col width="75%"/>
			</colgroup>
				<tr>
					<td>
						<select id="rackTemplateList" size="20" style="width: 100%; height: 100%; min-width: 150px;" onchange="selectRackTemplate(value)">
						</select>
					</td>
					<td style="min-width: 150px; background-color: #dcdcdc;">
						preview
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="path">
			<table>
				<tr>
					<td width="100%"></td>
					<td><a href="#" onclick="rackTemplateCancel()">Отмена</a></td>
					<td>&nbsp;</td>
					<td><a href="#" onclick="rackTemplateOk()">Ок</a></td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">

var edit_canvas;
var preview_canvas;
var edit_context;
var preview_context;

var edit_m;
var preview_m;

var km;
var kx;
var ky;
var editMove;
var previewMove;
var x;
var y;
var showcase;
var copyObject;

function loadComplite()
{
	var code_shop=getCookie('code_shop');
	var code_sector=getCookie('code_sector');
	if (code_sector!=null && code_sector.length>0)
	{
		postJson('<%=SectorEdit.URL%>', {code_sector:code_sector}, function (data) {
			window.sector=data.sector;
			window.showcaseList = data.rackList;
			loadComplite2();
		});
	}
	else
	{
		window.sector=<%=new Sector(null, null, null, "Наименование", 1200, 600, 300, null, null,null, null,null,null).toJsonObject()%>;
		window.sector.code_shop=code_shop;
		window.showcaseList = [<%=new Rack(null, null, "Наименование", "",100, 100, 100, null, 100, 100, 0, LoadSide.S, null, null, null, null, null, null, null).toJsonObject()%>];
		loadComplite2();
	}
}

function loadComplite2()
{
	edit_canvas = document.getElementById("edit_canvas");
	edit_context = edit_canvas.getContext("2d");
	var edit_td = $('#edit_td');
	edit_canvas.width = edit_td.width() - 6;
	edit_canvas.height = edit_td.height() - 6;
	edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)

	preview_canvas = document.getElementById("preview_canvas");
	preview_context = preview_canvas.getContext("2d");
	var preview_td = $('#preview_td');
	preview_canvas.width = preview_td.width();// - 4;
	preview_canvas.height = preview_td.height();// - 4;
	preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);

 km = edit_m;
 kx = 0;
 ky = 0;
// draw sector
 window.rackAdd = false;
	window.rackTemplateAdd = null;
 editMove = 0;
 previewMove = false;
 x = 0;
 y = 0;
 showcase = null;
 copyObject=null;

for (var i = 0; i < window.showcaseList.length; i++) {
	calcCoordinates(window.showcaseList[i]);
}

drawEditCanvas();

drawPreviewCanvas();

setSectorProperty(sector);

	editCanvasMouseListener();
	previewCanvasMouseListener();
}

/**
 * определить координаты каждого угла объекта
 * @param showcase
 */
function calcCoordinates(showcase)
{
	// поворот объекта
	showcase.cos = Math.cos(showcase.angle*Math.PI/180);
	showcase.sin = Math.sin(showcase.angle*Math.PI/180);
	// правый верхний угол
	var x = showcase.length / 2;
	var y = showcase.width / 2;
	// относительно сцены
	showcase.x1 = showcase.x_coord + x * showcase.cos - y * showcase.sin;
	showcase.y1 = showcase.y_coord + x * showcase.sin + y * showcase.cos;
	// правый нижний угол
	y = -y;
	// относительно сцены
	showcase.x2 = showcase.x_coord + x * showcase.cos - y * showcase.sin;
	showcase.y2 = showcase.y_coord + x * showcase.sin + y * showcase.cos;
	// левый нижний угол
	x = -x;
	// относительно сцены
	showcase.x3 = showcase.x_coord + x * showcase.cos - y * showcase.sin;
	showcase.y3 = showcase.y_coord + x * showcase.sin + y * showcase.cos;
	// левый верхний угол
	y = -y;
	// относительно сцены
	showcase.x4 = showcase.x_coord + x * showcase.cos - y * showcase.sin;
	showcase.y4 = showcase.y_coord + x * showcase.sin + y * showcase.cos;
}

function setSectorProperty(sector) {
	document.getElementById('sectorName').value = sector.name_sector;
	document.getElementById('sectorLength').value = sector.length;
	document.getElementById('sectorWidth').value = sector.width;
	document.getElementById('sectorHeight').value = sector.height;
}

function ie_event(e) {
	if (e === undefined) {
		return window.event;
	}
	return e;
}

function drawEditCanvas() {
	window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
	window.edit_context.lineWidth = 1;
	window.edit_context.strokeStyle = "BLACK";
	window.edit_context.strokeRect(
			-window.kx / window.km,
			-window.ky / window.km,
			window.sector.length / window.km,
			window.sector.width / window.km);
	for (var i = 0; i < window.showcaseList.length; i++) {
		drawShowcase(window.showcaseList[i], window.edit_context, window.kx, window.ky, window.km);
	}
}

function drawPreviewCanvas() {
	window.preview_context.clearRect(0, 0, window.preview_canvas.width, window.preview_canvas.height);
	window.preview_context.lineWidth = 1;
	window.preview_context.strokeStyle = "BLACK";
	window.preview_context.strokeRect(0, 0, window.sector.length / window.preview_m, window.sector.width / window.preview_m);
	for (var i = 0; i < window.showcaseList.length; i++) {
		drawShowcase(window.showcaseList[i], window.preview_context, 0, 0, window.preview_m);
	}
	window.preview_context.lineWidth = 1;
	window.preview_context.strokeStyle = "BLUE";
	window.preview_context.strokeRect(window.kx / window.preview_m,
			window.ky / window.preview_m,
			window.edit_canvas.width * window.km / window.preview_m,
			window.edit_canvas.height * window.km / window.preview_m);
}

function drawShowcase(showcase, context, kx, ky, m) {
	var x1 = Math.round((showcase.x1 - kx) / m);
	var y1 = Math.round((showcase.y1 - ky) / m);
	var x2 = Math.round((showcase.x2 - kx) / m);
	var y2 = Math.round((showcase.y2 - ky) / m);
	var x3 = Math.round((showcase.x3 - kx) / m);
	var y3 = Math.round((showcase.y3 - ky) / m);
	var x4 = Math.round((showcase.x4 - kx) / m);
	var y4 = Math.round((showcase.y4 - ky) / m);
	if (window.showcase==showcase)
	{
		context.strokeStyle = "BLUE";
	}
	else
	{
		context.strokeStyle = "BLACK";
	}
	// сверху или с востока
	if (showcase.load_side == 'U' || showcase.load_side == 'E')
	{
//		context.strokeStyle = "GREEN";
		context.lineWidth = 3;
	}
	else
	{
//		context.strokeStyle = "BLACK";
		context.lineWidth = 1;
	}
	context.beginPath();
	context.moveTo(x1, y1);
	context.lineTo(x2, y2);
	context.stroke();
	// сверху или с севера
	if (showcase.load_side == 'U' || showcase.load_side == 'N')
	{
//		context.strokeStyle = "GREEN";
		context.lineWidth = 3;
	}
	else
	{
//		context.strokeStyle = "BLACK";
		context.lineWidth = 1;
	}
	context.beginPath();
	context.moveTo(x2, y2);
	context.lineTo(x3, y3);
	context.stroke();
	// сверху или с запада
	if (showcase.load_side == 'U' || showcase.load_side == 'W')
	{
//		context.strokeStyle = "GREEN";
		context.lineWidth = 3;
	}
	else
	{
//		context.strokeStyle = "BLACK";
		context.lineWidth = 1;
	}
	context.beginPath();
	context.moveTo(x3, y3);
	context.lineTo(x4, y4);
	context.stroke();

	// сверху или с юга
	if (showcase.load_side == 'U' || showcase.load_side == 'S')
	{
//		context.strokeStyle = "GREEN";
		context.lineWidth = 3;
	}
	else
	{
//		context.strokeStyle = "BLACK";
		context.lineWidth = 1;
	}
	context.beginPath();
	context.moveTo(x4, y4);
	context.lineTo(x1, y1);
	context.stroke();
}

function selectShowcase(showcase) {
	if (showcase != null) {
		document.getElementById('rackName').value = showcase.name_rack;
		document.getElementById('rackBarcode').value = showcase.rack_barcode;
		document.getElementById('showcaseX').value = showcase.x_coord;
		document.getElementById('showcaseY').value = showcase.y_coord;
		document.getElementById('showcaseAngle').value = showcase.angle;
		document.getElementById('showcaseWidth').value = showcase.width;
		document.getElementById('showcaseLength').value = showcase.length;
		document.getElementById('showcaseHeight').value = showcase.height;
		document.getElementById('rackLoadSide').value = showcase.load_side;
		if (showcase.code_rack != null && showcase.code_rack != '') {
			$('#butEdit').removeClass('disabled');
		} else {
			$('#butEdit').addClass('disabled');
		}
		$('#butCopy').removeClass('disabled');
		$('#butCut').removeClass('disabled');
	} else {
		document.getElementById('rackName').value = '';
		document.getElementById('rackBarcode').value = '';
		document.getElementById('showcaseX').value = '';
		document.getElementById('showcaseY').value = '';
		document.getElementById('showcaseAngle').value = 0;
		document.getElementById('showcaseWidth').value = '';
		document.getElementById('showcaseLength').value = '';
		document.getElementById('showcaseHeight').value = '';
		document.getElementById('rackLoadSide').value = '';
		$('#butEdit').addClass('disabled');
		$('#butCopy').addClass('disabled');
		$('#butCut').addClass('disabled');
	}
}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fSectorSave()
	{
		postJson('<%=SectorSave.URL%>', {sector:window.sector, rackList:window.showcaseList}, function (data) {
			setCookie('<%=SectorConst.CODE_SECTOR%>', data.code_sector);
			postJson('<%=SectorEdit.URL%>', {code_sector: data.code_sector}, function (data) {
				window.sector=data.sector;
				setSectorProperty(sector);
				window.showcaseList = data.rackList;
				for (var i = 0; i < window.showcaseList.length; i++) {
					calcCoordinates(window.showcaseList[i]);
				}
				drawEditCanvas();
				drawPreviewCanvas();
			});
		});
	}

	function fSectorReload()
	{
		window.location.reload();
	}

	function fRackAdd()
	{
		window.rackAdd=true;
	}

	function fRackTemplateAdd()
	{
		postJson('<%=RackTemplateList.URL%>', null, function (data) {
			var rackTemplateList = $('#rackTemplateList');
			rackTemplateList.empty();
			for (var i in data.rackTemplateList) {
				var item = data.rackTemplateList[i];
				var option = $('<option></option>')
				option.val(item.code_rack_template);
				option.text(item.name_rack_template);
				option.attr('json', $.toJSON(item));
				rackTemplateList.append(option)
			}
			var code_rack_template = getCookie('code_rack_template');
			if (code_rack_template != null && code_rack_template.length > 0) {
				var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
				if (option.length == 1) {
					option.attr('selected', 'selected');
				}
			}
			var rackTemplate=$('#rackTemplate');
			rackTemplate.animate({opacity:'show'},500);
		});
	}
	// TODO rackView
	function fRackEdit()
	{
		if (window.showcase!=null)
		{
		var code_rack = window.showcase.code_rack;
		if (code_rack!=null && code_rack!='')
		{
			setCookie('code_rack', code_rack);
			document.location='rackEdit.jsp';
		}
		}
	}

	function fRackCopy()
	{
		if (window.showcase!=null)
		{
			window.copyObject=window.showcase;
			$('#butPaste').removeClass('disabled');
		}
	}

	function fRackCut() {
		if (window.showcase != null) {
			for (var i = 0; i < window.showcaseList.length; i++) {
				if (window.showcaseList[i] == window.showcase) {
					window.showcaseList.splice(i, 1);
					i = window.showcaseList.length;
				}
			}
			window.copyObject = window.showcase;
			$('#butPaste').removeClass('disabled');
			window.showcase = null;
			selectShowcase(window.showcase);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

	function fRackPaste() {
		if (window.copyObject != null) {
				window.showcase = clone(window.copyObject);
				if (window.showcase.code_rack != null && window.showcase.code_rack != '') {
					window.showcase.copy_from_code_rack = window.showcase.code_rack;
				}
			window.showcase.code_rack = '';
			window.showcase.name_rack = window.showcase.name_rack + ' copy';
			window.showcase.x_coord = window.showcase.x_coord + 10 * km;
			window.showcase.y_coord = window.showcase.y_coord + 10 * km;
			window.showcaseList.push(window.showcase);
			window.copyObject=window.showcase;
			selectShowcase(window.showcase);
			calcCoordinates(window.showcase);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}
</script>
<%--обработка событий редактора--%>
<script type="text/javascript">
	function editCanvasMouseListener()
	{
		window.edit_canvas.onmousedown = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx + evnt.offsetX * window.km;
			var sy = window.ky + evnt.offsetY * window.km;

			if (window.rackAdd==true)
			{
				window.rackAdd=false;
				window.showcase=<%=new Rack(null, null, "", "", 1, 1, 1,null, 0, 0, 0, LoadSide.N, null, null, null, null, null, null, null).toJsonObject()%>;
				window.showcase.code_sector=window.sector.code_sector;
				window.showcase.x_coord=sx;
				window.showcase.y_coord=sy;
				window.showcaseList.push(window.showcase);
				selectShowcase(window.showcase);
				calcCoordinates(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove=19;
			}
			else if (window.rackTemplateAdd!=null)
			{
				window.showcase=clone(window.rackTemplateAdd);
				window.showcase.code_sector=window.sector.code_sector;
				window.showcase.name_rack=window.rackTemplateAdd.name_rack_template;
				if (window.rackTemplateAdd.load_side=='<%=LoadSide.U%>')
				{
					window.showcase.length=window.rackTemplateAdd.width;
					window.showcase.width=window.rackTemplateAdd.height;
					window.showcase.height=window.rackTemplateAdd.length;
				}
				else
				{
					window.showcase.length=window.rackTemplateAdd.width;
					window.showcase.width=window.rackTemplateAdd.length;
					window.showcase.height=window.rackTemplateAdd.height;
				}
				window.showcase.rack_barcode='';
				window.showcase.x_coord=sx;
				window.showcase.y_coord=sy;
				window.showcaseList.push(window.showcase);
				selectShowcase(window.showcase);
				calcCoordinates(window.showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove=1;
				window.rackTemplateAdd=null;
			}
			else
			{
				window.showcase = null;
				var d1 = null;
				var d2 = null;
				var d3 = null;
				var d4 = null;
				for (var i = window.showcaseList.length-1; window.showcase == null && i >=0; i--) {
					d1 = distance(window.showcaseList[i].x1,window.showcaseList[i].y1,window.showcaseList[i].x2,window.showcaseList[i].y2,sx,sy);
					d2 = distance(window.showcaseList[i].x2,window.showcaseList[i].y2,window.showcaseList[i].x3,window.showcaseList[i].y3,sx,sy);
					d3 = distance(window.showcaseList[i].x3,window.showcaseList[i].y3,window.showcaseList[i].x4,window.showcaseList[i].y4,sx,sy);
					d4 = distance(window.showcaseList[i].x4,window.showcaseList[i].y4,window.showcaseList[i].x1,window.showcaseList[i].y1,sx,sy);
					if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
						window.showcase = window.showcaseList[i];
					}
				}
				selectShowcase(showcase);
				drawEditCanvas();
				drawPreviewCanvas();
				if (window.showcase != null) {
					x = evnt.clientX;
					y = evnt.clientY;
					window.editMove = 1;
					if (window.showcase.length >= 7 * window.km && window.showcase.width >= 7 * window.km) {
						if (Math.abs(d1) < 3 * window.km) {
							// восток
							window.editMove += 2;
						} else if (Math.abs(d3) < 3 * window.km) {
							// запад
							window.editMove += 8;
						}
						if (Math.abs(d2) < 3 * window.km) {
							// север
							window.editMove += 4;
						} else if (Math.abs(d4) < 3 * window.km) {
							// юг
							window.editMove += 16;
						}
					}
				}
			}
		}

		window.edit_canvas.onmouseup = function(e) {
			window.editMove = 0;
		}

		window.edit_canvas.onmouseout = function(e) {
			window.editMove = 0;
		}

		window.edit_canvas.onmousemove = function (e) {
			if (window.editMove != 0 && window.showcase != null) {
				var evnt = ie_event(e);
				var dx = (evnt.clientX - x) * window.km;
				var dy = (evnt.clientY - y) * window.km;
				if (dx != 0 || dy != 0) {
					// перемещение
					if (window.editMove == 1) {
						var oldx = window.showcase.x_coord;
						var oldy = window.showcase.y_coord;
						// перемещение
						window.showcase.x_coord = window.showcase.x_coord + dx;
						window.showcase.y_coord = window.showcase.y_coord + dy;
						calcCoordinates(window.showcase);
						if ((showcase.x_coord < oldx && (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0)) ||
								(showcase.x_coord > oldx && (showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length)) ||
								(showcase.y_coord < oldy && (showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0)) ||
								(showcase.y_coord > oldy && (showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width))) {
							showcase.x_coord = oldx;
							showcase.y_coord = oldy;
							calcCoordinates(showcase);
						} else {
							document.getElementById('showcaseX').value = showcase.x_coord;
							document.getElementById('showcaseY').value = showcase.y_coord;
						}
					} else {
						var oldX = showcase.x_coord;
						var oldY = showcase.y_coord;
						var oldLength = showcase.length;
						var oldWidth=showcase.width;
						var dxy=dx*showcase.cos + dy*showcase.sin;
						if (editMove & 2) {
							// восток
							showcase.x_coord = showcase.x_coord + dxy * showcase.cos / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.sin / 2;
							showcase.length = showcase.length + dxy;
						} else if (editMove & 8) {
							// запад
							showcase.x_coord = showcase.x_coord + dxy * showcase.cos / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.sin / 2;
							showcase.length = showcase.length - dxy;
						}
						if (showcase.length < 7 * km) {
							showcase.length = 7 * km;
							showcase.x_coord = showcase.x_coord - dxy * showcase.cos / 2;
							showcase.y_coord = showcase.y_coord - dxy * showcase.sin / 2;
						}
						dxy=dy*showcase.cos - dx*showcase.sin;
						if (editMove & 4) {
							// север
							showcase.x_coord = showcase.x_coord - dxy * showcase.sin / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.cos / 2;
							showcase.width = showcase.width - dxy;
						} else if (editMove & 16) {
							// юг
							showcase.x_coord = showcase.x_coord - dxy * showcase.sin / 2;
							showcase.y_coord = showcase.y_coord + dxy * showcase.cos / 2;
							showcase.width = showcase.width + dxy;
						}
						if (showcase.width < 7 * km) {
							showcase.width = 7 * km
							showcase.x_coord = showcase.x_coord + dxy * showcase.sin / 2;
							showcase.y_coord = showcase.y_coord - dxy * showcase.cos / 2;
						}
						calcCoordinates(showcase);
						if (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0 ||
								showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length ||
								showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0 ||
								showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width) {
							showcase.x_coord = oldX;
							showcase.y_coord = oldY;
							showcase.length = oldLength;
							showcase.width = oldWidth;
							calcCoordinates(showcase);
						} else {
							document.getElementById('showcaseX').value = showcase.x_coord;
							document.getElementById('showcaseY').value = showcase.y_coord;
							document.getElementById('showcaseLength').value = showcase.length;
							document.getElementById('showcaseWidth').value = showcase.width;
						}
					}
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
				}
			}
		}
	}
</script>
<%-- обработка событий окна навигации--%>
<script type="text/javascript">
	function previewCanvasMouseListener()
	{
	preview_canvas.onmousedown = function(e) {
		previewMove = true;
		var evnt = ie_event(e);
		x = evnt.clientX;
		y = evnt.clientY;
		kx = evnt.offsetX * preview_m - edit_canvas.width * km / 2;
		ky = evnt.offsetY * preview_m - edit_canvas.height * km / 2;
		checkKxKy();
		drawPreviewCanvas();
	};

	preview_canvas.onmouseup = function(e) {
		if (previewMove) {
			previewMove = false;
			drawEditCanvas();
		}
	};

	preview_canvas.onmouseover = function(e) {
		if (previewMove) {
			previewMove = false;
			drawEditCanvas();
		}
	};

	preview_canvas.onmousemove = function (e) {
		if (previewMove) {
			var evnt = ie_event(e);
			kx = kx + (evnt.clientX - x) * preview_m;
			ky = ky + (evnt.clientY - y) * preview_m;
			checkKxKy();
			x = evnt.clientX;
			y = evnt.clientY;
			drawPreviewCanvas();
		}
	};
	}

	function kMadd() {
		km = km * 1.5;
		if (km > edit_m)
			km = edit_m;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	};

	function kMsub() {
		km = km / 1.5;
		if (km < 0.1)
			km = 0.1;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	};

	function checkKxKy() {
		if (kx > sector.length - edit_canvas.width * km)
			kx = sector.length - edit_canvas.width * km;
		if (kx < 0)
			kx = 0;
		if (ky > sector.width - edit_canvas.height * km)
			ky = sector.width - edit_canvas.height * km;
		if (ky < 0)
			ky = 0;
	};
</script>
<%-- обработка событий панели зала--%>
<script type="text/javascript">
	function changeSectorName(sectorName) {
		window.sector.name_sector = sectorName.value;
	}
	function changeSectorLength(sectorLength) {
		var currentLength = Number(sectorLength.value);
		var maxLength = 0;
		for (var i = 0; i < showcaseList.length; i++) {
			if (maxLength < showcaseList[i].x1) {
				maxLength = showcaseList[i].x1;
			}
			if (maxLength < showcaseList[i].x2) {
				maxLength = showcaseList[i].x2;
			}
			if (maxLength < showcaseList[i].x3) {
				maxLength = showcaseList[i].x3;
			}
			if (maxLength < showcaseList[i].x4) {
				maxLength = showcaseList[i].x4;
			}
		}
		if (currentLength > maxLength) {
			sector.length = currentLength;
			edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
			preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
			km = edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentLength > 0) {
				sectorLength.value = maxLength;
				sector.length = maxLength;
				edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
				preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
				km = edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				sectorLength.value = sector.length;
		}
	}
	function changeSectorWidth(sectorWidth) {
		var currentWidth = Number(sectorWidth.value);
		var maxWidth = 0;
		for (var i = 0; i < showcaseList.length; i++) {
			if (maxWidth < showcaseList[i].y1) {
				maxWidth = showcaseList[i].y1;
			}
			if (maxWidth < showcaseList[i].y2) {
				maxWidth = showcaseList[i].y2;
			}
			if (maxWidth < showcaseList[i].y3) {
				maxWidth = showcaseList[i].y3;
			}
			if (maxWidth < showcaseList[i].y4) {
				maxWidth = showcaseList[i].y4;
			}
		}
		if (currentWidth > maxWidth) {
			sector.width = currentWidth;
			edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
			preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
			km = edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentWidth > 0) {
				sectorWidth.value = maxWidth;
				sector.width = maxWidth;
				edit_m = Math.max(sector.length / edit_canvas.width, sector.width / edit_canvas.height)
				preview_m = Math.max(sector.length / preview_canvas.width, sector.width / preview_canvas.height);
				km = edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				sectorWidth.value = sector.width;
		}
	}

	function changeSectorHeight(sectorHeight) {
		var height = Number(sectorHeight.value);
		var maxHeight = 0;
		for (var i = 0; i < showcaseList.length; i++) {
			if (maxHeight < showcaseList[i].height) {
				maxHeight = showcaseList[i].height;
			}
		}
		if (height > maxHeight) {
			sector.height = height;
		}
		else {
			if (height > 0) {
				sectorHeight.value = maxHeight;
				sector.height = maxHeight;
			}
			else
				sectorHeight.value = sector.height;
		}
	}
</script>
<%-- обработка событий панели стеллажа--%>
<script type="text/javascript">
	function changeShowcaseName(rackName) {
		if (showcase != null) {
			showcase.name_rack = rackName.value;
		}
	}

	function changeRackBarcode(rackBarcode) {
		if (showcase != null) {
			showcase.rack_barcode = rackBarcode.value;
		}
	}

	function changeShowcaseX(showcaseX) {
		if (showcase != null) {
			var x = Number(showcaseX.value);
			if (x != null && !isNaN(x) && x != Infinity) {
				var oldx = showcase.x_coord;
				showcase.x_coord = x;
				calcCoordinates(showcase);
				// не выходит за области сектора
				if ((showcase.x_coord < oldx && (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0)) ||
						(showcase.x_coord > oldx && (showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length))) {
					showcase.x_coord = oldx;
					calcCoordinates(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseX.value = showcase.x_coord;
		}
	}

	function changeShowcaseY(showcaseY) {
		if (showcase != null) {
			var y = Number(showcaseY.value);
			if (y != null && !isNaN(y) && y != Infinity) {
				var oldy = showcase.y_coord;
				showcase.y_coord = y;
				calcCoordinates(showcase);
				// не выходит за области сектора
				if ((showcase.y_coord < oldy && (showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0)) ||
						(showcase.y_coord > oldy && (showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width))) {
					showcase.y_coord = oldy;
					calcCoordinates(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseY.value = showcase.y_coord;
		}
	}

	function changeShowcaseAngle(showcaseAngle) {
		// TODO не выходит за области сектора
		if (showcase != null) {
			var angle = Number(showcaseAngle.value);
			if (angle >= 0 && angle <= 90) {
				if (angle == 90) {
					showcaseAngle.value = 0;
					showcase.angle = 0;
					var temp = showcase.length;
					showcase.length = showcase.width;
					showcase.width = temp;
				}
				else {
					showcase.angle = angle;
				}
				calcCoordinates(showcase);
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else {
				showcaseAngle.value = showcase.angle;
			}
		}
	}

	function changeShowcaseWidth(showcaseWidth) {
		if (showcase != null) {
			var width = Number(showcaseWidth.value);
			// не выходит за области сектора
			if (width > 0 && width != Infinity) {
				var oldWidth = showcase.width;
				showcase.width = width;
				calcCoordinates(showcase);
				if (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0 ||
						showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length ||
						showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0 ||
						showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width) {
					showcase.width = oldWidth;
					calcCoordinates(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseWidth.value = showcase.width;
		}
	}

	function changeShowcaseLength(showcaseLength) {
		if (showcase != null) {
			var length = Number(showcaseLength.value);
			// не выходит за области сектора
			if (length > 0 && length != Infinity) {
				var oldLength = showcase.length;
				showcase.length = length;
				calcCoordinates(showcase);
				if (showcase.x1 < 0 || showcase.x2 < 0 || showcase.x3 < 0 || showcase.x4 < 0 ||
						showcase.x1 > sector.length || showcase.x2 > sector.length || showcase.x3 > sector.length || showcase.x4 > sector.length ||
						showcase.y1 < 0 || showcase.y2 < 0 || showcase.y3 < 0 || showcase.y4 < 0 ||
						showcase.y1 > sector.width || showcase.y2 > sector.width || showcase.y3 > sector.width || showcase.y4 > sector.width) {
					showcase.length = oldLength;
					calcCoordinates(showcase);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			showcaseLength.value = showcase.length;
		}
	}

	function changeShowcaseHeight(showcaseHeight) {
		if (showcase != null) {
			var height = Number(showcaseHeight.value);
			// не выходит за области сектора
			if (height > 0 && height <= sector.height) {
				showcase.height = height;
			}
			else {
				showcaseHeight.value = showcase.height;
			}
		}
	}

	function changeShowcaseOpen(rackLoadSide) {
		if (window.showcase != null) {
			//TODO
//			if (window.showcase.code_rack_template==null
//					|| window.showcase.code_rack_template==''
//					|| window.showcase.load_side!='<%=LoadSide.U%>')
//			{
				// для шаблонного стеллажа нельзя меньть сторону загрузки "сверху" на другую
//				if (window.showcase!='<%=LoadSide.U%>' && rackLoadSide.value!='<%=LoadSide.U%>')
//				{
//				}
			window.showcase.load_side = rackLoadSide.value;
			drawEditCanvas();
			drawPreviewCanvas();
//			}
		}
	}
</script>
<%-- обработка событий окно выбора стандартного стеллажа --%>
<script type="text/javascript">
	function rackTemplateCancel() {
		var rackTemplate = $('#rackTemplate');
		rackTemplate.animate({opacity:'hide'}, 500);
	}
	function rackTemplateOk() {
		var rackTemplate = $('#rackTemplate');
		rackTemplate.animate({opacity:'hide'}, 500);
		var rackTemplateList = $('#rackTemplateList');
		var code_rack_template = rackTemplateList.val();
		if (code_rack_template != null && code_rack_template.length > 0) {
			setCookie('code_rack_template', code_rack_template);
			var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
			if (option.length == 1) {
				window.rackTemplateAdd = $.evalJSON(option.attr('json'));
			}
		}
	}
</script>
</body>
</html>