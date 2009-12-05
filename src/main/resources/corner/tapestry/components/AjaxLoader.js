var AjaxLoader = Class.create(BlackCover,{
	_ajax_loader_div:null,
	 initialize: function(msg) {this.msg = msg;this.register_ajaxloader_indicator();},
	_ensureIndicatorExists :function(){
		if(this._ajax_loader_div == null){
			this.initCover();
			this._ajax_loader_div = document.createElement('div');
			Element.extend(this._ajax_loader_div);
			this._ajax_loader_div.id = "ajax-indicator";
			_span = document.createElement("span");
			_span.appendChild(document.createTextNode(this.msg));
			this._ajax_loader_div.appendChild(_span);
			document.body.appendChild(this._ajax_loader_div);
		}
	},
	register_ajaxloader_indicator:function(){
		Ajax.Responders.register({
		  onCreate:( function() {
		  	this._ensureIndicatorExists();
		  	this.showCover();
		  	this._ajax_loader_div.show();
		  }).bind(this),
		  onComplete: (function() {
		  	this._ensureIndicatorExists();
		  	this._ajax_loader_div.hide();
		  	this.hideCover();
		  }).bind(this)
		});
	}
});


