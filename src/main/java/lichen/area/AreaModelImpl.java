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
package lichen.area;

/**
 * 表示行政区选择的类
 * 
 * @author dong
 * @version $Revision: 2089 $
 * @since 0.0.1
 */
public class AreaModelImpl implements AreaModel {
	private static final long serialVersionUID = 1L;
	private final AreaModelItem province;
	private final AreaModelItem city;
	private final AreaModelItem town;

	public AreaModelImpl(AreaModelItem province, AreaModelItem city, AreaModelItem town) {
		super();
		this.province = province;
		this.city = city;
		this.town = town;
	}

	public AreaModelItem getProvince() {
		return province;
	}

	public AreaModelItem getCity() {
		return city;
	}

	public AreaModelItem getTown() {
		return town;
	}

}
