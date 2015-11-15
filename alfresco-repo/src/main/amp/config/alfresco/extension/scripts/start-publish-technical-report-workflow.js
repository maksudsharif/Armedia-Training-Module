function startWorkflow()
{
    var workflow = actions.create("start-workflow");
    workflow.parameters.workflowName = "activiti$publishTechnicalReport";
    workflow.parameters["bpm:workflowDescription"] = document.name + " has been submitted for publication. Please review this document and submit a result."
    workflow.parameters["scwf:reviewerEmail"] = "";
    var futureDate = new Date();
    futureDate.setDate(futureDate.getDate() + 7);
    workflow.parameters["bpm:workflowDueDate"] = futureDate; 
    return workflow.execute(document);
}

function main()
{
      startWorkflow();
}

main();
