function getJSON(url, successHandler, errorHandler) {
	if(typeof XMLHttpRequest == 'undefined') {
		return notSupported();
	}
	var xhr = new XMLHttpRequest();
	xhr.open('get', url, true);
	xhr.responseType = 'json';
	xhr.onload = function() {
		var status = xhr.status;
		if (status == 200) {
			successHandler && successHandler(xhr.response);
		} else {
			errorHandler && errorHandler(status);
		}
	};
	xhr.send();
}

function officeSelected($i, siteId) {
	console.log(siteId);
	var url = "http://localhost:8080/alfresco/s/someco/fda/search?siteId=" + siteId + "&office=" + $i;
	var json = getJSON(url, 
			function(data){
		//clear branches dropdown
		var select = document.getElementById('branches');
		while(select.options.length > 0) {
			select.options[0] = null;
		}
		//create default option
		var bOption = document.createElement('option');
		bOption.disabled = true;
		bOption.selected = true;
		bOption.text = bOption.value = "Select a Branch";
		select.add(bOption, 0);

		select = document.getElementById('divisions');
		while (select.options.length > 0) {
			select.options[0] = null;
		}
		var defOption = document.createElement('option');
		defOption.disabled = true;
		defOption.selected = true;
		defOption.text = defOption.value = "Select a Division.";
		select.add(defOption,0);
		select.disabled = true;
		for(var i = 0; i < data.offices.length; i++) {
			var option = document.createElement('option');
			option.text = option.value = data.offices[i].id;
			select.add(option, i+1);
		}
		select.disabled = false;

	}, function(data){
		alert("fail");
	});

}

function divisionSelected($i, siteId) {
	console.log(siteId);
	var url = "http://localhost:8080/alfresco/s/someco/fda/search?siteId=" + siteId + "&office=";
	var office = document.getElementById('offices');
	var selected = office.options[office.selectedIndex].value;
	url += selected + "&division=";
	url += $i;
	var json = getJSON(url, 
			function(data){
		var select = document.getElementById('branches');
		var length = select.options.length;
		while (select.options.length > 0) {
			select.options[0] = null;
		}
		var defOption = document.createElement('option');
		defOption.disabled = true;
		defOption.selected = true;
		defOption.text = defOption.value = "Select a branch.";
		select.add(defOption,0);
		for(var i = 0; i < data.offices.length; i++) {
			var option = document.createElement('option');
			option.text = option.value = data.offices[i].id;
			select.add(option, i+1);
		}
		select.disabled = false;


	}, function(data){
		alert("fail");
	});


}