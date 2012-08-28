// прорисовка элементов
function drawEditCanvas() {
	window.edit_context.clearRect(0, 0, window.edit_canvas.width, window.edit_canvas.height);
	window.edit_context.lineWidth = 1;
	window.edit_context.strokeStyle = "BLACK";
	window.edit_context.strokeRect(
		(-window.kx + window.offset_rack_x) / window.km,
		window.edit_canvas.height + (window.ky - window.offset_rack_y) / window.km,
		window.rack.width / window.km,
		-window.rack.height / window.km);
	window.edit_context.strokeStyle = "GREEN";
	window.edit_context.strokeRect(
		(-window.kx + window.offset_real_rack_x) / window.km,
		window.edit_canvas.height + (window.ky - window.offset_real_rack_y) / window.km,
		window.rack.real_width / window.km,
		-window.rack.real_height / window.km);
	for (var i = 0; i < window.rackShelfList.length; i++) {
		drawShelf(window.rackShelfList[i], window.edit_canvas, window.edit_context, window.kx, window.ky, window.km);
	}
}