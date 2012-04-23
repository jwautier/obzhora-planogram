<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.data.Rack" %>
<%@ page import="planograma.servlet.rack.RackEdit" %>
<%@ page import="planograma.constant.data.RackConst" %>
<%@ page import="planograma.servlet.rack.RackSave" %>
<%@ page import="planograma.data.RackShelf" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Редактирование стеллажа</title>
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
					<td><a href="sectorEdit.jsp">Редактирование зала</a></td>
					<td>&gt;</td>
					<td><i>Редактирование стеллажа</i></td>
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
								<td><a href="#" onclick="return aOnClick(this, fRackSave)"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackShelfAdd)"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCopy" onclick="return aOnClick(this, fCopy)" class="disabled"><%=JspUtils.toMenuTitle("Копировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCut" onclick="return aOnClick(this, fCut)" class="disabled"><%=JspUtils.toMenuTitle("Вырезать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butPaste" onclick="return aOnClick(this, fPaste)" class="disabled"><%=JspUtils.toMenuTitle("Вставить")%></a></td>
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
									<table>
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства стеллажа:</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td>
												<input type="text" id="rackName"
													   onchange="changeRackName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="rackWidth"
													   onchange="changeRackWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="rackHeight"
													   onchange="changeRackHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="rackLength"
													   onchange="changeRackLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">поворот</td>
											<td>
												<select id="rackAngle" onchange="changeRackAngle(this)">
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
											<td align="right">загрузка</td>
											<td>
												<select id="rackLoadSide" onchange="changeRackLoadSide(this)">
													<option value="U">Сверху</option>
													<option value="N">С переди</option>
												</select>
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
											<td colspan="2">свойства полки:</td>
										</tr>
										<tr>
											<td align="right">x</td>
											<td><input type="text" id="shelfX" onchange="changeShelfX(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text" id="shelfY" onchange="changeShelfY(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">поворот</td>
											<td>
												<select id="shelfAngle" onchange="changeShelfAngle(this)">
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
											<td><input type="text" id="shelfWidth" onchange="changeShelfWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="shelfHeight" onchange="changeShelfHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="shelfLength" onchange="changeShelfLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">тип</td>
											<td>
												<select id="shelfType" onchange="changeShelfType(this)">
													<option value="<%=TypeShelf.DZ%>">Мертвая зона</option>
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

<script type="text/javascript">
	function loadComplite() {
		var code_rack = getCookie('code_rack');
		postJson('<%=RackEdit.URL%>', {code_rack:code_rack}, function (data) {
			window.rack = data.rack;
			window.rackShelfList = data.rackShelfList;
			loadComplite2();
		});
	}
	function loadComplite2()
	{
		window.edit_canvas = document.getElementById("edit_canvas");
		window.edit_context = edit_canvas.getContext("2d");
		var edit_td = $('#edit_td');
		window.edit_canvas.width = edit_td.width() - 6;
		window.edit_canvas.height = edit_td.height() - 6;
		window.edit_m = Math.max(window.rack.width / edit_canvas.width, window.rack.height / edit_canvas.height)

		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		window.preview_canvas.width = preview_td.width();// - 4;
		window.preview_canvas.height = preview_td.height();// - 4;
		window.preview_m = Math.max(window.rack.width / preview_canvas.width,  window.rack.height / preview_canvas.height);

		window.km = edit_m;
		window.kx = 0;
		window.ky = 0;

		window.shelfAdd = false;
		window.editMove = 0;
		window.previewMove = false;
		x = 0;
		y = 0;
		window.shelf = null;
		window.copyObject=null;

		for (var i = 0; i < window.rackShelfList.length; i++) {
			calcCoordinates(window.rackShelfList[i]);
		}
		drawEditCanvas();
		drawPreviewCanvas();

		setRack(window.rack);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}

	function setRack(rack)
	{
		document.getElementById('rackName').value = window.rack.name_rack;
		document.getElementById('rackWidth').value =window.rack.width;
		document.getElementById('rackHeight').value =window.rack.height;
		document.getElementById('rackLength').value =window.rack.length;
		document.getElementById('rackAngle').value =window.rack.angle;
		document.getElementById('rackLoadSide').value =window.rack.load_side;
	}

	function selectShelf(shelf) {
		if (shelf != null) {
			document.getElementById('shelfX').value = shelf.x_coord;
			document.getElementById('shelfY').value = shelf.y_coord;
			document.getElementById('shelfAngle').value = shelf.angle;
			document.getElementById('shelfWidth').value = shelf.shelf_width;
			document.getElementById('shelfHeight').value = shelf.shelf_height;
			document.getElementById('shelfLength').value = shelf.shelf_length;
			document.getElementById('shelfType').value = shelf.type_shelf;
			$('#butCopy').removeClass('disabled');
			$('#butCut').removeClass('disabled');
		} else {
			document.getElementById('shelfX').value = '';
			document.getElementById('shelfY').value = '';
			document.getElementById('shelfAngle').value = 0;
			document.getElementById('shelfWidth').value = '';
			document.getElementById('shelfHeight').value = '';
			document.getElementById('shelfLength').value = '';
			document.getElementById('shelfType').value = '<%=TypeShelf.DZ%>';
			$('#butCopy').addClass('disabled');
			$('#butCut').addClass('disabled');
		}
	}

	function ie_event(e) {
		if (e === undefined) {
			return window.event;
		}
		return e;
	}

	/**
	 * определить координаты каждого угла объекта
	 * @param shelf
	 */
	function calcCoordinates(shelf)
	{
		// поворот объекта
		shelf.cos = Math.cos(-shelf.angle*Math.PI/180);
		shelf.sin = Math.sin(-shelf.angle*Math.PI/180);
		// правый верхний угол
		var x = shelf.shelf_width / 2;
		var y = shelf.shelf_height / 2;
		// относительно сцены
		shelf.x1 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
		shelf.y1 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
		// правый нижний угол
		y = -y;
		// относительно сцены
		shelf.x2 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
		shelf.y2 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
		// левый нижний угол
		x = -x;
		// относительно сцены
		shelf.x3 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
		shelf.y3 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
		// левый верхний угол
		y = -y;
		// относительно сцены
		shelf.x4 = shelf.x_coord + x * shelf.cos - y * shelf.sin;
		shelf.y4 = shelf.y_coord + x * shelf.sin + y * shelf.cos;
	}
	function roundShelf(shelf)
	{
		shelf.x_coord=Math.round(shelf.x_coord);
		shelf.y_coord=Math.round(shelf.y_coord);
		shelf.shelf_width=Math.round(shelf.shelf_width);
		shelf.shelf_height=Math.round(shelf.shelf_height);
		shelf.shelf_length=Math.round(shelf.shelf_length);
		selectShelf(shelf);
		calcCoordinates(shelf);
		drawEditCanvas();
		drawPreviewCanvas();
	}
</script>
<%--прорисовка элементов--%>
<script type="text/javascript">
	function drawEditCanvas() {
		window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "BLACK";
		window.edit_context.strokeRect(
				-window.kx / window.km,
				window.edit_canvas.height + window.ky / window.km,
				window.rack.width / window.km,
				- window.rack.height / window.km);
		for (var i = 0; i < window.rackShelfList.length; i++) {
			drawShelf( window.rackShelfList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
		}
	}
	function drawPreviewCanvas()
	{
		window.preview_context.clearRect(0, 0, window.preview_canvas.width, window.preview_canvas.height);
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLACK";
		window.preview_context.strokeRect(0, window.preview_canvas.height,
				window.rack.width / window.preview_m,
				-window.rack.height / window.preview_m);
		for (var i = 0; i < window.rackShelfList.length; i++) {
			drawShelf(window.rackShelfList[i], window.preview_canvas, window.preview_context, 0, 0, window.preview_m);
		}
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLUE";
		window.preview_context.strokeRect(window.kx / window.preview_m,
				window.preview_canvas.height - window.ky / window.preview_m,
				window.edit_canvas.width * window.km / window.preview_m,
				- window.edit_canvas.height * window.km / window.preview_m);
	}
	function drawShelf(shelf, canvas, context, kx, ky, m) {
		context.lineWidth = 1;
		if (window.shelf==shelf)
		{
			context.strokeStyle = "BLUE";
		}
		else
		{
			context.strokeStyle = "BLACK";
		}
		context.beginPath();
		var x1 = (shelf.x1 - kx) / m;
		var y1 = canvas.height - (shelf.y1 - ky) / m;
		var x2 = (shelf.x2 - kx) / m;
		var y2 = canvas.height - (shelf.y2 - ky) / m;
		var x3 = (shelf.x3 - kx) / m;
		var y3 = canvas.height - (shelf.y3 - ky) / m;
		var x4 = (shelf.x4 - kx) / m;
		var y4 = canvas.height - (shelf.y4 - ky) / m;
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.lineTo(x3, y3);
		context.lineTo(x4, y4);
		context.lineTo(x1, y1);
		context.stroke();
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackSave()
	{
		postJson('<%=RackSave.URL%>', {rack:window.rack, rackShelfList:window.rackShelfList}, function (data) {
			setCookie('<%=RackConst.CODE_RACK%>', data.code_rack);
		});
	}
	function fRackReload()
	{
		window.location.reload();
	}
	function fRackShelfAdd()
	{
		window.shelfAdd=true;
	}
	function fCopy()
	{
		if (window.shelf!=null)
		{
			window.copyObject=window.shelf;
			$('#butPaste').removeClass('disabled');
		}
	}
	function fCut()
	{
		if (window.shelf!=null)
		{
			for (var i = 0; i < window.rackShelfList.length; i++) {
				if (window.rackShelfList[i] == window.shelf) {
					window.rackShelfList.splice(i, 1);
					i = window.rackShelfList.length;
				}
			}
			window.copyObject = window.shelf;
			$('#butPaste').removeClass('disabled');
			window.shelf = null;
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}
	function fPaste()
	{
		if (window.copyObject != null) {
			window.shelf = clone(window.copyObject);
			window.shelf.code_rack = '';
			window.shelf.x_coord = window.copyObject.x_coord + window.copyObject.shelf_width;
			window.shelf.y_coord;

			calcCoordinates(window.shelf);
			if (shelfBeyondRack(window.shelf))
			{
				window.shelf.x_coord = window.copyObject.x_coord;
				window.shelf.y_coord = window.copyObject.y_coord + window.copyObject.shelf_height;
				calcCoordinates(window.shelf);
				if (shelfBeyondRack(window.shelf)){
					window.shelf.x_coord = window.copyObject.x_coord;
					window.shelf.y_coord = window.copyObject.y_coord;
					calcCoordinates(window.shelf);
				}
			}

			window.rackShelfList.push(window.shelf);
			window.copyObject=window.shelf;
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

</script>
<%--обработка событий редактора--%>
<script type="text/javascript">

	function editCanvasMouseListener()
	{
		// TODO
	window.edit_canvas.onmousedown = function (e) {
		var evnt = ie_event(e);
		var sx = window.kx + evnt.offsetX * window.km;
		var sy = window.ky + (window.edit_canvas.height - evnt.offsetY) * window.km;

		if (window.shelfAdd==true)
		{
			window.shelfAdd=false;
			window.shelf=<%=new RackShelf(null, null, 0, 0, 1,1, 1, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>;
			window.shelf.code_rack=window.rack.code_rack;
			window.shelf.x_coord=sx;
			window.shelf.y_coord=sy;
			rackShelfList.push(window.shelf);
			selectShelf(window.shelf);
			calcCoordinates(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			x = evnt.clientX;
			y = evnt.clientY;
			window.editMove=7;
		}
		else
		{
			window.shelf = null;
			var d1 = null;
			var d2 = null;
			var d3 = null;
			var d4 = null;
			for (var i = window.rackShelfList.length-1; window.shelf == null && i >=0; i--) {
				d1 = distance(window.rackShelfList[i].x1,window.rackShelfList[i].y1,
						window.rackShelfList[i].x2,window.rackShelfList[i].y2,
						sx,sy);
				d2 = distance(window.rackShelfList[i].x2,window.rackShelfList[i].y2,
						window.rackShelfList[i].x3,window.rackShelfList[i].y3,
						sx,sy);
				d3 = distance(window.rackShelfList[i].x3,window.rackShelfList[i].y3,
						window.rackShelfList[i].x4,window.rackShelfList[i].y4,
						sx,sy);
				d4 = distance(window.rackShelfList[i].x4,window.rackShelfList[i].y4,
						window.rackShelfList[i].x1,window.rackShelfList[i].y1,
						sx,sy);
				if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
					window.shelf = window.rackShelfList[i];
				}
			}
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			if (window.shelf != null) {
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove = 1;
				if (window.shelf.shelf_width >= 7 * window.km && window.shelf.shelf_height >= 7 * window.km) {
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
			if (window.shelf!=null)
			{
				roundShelf(window.shelf);
			}
		window.editMove = 0;
	}

		window.edit_canvas.onmouseout = function(e) {
			if (window.shelf!=null)
			{
				roundShelf(window.shelf);
			}
		window.editMove = 0;
	}

		window.edit_canvas.onmousemove = function (e) {
		if (window.editMove != 0 && window.shelf != null) {
			var evnt = ie_event(e);
			var dx = (evnt.clientX - x) * window.km;
			var dy = -(evnt.clientY - y) * window.km;
			if (dx != 0 || dy != 0) {
				// перемещение
				if (window.editMove == 1) {
					var oldx = window.shelf.x_coord;
					var oldy = window.shelf.y_coord;
					// перемещение
					window.shelf.x_coord = window.shelf.x_coord + dx;
					calcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf))
					{
						window.shelf.x_coord = oldx;
					}
					window.shelf.y_coord = window.shelf.y_coord + dy;
					calcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf))
					{
						window.shelf.y_coord = oldy;
						calcCoordinates(window.shelf);
					}
					document.getElementById('shelfX').value = window.shelf.x_coord;
					document.getElementById('shelfY').value = window.shelf.y_coord;
				} else {
					var oldX = window.shelf.x_coord;
					var oldY = window.shelf.y_coord;
					var oldWidth=window.shelf.shelf_width;
					var oldHeight = window.shelf.shelf_height;
					var dxy=dx*window.shelf.cos + dy*window.shelf.sin;
					if (editMove & 2) {
						// восток
						window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.cos / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.sin / 2;
						window.shelf.shelf_width = window.shelf.shelf_width+ dxy;
					} else if (editMove & 8) {
						// запад
						window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.cos / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.sin / 2;
						window.shelf.shelf_width = window.shelf.shelf_width- dxy;
					}
					if (window.shelf.shelf_width< 8 * km) {
						window.shelf.shelf_width = 8 * km;
						window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.cos / 2;
						window.shelf.y_coord = window.shelf.y_coord - dxy * window.shelf.sin / 2;
					}
					dxy=dy*window.shelf.cos - dx*window.shelf.sin;
					if (editMove & 4) {
						// север
						window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.sin / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.cos / 2;
						window.shelf.shelf_height = window.shelf.shelf_height - dxy;
					} else if (editMove & 16) {
						// юг
						window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.sin / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.cos / 2;
						window.shelf.shelf_height = window.shelf.shelf_height + dxy;
					}
					if (window.shelf.shelf_height < 8 * km) {
						window.shelf.shelf_height = 8 * km
						window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.sin / 2;
						window.shelf.y_coord = window.shelf.y_coord - dxy * window.shelf.cos / 2;
					}
					calcCoordinates(window.shelf);
					if (window.shelf.x1 < 0 || window.shelf.x2 < 0 || window.shelf.x3 < 0 || window.shelf.x4 < 0 ||
							window.shelf.x1 > window.rack.width || window.shelf.x2 > window.rack.width || window.shelf.x3 > window.rack.width || window.shelf.x4 > window.rack.width ||
							window.shelf.y1 < 0 || window.shelf.y2 < 0 || window.shelf.y3 < 0 || window.shelf.y4 < 0 ||
							window.shelf.y1 > window.rack.height || window.shelf.y2 > window.rack.height || window.shelf.y3 > window.rack.height || window.shelf.y4 > window.rack.height) {
						window.shelf.x_coord = oldX;
						window.shelf.y_coord = oldY;
						window.shelf.shelf_width = oldWidth;
						window.shelf.shelf_height = oldHeight;
						calcCoordinates(window.shelf);
					} else {
						document.getElementById('shelfX').value = window.shelf.x_coord;
						document.getElementById('shelfY').value = window.shelf.y_coord;
						document.getElementById('shelfWidth').value = window.shelf.shelf_width;
						document.getElementById('shelfHeight').value = window.shelf.shelf_height;
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
<%-- обработка событий окна навигации --%>
<script type="text/javascript">
	function previewCanvasMouseListener() {
		window.preview_canvas.onmousedown = function (e) {
			window.previewMove = true;
			var evnt = ie_event(e);
			x = evnt.clientX;
			y = evnt.clientY;
			window.kx = evnt.offsetX * window.preview_m - window.edit_canvas.width * window.km / 2;
//			window.ky = evnt.offsetY * window.preview_m - window.edit_canvas.height * window.km / 2;
			window.ky = window.rack.height - evnt.offsetY * window.preview_m - window.edit_canvas.height * window.km / 2;
			checkKxKy();
			drawPreviewCanvas();
		};

		preview_canvas.onmouseup = function (e) {
			if (window.previewMove) {
				window.previewMove = false;
				drawEditCanvas();
			}
		};

		preview_canvas.onmouseover = function (e) {
			if (window.previewMove) {
				window.previewMove = false;
				drawEditCanvas();
			}
		};

		preview_canvas.onmousemove = function (e) {
			if (window.previewMove) {
				var evnt = ie_event(e);
				window.kx = window.kx + (evnt.clientX - x) * window.preview_m;
//				window.ky = window.ky + (evnt.clientY - y) * window.preview_m;
				window.ky = window.ky - (evnt.clientY - y) * window.preview_m;
				checkKxKy();
				x = evnt.clientX;
				y = evnt.clientY;
				drawPreviewCanvas();
			}
		};
	}

	function kMadd() {
		window.km = window.km * 1.5;
		if (window.km > window.edit_m)
			window.km = window.edit_m;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	}

	function kMsub() {
		window.km = window.km / 1.5;
		if (window.km < 0.1)
			window.km = 0.1;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
	}

	function checkKxKy() {
		if (window.kx > window.rack.width - window.edit_canvas.width * window.km)
			window.kx = window.rack.width - window.edit_canvas.width * window.km;
		if (window.kx < 0)
			window.kx = 0;
		if (window.ky > window.rack.height - window.edit_canvas.height * window.km)
			window.ky = window.rack.height - window.edit_canvas.height * window.km;
		if (window.ky < 0)
			window.ky = 0;
	}
</script>
<%-- обработка событий панели стеллажа --%>
<script type="text/javascript">
	function changeRackName(rackName) {
		window.rack.name_rack = rackName.value;
	}
	function changeRackWidth(rackWidth) {
		var currentWidth = Number(rackWidth.value);
		var maxWidth = 0;
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (maxWidth < window.rackShelfList[i].x1) {
				maxWidth = window.rackShelfList[i].x1;
			}
			if (maxWidth < window.rackShelfList[i].x2) {
				maxWidth = window.rackShelfList[i].x2;
			}
			if (maxWidth < window.rackShelfList[i].x3) {
				maxWidth = window.rackShelfList[i].x3;
			}
			if (maxWidth < window.rackShelfList[i].x4) {
				maxWidth = window.rackShelfList[i].x4;
			}
		}
		if (currentWidth > maxWidth) {
			window.rack.width = currentWidth;
			window.edit_m = Math.max(window.rack.width / edit_canvas.width, window.rack.height / edit_canvas.height)
			window.preview_m = Math.max(window.rack.width / preview_canvas.width, window.rack.height / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentWidth > 0) {
				rackWidth.value = maxWidth;
				window.rack.width = maxWidth;
				window.edit_m = Math.max(window.rack.width / edit_canvas.width, window.rack.height / edit_canvas.height)
				window.preview_m = Math.max(window.rack.width / preview_canvas.width, window.rack.height / preview_canvas.height);
				window.km = window.edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				rackWidth.value = window.rack.width;
		}
	}
	function changeRackHeight(rackHeight) {
		var currentHeight = Number(rackHeight.value);
		var maxHeight = 0;
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (maxHeight < window.rackShelfList[i].y1) {
				maxHeight = window.rackShelfList[i].y1;
			}
			if (maxHeight < window.rackShelfList[i].y2) {
				maxHeight = window.rackShelfList[i].y2;
			}
			if (maxHeight < window.rackShelfList[i].y3) {
				maxHeight = window.rackShelfList[i].y3;
			}
			if (maxHeight < window.rackShelfList[i].y4) {
				maxHeight = window.rackShelfList[i].y4;
			}
		}
		if (currentHeight > maxHeight) {
			window.rack.height = currentHeight;
			window.edit_m = Math.max(window.rack.width / edit_canvas.width, window.rack.height / edit_canvas.height)
			window.preview_m = Math.max(window.rack.width / preview_canvas.width, window.rack.height / preview_canvas.height);
			window.km = window.edit_m
			drawEditCanvas();
			drawPreviewCanvas();
		}
		else {
			if (currentHeight > 0) {
				rackHeight.value = maxHeight;
				window.rack.height = maxHeight;
				window.edit_m = Math.max(window.rack.width / edit_canvas.width, window.rack.height / edit_canvas.height)
				window.preview_m = Math.max(window.rack.width / preview_canvas.width, window.rack.height / preview_canvas.height);
				window.km = window.edit_m
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else
				rackHeight.value = window.rack.height;
		}
	}
	function changeRackLength(rackLength) {
		var currentLength = Number(rackLength.value);
		var maxLength = 0;
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (maxLength < window.rackShelfList[i].shelf_length) {
				maxLength = window.rackShelfList[i].shelf_length;
			}
		}
		if (currentLength > maxLength) {
			window.rack.length = currentLength;
		}
		else {
			if (currentLength > 0) {
				rackLength.value = maxLength;
				window.rack.length = maxLength;
			}
			else
				rackLength.value = window.rack.length;
		}
	}
	function changeRackAngle(rackAngle) {
		window.rack.angle = rackAngle.value;
	}
	function changeRackLoadSide(rackLoadSide) {
		window.rack.load_side = rackLoadSide.value;
	}
</script>
<%-- обработка событий панели полки --%>
<script type="text/javascript">
	function changeShelfX(shelfX) {
		if (window.shelf != null) {
			var x = Number(shelfX.value);
			if (x != null && !isNaN(x) && x != Infinity) {
				var oldx = window.shelf.x_coord;
				window.shelf.x_coord = x;
				calcCoordinates(window.shelf);
				// не выходит за области сектора
				if ((window.shelf.x_coord < oldx
						&& (window.shelf.x1 < 0
							|| window.shelf.x2 < 0
							|| window.shelf.x3 < 0
							|| window.shelf.x4 < 0))
					|| (window.shelf.x_coord > oldx
						&& (window.shelf.x1 > window.rack.width
							|| window.shelf.x2 > window.rack.width
							|| window.shelf.x3 > window.rack.width
							|| window.shelf.x4 > window.rack.width))) {
					window.shelf.x_coord = oldx;
					calcCoordinates(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfX.value = window.shelf.x_coord;
		}
	}
	function changeShelfY(shelfY) {
		if (window.shelf != null) {
			var y = Number(shelfY.value);
			if (y != null && !isNaN(y) && y != Infinity) {
				var oldY = window.shelf.y_coord;
				window.shelf.y_coord = y;
				calcCoordinates(window.shelf);
				// не выходит за области сектора
				if ((window.shelf.y_coord < oldY
						&& (window.shelf.y1 < 0
						|| window.shelf.y2 < 0
						|| window.shelf.y3 < 0
						|| window.shelf.y4 < 0))
						|| (window.shelf.y_coord > oldY
						&& (window.shelf.y1 > window.rack.height
						|| window.shelf.y2 > window.rack.height
						|| window.shelf.y3 > window.rack.height
						|| window.shelf.y4 > window.rack.height))) {
					window.shelf.y_coord = oldY;
					calcCoordinates(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfY.value = window.shelf.y_coord;
		}
	}
	function changeShelfAngle(shelfAngle) {
		if (window.shelf != null) {
			var angle = Number(shelfAngle.value);
			if (angle >= 0 && angle <= 90) {
				var oldAngle = window.shelf.angle;
				if (angle == 90) {
					angle = 0;
					var temp = window.shelf.shelf_width;
					window.shelf.shelf_width = window.shelf.shelf_height;
					window.shelf.shelf_height = temp;
				}
				window.shelf.angle = angle;
				calcCoordinates(window.shelf);
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.angle = oldAngle;
					calcCoordinates(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfAngle.value = window.shelf.angle;
		}
	}
	function changeShelfWidth(shelfWidth) {
		if (window.shelf != null) {
			var value = Number(shelfWidth.value);
			if (value != null && value>0 && value != Infinity) {
				var oldValue = window.shelf.shelf_width;
				window.shelf.shelf_width = value;
				calcCoordinates(window.shelf);
				// не выходит за области сектора
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.shelf_width = oldValue;
					calcCoordinates(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfWidth.value = window.shelf.shelf_width;
		}
	}
	function changeShelfHeight(shelfHeight) {
		if (window.shelf != null) {
			var value = Number(shelfHeight.value);
			if (value != null && value>0 && value != Infinity) {
				var oldValue = window.shelf.shelf_height;
				window.shelf.shelf_height = value;
				calcCoordinates(window.shelf);
				// не выходит за области сектора
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.shelf_height = oldValue;
					calcCoordinates(window.shelf);
				}
				else {
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			shelfHeight.value = window.shelf.shelf_height;
		}
	}
	function changeShelfLength(shelfLength) {
		if (window.shelf != null) {
			var value = Number(shelfLength.value);
			// не выходит за области сектора
			if (value > 0 && value <= window.rack.length) {
				window.shelf.shelf_length = value;
			}
			else {
				shelfLength.value = window.shelf.shelf_length;
			}
		}
	}
	function changeShelfType(shelfType) {
	}

	function shelfBeyondRack(shelf) {
		return shelf.x1 < 0
				|| shelf.x2 < 0
				|| shelf.x3 < 0
				|| shelf.x4 < 0
				|| shelf.y1 < 0
				|| shelf.y2 < 0
				|| shelf.y3 < 0
				|| shelf.y4 < 0
				|| shelf.x1 > rack.width
				|| shelf.x2 > rack.width
				|| shelf.x3 > rack.width
				|| shelf.x4 > rack.width
				|| shelf.y1 > rack.height
				|| shelf.y2 > rack.height
				|| shelf.y3 > rack.height
				|| shelf.y4 > rack.height;
	}
</script>




</body>
</html>