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