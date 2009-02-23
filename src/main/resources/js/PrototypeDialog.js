//Copyright 2008 1king1.com
// 实现对话框的类.
// required:BlackCover.js
var PrototypeDialog = Class.create();
PrototypeDialog.prototype = {
		dialogDiv:null,
		closeDiv:null,
		initialize:function(content) {
			this.content = content;
		},
		//显示Dialog
		show:function(){
			coverObject.showCover();
			this.loadDialog();
			this.dialogDiv.show();
		},
		//隐藏对话框
		hide:function(){
			this.dialogDiv.hide();
			coverObject.hideCover();
			
		},
		loadDialog:function(){
			if(!this.dialogDiv){
				//create dialog div
				this.dialogDiv = document.createElement('div');
				Element.extend(this.dialogDiv);
				this.dialogDiv.setStyle({
					border:'1px solid #dedcd8',padding:'1px 5px 10px 5px',background:'#fff',position:'absolute','z-index':100,height:'50px',width:'150px'
				});
				//找到绝对位置
				//TODO 目前还有问题尤其在IE下的时候
				dime=this.dialogDiv.getDimensions();
				pagesize = this.getPageSize();
				
				with(this.dialogDiv.style){
					left=(pagesize[0]-100)/2+"px";	
					top=(pagesize[1]-150)/2+"px";
				}
				//create close div
				this.closeDiv = document.createElement('div');
				Element.extend(this.closeDiv);
				
				this.closeDiv.setStyle({'margin-bottom':'5px','border-bottom':'solid 1px #ccc'})
				this.closeDiv.update('close');
				this.closeDiv.observe('click',this.hide.bindAsEventListener(this));
				this.dialogDiv.appendChild(this.closeDiv);

				
				//content
				this.dialogDiv.appendChild(document.createTextNode(this.content));
				document.body.appendChild(this.dialogDiv);
			}
		},
		getPageSize:function() {
	        
		   var xScroll, yScroll;
			
			if (window.innerHeight && window.scrollMaxY) {	
				xScroll = window.innerWidth + window.scrollMaxX;
				yScroll = window.innerHeight + window.scrollMaxY;
			} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
				xScroll = document.body.scrollWidth;
				yScroll = document.body.scrollHeight;
			} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
				xScroll = document.body.offsetWidth;
				yScroll = document.body.offsetHeight;
			}
			
			var windowWidth, windowHeight;
			
			if (self.innerHeight) {	// all except Explorer
				if(document.documentElement.clientWidth){
					windowWidth = document.documentElement.clientWidth; 
				} else {
					windowWidth = self.innerWidth;
				}
				windowHeight = self.innerHeight;
			} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
				windowWidth = document.documentElement.clientWidth;
				windowHeight = document.documentElement.clientHeight;
			} else if (document.body) { // other Explorers
				windowWidth = document.body.clientWidth;
				windowHeight = document.body.clientHeight;
			}	
			
			// for small pages with total height less then height of the viewport
			if(yScroll < windowHeight){
				pageHeight = windowHeight;
			} else { 
				pageHeight = yScroll;
			}
		
			// for small pages with total width less then width of the viewport
			if(xScroll < windowWidth){	
				pageWidth = xScroll;		
			} else {
				pageWidth = windowWidth;
			}
	
			return [pageWidth,pageHeight];
	}

}	