/* 
 * Copyright 2009 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.tapestry.persistent;

import static org.apache.tapestry5.ioc.internal.util.CollectionFactory.newList;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.tapestry5.internal.services.CookieSource;
import org.apache.tapestry5.internal.services.PersistentFieldChangeImpl;
import org.apache.tapestry5.services.ClientDataEncoder;
import org.apache.tapestry5.services.ClientDataSink;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.PersistentFieldChange;
import org.apache.tapestry5.services.PersistentFieldStrategy;

import corner.utils.Defense;

/**
 * 用来保存数据到客户端的cooki中的处理
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class CookiePersistentFieldStrategy implements PersistentFieldStrategy {

	private String prefix = "cookie:";
	private Cookies cookies;
	private CookieSource cookieSource;
	private ClientDataEncoder encoder;

	public CookiePersistentFieldStrategy(Cookies cookies,
			CookieSource cookieSource, ClientDataEncoder encoder) {
		this.cookies = cookies;
		this.cookieSource = cookieSource;
		this.encoder = encoder;
	}

	/**
	 * @see org.apache.tapestry5.services.PersistentFieldStrategy#discardChanges(java.lang.String)
	 */
	@Override
	public void discardChanges(String pageName) {
		String fullPrefix = prefix + pageName + ":";

		Cookie[] cookies = cookieSource.getCookies();

		if (cookies == null)
			return;

		for (Cookie cooky : cookies) {
			if (cooky.getName().startsWith(fullPrefix)) {

				this.cookies.readCookieValue(cooky.getName());
			}
		}
	}

	/**
	 * @see org.apache.tapestry5.services.PersistentFieldStrategy#gatherFieldChanges(java.lang.String)
	 */
	@Override
	public Collection<PersistentFieldChange> gatherFieldChanges(String pageName) {
		List<PersistentFieldChange> result = newList();

		String fullPrefix = prefix + pageName + ":";

		Cookie[] cookies = cookieSource.getCookies();

		if (cookies == null)
			return result;

		for (Cookie cooky : cookies) {
			if (cooky.getName().startsWith(fullPrefix)) {

				result.add(buildChange(cooky.getName(), cooky.getValue()));
			}
		}

		return result;
	}

	private PersistentFieldChange buildChange(String name, String attribute)
    {
		
		Object r=null;
		//解压缩
		try{
	     r = this.encoder.decodeClientData(attribute).readObject();
		}catch(Exception e){
			return null;
		}
        
      

        String[] chunks = name.split(":");

        // Will be empty string for the root component
        String componentId = chunks[2];
        String fieldName = chunks[3];
        
        return new PersistentFieldChangeImpl(componentId, fieldName, r);
    }

	/**
	 * @see org.apache.tapestry5.services.PersistentFieldStrategy#postChange(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public void postChange(String pageName, String componentId,
			String fieldName, Object newValue) {
		Defense.notBlank(pageName, "pageName");
		Defense.notBlank(fieldName, "fieldName");

		StringBuilder builder = new StringBuilder(prefix);
		builder.append(pageName);
		builder.append(':');

		if (componentId != null)
			builder.append(componentId);

		builder.append(':');
		builder.append(fieldName);

		if (newValue == null) {
			this.cookies.removeCookieValue(builder.toString());
			return;
		}
		ClientDataSink sink = this.encoder.createSink();
		try {
			sink.getObjectOutputStream().writeObject(newValue);

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		cookies.writeCookieValue(builder.toString(), sink.getClientData(), 30 * 60);
	}
}
