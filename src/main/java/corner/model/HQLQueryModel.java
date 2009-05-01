/*		
 * Copyright 2008 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-4-28
 */
package corner.model;


/**
 * hibernate query model
 * @author <a href="jun.tsai@fepss.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class HQLQueryModel {
    //persist class
    private Class<?> persistClass;
    private Object condition;
    private String order;

    public HQLQueryModel(Class<?> persistClass) {
        this.persistClass = persistClass;
    }

    public HQLQueryModel(Class<?> persistClass,Object condition) {
        this.condition = condition;
        this.persistClass = persistClass;
    }
    public HQLQueryModel(Class<?> persistClass,Object condition,String order) {
        this.condition = condition;
        this.persistClass = persistClass;
        this.order=order;
    }
    public Object getCondition() {
        return condition;
    }
    public void setCondition(Object condition) {
        this.condition = condition;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }

    public Class<?> getPersistClass() {
        return persistClass;
    }
    public void setPersistClass(Class<?> persistClass) {
        this.persistClass = persistClass;
    }
}
