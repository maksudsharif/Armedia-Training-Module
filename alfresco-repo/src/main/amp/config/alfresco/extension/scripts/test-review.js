function ingest()
{
	var props = new Array(1);
	props["sc:reviewFlag"] = true;
    document.addAspect("sc:techReviewable", props);
    document.save();
}

function main()
{
      ingest();
}

main();
