/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: PaginatedJapEntityService.java 6101 2009-10-19 04:20:03Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import java.util.Iterator;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;

import corner.orm.EntityConstants;
import corner.orm.hibernate.impl.PaginatedEntityService;
import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;

/**
 * paginated jpa entity service
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 6101 $
 * @since 0.1
 */
public class PaginatedJapEntityService {
	 private Logger logger = LoggerFactory.getLogger(PaginatedEntityService.class);
	    private TypeCoercer typeCoercer;
	    private JpaTemplate template;

	    public PaginatedJapEntityService(final  JpaTemplate template,final TypeCoercer typeCoercer){
	        this.template =template;
	        this.typeCoercer = typeCoercer;
	    }

	    /**
	     * 
	     * magic paginate method.
	     * eg:
	     * <code>
	     *  options.setPage(2);
	     *
	     *
	     *  paginate(Member.class,new Object[]{"email=?","asdf@asdf.net"},"userName desc",options)

	     *  paginate(Member.class,"email='asdf@asdf.net'","userName desc",options)
	     *
	     *  List conditions = new ArrayList();
	     *  conditions.add("userName=? and password=?");
	     *  conditions.add(userName);
	     *  conditions.add(password);
	     *  paginate(Member.class,conditions,"userName desc",options)
	     * 
	     * </code>
	     * Magic conditions query criteria
	     * @param persistClass persistence class
	     * @param conditions query criteria
	     * @param order order by sql
	     * @param options pagination options.
	     * @return include result and totalRecord.
	     */
	    public PaginationList paginate(final Class<?> persistClass,final Object conditions,
	                                   final String order,final PaginationOptions options){
	        final Iterable con = typeCoercer.coerce(conditions, Iterable.class);

	        return (PaginationList) this.template.execute(new JpaCallback() {
				@Override
				public Object doInJpa(EntityManager entityManager)
						throws PersistenceException {
					
	                final Iterator it = con==null?null:con.iterator();

	                String conditionJPQL = buildConditionJPQL(persistClass, it).toString();

	                //query list
	                final StringBuffer queryJPQL= new StringBuffer(conditionJPQL);
	                appendOrder(queryJPQL, order);
	                queryJPQL.insert(0, "select root."+EntityConstants.ID_PROPERTY_NAME);
	                Query query = entityManager.createQuery(queryJPQL.toString());

	                //count query
	                final StringBuffer countJPQL= new StringBuffer(conditionJPQL);
	                countJPQL.insert(0,"select count(root) ");
	                Query countQuery = entityManager.createQuery(countJPQL.toString());
	                
	                if (it != null) {
	                    int i = 0;
	                    while (it.hasNext()) {
	                    	i++;
	                        Object obj = it.next();
	                        query.setParameter(String.valueOf(i), obj);
	                        countQuery.setParameter(String.valueOf(i),obj);
	                    }
	                }
	                //get perpage
	                int perPage = options.getPerPage();
	                int page = options.getPage();
	                if(page<1){
	                    page =1;
	                }
	                query.setFirstResult((page-1)*perPage);
	                query.setMaxResults(perPage);
	                PaginationList list = new PaginationList(query.getResultList().iterator(),options);

	                //query total record number
	                //beacause jpa rowCount is integer type.so convert as long
	                options.setTotalRecord(Long.parseLong(countQuery.getSingleResult().toString()));
	                return list;
				}
	        });
	    }
	    public Iterator find(final Class<?> persistClass,final Object conditions,final String order){
	    	return find(persistClass,conditions,order,0,Integer.MAX_VALUE);
		}

	    public Iterator find(final Class<?> persistClass,final Object conditions,final String order,final int start,final int offset){
		       return (Iterator) this.template.execute(new JpaCallback(){
				@Override
				public Object doInJpa(EntityManager entityManager)
						throws PersistenceException {
		               Iterable con = typeCoercer.coerce(conditions, Iterable.class);
		               final Iterator it = con==null?null:con.iterator();
		               final StringBuffer sb = buildConditionJPQL(persistClass, it);
		               appendOrder(sb, order);
		               sb.insert(0, "select root."+EntityConstants.ID_PROPERTY_NAME);
		               Query query = entityManager.createQuery(sb.toString());
		               if(it!=null){
		                  int i=0;
		                  while(it.hasNext()) {
		                      query.setParameter(String.valueOf(++i),it.next());
		                  }
		               }
		               query.setFirstResult(start);
		               query.setMaxResults(offset);
		               return query.getResultList().iterator();
				}
		       });
		    }

	    void appendOrder(StringBuffer sb, String order) {
	        if(order!=null){
	            if(!Pattern.compile("^order\\s+").matcher(order.trim()).find()){ //no order keywords
	                sb.append(" order by");
	            }
	            sb.append(" ").append(order.trim());
	        }
	    }

	    public long count(final Class<?> persistClass,final Object conditions){
	        return (Long) this.template.execute(new JpaCallback(){
				@Override
				public Object doInJpa(EntityManager entityManager)
						throws PersistenceException {
	                Iterable con = typeCoercer.coerce(conditions, Iterable.class);
	                final Iterator it = con==null?null:con.iterator();
	                final StringBuffer sb = buildConditionJPQL(persistClass, it);
	                sb.insert(0,"select count(root) ");
	                Query query = entityManager.createQuery(sb.toString());
	                if(it!=null){
	                    int i=0;
	                    while(it.hasNext()) {
	                        query.setParameter(i++,it.next());
	                    }
	                }
	                return Long.parseLong(query.getSingleResult().toString());
				}
	        });
	    }

	    private StringBuffer buildConditionJPQL(Class<?> persistClass, Iterator conditions) {
	        final StringBuffer sb = new StringBuffer();
	        sb.append(" from ").append(persistClass.getName()).append(" as root ");
	        if(conditions !=null&& conditions.hasNext()){
	            String where = String.valueOf(conditions.next()).trim();
	            
	            if(!Pattern.compile("^where\\s+").matcher(where).find()){//no where keywords
	                sb.append(" where");
	            }
	            sb.append(" ").append(where);
	        }
	        if(logger.isDebugEnabled()){
	            logger.debug("construct JPQL:["+sb.toString()+"]");
	        }
	        return sb;
	    }
}
