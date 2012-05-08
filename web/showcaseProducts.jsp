<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.wares.WaresEdit" %>
<%@ page import="planograma.data.*" %>
<%@ page import="planograma.data.wrapper.WaresWrapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Товары стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body onload="loadComplete();">
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
					<td><i>Товары стеллажа</i></td>
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
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Добавить товар")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Копировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Вырезать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Вставить")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
					<td id="edit_td" width="100%">
						<canvas id='edit_canvas' width="320" height="200">canvas not supported</canvas>
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table class="frame">
										<tr>
											<td id="preview_td" colspan="2">
												<canvas id='preview_canvas'>canvas not supported</canvas>
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
									<table width="100%">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства стеллажа:</td>
										</tr>
										<tr>
											<td align="right">название&nbsp;</td>
											<td id="rackName"></td>
										</tr>
										<tr>
											<td align="right">ширина&nbsp;</td>
											<td id="rackWidth"></td>
										</tr>
										<tr>
											<td align="right">высота&nbsp;</td>
											<td id="rackHeight"></td>
										</tr>
										<tr>
											<td align="right">глубина&nbsp;</td>
											<td id="rackLength"></td>
										</tr>
										<tr>
											<td align="right">загрузка&nbsp;</td>
											<td id="rackLoadSide"></td>
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
									<table id="shelfPanel" style="display: none;">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства полки:</td>
										</tr>
										<tr>
											<td align="right">x&nbsp;</td>
											<td id="sheldX"></td>
										</tr>
										<tr>
											<td align="right">y&nbsp;</td>
											<td id="sheldY"></td>
										</tr>
										<tr>
											<td align="right">ширина&nbsp;</td>
											<td id="sheldWidth"></td>
										</tr>
										<tr>
											<td align="right">высота&nbsp;</td>
											<td id="sheldHeight"></td>
										</tr>
										<tr>
											<td align="right">длина&nbsp;</td>
											<td id="sheldLength"></td>
										</tr>
									</table>
									<table id="waresPanel" style="display: none;">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства товара:</td>
										</tr>
										<tr>
											<td align="right">код&nbsp;</td>
											<td id="waresCode"></td>
										</tr>
										<tr>
											<td align="right">название&nbsp;</td>
											<td id="waresName"></td>
										</tr>
										<tr>
											<td align="right" title="Единица измерения">е.и.&nbsp;</td>
											<td id="waresUnit"></td>
										</tr>
										<tr>
											<td align="right">штрихкод&nbsp;</td>
											<td id="waresBarcode"></td>
										</tr>
										<tr>
											<td align="right">x</td>
											<td><input type="text" id="waresX" onchange="changeWaresX(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text" id="waresY" onchange="changeWaresY(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
										</tr>
										<tr>
											<td align="right">ширина&nbsp;</td>
											<td id="waresWidth"></td>
										</tr>
										<tr>
											<td align="right">высота&nbsp;</td>
											<td id="waresHeight"></td>
										</tr>
										<tr>
											<td align="right">длина&nbsp;</td>
											<td id="waresLength"></td>
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
				<%--корзина--%>
				<tr>
					<td colspan="3" style="height:250px; border-top: 1px solid black;">
						<div id="basketPanel" style="height:250px; overflow-y: auto;">
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<script type="text/javascript">
	function loadComplete() {
		var code_rack = getCookie('code_rack');
		//postJson('<%=WaresEdit.URL%>', {code_rack:code_rack}, function (data) {
		//	window.rack = data.rack;
		//		switch (window.rack.load_side)
		//	{
		//		case '<%=LoadSide.U%>':
		//			var temp=window.rack.width;
		//			window.rack.width=window.rack.length;
		//			window.rack.length=window.rack.height;
		//			window.rack.height=temp;
		//			break;
		//		case '<%=LoadSide.F%>':
		//			var temp=window.rack.width;
		//			window.rack.width=window.rack.length;
		//			window.rack.length=temp;
		//			break;
		//	}
		//	window.rackShelfList = data.rackShelfList;
		//	window.rackWaresList = data.rackWaresList;
		//	loadComplete2();
		//});
		window.rack = <%=new Rack(null, null, "testRack", null, 80, 120, 200, null, 0, 0, 0, LoadSide.F, null, true, true, TypeRack.R, null, null, null, null, null, null).toJsonObject()%>;
		window.rackShelfList =
				[
					<%=new RackShelf(null, null, 60, 1, 2, 120, 80, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>,
					<%=new RackShelf(null, null, 60, 51, 2, 120, 80, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>,
					<%=new RackShelf(null, null, 60, 101, 2, 120, 80, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>,
					<%=new RackShelf(null, null, 60, 151, 2, 120, 80, 0, TypeShelf.DZ, null, null, null, null).toJsonObject()%>
				];
		window.rackWaresList =
				[
						<%=new RackWares(null, 12851, 19, null, 1, 1, 25, 160, 5, 50,30, 1, null, null, null, null, 46).toJsonObject()%>,
					<%=new RackWares(null, 12851, 19, null, 1, 1, 25, 130, 5, 30,50, 1, null, null, null, null, 46).toJsonObject()%>
				];
		window.basket=[
				<%=new WaresWrapper(null, 12851, 19, 46, "testWares", "шт", 5, 50, 30, null).toJsonObject()%>
		];
		loadComplete2();
	}

	function loadComplete2()
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
		window.preview_canvas.width = preview_td.width();
		window.preview_canvas.height = preview_td.height();
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
		window.selectRackWares=[];
		window.copyObject=null;

		for (var i = 0; i < window.rackShelfList.length; i++) {
			rackShelfCalcCoordinates(window.rackShelfList[i]);
		}
		drawEditCanvas();
		drawPreviewCanvas();
		basketToHtml(window.basket);

		setRack(window.rack);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}

	function setRack(rack)
	{
		$('#rackName').text(window.rack.name_rack);
		$('#rackWidth').text(window.rack.width);
		$('#rackHeight').text(window.rack.height);
		$('#rackLength').text(window.rack.length);
		//TODO
		$('#rackLoadSide').text(window.rack.load_side);
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
		for (var i = 0; i < window.rackWaresList.length; i++) {
			drawWares(window.rackWaresList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
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
		for (var i = 0; i < window.rackWaresList.length; i++) {
			drawWares(window.rackWaresList[i], window.preview_canvas, window.preview_context, 0, 0, window.preview_m);
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

	function drawWares(rackWares, canvas, context, kx, ky, m) {
		var img = new Image();
		img.onload = function(){
			var mx=(rackWares.position_x-kx)/m;
			var my=canvas.height-(rackWares.position_y-ky)/m;
			context.translate(mx, my);
			// TODO angle
//			context.rotate(45*Math.PI/180);
			var x1= -rackWares.wares_width/m/2;
			var y1= -rackWares.wares_height/m/2;
			var w1= rackWares.wares_width/m;
			var h1= rackWares.wares_height/m;
			context.drawImage(img, x1, y1, w1, h1);
			context.lineWidth = 1;
			if ($.inArray(rackWares, window.selectRackWares)>=0) {
				context.strokeStyle = "BLUE";
			}
			else{
				context.strokeStyle = "BLACK";
			}
			context.strokeRect(x1, y1, w1, h1);
//			context.rotate(-45*Math.PI/180);
			context.translate(-mx, -my);
		}
		img.src = 'image/'+rackWares.code_image;
	}

	function basketToHtml(basket)
	{
		var basketPanel=$('#basketPanel');
		basketPanel.empty();
		for (var i=0; i<basket.length; i++)
		{
			var w=basket[i];
			var wHtml='<input type="image" src="image/'+ w.code_image+'" width="'+ w.width/window.km+'" height="'+ w.height/window.km+'" class="basket" onclick="waresSelect(this)">';
			basketPanel.append(wHtml);
		}
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	// TODO
</script>

<%--обработка событий редактора--%>
<script type="text/javascript">
	function editCanvasMouseListener() {
		// TODO
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
		basketToHtml(window.basket);
	}

	function kMsub() {
		window.km = window.km / 1.5;
		if (window.km < 0.1)
			window.km = 0.1;
		checkKxKy();
		drawEditCanvas();
		drawPreviewCanvas();
		basketToHtml(window.basket);
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

<%-- обработка событий панели товара --%>
<script type="text/javascript">
	// TODO
</script>

<%-- обработка событий корзины --%>
<script type="text/javascript">
	 function waresSelect(image)
	 {
		 var image=$(image);
		 if (image.hasClass('basketSelect'))
		 {
			 image.removeClass('basketSelect');
			 image.addClass('basket');
		 }
		 else
		 {
			 $('input.basketSelect').removeClass('basketSelect').addClass('basket');
			 image.removeClass('basket');
			 image.addClass('basketSelect');
		 }
	 }
</script>
</body>
</html>