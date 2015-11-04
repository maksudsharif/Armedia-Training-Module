<!DOCTYPE html>

<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><meta charset="utf-8">
<title>FDA Search</title>
<style>
	#support { border: 1px solid; padding: 1em; }
	.fail { background: pink; border-color: red; padding: 1em; }
	.pass { background: lightgreen; border-color: #468847; padding: 1em; }
</style>
<style>#content > #right > .dose > .dosesingle,
#content > #center > .dose > .dosesingle
{display:none !important;}</style></head><body><p>This document loads a raw JSON resource using Ajax, avoiding the explicit parsing step by setting <code>xhr.responseType = 'json'</code>. It then displays (part of) the data in an alert box.
</p><p>See <a href="https://mathiasbynens.be/notes/xhr-responsetype-json">Loading JSON-formatted data with Ajax and <code>responseType='json'</code></a> for more information.
</p><p id="support" class="pass">Your browser seems to support <code>xhr.responseType="json"</code>. Hurray! You should see an alert box with your public IP address, which was loaded as JSON via Ajax.</p>

<script>
	(function() {

		var element = document.getElementById('support');

		var notSupported = function() {
			element.innerHTML = 'Your browser doesn’t seem to support <code>xhr.responseType="json"</code> yet. :(';
			element.className = 'fail';
		};

		var supported = function() {
			element.innerHTML = 'Your browser seems to support <code>xhr.responseType="json"</code>. Hurray! You should see an alert box with your public IP address, which was loaded as JSON via Ajax.';
			element.className = 'pass';
		};

		var getJSON = function(url, successHandler, errorHandler) {
			if (typeof XMLHttpRequest == 'undefined') {
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
		};

		// load a non-JSON resource
		getJSON('http://localhost:8080/alfresco/s/someco/fda/search?siteId=training', function(data) {
			if (typeof data == 'string') {
				notSupported();
			} else {
				supported();
				alert('Your public IP address is: ' + data.offices[0].id);
			}
		});

	}());
</script></body></html>