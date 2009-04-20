ganshane = {
	all_validations:{},
	presence : function(field, options) {
		lv = this.createOrGetValidation(field);
		lv.add( Validate.Presence,options);
	},
	confirmation : function(field, options) {
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Confirmation,options);
	},
	email:function(field,options){
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Email,options);
	},
	Length:function(field,options){
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Length,options);
	},
	Numericality:function(field,options){																	
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Numericality,options);
	},
	regexp:function(field,options){
		lv =  this.createOrGetValidation(field);
		if(options.pattern){
			options.pattern = new RegExp(options.pattern)
		}
		lv.add(Validate.Format,options);
   },
   mutiplePresence:function(field, options) {
		var _lv =  this.createOrGetValidation(field);
		var _form = $(field).form;
		var args = options.args.clone();
		args.push(field);
		var failMessage = options.failureMessage;
		var _val = function(){
 				var pass =false; 
    			for(i = 0;i<args.length;i++){
    				var item = args[i];
    				var _value = $F(item)
    				if(_value != null  &&  _value != undefined && _value.strip() != ''){
    					pass = true;
    					break;
    				}
    			}
    			return pass;
		}
		var _afunc = function(e){
  			var _p = _val();			
			if(!_p){  			
				$T(e.memo).validationError=true;       
            alert(failMessage);
  			}
    		return _p;
    	}
		//我们在这里监听T5的Validate事件,触发校验,因为LV对于空值在不使用Presence的情况下,校验会通过
		Event.observe(_form,Tapestry.FORM_VALIDATE_EVENT,_afunc);
	},
   fckeditor:function(field, options) {
		var _lv =  this.createOrGetValidation(field);
		var _form = $(field).form;
		var failMessage = options.failureMessage;
		var _afunc = function(e){
			//先更新与FCkEditor对应的表单元素的值,之后再判断是否为空,对于FckEditor的校验需要单独进行
			var oEditor = FCKeditorAPI.GetInstance(field) ;
			oEditor.UpdateLinkedField();
			if($F(field).strip().length==0){
				$T(e.memo).validationError=true;       
                alert(failMessage);
                return false;
			}
    		return true;
    	}
		//我们在这里监听T5的Validate事件,触发校验,因为LV对于空值在不使用Presence的情况下,校验会通过
		Event.observe(_form,Tapestry.FORM_VALIDATE_EVENT,_afunc);
	},
	createOrGetValidation:function(field){
		lv = this.all_validations[field];
		if (lv == null) {
			lv = new LiveValidation(field, {
				onValid: function(){
					this.removeMessage();
					this.addFieldClass();
				},
				wait: 5000
			});
			this.all_validations[field] = lv;
		}
		return lv;
	}
};
Tapestry.Initializer.liveValidation=function(field,validator,specs){
	 _field = $(field);
    $(_field.form).getFormEventManager();
    $(_field).getFieldEventManager();
    var vfunc = ganshane[validator];
    vfunc.call(ganshane, field,specs);
}
