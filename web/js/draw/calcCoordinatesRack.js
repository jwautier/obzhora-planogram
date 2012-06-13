/**
 * определить координаты каждого угла объекта
 * @param rack стеллаж
 */
function calcCoordinatesRack(rack)
{
	// поворот объекта
	rack.cos = Math.cos(rack.angle*Math.PI/180);
	rack.sin = Math.sin(rack.angle*Math.PI/180);
	// правый верхний угол
	var x = rack.length / 2;
	var y = rack.width / 2;
	// относительно сцены
	rack.x1 = rack.x_coord + x * rack.cos - y * rack.sin;
	rack.y1 = rack.y_coord + x * rack.sin + y * rack.cos;
	// правый нижний угол
	y = -y;
	// относительно сцены
	rack.x2 = rack.x_coord + x * rack.cos - y * rack.sin;
	rack.y2 = rack.y_coord + x * rack.sin + y * rack.cos;
	// левый нижний угол
	x = -x;
	// относительно сцены
	rack.x3 = rack.x_coord + x * rack.cos - y * rack.sin;
	rack.y3 = rack.y_coord + x * rack.sin + y * rack.cos;
	// левый верхний угол
	y = -y;
	// относительно сцены
	rack.x4 = rack.x_coord + x * rack.cos - y * rack.sin;
	rack.y4 = rack.y_coord + x * rack.sin + y * rack.cos;
}