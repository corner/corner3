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
package corner.area;

import java.util.List;

/**
 * 用于获取行政区数据的接口
 * 
 * @author dong
 * @version $Revision: 2089 $
 * @since 0.0.1
 */
public interface AreaModelCallback {
	/**
	 * 取得省一级的行政区列表
	 * 
	 * @return
	 */
	public List<AreaModelItem> getProvince();

	/**
	 * 取得市一级的行政区列表
	 * 
	 * @param provinceCode
	 *            省一级的行政区代码
	 * @return
	 */
	public List<AreaModelItem> getCities(String provinceCode);

	/**
	 * 取得县一级的行政区列表
	 * @param cityCode 市一级的行政区代码
	 * @return
	 */
	public List<AreaModelItem> getTowns(String cityCode);
	
	/**
	 * 根据行政区代码取得AreaModelItem
	 * @param code
	 * @return
	 */
	public AreaModelItem getAreaModelItemByCode(String code);

}
