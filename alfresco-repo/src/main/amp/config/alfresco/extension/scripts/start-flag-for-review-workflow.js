/**
 * 
 */function startWorkflow()
{
    var workflow = actions.create("start-workflow");
    workflow.parameters.workflowName = "activiti$flagForReview";
    workflow.parameters["bpm:workflowDescription"] = document.name + " has been submitted for publication. Approve/reject a formal review process."
    var futureDate = new Date();
    futureDate.setDate(futureDate.getDate() + 14);
    workflow.parameters["bpm:workflowDueDate"] = futureDate; 
    if(document != null){
    return workflow.execute(document);
    } else {
    	logger.log("DOC NULL");
    }
}

function main()
{
      startWorkflow();
}

main();
