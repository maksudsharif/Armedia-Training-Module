<import resource="classpath:alfresco/module/alfresco-repo/scripts/rating.js">

if (args.id == null || args.id.length == 0) {
	logger.log("ID arg not set");
	status.code = 400;
	status.message = "Node ID has not been provided";
	status.redirect = true;
} else {
	logger.log("Getting current node");
	var curNode = search.findNode("workspace://SpacesStore/" + args.id);	
	if (curNode == null) {
		logger.log("Node not found");
		status.code = 404;
		status.message = "No node found for id:" + args.id;
		status.redirect = true;
	} else {
		logger.log("Setting model rating data");
		model.rating = getRating(curNode, args.user);
	}
}