/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-9-24
 */
package corner.orm.gae.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.apphosting.api.ApiProxy.Environment;

/**
 * test environment
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class TestEnvironment implements Environment {

	    public String getAppId() {
	        return "Unit Tests";
	    }

	    public String getVersionId() {
	        return "1.0";
	    }

	    public void setDefaultNamespace(String s) { }

	    public String getRequestNamespace() {
	        return "gmail.com";
	    }

	    public String getDefaultNamespace() { 
	        return null;
	    }

	    public String getAuthDomain() {
	      return null;
	    }

	    public boolean isLoggedIn() {
	      return false;
	    }

	    public String getEmail() {
	      return null;
	    }

	    public boolean isAdmin() {
	      return false;
	    }

		@Override
		public Map<String, Object> getAttributes() {
			Map<String,Object> map = new HashMap<String,Object>();
			return map;
		}
		 

}
