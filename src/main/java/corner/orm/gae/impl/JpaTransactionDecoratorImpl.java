/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id: JpaTransactionDecoratorImpl.java 5915 2009-09-22 04:56:54Z jcai $
 * created at:2009-09-22
 */

package corner.orm.gae.impl;

import org.apache.tapestry5.ioc.internal.util.Defense;
import org.apache.tapestry5.ioc.services.AspectDecorator;
import org.apache.tapestry5.ioc.services.AspectInterceptorBuilder;

import corner.transaction.services.TransactionAdvisor;
import corner.transaction.services.TransactionDecorator;

/**
 * JPA Transaction decorder Implemention
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision: 5915 $
 * @since 0.1
 */
public class JpaTransactionDecoratorImpl implements TransactionDecorator {
	private final AspectDecorator aspectDecorator;

    private final TransactionAdvisor advisor;

    public JpaTransactionDecoratorImpl(AspectDecorator aspectDecorator, TransactionAdvisor advisor)
    {
        this.aspectDecorator = aspectDecorator;
        this.advisor = advisor;
    }

    /**
	 * @see lichen.common.services.jpa.impl.JpaTransactionDecorder#build(java.lang.Class, T, java.lang.String)
	 */
    public <T> T build(Class<T> serviceInterface, T delegate, String serviceId)
    {
        Defense.notNull(serviceInterface, "serviceInterface");
        Defense.notNull(delegate, "delegate");
        Defense.notBlank(serviceId, "serviceId");

        String description = String.format("<JPA Transaction interceptor for %s(%s)>",
                                           serviceId,
                                           serviceInterface.getName());

        AspectInterceptorBuilder<T> builder = aspectDecorator.createBuilder(serviceInterface, delegate, description);

        advisor.addTransactionCommitAdvice(builder);

        return builder.build();
    }
}
