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
package lichen.integration.app1.pages;

import lichen.services.http.CacheHeader;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.util.TextStreamResponse;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
@CacheHeader
public class CacheHeaderTest {

	StreamResponse onActivate() {
		return new TextStreamResponse("text/plain", "test");
	}

}
