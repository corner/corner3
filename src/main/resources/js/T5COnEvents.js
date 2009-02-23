/*
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 * Copyright 1996-2008 by Sven Homburg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

var T5COnEvent = Class.create();
T5COnEvent.prototype = {
    initialize: function(eventName, elementId, requestUrl, onCompleteCallback,onBeforeRequest)
    {
        if (!$(elementId))
            throw(elementId + " doesn't exist!");

        this.elementId = elementId;
        this.element = $(elementId);
        this.requestUrl = requestUrl;
        this.onCompleteCallback = onCompleteCallback;
				this.onBeforeRequest = null;
				var _btype = typeof onBeforeRequest;
				if(_btype == 'function'){
					this.onBeforeRequest = onBeforeRequest;
				}
        var formElement = this.element.form;
        if (typeof formElement != 'undefined'){
						this.element.oldValue = $F(this.element)
				}
        Event.observe(this.element, eventName, this.reflectOnEvent.bind(this, this.element), false);
    },
    reflectOnEvent: function(myEvent)
    {
        var fieldValue;
        var formElement = this.element.form;
        //fix 每次请求时使用单独的变量,避免requestUrl错误 
        var _requestUrl = this.requestUrl;

        if (typeof formElement != 'undefined')
            fieldValue = $F(this.element);

				//在发送请求到服务器之前,先进行必要的检验,如果返回false,则不进行提交
				if(this.onBeforeRequest != null){
					if(!this.onBeforeRequest(this.element)){
						return;
					}
				}

        if (typeof fieldValue != 'undefined'){
				//修正url中有session的id的导致参数错误的问题,目前在jetty6下测试通过
    			var _suffix = "";
				var _jettySession = ";jsessionid=";
				var _t5_tac = "?t:ac=";
				if((_si=_requestUrl.indexOf(_jettySession))>0){
    				_suffix = _requestUrl.substring(_si);
    				_requestUrl = _requestUrl.substring(0,_si);
				}
				//修正url中有t:ac的参数,将其放到最后的,避免T5解析出错
				if((_si=_requestUrl.indexOf(_t5_tac))>0){
    				_tac = _requestUrl.substring(_si);
    				_requestUrl = _requestUrl.substring(0,_si);
    				_suffix = _tac + _suffix;
				}
            _requestUrl += "/" + fieldValue+_suffix;
          }

        new Ajax.Request(_requestUrl, {
            method: 'post',
            onFailure: function(t)
            {
        		if(Tapestry.DEBUG_ENABLED){       
                	alert("Failure:"+t.responseText);
        		}else{
                	alert("抱歉,服务器响应错误");
        		}	
            },
            onException: function(t, exception)
            {
        		if(Tapestry.DEBUG_ENABLED){       
                	alert("Exception:["+exception+"],response:["+t+"]");
        		}else{
                	alert("抱歉,系统异常");
        		}	
            },
            onSuccess: function(t)
            {
                if (typeof this.onCompleteCallback != 'undefined'){
                	try{   
                    	eval(this.onCompleteCallback + "('" + t.responseText + "','"+this.elementId+"')");
                	}catch(e){
                		if(Tapestry.DEBUG_ENABLED){       
                        	alert("onSuccess Exception:["+e+"],response:["+t.responseText +"]");
                		}else{
                        	alert("抱歉,系统异常");
                		}	
                	}
                }
         }.bind(this)
        });
    }
}
