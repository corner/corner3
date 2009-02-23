/* 
 * Copyright 2008 The Lichen Team.
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
package corner.mixins;


import org.apache.tapestry5.annotations.Parameter;

import corner.base.AbstractEventMixin;


/**
 * OnChange mixin catch the browser event "onChange" from a select component
 * and redirect it to your application via tapestry event "change".
 *
 * @author <a href="mailto:homburgs@googlemail.com">S.Homburg</a>
 * @version $Id: OnAjaxEvent.java 2956 2008-11-11 05:12:49Z jcai $
 */
public class OnAjaxEvent extends AbstractEventMixin
{
    @Parameter(required = true, defaultPrefix = "literal")
    private String event;

    /**
     * set the event name.
     *
     * @return the event name
     */
    @Override
	public String getEventName()
    {
        return event;
    }
}