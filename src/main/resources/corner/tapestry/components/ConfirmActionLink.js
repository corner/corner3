function confirmActionLink(linkId,msg){
	$(linkId).observe("click",function(e){
		if(!confirm(msg)){
			Event.stop(e);
			return false;
		}
	});
	
}
