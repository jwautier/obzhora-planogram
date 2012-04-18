var oldPost=null;

function postJson(url, data, success) {
	loadingShow();
	if (data != null)
		data = $.toJSON(data);
	$.ajax(
		url, {
			type:'post',
			data:{data:data},
			dataType:'json',
			success:success,
			error:function (data, textStatus) {
//				console.log(data);
//				console.log(textStatus);
				switch (data.status) {
					case 401:
						loginShow();
						oldPost={url:url, data:data, success:success};
						break;
					case 403:
						alert('У вас нет прав на выполнение операции');
						break;
					case 500:
						alert('Ошибка: ' + data.responseText);
						break;
					default:
						alert('Ошибка соединения с сервером попробуйте позже');
						break;
				}
			},
			complete:function () {
				loadingHide();
			}
		});
}

function loginShow() {
	var loginTable = $('#login');
	if (loginTable[0] == null) {
		loginTable = $('<table id="login">\n' +
			'<tr>\n' +
			'<td align="center" valign="middle">\n' +
			'<form action="#" method="post" onsubmit="loginSend(); return false;">\n' +
			'<table>\n' +
			'<tr>\n' +
			'<td colspan="3">Войдите на сайт под своей учетной записью</td>\n' +
			'</tr>\n' +
			'<tr>\n' +
			'<td>Login</td>\n' +
			'<td><input name="login" type="text" tabindex="1"/></td>\n' +
			'<td rowspan="2"><input type="submit" value="Login" tabindex="3"/></td>\n' +
			'</tr>\n' +
			'<tr>\n' +
			'<td>Password</td>\n' +
			'<td><input name="password" type="password" tabindex="2"/></td>\n' +
			'</tr>\n' +
			'</table>\n' +
			'</form>\n' +
			'</td>\n' +
			'</tr>\n' +
			'</table>');
		$('body').append(loginTable);
	}
	loginTable.animate({opacity:'show'}, 500);
}

function loginSend() {
	var loginTable = $('#login');
	var login = loginTable.find('input[name=login]');
	var password = loginTable.find('input[name=password]');
	if (login.val() != '' && password.val() !='')
	{
		postJson('login',
			{login:login.val(), password:password.val()},
			function () {
				loginHide();
				if (oldPost!=null)
				{
					postJson(oldPost.url, oldPost.data, oldPost.success);
					oldPost=null;
				}
			});
	}
}

function loginHide() {
	$('#login').animate({opacity:'hide'}, 500);
}

function logout() {
	postJson('logout', null, loginShow)
}

var loadingIndex = 0;

function loadingShow() {
	var loading = $('#loading');
	if (loading[0] == null) {
		loading = $('<table id="loading">\n' +
			'<tr>\n' +
			'<td align="center" valign="middle">\n' +
			'Loading...\n' +
			'</td>\n' +
			'</tr>\n' +
			'</table>');
		$('body').append(loading);
	}
	loading.animate({opacity:'show'}, 100);
	loadingIndex++;
}

function loadingHide() {
	loadingIndex--;
	if (loadingIndex == 0)
		$('#loading').animate({opacity:'hide'}, 200);
}

function setCookie(name, value) {
	if (name) {
		document.cookie = name + '=' + encodeURIComponent(value);
	}
}

function getCookie(name) {
	var pattern = "(?:; )?" + name + "=([^;]*);?";
	var regexp = new RegExp(pattern);
	if (regexp.test(document.cookie)) {
		return decodeURIComponent(RegExp["$1"]);
	}
	else {
		return null;
	}
}

function clone(o){
	var no = {};
	for (var k in o) {
		no[k] = o[k];
	}
	return no;
}

/**
 * растояние от дочки (x,y) до прямой (заданой 2 точками x1,y1,x2,y2)
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @param x
 * @param y
 */
function distance(x1, y1, x2, y2, x, y) {
	return ((y1-y2)*x+(x2-x1)*y+(x1*y2- x2*y1))/(Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
}

function aOnClick(anchor, action) {
	var anchor = $(anchor);
	var res=!anchor.hasClass('disabled');
	if (res) {
		if (action != null) {
			action();
		}
	}
	return res;
}

function numberFieldKeyDown(event, obj) {
	if (event.ctrlKey) {
		var value = Number(obj.value);
		if (value != null && !isNaN(x) && value!=Infinity) {
			switch (event.keyCode) {
				// up
				case 38:
					value = Math.ceil(value);
					value++;
					obj.value = value;
					$(obj).change();
					break;
				// down
				case 40:
					value = Math.ceil(value);
					value--;
					obj.value = value;
					$(obj).change();
					break;
			}
		}
	}
}

