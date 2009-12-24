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
package corner.orm.hibernate.impl;

import org.apache.tapestry5.hibernate.HibernateTransactionAdvisor;
import org.apache.tapestry5.internal.hibernate.HibernateTransactionDecoratorImpl;
import org.apache.tapestry5.ioc.services.AspectDecorator;

import corner.transaction.services.TransactionDecorator;

/**
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class TapestryHibernateTransactionDecorterImpl extends HibernateTransactionDecoratorImpl implements TransactionDecorator{

	public TapestryHibernateTransactionDecorterImpl(
			AspectDecorator aspectDecorator, HibernateTransactionAdvisor advisor) {
		super(aspectDecorator, advisor);
	}
}
