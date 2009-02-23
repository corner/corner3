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
package corner.base;

import java.io.Serializable;

import org.apache.tapestry5.Binding;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentAction;
import org.apache.tapestry5.Field;
import org.apache.tapestry5.FieldValidator;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.annotations.Mixin;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.mixins.DiscardBody;
import org.apache.tapestry5.corelib.mixins.RenderDisabled;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;

/**
 * Ajax组件的抽象类实现,使用T5COnEvents.js完成Ajax的请求
 * @author <a href="mailto:shomburg@hsofttec.com">S.Homburg</a>
 * @author dong
 * @version $Id: AbstractAjaxField.java 2945 2008-11-11 05:01:15Z jcai $
 */
abstract public class AbstractAjaxField extends AbstractAjaxEvent implements
		Field {
	/**
	 * The user presentable label for the field. If not provided, a reasonable
	 * label is generated from the component's id, first by looking for a
	 * message key named "id-label" (substituting the component's actual id),
	 * then by converting the actual id to a presentable string (for example,
	 * "userId" to "User Id").
	 */
	@Parameter(defaultPrefix = "literal")
	private String _label;

	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String eventName;

	/**
	 * If true, then the field will render out with a disabled attribute (to
	 * turn off client-side behavior). Further, a disabled field ignores any
	 * value in the request when the form is submitted.
	 */
	@Parameter("false")
	private boolean _disabled;

	@Parameter(value = "prop:componentResources.id", defaultPrefix = "literal")
	private String _clientId;

	@SuppressWarnings("unused")
	@Mixin
	private RenderDisabled _renderDisabled;

	@SuppressWarnings("unused")
	@Mixin
	private DiscardBody _discardBody;



	@Inject
	private ComponentDefaultProvider _defaultProvider;

	private String _assignedClientId;

	private String _controlName;

	protected static final FieldValidator<Object> NOOP_VALIDATOR = new FieldValidator<Object>() {
		public void validate(Object value) throws ValidationException {
			// Do nothing
		}

		public void render(MarkupWriter writer) {
		}

		public boolean isRequired() {
			return false;
		}
	};

	static class SetupAction implements ComponentAction<AbstractAjaxField>,
			Serializable {
		private static final long serialVersionUID = 2690270808212097020L;

		private final String _elementName;

		public SetupAction(final String elementName) {
			_elementName = elementName;
		}

		public void execute(AbstractAjaxField component) {
			component.setupControlName(_elementName);
		}
	}

	final String defaultLabel() {
		return _defaultProvider.defaultLabel(this.getResources());
	}

	public final String getLabel() {
		return _label;
	}

	@SetupRender
	final void setup() {
		String id = _clientId;
		_assignedClientId = this.getPageRenderSupport().allocateClientId(id);
	}

	@Override
	public final String getClientId() {
		return _assignedClientId;
	}

	/**
	 * Returns the value used as the name attribute of the rendered element.
	 * This value will be unique within an enclosing form, even if the same
	 * component renders multiple times.
	 * 
	 * @see org.apache.tapestry5.services.FormSupport#allocateControlName(String)
	 */
	public String getControlName() {
		return _controlName;
	}

	public final boolean isDisabled() {
		return _disabled;
	}

	/**
	 * Invoked from within a ComponentCommand callback, to restore the
	 * component's elementName.
	 */
	private void setupControlName(String controlName) {
		_controlName = controlName;
	}

	/**
	 * Used by subclasses to create a default binding to a property of the
	 * container matching the component id.
	 * 
	 * @return a binding to the property, or null if the container does not have
	 *         a corresponding property
	 */
	protected final Binding createDefaultParameterBinding(String parameterName) {
		return _defaultProvider.defaultBinding(parameterName, this
				.getResources());
	}

	/**
	 * Returns false; most components do not support declarative validation.
	 */
	public boolean isRequired() {
		return false;
	}

	@Override
	public String getEventName() {
		return this.eventName;
	}

}