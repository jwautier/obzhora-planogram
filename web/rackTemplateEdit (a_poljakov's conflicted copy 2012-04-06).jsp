<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.data.RackTemplate" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateEdit" %>
<%@ page import="planograma.constant.data.RackTemplateConst" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateSave" %>
<%@ page import="planograma.data.RackShelfTemplate" %>
<%@ page import="planograma.data.LoadSide" %>
<%@ page import="planograma.data.TypeShelf" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Редактирование стеллажа стандартного типа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body onload="loadComplite();" style="overflow-x:hidden;">
<table class="frame">
	<tr>
		<td class="path">
			<!--TODO-->
			<table>
				<tr>
					<td><a href="menu.jsp">Меню</a></td>
					<td>&gt;</td>
					<td><a href="rackTemplateList.jsp">Типовые стеллажи</a></td>
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
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateSave)"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackTemplateShelfAdd)"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
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
												<input type="text" id="rackTemplateName"
													   onchange="changeRackTemplateName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="rackTemplateWidth"
													   onchange="changeRackTemplateWidth(this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="rackTemplateHeight"
													   onchange="changeRackTemplateHeight(this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="rackTemplateLength"
													   onchange="changeRackTemplateLength(this)"/></td>
										</tr>
										<tr>
											<td align="right">поворот</td>
											<td>
												<select id="rackTemplateAngle" onchange="changeRackTemplateAngle(this)">
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
												<select id="rackTemplateLoadSide" onchange="changeRackTemplateLoadSide(this)">
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
											<td><input type="text" id="shelfX" onchange="changeShelfX(this)"/></td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text" id="shelfY" onchange="changeShelfY(this)"/></td>
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
											<td><input type="text" id="shelfWidth" onchange="changeShelfWidth(this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="shelfHeight" onchange="changeShelfHeight(this)"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text" id="shelfLength" onchange="changeShelfLength(this)"/></td>
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
	function loadComplite()
	{
		var code_rack_template=getCookie('code_rack_template');
		if (code_rack_template!=null && code_rack_template.length>0)
		{
			postJson('<%=RackTemplateEdit.URL%>', {code_rack_template:code_rack_template}, function (data) {
				window.rackTemplate=data.rackTemplate;
				window.rackShelfTemplateList = data.rackShelfTemplateList;
				loadComplite2();
			});
		}
		else
		{
			window.rackTemplate=<%=new RackTemplate(null, null, null, 100, 100, 100, 0, LoadSide.N, null,null, null,null,null,null).toJsonObject()%>;
			window.rackShelfTemplateList = [<%=new RackShelfTemplate(null, null, 0,3, 5,100, 100, 0, null, null, null, null, null).toJsonObject()%>];
			loadComplite2();
		}
	}
	function loadComplite2()
	{
		window.edit_canvas = document.getElementById("edit_canvas");
		window.edit_context = edit_canvas.getContext("2d");
		var edit_td = $('#edit_td');
		window.edit_canvas.width = edit_td.width() - 6;
		window.edit_canvas.height = edit_td.height() - 6;
		window.edit_m = Math.max(window.rackTemplate.width / edit_canvas.width, window.rackTemplate.height / edit_canvas.height)

		window.preview_canvas = document.getElementById("preview_canvas");
		window.preview_context = preview_canvas.getContext("2d");
		var preview_td = $('#preview_td');
		window.preview_canvas.width = preview_td.width();// - 4;
		window.preview_canvas.height = preview_td.height();// - 4;
		window.preview_m = Math.max(window.rackTemplate.width / preview_canvas.width,  window.rackTemplate.height / preview_canvas.height);

		window.km = edit_m;
		window.kx = 0;
		window.ky = 0;
		//TODO

		window.shelfAdd = false;
		window.editMove = 0;
		window.previewMove = false;
		x = 0;
		y = 0;
		window.shelf = null;
		window.copyObject=null;

		// TODO DRAW
		drawEditCanvas();
		drawPreviewCanvas();

		setRackTemplate(window.rackTemplate);
		editCanvasMouseListener();
	}

	function setRackTemplate(rackTemplate)
	{
		document.getElementById('rackTemplateName').value = window.rackTemplate.name_rack_template;
		document.getElementById('rackTemplateWidth').value =window.rackTemplate.width;
		document.getElementById('rackTemplateHeight').value =window.rackTemplate.height;
		document.getElementById('rackTemplateLength').value =window.rackTemplate.length;
		document.getElementById('rackTemplateAngle').value =window.rackTemplate.angle;
		document.getElementById('rackTemplateLoadSide').value =window.rackTemplate.load_side;
	}

	function selectShelf(shelf) {
		if (shelf != null) {
			document.getElementById('shelfX').value = shelf.x_coord;
			document.getElementById('shelfY').value = shelf.y_coord;
			document.getElementById('shelfAngle').value = shelf.angle;
			document.getElementById('shelfWidth').value = shelf.width;
			document.getElementById('shelfHeight').value = shelf.length;
			document.getElementById('shelfLength').value = shelf.height;
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
		shelf.cos = Math.cos(shelf.angle*Math.PI/180);
		shelf.sin = Math.sin(shelf.angle*Math.PI/180);
		// правый верхний угол
		var x = shelf.length / 2;
		var y = shelf.width / 2;
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
</script>
<%--прорисовка элементов--%>
<script type="text/javascript">
	function drawEditCanvas() {
		window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
		window.edit_context.lineWidth = 1;
		window.edit_context.strokeStyle = "BLACK";
		window.edit_context.strokeRect(
				-window.kx / window.km,
				-window.ky / window.km,
				window.rackTemplate.width / window.km,
				window.rackTemplate.height / window.km);
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			drawShelf( window.rackShelfTemplateList[i], window.edit_context, window.kx, window.ky, window.km);
		}
	}
	function drawPreviewCanvas()
	{
		window.preview_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLACK";
		window.preview_context.strokeRect(0, 0,
				window.rackTemplate.width / window.preview_m,
				window.rackTemplate.height / window.preview_m);
		for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
			drawShelf(window.rackShelfTemplateList[i], window.preview_context, 0, 0, window.preview_m);
		}
		window.preview_context.lineWidth = 1;
		window.preview_context.strokeStyle = "BLUE";
		window.preview_context.strokeRect(window.kx / window.preview_m,
				window.ky / window.preview_m,
				window.edit_canvas.width * window.km / window.preview_m,
				window.edit_canvas.height * window.km / window.preview_m);
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackTemplateSave()
	{
		postJson('<%=RackTemplateSave.URL%>', {sector:window.rackTemplate, rackShelfTemplateList:window.rackShelfTemplateList}, function (data) {
			setCookie('<%=RackTemplateConst.CODE_RACK_TEMPLATE%>', data.code_rack_template);
		});
	}
	function fRackTemplateReload()
	{
		window.location.reload();
	}
	function fRackTemplateShelfAdd()
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
			for (var i = 0; i < window.rackShelfTemplateList.length; i++) {
				if (window.rackShelfTemplateList[i] == window.shelf) {
					window.rackShelfTemplateList.splice(i, 1);
					i = window.rackShelfTemplateList.length;
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
			window.shelf.x_coord = window.showcase.x_coord + 10 * km;
			window.shelf.y_coord = window.showcase.y_coord + 10 * km;
			window.rackShelfTemplateList.push(window.shelf);
			window.copyObject=window.shelf;
			selectShelf(window.shelf);
			calcCoordinates(window.shelf);
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
		var sy = window.ky + evnt.offsetY * window.km;

		if (window.shelfAdd==true)
		{
			window.shelfAdd=false;
			window.shelf=<%=new RackShelfTemplate(null, null, 0, 0, 1, 1, 1, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>;
			window.shelf.code_rack_template=window.rackTemplate.code_rack_template;
			window.shelf.x_coord=sx;
			window.shelf.y_coord=sy;
			rackShelfTemplateList.push(window.shelf);
			selectShelf(window.shelf);
			calcCoordinates(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			x = evnt.clientX;
			y = evnt.clientY;
			window.editMove=19;
		}
		else
		{
			window.shelf = null;
			var d1 = null;
			var d2 = null;
			var d3 = null;
			var d4 = null;
			for (var i = window.rackShelfTemplateList.length-1; window.shelf == null && i >=0; i--) {
				d1 = distance(window.rackShelfTemplateList[i].x1,window.rackShelfTemplateList[i].y1,
						window.rackShelfTemplateList[i].x2,window.rackShelfTemplateList[i].y2,
						sx,sy);
				d2 = distance(window.rackShelfTemplateList[i].x2,window.rackShelfTemplateList[i].y2,
						window.rackShelfTemplateList[i].x3,window.rackShelfTemplateList[i].y3,
						sx,sy);
				d3 = distance(window.rackShelfTemplateList[i].x3,window.rackShelfTemplateList[i].y3,
						window.rackShelfTemplateList[i].x4,window.rackShelfTemplateList[i].y4,
						sx,sy);
				d4 = distance(window.rackShelfTemplateList[i].x4,window.rackShelfTemplateList[i].y4,
						window.rackShelfTemplateList[i].x1,window.rackShelfTemplateList[i].y1,
						sx,sy);
				if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
					window.shelf = window.rackShelfTemplateList[i];
				}
			}
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
			if (window.shelf != null) {
				x = evnt.clientX;
				y = evnt.clientY;
				window.editMove = 1;
				if (window.shelf.length >= 7 * window.km && window.shelf.width >= 7 * window.km) {
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
		if (window.editMove != 0 && window.shelf != null) {
			var evnt = ie_event(e);
			var dx = (evnt.clientX - x) * window.km;
			var dy = (evnt.clientY - y) * window.km;
			if (dx != 0 || dy != 0) {
				// перемещение
				if (window.editMove == 1) {
					var oldx = window.shelf.x_coord;
					var oldy = window.shelf.y_coord;
					// перемещение
					window.shelf.x_coord = window.shelf.x_coord + dx;
					window.shelf.y_coord = window.shelf.y_coord + dy;
					calcCoordinates(window.shelf);
					if ((window.shelf.x_coord < oldx && (window.shelf.x1 < 0 || window.shelf.x2 < 0 || window.shelf.x3 < 0 || window.shelf.x4 < 0)) ||
							(window.shelf.x_coord > oldx && (window.shelf.x1 > window.rackTemplate.width || window.shelf.x2 > window.rackTemplate.width || window.shelf.x3 > window.rackTemplate.width || window.shelf.x4 > window.rackTemplate.width)) ||
							(window.shelf.y_coord < oldy && (window.shelf.y1 < 0 || window.shelf.y2 < 0 || window.shelf.y3 < 0 || window.shelf.y4 < 0)) ||
							(window.shelf.y_coord > oldy && (window.shelf.y1 > window.rackTemplate.height || window.shelf.y2 > window.rackTemplate.height || window.shelf.y3 > window.rackTemplate.height || window.shelf.y4 > window.rackTemplate.height))) {
						window.shelf.x_coord = oldx;
						window.shelf.y_coord = oldy;
						calcCoordinates(window.shelf);
					} else {
						document.getElementById('shelfX').value = window.shelf.x_coord;
						document.getElementById('shelfY').value = window.shelf.y_coord;
					}
				} else {
					var oldX = window.shelf.x_coord;
					var oldY = window.shelf.y_coord;
					var oldWidth=window.shelf.width;
					var oldHeight = window.shelf.height;
					var dxy=dx*window.shelf.cos + dy*window.shelf.sin;
					if (editMove & 2) {
						// восток
						window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.cos / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.sin / 2;
						window.shelf.width = window.shelf.width+ dxy;
					} else if (editMove & 8) {
						// запад
						window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.cos / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.sin / 2;
						window.shelf.width = window.shelf.width- dxy;
					}
					if (window.shelf.width< 7 * km) {
						window.shelf.width = 7 * km;
						window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.cos / 2;
						window.shelf.y_coord = window.shelf.y_coord - dxy * window.shelf.sin / 2;
					}
					dxy=dy*window.shelf.cos - dx*window.shelf.sin;
					if (editMove & 4) {
						// север
						window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.sin / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.cos / 2;
						window.shelf.height = window.shelf.height - dxy;
					} else if (editMove & 16) {
						// юг
						window.shelf.x_coord = window.shelf.x_coord - dxy * window.shelf.sin / 2;
						window.shelf.y_coord = window.shelf.y_coord + dxy * window.shelf.cos / 2;
						window.shelf.height = window.shelf.height + dxy;
					}
					if (window.shelf.height < 7 * km) {
						window.shelf.height = 7 * km
						window.shelf.x_coord = window.shelf.x_coord + dxy * window.shelf.sin / 2;
						window.shelf.y_coord = window.shelf.y_coord - dxy * window.shelf.cos / 2;
					}
					calcCoordinates(window.shelf);
					if (window.shelf.x1 < 0 || window.shelf.x2 < 0 || window.shelf.x3 < 0 || window.shelf.x4 < 0 ||
							window.shelf.x1 > window.rackTemplate.width || window.shelf.x2 > window.rackTemplate.width || window.shelf.x3 > window.rackTemplate.width || window.shelf.x4 > window.rackTemplate.width ||
							window.shelf.y1 < 0 || window.shelf.y2 < 0 || window.shelf.y3 < 0 || window.shelf.y4 < 0 ||
							window.shelf.y1 > window.rackTemplate.height || window.shelf.y2 > window.rackTemplate.height || window.shelf.y3 > window.rackTemplate.height || window.shelf.y4 > window.rackTemplate.height) {
						window.shelf.x_coord = oldX;
						window.shelf.y_coord = oldY;
						window.shelf.width = oldWidth;
						window.shelf.height = oldHeight;
						calcCoordinates(window.shelf);
					} else {
						document.getElementById('shelfX').value = window.shelf.x_coord;
						document.getElementById('shelfY').value = window.shelf.y_coord;
						document.getElementById('shelfWidth').value = window.shelf.width;
						document.getElementById('shelfHeight').value = window.shelf.height;
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


</body>
</html>