function fDel() {
	if (window.shelf != null) {
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (window.rackShelfList[i] == window.shelf) {
				window.rackShelfList.splice(i, 1);
				i = window.rackShelfList.length;
			}
		}
		window.shelf = null;
		selectShelf(window.shelf);
	}
	else {

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
	}

	drawEditCanvas();
	drawPreviewCanvas();
}