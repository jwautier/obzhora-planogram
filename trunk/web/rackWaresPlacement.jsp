<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.wares.WaresEdit" %>
<%@ page import="planograma.data.*" %>
<%@ page import="planograma.servlet.wares.WaresGroupTree" %>
<%@ page import="planograma.servlet.wares.RackWaresPlacementSave" %>
<%@ page import="planograma.constant.SecurityConst" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	final String access_rack_wares_placement=JspUtils.actionAccess(session, SecurityConst.ACCESS_RACK_WARES_PLACEMENT);
%>
<html>
<head>
	<title>Товары стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/jquery.hotkeys-0.7.9.js"></script>
	<script type="text/javascript" src="js/planogram.js"></script>
	<script type="text/javascript" src="js/planogram2D.js"></script>
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
								<td><a href="#" onclick="return aOnClick(this, fRackWaresPlacementSave)" class="<%=access_rack_wares_placement%>"><%=JspUtils.toMenuTitle("Сохранить")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fRackWaresPlacementReload)"><%=JspUtils.toMenuTitle("Перезагрузить")%></a></td>
							</tr>
							<tr><td></td></tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this, fAddWares)" class="<%=access_rack_wares_placement%>"><%=JspUtils.toMenuTitle("Добавить товар")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCopy" onclick="return aOnClick(this, fCopy)" class="disabled"><%=JspUtils.toMenuTitle("Копировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butCut" onclick="return aOnClick(this, fCut)" class="disabled"><%=JspUtils.toMenuTitle("Вырезать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="butPaste" onclick="return aOnClick(this, fPas)" class="disabled"><%=JspUtils.toMenuTitle("Вставить")%></a></td>
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
											<td align="right">стеллаж&nbsp;</td>
											<td id="rackName"></td>
										</tr>
										<tr>
											<td align="right">габариты&nbsp;</td>
											<td id="rackSize"></td>
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
									<table id="shelfPanel" width="100%" style="display: none;">
										<colgroup>
											<col width="70"/>
										</colgroup>
										<tr>
											<td colspan="2">свойства полки:</td>
										</tr>
										<tr>
											<td align="right">x&nbsp;</td>
											<td id="shelfX"></td>
										</tr>
										<tr>
											<td align="right">y&nbsp;</td>
											<td id="shelfY"></td>
										</tr>
										<tr>
											<td align="right">угол&nbsp;</td>
											<td id="shelfAngle"></td>
										</tr>
										<tr>
											<td align="right">ширина&nbsp;</td>
											<td id="shelfWidth"></td>
										</tr>
										<tr>
											<td align="right">высота&nbsp;</td>
											<td id="shelfHeight"></td>
										</tr>
										<tr>
											<td align="right">глубина&nbsp;</td>
											<td id="shelfLength"></td>
										</tr>
										<tr>
											<td align="right">тип&nbsp;</td>
											<td id="shelfType"></td>
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
											<td align="right">глубина&nbsp;</td>
											<td id="waresLength"></td>
										</tr>
										<tr>
											<td align="right" title="количество товара в глубину стеллажа">кол-во</td>
											<td><input type="text" id="waresCountInLength" onchange="changeWaresCountInLength(this)" onkeydown="numberFieldKeyDown(event, this)"/></td>
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

<jsp:include page="choiceWares.jsp"/>
<%--<%@include file="choiceWares.jsp"%>--%>

<script type="text/javascript">
/**
 * товары в корзине
 * @type {Object}
 */
window.basket = {};
/**
 * выделеные товары
 * @type {Array}
 */
window.selectRackWaresList = [];
/**
 * товар выбраный в корзине
 * @type {*}
 */
window.selectBasket = null;
/**
 * скопированые товары
 * @type {Array}
 */
window.copyObjectList = [];
/**
 * растояние по ширине между товарами при вставке группами
 * @type {Number}
 */
window.d_wares_width = 1;
/**
 * растояние по высоте между товарами при вставке группами
 * @type {Number}
 */
window.d_wares_height = 1;

var canRackWaresPlacement='<%=access_rack_wares_placement%>';

	function loadComplete() {
		var code_rack = getCookie('code_rack');
		postJson('<%=WaresEdit.URL%>', {code_rack:code_rack}, function (data) {
			window.rack = data.rack;
				switch (window.rack.load_side)
			{
				case '<%=LoadSide.U%>':
					var temp=window.rack.width;
					window.rack.width=window.rack.length;
					window.rack.length=window.rack.height;
					window.rack.height=temp;
					break;
				case '<%=LoadSide.F%>':
					var temp=window.rack.width;
					window.rack.width=window.rack.length;
					window.rack.length=temp;
					break;
			}
			window.rackShelfList = data.rackShelfList;
			window.rackWaresList = data.rackWaresList;
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

		window.editMove = 0;
		window.flagPaste=0;

		x = 0;
		y = 0;
		window.shelf = null;

		for (var i = 0; i < window.rackShelfList.length; i++) {
			rackShelfCalcCoordinates(window.rackShelfList[i]);
		}

		for (var i = 0; i < window.rackWaresList.length; i++) {
			rackWaresCalcCoordinates(window.rackWaresList[i]);
		}
		rackWaresLoadImage();
//		drawEditCanvas();
//		drawPreviewCanvas();

		basketToHtml(window.basket);

		setRack(window.rack);
		editCanvasMouseListener();
		previewCanvasMouseListener();
	}

	var images=[];

	function getImage(code_image)
	{
		if(images[code_image] === undefined)
		{
//			var loadComplite=0;
			images[code_image]= new Image();
//			images[code_image].onload  =function (){
//				loadComplite=1;
//			}
			images[code_image].src = 'image/'+code_image;
			// TODO загрузить, а лиш потом вернуть
//			images[code_image].load('image/'+code_image);
//			while (loadComplite==0);
			return images[code_image];
		}else
		{
			return images[code_image];
		}
	}

	function rackWaresLoadImage()
	{
		var countImg=window.rackWaresList.length;
		for (var i = 0; i < window.rackWaresList.length; i++) {
			var code_image=window.rackWaresList[i].code_image;
			if(code_image>0)
			{
				images[code_image] = new Image();
				images[code_image].onload = function(){
					countImg--;
					if (countImg==0)
					{
						drawEditCanvas();
						drawPreviewCanvas();
					}
				}
				images[code_image].src = 'image/'+code_image;
			}
		}
		drawEditCanvas();
		drawPreviewCanvas();
	}

	function setRack(rack)
	{
		$('#rackName').text(window.rack.name_rack);
		$('#rackSize').text(window.rack.width + 'x' + window.rack.height + 'x' + window.rack.length);
		switch (window.rack.load_side) {
			<%
			for (final LoadSide loadSide:LoadSide.values())
			{
			%>
			case '<%=loadSide.name()%>':
				$('#rackLoadSide').text('<%=loadSide.getDesc()%>');
				break;
			<%
			}
			%>
		}
	}

	function fSelectRackWares() {
		if (window.selectRackWaresList.length > 0) {
			if (canRackWaresPlacement!='disabled')
			{
				$('#butCopy').removeClass('disabled');
				$('#butCut').removeClass('disabled');
			}
			if (window.selectRackWaresList.length == 1) {
				var rackWares = window.selectRackWaresList[0];
				$('#waresCode').text(rackWares.code_wares);
				$('#waresName').text(rackWares.name_wares);
				$('#waresUnit').text(rackWares.abr_unit);
				$('#waresBarcode').text(rackWares.bar_code);
				$('#waresX').val(rackWares.position_x);
				$('#waresY').val(rackWares.position_y);
				$('#waresWidth').text(rackWares.wares_width);
				$('#waresHeight').text(rackWares.wares_height);
				$('#waresLength').text(rackWares.wares_length);
				$('#waresCountInLength').val(rackWares.count_length_on_shelf);
				$('#waresPanel').show();
			} else {
				$('#waresPanel').hide();
			}
		}
		else {
			$('#waresPanel').hide();
			$('#butCopy').addClass('disabled');
			$('#butCut').addClass('disabled');
		}
	}

	function selectShelf(shelf) {
		if (shelf != null) {
			$('#shelfX').text(shelf.x_coord);
			$('#shelfY').text(shelf.y_coord);
			$('#shelfAngle').text(shelf.angle);
			$('#shelfWidth').text(shelf.shelf_width);
			$('#shelfHeight').text(shelf.shelf_height);
			$('#shelfLength').text( shelf.shelf_length);
			switch (shelf.type_shelf)
			{
			<%
				for (final TypeShelf typeShelf:TypeShelf.values())
				{
					out.print("case '");
					out.print(typeShelf.name());
					out.print("': $('#shelfType').text('");
					out.print(typeShelf.getDesc());
					out.println("');");
				}
			%>
			}
			$('#shelfPanel').show();
		} else {
			$('#shelfPanel').hide();
		}
	}

	/**
	 * определить координаты каждого угла объекта
	 * @param rackWares полка стеллажа
	 */
	function rackWaresCalcCoordinates(rackWares)
	{
		// поворот объекта
		// TODO angle
//		rackWares.cos = Math.cos(-rackWares.angle*Math.PI/180);
//		rackWares.sin = Math.sin(-rackWares.angle*Math.PI/180);
		rackWares.cos = 1;
		rackWares.sin = 0;
		// правый верхний угол
		var x = rackWares.wares_width / 2;
		var y = rackWares.wares_height / 2;
		// относительно сцены
		rackWares.p1 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
									rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
		// правый нижний угол
		y = -y;
		// относительно сцены
		rackWares.p2 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
									rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
		// левый нижний угол
		x = -x;
		// относительно сцены
		rackWares.p3 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
									rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
		// левый верхний угол
		y = -y;
		// относительно сцены
		rackWares.p4 = new Point2D(rackWares.position_x + x * rackWares.cos - y * rackWares.sin,
									rackWares.position_y + x * rackWares.sin + y * rackWares.cos);
	}

	function ie_event(e) {
		if (e === undefined) {
			return window.event;
		}
		return e;
	}
	function roundRackWares(rackWares)
	{
		rackWares.position_x=Math.round(rackWares.position_x);
		rackWares.position_y=Math.round(rackWares.position_y);
		rackWaresCalcCoordinates(rackWares);
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
		if (window.flagPaste==2)
		{
			for (var i = 0; i < window.copyObjectList.length; i++) {
				drawWares(window.copyObjectList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
			}
		}
		else if (window.m_select==1)
		{
			// select
			window.edit_context.lineWidth = 1;
			window.edit_context.strokeStyle = "rgba(128, 128, 128, 1)";
			window.edit_context.strokeRect(
					0.5+(window.m_x_begin - window.kx)/ window.km,
					0.5+window.edit_canvas.height - (window.m_y_begin - window.ky) / window.km,
					(window.m_x_end - window.m_x_begin) / window.km,
					(window.m_y_begin - window.m_y_end) / window.km);
			if (window.selectBasket!=null)
			{
				var m_x_begin2 = Math.round(Math.min(window.m_x_end, window.m_x_begin));
				var m_x_end2 = Math.round(Math.max(window.m_x_end, window.m_x_begin));
				var m_y_begin2 = Math.round(Math.min(window.m_y_end, window.m_y_begin));
				var m_y_end2 = Math.round(Math.max(window.m_y_end, window.m_y_begin));
				var m_x_d=window.selectBasket.width / window.km;
				var m_y_d=-window.selectBasket.height / window.km;
				var dx=window.selectBasket.width+window.d_wares_width;
				if (dx%2!=0)
					dx++;
				var dy=window.selectBasket.height+window.d_wares_height;
				if (dy%2!=0)
					dy++;
				for (var i=m_x_begin2; i<m_x_end2-dx; i=i+dx)
				{
					for (var j=m_y_begin2; j<m_y_end2-dy; j=j+dy)
					{
						if (window.selectBasket.code_image>0)
						{
							window.edit_context.drawImage(getImage(window.selectBasket.code_image),
									0.5+(i - window.kx)/ window.km,
									0.5+window.edit_canvas.height - (j - window.ky) / window.km,
									m_x_d ,
									m_y_d );
						}
						window.edit_context.strokeRect(
								0.5+(i - window.kx)/ window.km,
								0.5+window.edit_canvas.height - (j - window.ky) / window.km,
								m_x_d ,
								m_y_d );
					}
				}
			}
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
		var x1 = (shelf.p1.x - kx) / m;
		var y1 = canvas.height - (shelf.p1.y - ky) / m;
		var x2 = (shelf.p2.x - kx) / m;
		var y2 = canvas.height - (shelf.p2.y - ky) / m;
		var x3 = (shelf.p3.x - kx) / m;
		var y3 = canvas.height - (shelf.p3.y - ky) / m;
		var x4 = (shelf.p4.x - kx) / m;
		var y4 = canvas.height - (shelf.p4.y - ky) / m;
		context.moveTo(x1, y1);
		context.lineTo(x2, y2);
		context.lineTo(x3, y3);
		context.lineTo(x4, y4);
		context.closePath();
		context.stroke();
		context.fill();
	}

	function drawWares(rackWares, canvas, context, kx, ky, m) {
		var movx = (rackWares.position_x - kx) / m;
		var movy = canvas.height - (rackWares.position_y - ky) / m;
		context.translate(movx, movy);
		// TODO angle
//			context.rotate(45*Math.PI/180);
		var x1 = -rackWares.wares_width / m / 2;
		var y1 = -rackWares.wares_height / m / 2;
		var w1 = rackWares.wares_width / m;
		var h1 = rackWares.wares_height / m;
		if (rackWares.code_image > 0) {
			context.drawImage(getImage(rackWares.code_image), x1, y1, w1, h1);
		}
		context.lineWidth = 1;
		if ($.inArray(rackWares, window.selectRackWaresList) >= 0) {
			context.strokeStyle = "BLUE";
		}
		else {
			context.strokeStyle = "BLACK";
		}
		context.strokeRect(x1, y1, w1, h1);
//			context.rotate(-45*Math.PI/180);
		context.translate(-movx, -movy);
	}

	function basketToHtml(basket)
	{
		var basketPanel=$('#basketPanel');
		basketPanel.empty();
		for (var i in basket)
		{
			var w=basket[i];
			var wHtml='<input id="'+i+'" type="image"';
			if (w.code_image>0)
			{
				wHtml=wHtml+' src="image/'+ w.code_image+'"';
			}
			wHtml=wHtml+' width="'+ w.width/window.km+'"';
			wHtml=wHtml+' height="'+ w.height/window.km+'"';
			wHtml=wHtml+' title="'+ w.code_wares+'\n'+ w.name_wares+'\n'+ w.abr_unit+'"';
			wHtml=wHtml+' class="basket"';
			wHtml=wHtml+' onclick="basketWaresSelect(this)">';
			basketPanel.append(wHtml);
		}
	}
</script>
<%-- обработка событий меню --%>
<script type="text/javascript">
	function fRackWaresPlacementSave() {
		// проверить выход товара за стеллаж
		var rackWaresOutside = [];
		var sr = new SelectRectangle2D(new Point2D(0, 0), new Point2D(window.rack.width, window.rack.height));
		for (var i in window.rackWaresList) {
			var rackWares = window.rackWaresList[i];
			if (!rackWares.p1.insideSelectRectangle(sr) ||
					!rackWares.p2.insideSelectRectangle(sr) ||
					!rackWares.p3.insideSelectRectangle(sr) ||
					!rackWares.p4.insideSelectRectangle(sr)) {
				rackWaresOutside.push(rackWares);
			}
		}
		if (rackWaresOutside.length == 0 || confirm("Все товары выходящие за пределы стеллажа будут удалены.")) {
			for (var i in rackWaresOutside) {
				window.rackWaresList.splice(window.rackWaresList.indexOf(rackWaresOutside[i]), 1);
			}
			// проверить пересечение товаров между собой и стеллажами
			var rackWaresIntersects = [];
			var map = new Array(window.rack.width*window.rack.height);
			for (var i in window.rackShelfList) {
				var rackShelf = window.rackShelfList[i];
				drawQuadrangle(map, window.rack.width, window.rack.height,
						rackShelf.p1,
						rackShelf.p2,
						rackShelf.p3,
						rackShelf.p4)
			}
			for (var i in window.rackWaresList) {
				var rackWares = window.rackWaresList[i];
				if (drawQuadrangle(map, window.rack.width, window.rack.height,
						rackWares.p1,
						rackWares.p2,
						rackWares.p3,
						rackWares.p4)) {
					rackWaresIntersects.push(rackWares);
				}
			}

//			drawTestMap(window.edit_canvas, window.edit_context, window.rack.width, window.rack.height, map);
//			return;

			if (rackWaresIntersects.length > 0) {
				alert("Сохранение отменено.\n Существуют товары которые пересекаются.\n Переместите их или удалите.");
				window.selectRackWaresList = rackWaresIntersects;
				fSelectRackWares();
				drawEditCanvas();
				drawPreviewCanvas();
			}
			else {
				postJson('<%=RackWaresPlacementSave.URL%>', {code_rack:window.rack.code_rack, rackWaresList:window.rackWaresList}, function (data) {
					loadComplete();
				});
			}
		}
		else {
			alert("Сохранение отменено");
			window.selectRackWaresList = rackWaresOutside;
			fSelectRackWares();
			drawEditCanvas();
			drawPreviewCanvas();
		}
	}
	function fRackWaresPlacementReload()
	{
		window.location.reload();
	}
	function fAddWares()
	{
		postJson('<%=WaresGroupTree.URL%>', null, function(data){
			$('#tree').append(treeToHtml(data.waresGroupTree));
			$('#choiceWares').show();
		})
	}
	function fCopy()
	{
		window.copyObjectList=[];
		for (var i in window.selectRackWaresList)
		{
			window.copyObjectList[i]=clone(window.selectRackWaresList[i]);
		}
		if (window.copyObjectList.length>0)
		{
			$('#butPaste').removeClass('disabled');
		}
	}

	function fCut()
	{
		window.copyObjectList=[];
		for (var i in window.selectRackWaresList)
		{
			window.copyObjectList[i]=clone(window.selectRackWaresList[i]);
			var selectRackWares=window.selectRackWaresList[i];
			for (var j in window.rackWaresList)
			{
				if (selectRackWares==window.rackWaresList[j])
				{
					window.rackWaresList.splice(j,1);
				}
			}
		}
		window.selectRackWaresList=[];
		fSelectRackWares();
		drawEditCanvas();
		drawPreviewCanvas();
		if (window.copyObjectList.length>0)
		{
			$('#butPaste').removeClass('disabled');
		}
	}
	function fPas()
	{
		if (window.copyObjectList.length>0)
		{
			window.flagPaste=1;
		}
	}
	function fDel() {
		for (var i in window.selectRackWaresList) {
			var selectRackWares = window.selectRackWaresList[i];
			for (var j in window.rackWaresList) {
				if (selectRackWares == window.rackWaresList[j]) {
					window.rackWaresList.splice(j, 1);
				}
			}
		}
		window.selectRackWaresList = [];
		fSelectRackWares();
		drawEditCanvas();
		drawPreviewCanvas();
	}
</script>

<%--обработка событий редактора--%>
<script type="text/javascript">
	function editCanvasMouseListener() {

		window.edit_canvas.onmousedown = function (e) {
			var evnt = ie_event(e);
			var sx = window.kx + evnt.offsetX * window.km;
			var sy = window.ky + (window.edit_canvas.height - evnt.offsetY) * window.km;

			var oldSelectRackWaresList = window.selectRackWaresList;

			var findRackWares = null;
			window.shelf = null;
			window.selectRackWaresList = [];
			if (window.flagPaste == 1) {
				// найти центр
				var centerX = 0;
				var centerY = 0;
				for (var i in window.copyObjectList) {
					centerX += window.copyObjectList[i].position_x;
					centerY += window.copyObjectList[i].position_y;
				}
				centerX = sx - centerX / window.copyObjectList.length;
				centerY = sy - centerY / window.copyObjectList.length;
				for (var i in window.copyObjectList) {
					window.copyObjectList[i].position_x += centerX;
					window.copyObjectList[i].position_y += centerY;
				}
				window.flagPaste = 2;
				x = evnt.clientX;
				y = evnt.clientY;
			}
			else {
				// поиск товара под указателем
				for (var i = window.rackWaresList.length - 1; findRackWares == null && i >= 0; i--) {
					if (pointInsideRackWares(window.rackWaresList[i], sx, sy)) {
						findRackWares = window.rackWaresList[i];
					}
				}
				if (findRackWares != null) {
					// перемещение товара
					if ($.inArray(findRackWares, oldSelectRackWaresList) < 0) {
						window.selectRackWaresList.push(findRackWares);
					}
					else {
						window.selectRackWaresList = oldSelectRackWaresList;
					}

					window.editMove = 1;
					x = evnt.clientX;
					y = evnt.clientY;
				}
				else {
					// поиск полки под курсором
					var pClick=new Point2D(sx, sy);
					for (var i = window.rackShelfList.length - 1; window.shelf == null && i >= 0; i--) {
						d1 = new Segment2D(window.rackShelfList[i].p1,window.rackShelfList[i].p2).distance(pClick);
						d2 = new Segment2D(window.rackShelfList[i].p2,window.rackShelfList[i].p3).distance(pClick);
						d3 = new Segment2D(window.rackShelfList[i].p3,window.rackShelfList[i].p4).distance(pClick);
						d4 = new Segment2D(window.rackShelfList[i].p4,window.rackShelfList[i].p1).distance(pClick);
						if ((d1 >= 0 && d2 >= 0 && d3 >= 0 && d4 >= 0) || (d1 <= 0 && d2 <= 0 && d3 <= 0 && d4 <= 0)) {
							window.shelf = window.rackShelfList[i];
							$('#butCopy').addClass('disabled');
							$('#butCut').addClass('disabled');
						}
					}
					if (window.shelf == null) {
						// начало области выделения
						window.m_select = 1;
						window.m_x_begin = sx;
						window.m_y_begin = sy;
						window.m_x_end = sx;
						window.m_y_end = sy;
					}
				}
			}
			fSelectRackWares();
			selectShelf(window.shelf);
			drawEditCanvas();
			drawPreviewCanvas();
		}

		window.edit_canvas.onmouseup = function(e) {
			if (window.flagPaste==2)
			{
				window.selectRackWaresList=[];
				for (var i in window.copyObjectList)
				{
					var rackWares=clone(window.copyObjectList[i]);
					rackWares.code_wares_on_rack=null;
					roundRackWares(rackWares);
					window.selectRackWaresList.push(rackWares);
					window.rackWaresList.push(rackWares);
				}
				window.flagPaste=0;
			}
			else if (window.editMove==1)
			{
				// установка товара
				for (var i in window.selectRackWaresList)
				{
					var rackWares=window.selectRackWaresList[i];
					// округление координат
					roundRackWares(rackWares);
				}
				window.editMove=0;
			}
			else if (window.m_select==1)
			{
				// области выделения
				if (window.selectBasket!=null)
				{
					// добавление товара
					var m_x_begin2 = Math.round(Math.min(window.m_x_end, window.m_x_begin));
					var m_x_end2 = Math.round(Math.max(window.m_x_end, window.m_x_begin));
					var m_y_begin2 = Math.round(Math.min(window.m_y_end, window.m_y_begin));
					var m_y_end2 = Math.round(Math.max(window.m_y_end, window.m_y_begin));
					var countInsert=0;
					window.selectRackWaresList=[];
					var dx=window.selectBasket.width+window.d_wares_width;
					if (dx%2!=0)
						dx++;
					var dy=window.selectBasket.height+window.d_wares_height;
					if (dy%2!=0)
						dy++;
					for (var i=m_x_begin2; i<m_x_end2-dx; i=i+dx)
					{
						for (var j=m_y_begin2; j<m_y_end2-dy; j=j+dy)
						{
							var rackWares = {
								code_rack:window.rack.code_rack,
								code_wares:window.selectBasket.code_wares,
								code_unit:window.selectBasket.code_unit,
								type_wares_on_rack:'<%=TypeRackWares.NA.name()%>',
								order_number_on_rack:1,
								position_x:i+Math.ceil(window.selectBasket.width/2),
								position_y:j+Math.ceil(window.selectBasket.height/2),
								wares_length:window.selectBasket.length,
								wares_width:window.selectBasket.width,
								wares_height:window.selectBasket.height,
								count_length_on_shelf:Math.floor(window.rack.length/window.selectBasket.length),
								code_image: window.selectBasket.code_image,
								name_wares: window.selectBasket.name_wares,
								abr_unit: window.selectBasket.abr_unit,
								bar_code: window.selectBasket.bar_code
							};
							roundRackWares(rackWares);
							// TODO order_number_on_rack
							window.rackWaresList.push(rackWares);
							window.selectRackWaresList.push(rackWares);
							countInsert++;
						}
					}
					if (countInsert>0)
					{
						basketWaresDelete(window.selectBasket.code_wares);
						window.selectBasket=null;
					}
				}
				else
				{
					window.selectRackWaresList=[];
					// выделение нескольких товаров
					// поиск товара в области
					for (var i= window.rackWaresList.length-1; i>=0 ;i--)
					{
						var rackWares=window.rackWaresList[i];
						if (window.m_x_begin>window.m_x_end)
						{
							var t=window.m_x_begin;
							window.m_x_begin=window.m_x_end;
							window.m_x_end=t;
						}
						if (window.m_y_begin>window.m_y_end)
						{
							var t=window.m_y_begin;
							window.m_y_begin=window.m_y_end;
							window.m_y_end=t;
						}
						// если хоть одна из вершин лежит в области выделения
						var sr=new SelectRectangle2D(new Point2D(window.m_x_begin, window.m_y_begin), new Point2D(window.m_x_end,window.m_y_end));
						if (rackWares.p1.insideSelectRectangle(sr)||
							rackWares.p2.insideSelectRectangle(sr)||
							rackWares.p3.insideSelectRectangle(sr)||
							rackWares.p4.insideSelectRectangle(sr))
						{
							window.selectRackWaresList.push(rackWares);
						}
						else
						{
							// если одна из граней пересекается с гранью области выделения
							if (
								new Segment2D(rackWares.p1, rackWares.p2).intersectsSelectRectangle(sr) ||
								new Segment2D(rackWares.p2, rackWares.p3).intersectsSelectRectangle(sr) ||
								new Segment2D(rackWares.p3, rackWares.p4).intersectsSelectRectangle(sr) ||
								new Segment2D(rackWares.p4, rackWares.p1).intersectsSelectRectangle(sr)
							)
							{
								window.selectRackWaresList.push(rackWares);
							}
						}
					}
				}
				window.m_select=0;
			}
			fSelectRackWares();
			drawEditCanvas();
			drawPreviewCanvas();
		}
		window.edit_canvas.onmouseout = window.edit_canvas.onmouseup;

		window.edit_canvas.onmousemove = function (e) {
			var evnt = ie_event(e);
			if (window.flagPaste==2)
			{
				// перемещение товара
				var dx = (evnt.clientX - x) * window.km;
				var dy = -(evnt.clientY - y) * window.km;
				if (dx != 0 || dy != 0)
				{
					for(var i in window.copyObjectList)
					{
						var rackWares=window.copyObjectList[i];
						rackWares.position_x=rackWares.position_x+dx;
						rackWares.position_y=rackWares.position_y+dy;
						rackWaresCalcCoordinates(rackWares);
					}
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
				}
			}
			else if (window.editMove==1)
			{
				// перемещение товара
				var dx = (evnt.clientX - x) * window.km;
				var dy = -(evnt.clientY - y) * window.km;
				if (dx != 0 || dy != 0)
				{
					for (var i in window.selectRackWaresList)
					{
						var rackWares=window.selectRackWaresList[i];
						rackWares.position_x=rackWares.position_x+dx;
						rackWares.position_y=rackWares.position_y+dy;
						rackWaresCalcCoordinates(rackWares);
					}
					if (window.selectRackWaresList.length==1)
					{
						$('#waresX').val(window.selectRackWaresList[0].position_x);
						$('#waresY').val(window.selectRackWaresList[0].position_y);
					}
					drawEditCanvas();
					drawPreviewCanvas();
					x = evnt.clientX;
					y = evnt.clientY;
				}
			}
			else if (window.m_select==1)
			{
				// изменение области выделения
				window.m_x_end=window.kx + evnt.offsetX * window.km;
				window.m_y_end = window.ky + (window.edit_canvas.height - evnt.offsetY) * window.km;
				drawEditCanvas();
			}
		}
	}

	function pointInsideRackWares(rackWares, x, y)
	{
		var f=Math.abs(x-rackWares.position_x)<rackWares.wares_width/2;
		f=f && Math.abs(y-rackWares.position_y)<rackWares.wares_height/2;
		return f;
	}
</script>

<%-- обработка событий окна навигации --%>
<script type="text/javascript">
	function previewCanvasMouseListener() {

		window.previewMove = false;

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
	function changeWaresX(waresX)
	{
		if (window.selectRackWaresList.length==1) {
			var x = Number(waresX.value);
			if (x != null && !isNaN(x) && x != Infinity) {
//				var oldx = window.selectRackWaresList[0].position_x;
				window.selectRackWaresList[0].position_x = x;
				roundRackWares(window.selectRackWaresList[0]);
				//TODO
				// не выходит за области сектора
//				if ((window.shelf.x_coord < oldx
//						&& (window.shelf.x1 < 0
//						|| window.shelf.x2 < 0
//						|| window.shelf.x3 < 0
//						|| window.shelf.x4 < 0))
//						|| (window.shelf.x_coord > oldx
//						&& (window.shelf.x1 > window.rack.width
//						|| window.shelf.x2 > window.rack.width
//						|| window.shelf.x3 > window.rack.width
//						|| window.shelf.x4 > window.rack.width))) {
//					window.shelf.x_coord = oldx;
//					rackShelfCalcCoordinates(window.shelf);
//				}
//				else {
					drawEditCanvas();
					drawPreviewCanvas();
//				}
			}
			waresX.value =window.selectRackWaresList[0].position_x;
		}
	}
	function changeWaresY(waresY)
	{
		if (window.selectRackWaresList.length==1) {
			var y = Number(waresY.value);
			if (y != null && !isNaN(x) && y != Infinity) {
//				var oldY = window.selectRackWaresList[0].position_y;
				window.selectRackWaresList[0].position_y = y;
				roundRackWares(window.selectRackWaresList[0]);
				//TODO
				// не выходит за области сектора
//				if ((window.shelf.x_coord < oldY
//						&& (window.shelf.x1 < 0
//						|| window.shelf.x2 < 0
//						|| window.shelf.x3 < 0
//						|| window.shelf.x4 < 0))
//						|| (window.shelf.x_coord > oldY
//						&& (window.shelf.x1 > window.rack.width
//						|| window.shelf.x2 > window.rack.width
//						|| window.shelf.x3 > window.rack.width
//						|| window.shelf.x4 > window.rack.width))) {
//					window.shelf.position_y = oldY;
//					rackShelfCalcCoordinates(window.shelf);
//				}
//				else {
				drawEditCanvas();
				drawPreviewCanvas();
//				}
			}
			waresY.value =window.selectRackWaresList[0].position_y;
		}
	}
	function changeWaresCountInLength(waresCountInLength) {
		if (window.selectRackWaresList.length == 1) {
			var value = Number(waresCountInLength.value);
			var max = Math.floor(window.rack.length / window.selectRackWaresList[0].wares_length);
			if (value > 0 && value <= max) {
				window.selectRackWaresList[0].count_length_on_shelf = value;
			}
			waresCountInLength.value = window.selectRackWaresList[0].count_length_on_shelf;
		}
	}
</script>

<%-- обработка событий корзины --%>
<script type="text/javascript">
	 function basketWaresSelect(image)
	 {
		 var image=$(image);
		 if (image.hasClass('basketSelect'))
		 {
			 image.removeClass('basketSelect');
			 image.addClass('basket');
			 window.selectBasket=null;
		 }
		 else
		 {
			 $('input.basketSelect').removeClass('basketSelect').addClass('basket');
			 image.removeClass('basket');
			 image.addClass('basketSelect');
			 var code_wares=image.attr('id');
			 window.selectBasket=window.basket[code_wares];
		 }
	 }

	 function basketWaresDelete(code_wares)
	 {
		 delete window.basket[code_wares];
		 basketToHtml(window.basket);
	 }

</script>
<%-- обработка событий клавиатуры --%>
<script type="text/javascript">
    $(document).bind('keydown', {combi:'Ctrl+c', disableInInput: true}, fCopy);
    $(document).bind('keydown', {combi:'Ctrl+x', disableInInput: true}, fCut);
    $(document).bind('keydown', {combi:'Ctrl+v', disableInInput: true}, fPas);
    $(document).bind('keydown', {combi:'del', disableInInput: true}, fDel);
</script>
</body>
</html>