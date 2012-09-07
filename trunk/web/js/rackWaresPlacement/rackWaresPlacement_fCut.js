function fCut()
{
	window.copyObjectList=[];
	for (var i in window.selectRackWaresList)
	{
		window.copyObjectList[i]=clone(window.selectRackWaresList[i]);
		var selectRackWares=window.selectRackWaresList[i];
		for (var j in window.rackWaresList)
		{
			if (selectRackWares==window.rackWaresList[j])
			{
				window.rackWaresList.splice(j,1);
			}
		}
	}
	window.selectRackWaresList=[];
	fSelectRackWares();
	drawEditCanvas();
	drawPreviewCanvas();
	if (window.copyObjectList.length>0)
	{
		$('#butPaste').removeClass('disabled');
	}
}
