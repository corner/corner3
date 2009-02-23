var AreaSelect = Class.create();
AreaSelect.prototype = {initialize:function (pid, cid, tid) {
	this.pid = pid;
	this.cid = cid;
	this.tid = tid;
	$(pid).setAttribute("to", cid);
	$(cid).setAttribute("to", tid);
}};
function onAreaChanged(response, source) {
var _to = $(source).getAttribute("to");
var to = $(_to);
if (to == null) {
	return;
}
var toClean = to;
//连续删除下一级的数据
while(toClean !=null){
   var options = toClean.options;
   var olen = options.length;
   for (var i = 1; i < olen; i++) {
    	toClean.remove(1);
    }
   var _toClean = toClean.getAttribute("to")
   if(_toClean !=null){
       toClean = $(_toClean)
   }else{
       toClean = null;
   }
}
var json = response.evalJSON();
var array = $A(json.result);
array.each(function (item) {
	var optn = document.createElement("OPTION");
	optn.text = item[0];
	optn.value = item[1];
	to.options.add(optn);
});
}