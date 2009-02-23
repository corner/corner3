/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id$
 * created at:2008-12-6
 */
package corner;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Set;

import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

/**
 * 自定义序列化的JSONObject,对org.apache.tapestry5.json.JSONObject的代理,T5的JSONObject是不可序列化的.
 * 适用于需要序列化的地方.
 * 
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class SeriableJSONObject implements java.io.Externalizable {
	private static final long serialVersionUID = 1L;
	private JSONObject json;

	public SeriableJSONObject() {
		json = new JSONObject();
	}

	public SeriableJSONObject(String string) {
		json = new JSONObject(string);
	}

	public JSONObject accumulate(String key, Object value) {
		return json.accumulate(key, value);
	}

	public JSONObject append(String key, Object value) {
		return json.append(key, value);
	}

	@Override
	public boolean equals(Object obj) {
		 if (obj == null) return false;

        if (!(obj instanceof SeriableJSONObject)) return false;

        SeriableJSONObject other = (SeriableJSONObject) obj;
	        
		return json.equals(other.getProxyJSONObject());
	}

	public Object get(String key) {
		return json.get(key);
	}
	public JSONObject getProxyJSONObject(){
		return json;
	}

	public boolean getBoolean(String key) {
		return json.getBoolean(key);
	}

	public double getDouble(String key) {
		return json.getDouble(key);
	}

	public int getInt(String key) {
		return json.getInt(key);
	}

	public JSONArray getJSONArray(String key) {
		return json.getJSONArray(key);
	}

	public JSONObject getJSONObject(String key) {
		return json.getJSONObject(key);
	}

	public long getLong(String key) {
		return json.getLong(key);
	}

	public String getString(String key) {
		return json.getString(key);
	}

	public boolean has(String key) {
		return json.has(key);
	}

	@Override
	public int hashCode() {
		return json.hashCode();
	}

	public boolean isNull(String key) {
		return json.isNull(key);
	}

	public Set<String> keys() {
		return json.keys();
	}

	public int length() {
		return json.length();
	}

	public JSONArray names() {
		return json.names();
	}

	public Object opt(String key) {
		return json.opt(key);
	}

	public JSONObject put(String key, Object value) {
		return json.put(key, value);
	}

	public Object remove(String key) {
		return json.remove(key);
	}

	@Override
	public String toString() {
		return json.toString();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		String _value = in.readUTF();
		if (_value != null) {
			this.json = new JSONObject(_value);
		}
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		String _value = this.json.toString();
		out.writeUTF(_value);
	}
}