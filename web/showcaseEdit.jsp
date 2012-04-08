<%@ page import="planograma.utils.JspUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Редактирование стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body>
<table class="frame">
	<tr>
		<td class="path">
			<!--TODO-->
			<a href="menu.jsp">Меню</a> > <a href="sectorList.jsp">Залы торговых площадок</a> > <a href="sectorEdit.jsp">Редактирование зала</a> > Редактирование стеллажа
			<br/>
			<a href="menu.jsp">Меню</a> > <a href="rackTemplateList.jsp">Типовые стеллажи</a> > Редактирование стеллажа
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
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Добавить полку")%></a></td>
							</tr>
							<tr>
								<td><a href="#" onclick="return aOnClick(this)" class="disabled notReady"><%=JspUtils.toMenuTitle("Добавить перегородку")%></a></td>
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
						<canvas id='edit_canvas'>canvas not supported</canvas>
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
												<input type="text" id="showcaseName"
													   onchange="changeShowcaseName(this)"/>
											</td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text" id="showcaseWidth"
													   onchange="changeShowcaseWidth(this)"/></td>
										</tr>
										<tr>
											<td align="right">длина</td>
											<td><input type="text" id="showcaseLength"
													   onchange="changeShowcaseLength(this)"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text" id="showcaseHeight"
													   onchange="changeShowcaseHeight(this)"/></td>
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
											<td align="right">выше на</td>
											<td><input type="text"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text"/></td>
										</tr>
										<tr>
											<td align="right">глубина</td>
											<td><input type="text"/></td>
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
											<td colspan="2">свойства перегородки:</td>
										</tr>
										<tr>
											<td align="right">x</td>
											<td><input type="text"/></td>
										</tr>
										<tr>
											<td align="right">y</td>
											<td><input type="text"/></td>
										</tr>
										<tr>
											<td align="right">ширина</td>
											<td><input type="text"/></td>
										</tr>
										<tr>
											<td align="right">высота</td>
											<td><input type="text"/></td>
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
	var rack={};
</script>

</body>
</html>