/**
 * 
 */function addID()
{
    var props = new Array(1);
    var id = generateID();
    props["sc:documentId"] = id;
    document.addAspect("sc:identifiable", props);
    document.properties["cm:title"] = id;
    document.save();
}
 function generateID() {
	 var rand = new Array(5).join().replace(/(.|$)/g, function(){return ((Math.random()*36)|0).toString(36)[Math.random()<.5?"toString":"toUpperCase"]();});
	 var id = "BELVAC-P"+rand;
	 id = id.toUpperCase();
	 return id;
 }

function main()
{
      addID();
}

main();
