/* 
 * Copyright 2009 The Corner Team.
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
package corner.transaction.services.impl;

import org.apache.tapestry5.ioc.internal.util.Defense;
import org.apache.tapestry5.ioc.services.AspectDecorator;
import org.apache.tapestry5.ioc.services.AspectInterceptorBuilder;

import corner.transaction.services.TransactionAdvisor;
import corner.transaction.services.TransactionDecorator;

/**
 * JPA Transaction decorder Implemention
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class TransactionDecoratorImpl implements TransactionDecorator {
	private final AspectDecorator aspectDecorator;

    private final TransactionAdvisor advisor;

    public TransactionDecoratorImpl(AspectDecorator aspectDecorator, TransactionAdvisor advisor)
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
