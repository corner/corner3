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
package corner.exceptions;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class NoSuchResourceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchResourceException() {
		super();

	}

	public NoSuchResourceException(String message, Throwable cause) {
		super(message, cause);

	}

	public NoSuchResourceException(String message) {
		super(message);

	}

	public NoSuchResourceException(Throwable cause) {
		super(cause);

	}

}
