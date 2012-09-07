// обработка событий корзины

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