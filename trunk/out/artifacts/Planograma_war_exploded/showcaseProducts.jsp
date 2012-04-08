<%@ page import="planograma.utils.JspUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Товары стеллажа</title>
	<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
	<script type="text/javascript" src="js/jquery.json-2.3.js"></script>
	<script type="text/javascript" src="js/planograma.js"></script>
	<link rel="stylesheet" href="css/planograma.css"/>
</head>
<body>
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
					<td width="100%">
						<canvas id='edit_canvas'>canvas not supported</canvas>
					</td>
					<td>
						корзина
					</td>
					<td>
						<table class="frame">
							<tr>
								<td>
									<table>
										<tr>
											<td colspan="2">
												<canvas id='preview_canvas'>canvas not supported</canvas>
											</td>
										</tr>
										<tr>
											<td onclick="kMadd()" style="border: 1px solid black; text-align: center;">
												-
											</td>
											<td onclick="kMsub()" style="border: 1px solid black; text-align: center;">
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
											<td colspan="2">свойства товара:</td>
										</tr>
										<tr>
											<td align="right">название</td>
											<td>
												<input type="text" id="showcaseName"
													   onchange="changeShowcaseName(this)"/>
											</td>
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
								<td height="100%"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>