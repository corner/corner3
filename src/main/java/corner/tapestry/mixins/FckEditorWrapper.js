Tapestry.Initializer.createEditor=function(basePath,editorId,width,height,toolbar){ 
	var sBasePath = basePath;
	var oFCKeditor = new FCKeditor( editorId,width,height,toolbar) ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.ReplaceTextarea() ;
}