// 一个展示覆盖层的一个js
var BlackCover = Class.create();
BlackCover.prototype = {
	_coverDiv:null,

	initialize:function(){

	},
	
	showCover : function(){
		this.initCover();
		this.bgShade(0,50);
		this._coverDiv.show();
	},

	hideCover : function(){
		this.bgShade(50,0);
		this._coverDiv.hide();
	},

	//_civerDiv透明度渐变
	//begInt 初始透明度
	//endInt 终止透明度
	 bgShade : function(begInt,endInt) {
		 _begInt = begInt;
		 _endInt = endInt;

		 this.chgAlpha(_begInt);

		 if(_begInt<_endInt){
			 _begInt++;
		 }else
		 {
			 _begInt--;
		 }
		 if(_begInt!=_endInt) 
		 { 
		 	setTimeout(function (){this.bgShade(_begInt,_endInt);}.bind(this),30); 
		 } 
	}, 


	//设定层透明度
	//alpInt 透明值
	chgAlpha : function(alpInt) {
		var _opacity = alpInt/100;
		this._coverDiv.setStyle({
			left:0, top:0,margin:0,padding:0,width:'100%',height:'100%',
			background:'#FF9900',position:'absolute',right:'16px',
			'z-index':99,'-moz-opacity':_opacity,opacity:_opacity,filter:'alpha(alpInt)'
			});
	},

	initCover : function(){
		if(! this._coverDiv){
			this._coverDiv = document.createElement('div');
			Element.extend(this._coverDiv);
			document.body.appendChild(this._coverDiv);
			/*
			this._coverDiv.setStyle({
				left:0, top:0,margin:0,padding:0,width:'100%',height:'100%',
				background:'#FF9900',position:'absolute',right:'16px',
				'z-index':99,'-moz-opacity':0.8,opacity:0.8,filter:'alpha(opacity=80)'
				});
			*/
		
		}
	}

}

var coverObject = new BlackCover();