<%@ page import="planograma.servlet.wares.WaresGroupTree" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Выбор товара</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>

	<style type="text/css">
		.treeview { padding: 0; clear: both; font-family: monospace;  width: 100%; }
		/*.treeview * { font-size: 100.1%; }*/
		.treeview ul
		{
			/*overflow: hidden; */
			width: 100%; margin: 0; padding: 0 0 1.5em 0;
			list-style-type: none;
		}
		.treeview ul ul {
			/*overflow: visible; */
			width: auto; margin: 0; padding: 0 0 0 0.75em; }
			/* класс для ul после которых нет li в родительских ветках */
		.treeview ul.l {
			border-left: 1px solid;
			margin-left: -1px;
			border-color: white;
		}
		.treeview li.cl ul { display: none; }
		.treeview li { margin: 0; padding: 0; }
		.treeview li li { margin: 0 0 0 0.5em; border-left: 1px dotted;  padding: 0; }
		.treeview li div { position: relative;
			/*height: 1.5em; */
			min-height: 16px; //height: 1.3em; }
		.treeview li li div { border-bottom: 1px dotted; }
		.treeview li p
		{
			position: absolute; z-index: 1; top: 0.8em; //top: 0.65em; left: 1.75em;
			width: 100%; margin: 0;
			padding: 0;
		}
		.treeview a { padding: 0.1em 0.2em; white-space: nowrap; //height: 1px; text-decoration: none;}
		.treeview a.sc
		{
			color: black;
			position: absolute; top: 0.06em;
			margin-left: -1em;
			text-decoration: none;
		}

		/* colors */
		.treeview li p,
		.treeview .sc
		{ background: white; }
		.treeview ul li li,
		.treeview ul li li div
		{ border-color: #999999; }
		.treeview a
		{ color: #5555bb; }
		.treeview a:hover
		{
			color: white;
			background: #808080;
		}
	</style>

</head>
<body>

<table id="choiceWares">
	<tr>
		<td class="path">
			<table>
				<tr>
					<td><i>Выбор товара</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="choiceWaresCancel()">&#x2715;</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="24">
			<table>
				<tr>
					<td>
						<input type="text" name="searchText"/>
					</td>
					<td>
						<select name="searchBy">
							<option value="article">Артикул</option>
							<option value="name">Название</option>
							<option value="barcode">Штрих код</option>
						</select>
					</td>
					<td>
						<a href="#" title="Поиск"><img src="img/icon/iconSearch.png" alt="Поиск"/></a>
						<a href="#" title="Поиск по иерархии"><img src="img/icon/iconSearch2.png" alt="Поиск по иерархии"/></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="frame">
				<colgroup>
					<col width="35%"/>
					<col width="65%"/>
				</colgroup>
				<tr>
					<td>
						<div class="treeview" style="overflow-y: scroll; height: 100%">
							<ul id="tree">
							</ul>
						</div>
					</td>
					<td style="min-width: 150px;">
						<div class="frame" style="overflow-y: scroll; height: 100%">
							 <table id="list_wares" width="100%">

							 </table>
						</div>
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
					<td><a href="#" onclick="choiceWaresCancel()">Отмена</a></td>
					<td>&nbsp;</td>
					<td><a href="#" onclick="choiceWaresOk()">Ок</a></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<script type="text/javascript">

	postJson('<%=WaresGroupTree.URL%>', null, function(data){
		$('#tree').append(treeToHtml(data.waresGroupTree));
	})

	var list_wares= [
		{
			code_wares:1,
//			url_image:'img/moloko-romol-25-p-e-094l-vbd-paket.jpg',
			url_image:'image/42',
			atricl:1,
			name_wares:'wares1',
			bar_code:1,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:2,
//			url_image:'img/moloko-seljanske-32-t-f-09l-lustdorf.jpg',
			url_image:'image/43',
			atricl:2,
			name_wares:'wares2',
			bar_code:2,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
//			url_image:'img/moloko-zareche-32-p-e-05l-kupjansk.jpg',
			url_image:'image/44',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-chernigovskoe-beloe-firm-05l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-chernigovskoe-mcne-1l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-chernigovskoe-svetloe-firm-05l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-obolon-premium-j-b-05l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-obolon-svetloe-j-b-05l-eksport.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-rogan-monastyrskoe-firm-05l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-rogan-monastyrskoe-svetloe-1l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		},
		{
			code_wares:3,
			url_image:'img/pivo-staropramen-05l.jpg',
			atricl:3,
			name_wares:'wares3',
			bar_code:3,
			width:10,
			height:15,
			length:20
		}
	];

	function treeToHtml(tree) {
		var nodeHtml = ''
		for (var i = 0; i < tree.length; i++) {
			var node = tree[i];
			nodeHtml += '<li><div><p>';
			if (node.children.length > 0) {
				nodeHtml += '<a href="#" class="sc" onclick="return UnHide(this)">&#9660;</a>';
			}
			nodeHtml += '<a href="#" onclick="selectWaresGroup('+node.code_group_wares+')">' + node.name + '</a></p></div>'
			if (node.children.length > 0) {
				nodeHtml += '<ul';
				if(i==tree.length-1)
					nodeHtml += ' class="l"';
				nodeHtml += '>';
				nodeHtml += treeToHtml(node.children)
				nodeHtml += '</ul>';
			}
			nodeHtml += '</li>';
		}
		return nodeHtml;
	}

	function listWaresToHtml(list_wares)
	{
		var rowHtml='';
		for (var i=0; i<list_wares.length;i++)
		{
			var wares=list_wares[i];
			rowHtml+='<tr';
			if (i%2==0)
				rowHtml+=' bgcolor="#d3d3d3"';
			rowHtml+='>';
			rowHtml+='<td><input type="checkbox" value="'+wares.code_wares+'"></td>';
			rowHtml+='<td><img width="100" height="100" src="'+wares.url_image+'"></td>';
			rowHtml+='<td>&nbsp;</td>';
			rowHtml+='<td width="100%">';
			rowHtml+=wares.atricl+'<br>';
			rowHtml+=wares.name_wares+'<br>';
			rowHtml+=wares.bar_code+'<br>';
			rowHtml+=wares.width+'x'+wares.height+'x'+wares.length+'<br>';
			rowHtml+='</td>';
			rowHtml+='</tr>';
		}
		return rowHtml
	}

	function selectWaresGroup(code_group_wares)
	{
		//todo
		/*postJson('<%//TODO WaresList.URL%>', null, function(data){
			$('#list_wares').append(listWaresToHtml(data.waresList));
		})*/
		alert(code_group_wares);
	}


//	$('#list_wares').append(listWaresToHtml(list_wares));


</script>

<script type="text/javascript">
	function choiceWaresCancel()
	{
		alert("cancel");
	}
	function choiceWaresOk()
	{
		alert("ok")
	}
	function UnHide( eThis ){
		if( eThis.innerHTML.charCodeAt(0) == 9658 ){
			eThis.innerHTML = '&#9660;'
			eThis.parentNode.parentNode.parentNode.className = '';
		}else{
			eThis.innerHTML = '&#9658;'
			eThis.parentNode.parentNode.parentNode.className = 'cl';
		}
		return false;
	}
</script>

</body>
</html>