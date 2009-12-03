/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-5-14
 */
package corner.transaction;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.ioc.MethodAdvice;
import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 使用Spring的事物管理机制
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class SpringTransactionAdvisor implements HibernateTransactionAdvisor {
	 /**
     * The rules for advice are the same for any method: commit on success or checked exception, abort on thrown
     * exception ... so we can use a single shared advice object.
     */
   private class SpringTransactionMethodAdvice implements MethodAdvice
    {
		private Method method;

		public SpringTransactionMethodAdvice(Method m) {
        	this.method = m;
		}

		public void advise(final Invocation invocation)
        {
			try {
				//使用Spring的事物处理机制进行处理
				transactionInterceptor.invoke(new MethodInvocation(){

					@Override
					public Method getMethod() {
						return method;
					}

					@Override
					public Object[] getArguments() {
						throw new UnsupportedOperationException();
					}

					@Override
					public AccessibleObject getStaticPart() {
						throw new UnsupportedOperationException();
					}

					@Override
					public Object getThis() {
						return null;
					}

					@Override
					public Object proceed() throws Throwable {
						invocation.proceed();
						return invocation.getResult();
					}});
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
        }
    };
	private TransactionInterceptor transactionInterceptor;
    public SpringTransactionAdvisor(TransactionInterceptor transactionInterceptor){
    	this.transactionInterceptor = transactionInterceptor;
    }
	/**
	 * @see org.apache.tapestry5.hibernate.HibernateTransactionAdvisor#addTransactionCommitAdvice(org.apache.tapestry5.ioc.MethodAdviceReceiver)
	 */
	@Override
	public void addTransactionCommitAdvice(MethodAdviceReceiver receiver) {
		 for (Method m : receiver.getInterface().getMethods())
	        {
	            if (m.getAnnotation(Transactional.class) != null)
	            {
	                receiver.adviseMethod(m, new SpringTransactionMethodAdvice(m));
	            }
	        }
	}

}
