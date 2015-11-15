<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><meta charset="utf-8">
<title>FDA Search</title>
<script><#include "fdaSearch.js"/></script>
</head>
<body>
<p>Explore the different offices of the FDA</p>
<select id="offices" onchange="officeSelected(value,'${siteId}');">
	<option disabled selected value="">Select an Office</option>
	<#list results as child>
		<option value="${child}">${child}</option>
	</#list>
</select>
<select id="divisions" onchange="divisionSelected(value,'${siteId}');">
	<option disabled selected value="">Select a Division</option>
</select>
<select id="branches">
	<option disabled selected value="">Select a Branch</option>
</select>
</body>
</html>