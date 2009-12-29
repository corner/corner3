/* 
 * Copyright 2008 The Corner Team.
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
package corner.orm.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用来构建条件的构造器
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class ConditionBuilder {
	
	  public enum Op
	  {
	    LESS_THAN(" < "),
	    LESS_THAN_OR_EQUAL_TO(" <= "),
	    GREATER_THAN(" > "),
	    GREATER_THAN_OR_EQUAL_TO(" >= "),
	    EQUAL_TO(" = "),
	    NOT_EQUAL_TO(" <> "),
	    LIKE(" LIKE ");
	    
	    private final String _opStr;

	    private Op(String opStr) {
	      _opStr = opStr;
	    }

	    @Override
	    public String toString() { return _opStr; }
	  }
	  private List<String> conditions=new ArrayList<String>();
	  private List<Object> values = new ArrayList<Object>();
	  private ConditionBuilder(){};
	  public static ConditionBuilder newBuilder(){
		  return new ConditionBuilder();
	  }
	  /**
	   *  < 操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder less(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.LESS_THAN);
    		  values.add(value);
		  }
		  return this;
	  }
	  /**
	   * <=  操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder lessOrEqual(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.LESS_THAN_OR_EQUAL_TO);
    		  values.add(value);
		  }
		  return this;
	  }
	  /**
	   *  > 操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder greater(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.GREATER_THAN);
    		  values.add(value);
		  }
		  return this;
	  }
	  /**
	   *  >= 操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder greaterOrEqual(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.GREATER_THAN_OR_EQUAL_TO);
    		  values.add(value);
		  }
		  return this;
	  }
	  /**
	   *  = 操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder equal(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.EQUAL_TO);
    		  values.add(value);
		  }
		  return this;
	  }
	  /**
	   * <> 操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder notEqual(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.NOT_EQUAL_TO);
    		  values.add(value);
		  }
		  return this;
	  }
	  /**
	   * like 操作
	   * @param name
	   * @param value
	   * @return
	   * @since 3.1
	   */
	  public ConditionBuilder like(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.LIKE);
    		  values.add(value);
		  }
		  return this;
	  }
	  public ConditionBuilder fullLike(String name,Object value){
		  if(value!=null){
    		  conditions.add(name+Op.LIKE);
    		  values.add("%"+value+"%");
		  }
		  return this;
	  }
	 public  Object[] buildAsQueryArray(){
		  List<Object> conditionArray = new ArrayList<Object>();
		  if(conditions.size()==0){
			  return conditionArray.toArray();
		  }
		  Iterator<String> it = conditions.iterator();
		  StringBuilder sb = new StringBuilder();
		  while(it.hasNext()){
			  String condition = it.next();
			  sb.append(condition).append("? and ");
		  }
		  if(sb.length()>0){
			  sb.setLength(sb.length()-4);
		  }
		  
		  conditionArray.add(sb.toString());
		  conditionArray.addAll(values);
		  return conditionArray.toArray();
	  }
}
