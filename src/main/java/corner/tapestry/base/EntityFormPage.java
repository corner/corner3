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
package corner.tapestry.base;

import org.apache.tapestry5.EventContext;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

/**
 * entity generic form page
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 * @param <T> 实体对象
 */
public class EntityFormPage<T> extends AbstractEntityFormPage<T>{
	
	@Inject
	private Logger logger;
	
	public void onActivate(EventContext context){
		try{
			if(context.getCount()==1){
				this.setEntity(context.get(this.getEntityClass(), 0));
			}
		}catch(Exception e){
			logger.warn(e.toString());
		}
	}
}
