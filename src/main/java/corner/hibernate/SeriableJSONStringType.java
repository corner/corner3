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
package corner.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.type.Type;
import org.hibernate.usertype.UserType;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 实现SeriableJSONObject字符串作为一个Hibernate的类型存入数据库
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @author <a href="d0ng@ganshane.net">d0ng</a>
 * @version $Revision$
 * @since 0.0.2
 */

public class SeriableJSONStringType implements UserType {

	private static final boolean IS_VALUE_TRACING_ENABLED = LoggerFactory
			.getLogger(StringHelper.qualifier(Type.class.getName()))
			.isTraceEnabled();
	private transient Logger log;

	private Logger log() {
		if (log == null) {
			log = LoggerFactory.getLogger(getClass());
		}
		return log;
	}

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	public Class<?> returnedClass() {
		return SeriableJSONObject.class;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y) || (x != null && x.equals(y));
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public final Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		String name = names[0];
		try {
			Object value = rs.getString(name);
			if (value == null || rs.wasNull()) {
				if (IS_VALUE_TRACING_ENABLED) {
					log().trace("returning null as column: " + name);
				}
				return null;
			} else {
				if (IS_VALUE_TRACING_ENABLED) {
					log().trace("returning '" + value + "' as column: " + name);
				}
				return new SeriableJSONObject((String) value);
			}
		} catch (RuntimeException re) {
			log().info(
					"could not read column value from result set: " + name
							+ "; " + re.getMessage());
			throw re;
		} catch (SQLException se) {
			log().info(
					"could not read column value from result set: " + name
							+ "; " + se.getMessage());
			throw se;
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if (value != null) {
			st.setString(index, value.toString());
		} else {
			st.setNull(index, sqlTypes()[0]);
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		if (value != null) {
			return new SeriableJSONObject(value.toString());
		}
		return value;
	}

	public boolean isMutable() {
		return true;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

}
