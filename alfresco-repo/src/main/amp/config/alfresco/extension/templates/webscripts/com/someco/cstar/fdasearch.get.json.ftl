{
	"offices" : [
		<#list results as child>
		{
	 		"id" : "${child}"
		}<#if !(child == results?last)>,</#if>
		</#list>
	]
}
