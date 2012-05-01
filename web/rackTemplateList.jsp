<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateList" %>
<%@ page import="planograma.servlet.racktemplate.RackTemplateRemove" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Стантартные типы стеллажей</title>
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
					<td><i>Типовые стеллажи</i></td>
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
					<col width="75%"/>
				</colgroup>
				<tr>
					<td>
						<table class="menu">
							<tr>
								<td><a href="#" id="rackTemplateAdd" onclick="return aOnClick(this, fRackTemplateAdd)"><%=JspUtils.toMenuTitle("Добавить стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateView" onclick="return aOnClick(this, fRackTemplateView)" class="disabled"><%=JspUtils.toMenuTitle("Просмотр стеллажа")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateViewHistory" onclick="return aOnClick(this, fRackTemplateViewHistory)" class="disabled"><%=JspUtils.toMenuTitle("Просмотр истории стеллажа")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateEdit" onclick="return aOnClick(this, fRackTemplateEdit)" class="disabled"><%=JspUtils.toMenuTitle("Редактировать стеллаж")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateActive" onclick="return aOnClick(this, fRackTemplateActive)" class="disabled"><%=JspUtils.toMenuTitle("Активировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateNotActive" onclick="return aOnClick(this, fRackTemplateNotActive)" class="disabled"><%=JspUtils.toMenuTitle("Заблокировать")%></a></td>
							</tr>
							<tr>
								<td><a href="#" id="rackTemplateRemove" onclick="return aOnClick(this, fRackTemplateRemove)" class="disabled"><%=JspUtils.toMenuTitle("Удалить стеллаж")%></a></td>
							</tr>
							<tr>
								<td height="100%"></td>
							</tr>
						</table>
					</td>
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
</table>
<script type="text/javascript">
	function fRackTemplateAdd()
	{
		setCookie('code_rack_template', '');
		document.location='rackTemplateEdit.jsp';
	}
	function fRackTemplateView()
	{
		//TODO
	}
	function fRackTemplateViewHistory()
	{
		//TODO
	}
	function fRackTemplateEdit()
	{
		var code_rack_template = $('#rackTemplateList').val();
		if (code_rack_template!=null && code_rack_template.length>0)
		{
			setCookie('code_rack_template', code_rack_template);
			document.location='rackTemplateEdit.jsp';
		}
	}
	function fRackTemplateActive()
	{
		//TODO
	}
	function fRackTemplateNotActive()
	{
		//TODO
	}
	function fRackTemplateRemove()
	{
		var code_rack_template = $('#rackTemplateList').val();
		if (code_rack_template!=null && code_rack_template.length>0)
		{
			postJson(
					'<%=RackTemplateRemove.URL%>',
					{code_rack_template:code_rack_template},
					function () {
						loadComplete();
					})
		}
	}
</script>
<script type="text/javascript">
	function selectRackTemplate(code_rack_template) {
		if (code_rack_template != null && code_rack_template != '') {
			var rackTemplateList = $('#rackTemplateList');
			var option = rackTemplateList.find('option[value=' + code_rack_template + ']');
			if (option.length == 1) {
				option.attr('selected', 'selected');
				$('#rackTemplateView').removeClass('disabled');
				$('#rackTemplateEdit').removeClass('disabled');
				$('#rackTemplateUnlock').removeClass('disabled');
				$('#rackTemplateRemove').removeClass('disabled');
				setCookie('code_rack_template', code_rack_template);
			}
			else {
				setCookie('code_rack_template', '');
			}
		}
		else {
			setCookie('code_rack_template', '');
		}
	}
</script>
<script type="text/javascript">
	function loadComplete()
	{
	postJson('<%=RackTemplateList.URL%>', null, function (data) {
		var rackTemplateList = $('#rackTemplateList');
		rackTemplateList.empty();
		for (var i in data.rackTemplateList) {
			var item = data.rackTemplateList[i];
			var option = $('<option></option>')
			option.val(item.code_rack_template);
			option.text(item.name_rack_template);
			rackTemplateList.append(option)
		}
		var code_rack_template = getCookie('code_rack_template');
		if (code_rack_template != null && code_rack_template.length > 0) {
			selectRackTemplate(code_rack_template);
		}
	});
	}
</script>
</body>
</html>