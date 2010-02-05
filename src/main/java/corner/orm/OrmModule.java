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
package corner.orm;

import java.util.Iterator;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.services.Builtin;
import org.apache.tapestry5.ioc.services.Coercion;
import org.apache.tapestry5.ioc.services.CoercionTuple;
import org.apache.tapestry5.ioc.services.TypeCoercer;

import corner.orm.model.PaginationList;
import corner.orm.model.PaginationOptions;


/**
 * orm module
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class OrmModule {

    public static void contributeTypeCoercer(Configuration<CoercionTuple> configuration,
                                             @Builtin final
                                             TypeCoercer coercer)

    {
    	//PaginationList -> PaginationOptions
    	 add(configuration, PaginationList.class, PaginationOptions.class,
                 new Coercion<PaginationList, PaginationOptions>()
                 {
                     public PaginationOptions coerce(PaginationList input)
                     {
                         return input.options();
                     }
                 });
    	 //Iterator -> Iterable
    	 add(configuration,Iterator.class,Iterable.class,
                 new Coercion<Iterator, Iterable>()
                 {
                     public Iterable coerce(final Iterator input)
                     {
                         return new Iterable(){

							@Override
							public Iterator iterator() {
								return input;
							}};
                     }
                 });
    	 //PaginationList -> Iterable
         add(configuration, PaginationList.class, Iterable.class,
                new Coercion<PaginationList, Iterable>()
                {
                    public Iterable coerce(PaginationList input)
                    {
                        return coercer.coerce(input.collectionObject(),Iterable.class);
                    }
                });
    }
    private static <S, T> void add(Configuration<CoercionTuple> configuration, Class<S> sourceType, Class<T> targetType,
                                   Coercion<S, T> coercion)
    {
        CoercionTuple<S, T> tuple = new CoercionTuple<S, T>(sourceType, targetType, coercion);

        configuration.add(tuple);
    }

}
