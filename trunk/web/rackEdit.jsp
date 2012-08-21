<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.data.Rack" %>
<%@ page import="planograma.servlet.rack.RackEdit" %>
<%@ page import="planograma.constant.data.RackConst" %>
<%@ page import="planograma.servlet.rack.RackSave" %>
<%@ page import="planograma.data.RackShelf" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_rack_edit=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_EDIT);
%>
<html>
<head>
	<title>Редактирование стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/planogram2D.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body onload="loadComplete();" style="overflow-x:hidden;">
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
								<td><a href="#" onclick="return aOnClick(this, fRackSave)" class="<%=access_rack_edit%>"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackShelfAdd)" class="<%=access_rack_edit%>"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
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
						<canvas id='edit_canvas' width="640" height="480">canvas not supported</canvas>
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table class="frame">
										<tr>
											<td id="preview_td" colspan="2">
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
											<td colspan="3">свойства стеллажа:</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td colspan="2">
												<input type="text" id="rackName"
													   onchange="changeRackName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">загрузка</td>
											<td>
												<select id="rackLoadSide" onchange="changeRackLoadSide(this)">
													<%
														for (final LoadSide loadSide:LoadSide.values())
														{
															out.write("<option value=\"");
															out.write(loadSide.name());
															out.write("\">");
															out.write(loadSide.getDesc());
															out.write("</option>");
														}
													%>
												</select>
											</td>
										</tr>
										<tr>
											<td>объем</td>
											<td>стеллажа</td>
											<td>полезный</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="rackWidth" size="4"
													   onchange="changeRackWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackRealWidth" size="4"
													   onchange="changeRackRealWidth(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="rackHeight" size="4"
													   onchange="changeRackHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackRealHeight" size="4"
													   onchange="changeRackRealHeight(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="rackLength" size="4"
													   onchange="changeRackLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
											<td><input type="text" id="rackRealLength" size="4"
													   onchange="changeRackRealLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right" title="Смещение полезного обема относительно нижнего левого дальнего угла стеллажа">смещение</td>
											<td align="right">слева</td>
											<td><input type="text" id="rackX_offset" size="4"
													   onchange="changeRackX_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td></td>
											<td align="right">снизу</td>
											<td><input type="text" id="rackY_offset" size="4"
													   onchange="changeRackY_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td></td>
											<td align="right">сзади</td>
											<td><input type="text" id="rackZ_offset" size="4"
													   onchange="changeRackZ_offset(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
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
												<input type="text" id="shelfAngle" onchange="changeShelfAngle(this)" onkeydown="numberFieldKeyDown(event, this)"/>
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
													<%
														for (final TypeShelf typeShelf:TypeShelf.values())
														{
															out.write("<option value=\"");
															out.write(typeShelf.name());
															out.write("\">");
															out.write(typeShelf.getDesc());
															out.write("</option>");
														}
													%>
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

	var canRackEdit='<%=access_rack_edit%>';

	function loadComplete() {
		var code_rack = getCookie('code_rack');
		postJson('<%=RackEdit.URL%>', {code_rack:code_rack}, function (data) {
			window.rack = data.rack;
			switch (window.rack.load_side)
			{
				case '<%=LoadSide.U%>':
					var temp=window.rack.width;
					window.rack.width=window.rack.length;
					window.rack.length=window.rack.height;
					window.rack.height=temp;

					temp=window.rack.real_width;
					window.rack.real_width=window.rack.real_length;
					window.rack.real_length=window.rack.real_height;
					window.rack.real_height=temp;

					// TODO x_offset
					break;
				case '<%=LoadSide.F%>':
					var temp=window.rack.width;
					window.rack.width=window.rack.length;
					window.rack.length=temp;

					temp=window.rack.real_width;
					window.rack.real_width=window.rack.real_length;
					window.rack.real_length=temp;

					// TODO x_offset
					break;
			}
			// определение максимальных габаритов
			window.rack.min_x=Math.min(0, window.rack.x_offset);
			window.rack.max_x=Math.max(window.rack.width, Math.max(0,window.rack.x_offset)+window.rack.real_width);
			window.rack.min_y=Math.min(0, window.rack.y_offset);
			window.rack.max_y=Math.max(window.rack.height, Math.max(0,window.rack.y_offset)+window.rack.real_height);

			window.rackShelfList = data.rackShelfList;
			loadComplete2();
		});
	}
	function loadComplete2()
	{
		window.edit_canvas = document.getElementById("edit_canvas");
		window.edit_context = edit_canvas.getContext("2d");
		var edit_td = $('#edit_td');
		window.edit_canvas.width = edit_td.width() - 6;
		window.edit_canvas.height = edit_td.height() - 6;
		window.edit_m = Math.max(window.rack.max_x / edit_canvas.width, window.rack.max_y / edit_canvas.height)

		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		window.preview_canvas.width = preview_td.width();// - 4;
		window.preview_canvas.height = preview_td.height();// - 4;
		window.preview_m = Math.max(window.rack.max_x / preview_canvas.width,  window.rack.max_y / preview_canvas.height);

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
			rackShelfCalcCoordinates(window.rackShelfList[i]);
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
		document.getElementById('rackRealWidth').value =window.rack.real_width;
		document.getElementById('rackHeight').value =window.rack.height;
		document.getElementById('rackRealHeight').value =window.rack.real_height;
		document.getElementById('rackLength').value =window.rack.length;
		document.getElementById('rackRealLength').value =window.rack.real_length;
		document.getElementById('rackX_offset').value =window.rack.x_offset;
		document.getElementById('rackY_offset').value =window.rack.y_offset;
		document.getElementById('rackZ_offset').value =window.rack.z_offset;
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
			if (canRackEdit!='disabled')
			{
				$('#butCopy').removeClass('disabled');
				$('#butCut').removeClass('disabled');
			}
		} else {
			document.getElementById('shelfX').value = '';
			document.getElementById('shelfY').value = '';
			document.getElementById('shelfAngle').value = '';
			document.getElementById('shelfWidth').value = '';
			document.getElementById('shelfHeight').value = '';
			document.getElementById('shelfLength').value = '';
			document.getElementById('shelfType').value = '';
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

	function roundShelf(shelf)
	{
		shelf.x_coord=Math.round(shelf.x_coord);
		shelf.y_coord=Math.round(shelf.y_coord);
		shelf.shelf_width=Math.round(shelf.shelf_width);
		shelf.shelf_height=Math.round(shelf.shelf_height);
		shelf.shelf_length=Math.round(shelf.shelf_length);
		selectShelf(shelf);
		rackShelfCalcCoordinates(shelf);
		drawEditCanvas();
		drawPreviewCanvas();
	}
</script>
<%--прорисовка элементов--%>
<script type="text/javascript">
	// TODO
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
		switch (shelf.type_shelf)
		{
				<%
			 for (final TypeShelf typeShelf:TypeShelf.values())
			 {
				 out.print("case '");
				 out.print(typeShelf.name());
				 out.print("': context.fillStyle = '");
				 out.print(typeShelf.getColor());
				 out.println("'; break;");
			 }
			 %>
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
		context.closePath();
		context.stroke();
		context.fill();
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackSave()
	{
		switch (window.rack.load_side)
		{
			case '<%=LoadSide.U%>':
				var temp=window.rack.width;
				window.rack.width=window.rack.height;
				window.rack.height=window.rack.length;
				window.rack.length=temp;
				break;
			case '<%=LoadSide.F%>':
				var temp=window.rack.width;
				window.rack.width=window.rack.length;
				window.rack.length=temp;
				break;
		}
		postJson('<%=RackSave.URL%>', {rack:window.rack, rackShelfList:window.rackShelfList}, function (data) {
			setCookie('<%=RackConst.CODE_RACK%>', data.code_rack);
			loadComplete();
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
			// копия в право
			window.shelf.x_coord = window.copyObject.x_coord + window.copyObject.shelf_width;
			rackShelfCalcCoordinates(window.shelf);
			if (shelfBeyondRack(window.shelf))
			{
				// в право нельзя копия в низ
				window.shelf.x_coord = window.copyObject.x_coord;
				window.shelf.y_coord = window.copyObject.y_coord - window.copyObject.shelf_height;
				rackShelfCalcCoordinates(window.shelf);
				if (shelfBeyondRack(window.shelf)){
					// в низ нельзя копия в лево
					window.shelf.x_coord = window.copyObject.x_coord - window.copyObject.shelf_width;
					window.shelf.y_coord = window.copyObject.y_coord;
					rackShelfCalcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf)){
						// в лево нельзя копия в верх
						window.shelf.x_coord = window.copyObject.x_coord;
						window.shelf.y_coord = window.copyObject.y_coord + window.copyObject.shelf_height;
						rackShelfCalcCoordinates(window.shelf);
						if (shelfBeyondRack(window.shelf)){
							// в верх нельзя копия на месте
							window.shelf.x_coord = window.copyObject.x_coord;
							window.shelf.y_coord = window.copyObject.y_coord;
							rackShelfCalcCoordinates(window.shelf);
						}
					}
				}
			}

			window.rackShelfList.push(window.shelf);
			window.copyObject=window.shelf;
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}

	function fDel()
	{
		if (window.shelf!=null)
		{
			for (var i = 0; i < window.rackShelfList.length; i++) {
				if (window.rackShelfList[i] == window.shelf) {
					window.rackShelfList.splice(i, 1);
					i = window.rackShelfList.length;
				}
			}
			window.shelf = null;
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
			if (sx>0 && sy>0 && sx<window.rack.width && sy<window.rack.height){
			window.shelfAdd=false;
			window.shelf=<%=new RackShelf(null, null, 0, 0, 1,1, 1, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>;
			window.shelf.shelf_length=window.rack.length;
			window.shelf.code_rack=window.rack.code_rack;
			window.shelf.x_coord=sx;
			window.shelf.y_coord=sy;
			rackShelfList.push(window.shelf);
			selectShelf(window.shelf);
			rackShelfCalcCoordinates(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			x = evnt.clientX;
			y = evnt.clientY;
			window.editMove=7;
			}
		}
		else
		{
			window.shelf = null;
			var d1 = null;
			var d2 = null;
			var d3 = null;
			var d4 = null;
			var pClick=new Point2D(sx, sy);
			for (var i = window.rackShelfList.length-1; window.shelf == null && i >=0; i--) {
				var p1=new Point2D(window.rackShelfList[i].x1,window.rackShelfList[i].y1);
				var p2=new Point2D(window.rackShelfList[i].x2,window.rackShelfList[i].y2);
				var p3=new Point2D(window.rackShelfList[i].x3,window.rackShelfList[i].y3);
				var p4=new Point2D(window.rackShelfList[i].x4,window.rackShelfList[i].y4);
				d1 = new Segment2D(p1,p2).distance(pClick);
				d2 = new Segment2D(p2,p3).distance(pClick);
				d3 = new Segment2D(p3,p4).distance(pClick);
				d4 = new Segment2D(p4,p1).distance(pClick);
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
					rackShelfCalcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf))
					{
						window.shelf.x_coord = oldx;
					}
					window.shelf.y_coord = window.shelf.y_coord + dy;
					rackShelfCalcCoordinates(window.shelf);
					if (shelfBeyondRack(window.shelf))
					{
						window.shelf.y_coord = oldy;
						rackShelfCalcCoordinates(window.shelf);
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
					rackShelfCalcCoordinates(window.shelf);
					if (window.shelf.x1 < 0 || window.shelf.x2 < 0 || window.shelf.x3 < 0 || window.shelf.x4 < 0 ||
							window.shelf.x1 > window.rack.width || window.shelf.x2 > window.rack.width || window.shelf.x3 > window.rack.width || window.shelf.x4 > window.rack.width ||
							window.shelf.y1 < 0 || window.shelf.y2 < 0 || window.shelf.y3 < 0 || window.shelf.y4 < 0 ||
							window.shelf.y1 > window.rack.height || window.shelf.y2 > window.rack.height || window.shelf.y3 > window.rack.height || window.shelf.y4 > window.rack.height) {
						window.shelf.x_coord = oldX;
						window.shelf.y_coord = oldY;
						window.shelf.shelf_width = oldWidth;
						window.shelf.shelf_height = oldHeight;
						rackShelfCalcCoordinates(window.shelf);
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
			window.ky = (window.preview_canvas.height-evnt.offsetY) * window.preview_m - window.edit_canvas.height * window.km / 2;
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
		// TODO  RackRealWidth
		// TODO  min_x max_x
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
		// TODO  RackRealHeight
		// TODO  min_y max_y
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
		// TODO  RackRealLength
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
	function changeRackRealWidth(realWidth){
		//TODO
		// TODO  min_x max_x
	}

	function changeRackRealHeight(realHeight) {
		// TODO
		// TODO  min_y max_y
	}

	function changeRackRealLength(realLength) {
		//TODO
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
				rackShelfCalcCoordinates(window.shelf);
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
					rackShelfCalcCoordinates(window.shelf);
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
				rackShelfCalcCoordinates(window.shelf);
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
					rackShelfCalcCoordinates(window.shelf);
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
			if (angle >= 0 && angle <= 360) {
				var oldAngle = window.shelf.angle;
				window.shelf.angle = angle;
				rackShelfCalcCoordinates(window.shelf);
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.angle = oldAngle;
					rackShelfCalcCoordinates(window.shelf);
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
				rackShelfCalcCoordinates(window.shelf);
				// не выходит за области сектора
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.shelf_width = oldValue;
					rackShelfCalcCoordinates(window.shelf);
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
				rackShelfCalcCoordinates(window.shelf);
				// не выходит за области сектора
				if (shelfBeyondRack(window.shelf)) {
					window.shelf.shelf_height = oldValue;
					rackShelfCalcCoordinates(window.shelf);
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
<%-- обработка событий клавиатуры --%>
<script type="text/javascript">
	$(document).bind('keydown', function(e){
		var obj = $(e.target);
		if (!obj.is('input') && !obj.is('textarea'))
		{
			switch (e.keyCode)
			{
				case 67:
					if (e.ctrlKey)
						fCopy();
					break;
				case 88:
					if (e.ctrlKey)
						fCut();
					break;
				case 86:
					if (e.ctrlKey)
						fPaste();
					break;
				case 46:
					fDel();
					break;
			}
		}
	});
</script>
</body>
</html>