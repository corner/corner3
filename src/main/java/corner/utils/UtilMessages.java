package corner.utils;

import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.internal.util.MessagesImpl;
import org.apache.tapestry5.ioc.services.ClassFabUtils;

/**
 * 消息类
 * 
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 3.1
 */
public class UtilMessages {
	private static final Messages MESSAGES = MessagesImpl
			.forClass(UtilMessages.class);

	private UtilMessages() {
	}

	static String duplicateOrderer(String id) {
		return MESSAGES.format("duplicate-orderer", id);
	}

	static String constraintFormat(String constraint, String id) {
		return MESSAGES.format("constraint-format", constraint, id);
	}

	static String oneShotLock(StackTraceElement element) {
		return MESSAGES.format("one-shot-lock", element);
	}

	static String parameterWasNull(String parameterName) {
		return MESSAGES.format("parameter-was-null", parameterName);
	}

	static String parameterWasBlank(String parameterName) {
		return MESSAGES.format("parameter-was-blank", parameterName);
	}

	static String badCast(String parameterName, Object parameterValue,
			Class type) {
		return MESSAGES.format("bad-cast", parameterName, parameterValue,
				type.getName());
	}

	static String badMarkerAnnotation(Class annotationClass) {
		return MESSAGES.format("bad-marker-annotation",
				annotationClass.getName());
	}

	static String injectResourceFailure(String fieldName, Class fieldType) {
		return MESSAGES.format("inject-resource-failure", fieldName,
				ClassFabUtils.toJavaClassName(fieldType));
	}
}
