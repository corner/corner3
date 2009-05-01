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
package corner.bindings;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.BindingFactory;

/**
 * 针对格式化的模块
 * @author Jun Tsai
 * @version $Revision$
 * @since 0.0.2
 */
public class FormatterModule {
	/**
	 * 绑定使用的service.
	 * 
	 * @param binder
	 *            Service Binder
	 * @see ServiceBinder
	 */
	public static void bind(ServiceBinder binder) {
		binder.bind(FormatterSource.class, FormatterSourceImpl.class);
		binder.bind(BindingFactory.class,FormatBindingFactory.class).withId("FormatBindingFactory");
	} 

	// 针对日期的format
	public static Format buildDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

    // 针对日期的format
    public static Format buildLongDateFormat() {
        return new Format(){
            /**
			 * 
			 */
			private static final long serialVersionUID = 2187679797061508592L;
			private Format delegateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            @Override
			public StringBuffer format(Object o, StringBuffer stringBuffer, FieldPosition fieldPosition) {
                if(o.getClass().isAssignableFrom(Long.class)){
                    return delegateFormatter.format(new Date((Long) o),stringBuffer,fieldPosition);
                }
                throw new RuntimeException("only format long type date object");
            }

            @Override
			public Object parseObject(String s, ParsePosition parsePosition) {
                return Long.parseLong(s,parsePosition.getIndex());
            }
        };
    }
	// 针对时间和日期的format
	public static Format buildDateTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	// 针对货币的format
	public static Format buildCurrencyFormat() {
		return new DecimalFormat("0.00");
	}

	
	public static void contributeFormatterSource(
			MappedConfiguration<String, Format> configuration,
			@InjectService("DateFormat")
			Format dateFormat,
            @InjectService("LongDateFormat")
            Format longDateFormat,
            @InjectService("DateTimeFormat")
			Format dateTimeFormat,@InjectService("CurrencyFormat") Format currencyFormat) {
		configuration.add(FormatterConstants.DATE, dateFormat);
        configuration.add(FormatterConstants.LONG_DATE,longDateFormat);
		configuration.add(FormatterConstants.DATE_TIME, dateTimeFormat);
		configuration.add(FormatterConstants.CURRENCY, currencyFormat);
	}

	public static void contributeBindingSource(
			MappedConfiguration<String, BindingFactory> configuration,
			@InjectService("FormatBindingFactory")
			BindingFactory factory) {
		configuration.add("format", factory);
	}
}
