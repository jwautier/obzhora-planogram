function fCut() {
	if (window.shelf != null) {
		for (var i = 0; i < window.rackShelfList.length; i++) {
			if (window.rackShelfList[i] == window.shelf) {
				window.rackShelfList.splice(i, 1);
				i = window.rackShelfList.length;
			}
		}
		window.copyShelf = window.shelf;
		$('#butPaste').removeClass('disabled');
		window.shelf = null;
		selectShelf(window.shelf);
	}
	else {
		window.copyObjectList = [];
		for (var i in window.selectRackWaresList) {
			window.copyObjectList[i] = clone(window.selectRackWaresList[i]);
			var selectRackWares = window.selectRackWaresList[i];
			for (var j in window.rackWaresList) {
				if (selectRackWares == window.rackWaresList[j]) {
					window.rackWaresList.splice(j, 1);
				}
			}
		}
		window.selectRackWaresList = [];
		fSelectRackWares();
		if (window.copyObjectList.length > 0) {
			$('#butPaste').removeClass('disabled');
		}
	}

	drawEditCanvas();
	drawPreviewCanvas();
}
