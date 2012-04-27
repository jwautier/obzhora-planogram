<%@ page import="planograma.constant.SessionConst" %>
<%@ page import="planograma.utils.JspUtils" %>
<%@ page import="planograma.servlet.image.ImageCleanCache" %>
<%@ page import="planograma.servlet.TestAction" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Планограммы супермаркетов</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet"href="css/planograma.css"/>
</head>
<body>
<table class="frame">
	<tr>
		<td class="path">
			<table width="100%">
				<tr>
					<td><i>Меню</i></td>
					<td width="100%"></td>
					<td><a href="#" onclick="logout()">Выход</a></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td valign="top">
			<table class="menu">
				<tr>
					<td><a href="sectorList.jsp"><%=JspUtils.toMenuTitle("Залы торговых площадок")%></a></td>
				</tr>
				<tr>
					<td><a href="rackTemplateList.jsp"><%=JspUtils.toMenuTitle("Типовые стеллажи")%></a></td>
				</tr>
				<tr>
					<td><a href="#" onclick="postJson('<%=ImageCleanCache.URL%>', null, function () {alert('Кеш очищен')})"><%=JspUtils.toMenuTitle("Очистить кеш изображений")%></a></td>
				</tr>
				<tr>
					<td><a href="#" onclick="logout()"><%=JspUtils.toMenuTitle("Выход")%></a></td>
				</tr>
				<tr>
					<td><a href="choiceWares.jsp"><%=JspUtils.toMenuTitle("Окно выбора товара(-)")%></a></td>
				</tr>
				<tr>
					<td><a href="showcaseProducts.jsp"><%=JspUtils.toMenuTitle("Товары стеллажа(-)")%></a></td>
				</tr>
				<tr>
					<td><a href="#" onclick="postJson('<%=TestAction.URL%>', null, function () {alert('Test')})"><%=JspUtils.toMenuTitle("Test(-)")%></a></td>
				</tr>
				<tr>
					<td height="100%"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
<script type="text/javascript">
	<%
	if (session.getAttribute(SessionConst.SESSION_USER)==null)
	{
		out.write("loginShow();\n");
	}
	%>
</script>
</html>