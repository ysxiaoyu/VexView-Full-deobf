/*
 * Decompiled with CFR 0.152.
 */
package org.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.json.JSONPointerException;
import org.json.JSONTokener;

public class JSONArray
implements Iterable<Object> {
    private final ArrayList<Object> myArrayList;

    public JSONArray() {
        this.myArrayList = new ArrayList();
    }

    public JSONArray(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '[') {
            throw jSONTokener.syntaxError("A JSONArray text must start with '['");
        }
        char c = jSONTokener.nextClean();
        if (c == '\u0000') {
            throw jSONTokener.syntaxError("Expected a ',' or ']'");
        }
        if (c != ']') {
            jSONTokener.back();
            block5: while (true) {
                if (jSONTokener.nextClean() == ',') {
                    jSONTokener.back();
                    this.myArrayList.add(JSONObject.NULL);
                } else {
                    jSONTokener.back();
                    this.myArrayList.add(jSONTokener.nextValue());
                }
                switch (jSONTokener.nextClean()) {
                    case '\u0000': {
                        throw jSONTokener.syntaxError("Expected a ',' or ']'");
                    }
                    case ',': {
                        c = jSONTokener.nextClean();
                        if (c == '\u0000') {
                            throw jSONTokener.syntaxError("Expected a ',' or ']'");
                        }
                        if (c == ']') {
                            return;
                        }
                        jSONTokener.back();
                        continue block5;
                    }
                    case ']': {
                        return;
                    }
                }
                break;
            }
            throw jSONTokener.syntaxError("Expected a ',' or ']'");
        }
    }

    public JSONArray(String string) throws JSONException {
        this(new JSONTokener(string));
    }

    public JSONArray(Collection<?> collection) {
        if (collection == null) {
            this.myArrayList = new ArrayList();
        } else {
            this.myArrayList = new ArrayList(collection.size());
            for (Object obj : collection) {
                this.myArrayList.add(JSONObject.wrap(obj));
            }
        }
    }

    public JSONArray(Object object) throws JSONException {
        this();
        if (object.getClass().isArray()) {
            int n = Array.getLength(object);
            this.myArrayList.ensureCapacity(n);
            for (int i = 0; i < n; ++i) {
                this.put(JSONObject.wrap(Array.get(object, i)));
            }
        } else {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
    }

    @Override
    public Iterator<Object> iterator() {
        return this.myArrayList.iterator();
    }

    public Object get(int n) throws JSONException {
        Object object = this.opt(n);
        if (object == null) {
            throw new JSONException("JSONArray[" + n + "] not found.");
        }
        return object;
    }

    public boolean getBoolean(int n) throws JSONException {
        Object object = this.get(n);
        if (object.equals(Boolean.FALSE) || object instanceof String && ((String)object).equalsIgnoreCase("false")) {
            return false;
        }
        if (object.equals(Boolean.TRUE) || object instanceof String && ((String)object).equalsIgnoreCase("true")) {
            return true;
        }
        throw new JSONException("JSONArray[" + n + "] is not a boolean.");
    }

    public double getDouble(int n) throws JSONException {
        Object object = this.get(n);
        try {
            return object instanceof Number ? ((Number)object).doubleValue() : Double.parseDouble((String)object);
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] is not a number.", exception);
        }
    }

    public float getFloat(int n) throws JSONException {
        Object object = this.get(n);
        try {
            return object instanceof Number ? ((Number)object).floatValue() : Float.parseFloat(object.toString());
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] is not a number.", exception);
        }
    }

    public Number getNumber(int n) throws JSONException {
        Object object = this.get(n);
        try {
            if (object instanceof Number) {
                return (Number)object;
            }
            return JSONObject.stringToNumber(object.toString());
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] is not a number.", exception);
        }
    }

    public <E extends Enum<E>> E getEnum(Class<E> clazz, int n) throws JSONException {
        E e = this.optEnum(clazz, n);
        if (e == null) {
            throw new JSONException("JSONArray[" + n + "] is not an enum of type " + JSONObject.quote(clazz.getSimpleName()) + ".");
        }
        return e;
    }

    public BigDecimal getBigDecimal(int n) throws JSONException {
        Object object = this.get(n);
        try {
            return new BigDecimal(object.toString());
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] could not convert to BigDecimal.", exception);
        }
    }

    public BigInteger getBigInteger(int n) throws JSONException {
        Object object = this.get(n);
        try {
            return new BigInteger(object.toString());
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] could not convert to BigInteger.", exception);
        }
    }

    public int getInt(int n) throws JSONException {
        Object object = this.get(n);
        try {
            return object instanceof Number ? ((Number)object).intValue() : Integer.parseInt((String)object);
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] is not a number.", exception);
        }
    }

    public JSONArray getJSONArray(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        throw new JSONException("JSONArray[" + n + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        throw new JSONException("JSONArray[" + n + "] is not a JSONObject.");
    }

    public long getLong(int n) throws JSONException {
        Object object = this.get(n);
        try {
            return object instanceof Number ? ((Number)object).longValue() : Long.parseLong((String)object);
        }
        catch (Exception exception) {
            throw new JSONException("JSONArray[" + n + "] is not a number.", exception);
        }
    }

    public String getString(int n) throws JSONException {
        Object object = this.get(n);
        if (object instanceof String) {
            return (String)object;
        }
        throw new JSONException("JSONArray[" + n + "] not a string.");
    }

    public boolean isNull(int n) {
        return JSONObject.NULL.equals(this.opt(n));
    }

    public String join(String string) throws JSONException {
        int n = this.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append(string);
            }
            stringBuilder.append(JSONObject.valueToString(this.myArrayList.get(i)));
        }
        return stringBuilder.toString();
    }

    public int length() {
        return this.myArrayList.size();
    }

    public Object opt(int n) {
        return n < 0 || n >= this.length() ? null : this.myArrayList.get(n);
    }

    public boolean optBoolean(int n) {
        return this.optBoolean(n, false);
    }

    public boolean optBoolean(int n, boolean bl) {
        try {
            return this.getBoolean(n);
        }
        catch (Exception exception) {
            return bl;
        }
    }

    public double optDouble(int n) {
        return this.optDouble(n, Double.NaN);
    }

    public double optDouble(int n, double d) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return d;
        }
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        if (object instanceof String) {
            try {
                return Double.parseDouble((String)object);
            }
            catch (Exception exception) {
                return d;
            }
        }
        return d;
    }

    public float optFloat(int n) {
        return this.optFloat(n, Float.NaN);
    }

    public float optFloat(int n, float f) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return f;
        }
        if (object instanceof Number) {
            return ((Number)object).floatValue();
        }
        if (object instanceof String) {
            try {
                return Float.parseFloat((String)object);
            }
            catch (Exception exception) {
                return f;
            }
        }
        return f;
    }

    public int optInt(int n) {
        return this.optInt(n, 0);
    }

    public int optInt(int n, int n2) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return n2;
        }
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        if (object instanceof String) {
            try {
                return new BigDecimal(object.toString()).intValue();
            }
            catch (Exception exception) {
                return n2;
            }
        }
        return n2;
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, int n) {
        return this.optEnum(clazz, n, null);
    }

    public <E extends Enum<E>> E optEnum(Class<E> clazz, int n, E e) {
        try {
            Object object = this.opt(n);
            if (JSONObject.NULL.equals(object)) {
                return e;
            }
            if (clazz.isAssignableFrom(object.getClass())) {
                Enum enum_ = (Enum)object;
                return (E)enum_;
            }
            return Enum.valueOf(clazz, object.toString());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return e;
        }
        catch (NullPointerException nullPointerException) {
            return e;
        }
    }

    public BigInteger optBigInteger(int n, BigInteger bigInteger) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return bigInteger;
        }
        if (object instanceof BigInteger) {
            return (BigInteger)object;
        }
        if (object instanceof BigDecimal) {
            return ((BigDecimal)object).toBigInteger();
        }
        if (object instanceof Double || object instanceof Float) {
            return new BigDecimal(((Number)object).doubleValue()).toBigInteger();
        }
        if (object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte) {
            return BigInteger.valueOf(((Number)object).longValue());
        }
        try {
            String string = object.toString();
            if (JSONObject.isDecimalNotation(string)) {
                return new BigDecimal(string).toBigInteger();
            }
            return new BigInteger(string);
        }
        catch (Exception exception) {
            return bigInteger;
        }
    }

    public BigDecimal optBigDecimal(int n, BigDecimal bigDecimal) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return bigDecimal;
        }
        if (object instanceof BigDecimal) {
            return (BigDecimal)object;
        }
        if (object instanceof BigInteger) {
            return new BigDecimal((BigInteger)object);
        }
        if (object instanceof Double || object instanceof Float) {
            return new BigDecimal(((Number)object).doubleValue());
        }
        if (object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte) {
            return new BigDecimal(((Number)object).longValue());
        }
        try {
            return new BigDecimal(object.toString());
        }
        catch (Exception exception) {
            return bigDecimal;
        }
    }

    public JSONArray optJSONArray(int n) {
        Object object = this.opt(n);
        return object instanceof JSONArray ? (JSONArray)object : null;
    }

    public JSONObject optJSONObject(int n) {
        Object object = this.opt(n);
        return object instanceof JSONObject ? (JSONObject)object : null;
    }

    public long optLong(int n) {
        return this.optLong(n, 0L);
    }

    public long optLong(int n, long l) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return l;
        }
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        if (object instanceof String) {
            try {
                return new BigDecimal(object.toString()).longValue();
            }
            catch (Exception exception) {
                return l;
            }
        }
        return l;
    }

    public Number optNumber(int n) {
        return this.optNumber(n, null);
    }

    public Number optNumber(int n, Number number) {
        Object object = this.opt(n);
        if (JSONObject.NULL.equals(object)) {
            return number;
        }
        if (object instanceof Number) {
            return (Number)object;
        }
        if (object instanceof String) {
            try {
                return JSONObject.stringToNumber((String)object);
            }
            catch (Exception exception) {
                return number;
            }
        }
        return number;
    }

    public String optString(int n) {
        return this.optString(n, "");
    }

    public String optString(int n, String string) {
        Object object = this.opt(n);
        return JSONObject.NULL.equals(object) ? string : object.toString();
    }

    public JSONArray put(boolean bl) {
        return this.put(bl ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(Collection<?> collection) {
        return this.put(new JSONArray(collection));
    }

    public JSONArray put(double d) throws JSONException {
        return this.put((Object)d);
    }

    public JSONArray put(float f) throws JSONException {
        return this.put(Float.valueOf(f));
    }

    public JSONArray put(int n) {
        return this.put((Object)n);
    }

    public JSONArray put(long l) {
        return this.put((Object)l);
    }

    public JSONArray put(Map<?, ?> map) {
        return this.put(new JSONObject(map));
    }

    public JSONArray put(Object object) {
        JSONObject.testValidity(object);
        this.myArrayList.add(object);
        return this;
    }

    public JSONArray put(int n, boolean bl) throws JSONException {
        return this.put(n, bl ? Boolean.TRUE : Boolean.FALSE);
    }

    public JSONArray put(int n, Collection<?> collection) throws JSONException {
        return this.put(n, new JSONArray(collection));
    }

    public JSONArray put(int n, double d) throws JSONException {
        return this.put(n, (Object)d);
    }

    public JSONArray put(int n, float f) throws JSONException {
        return this.put(n, Float.valueOf(f));
    }

    public JSONArray put(int n, int n2) throws JSONException {
        return this.put(n, (Object)n2);
    }

    public JSONArray put(int n, long l) throws JSONException {
        return this.put(n, (Object)l);
    }

    public JSONArray put(int n, Map<?, ?> map) throws JSONException {
        this.put(n, new JSONObject(map));
        return this;
    }

    public JSONArray put(int n, Object object) throws JSONException {
        if (n < 0) {
            throw new JSONException("JSONArray[" + n + "] not found.");
        }
        if (n < this.length()) {
            JSONObject.testValidity(object);
            this.myArrayList.set(n, object);
            return this;
        }
        if (n == this.length()) {
            return this.put(object);
        }
        this.myArrayList.ensureCapacity(n + 1);
        while (n != this.length()) {
            this.myArrayList.add(JSONObject.NULL);
        }
        return this.put(object);
    }

    public Object query(String string) {
        return this.query(new JSONPointer(string));
    }

    public Object query(JSONPointer jSONPointer) {
        return jSONPointer.queryFrom(this);
    }

    public Object optQuery(String string) {
        return this.optQuery(new JSONPointer(string));
    }

    public Object optQuery(JSONPointer jSONPointer) {
        try {
            return jSONPointer.queryFrom(this);
        }
        catch (JSONPointerException jSONPointerException) {
            return null;
        }
    }

    public Object remove(int n) {
        return n >= 0 && n < this.length() ? this.myArrayList.remove(n) : null;
    }

    public boolean similar(Object object) {
        if (!(object instanceof JSONArray)) {
            return false;
        }
        int n = this.length();
        if (n != ((JSONArray)object).length()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            Object object2;
            Object object3 = this.myArrayList.get(i);
            if (object3 == (object2 = ((JSONArray)object).myArrayList.get(i))) continue;
            if (object3 == null) {
                return false;
            }
            if (!(object3 instanceof JSONObject ? !((JSONObject)object3).similar(object2) : (object3 instanceof JSONArray ? !((JSONArray)object3).similar(object2) : !object3.equals(object2)))) continue;
            return false;
        }
        return true;
    }

    public JSONObject toJSONObject(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.isEmpty() || this.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(jSONArray.length());
        for (int i = 0; i < jSONArray.length(); ++i) {
            jSONObject.put(jSONArray.getString(i), this.opt(i));
        }
        return jSONObject;
    }

    public String toString() {
        try {
            return this.toString(0);
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString(int n) throws JSONException {
        StringWriter stringWriter = new StringWriter();
        StringBuffer stringBuffer = stringWriter.getBuffer();
        synchronized (stringBuffer) {
            return this.write(stringWriter, n, 0).toString();
        }
    }

    public Writer write(Writer writer) throws JSONException {
        return this.write(writer, 0, 0);
    }

    public Writer write(Writer writer, int n, int n2) throws JSONException {
        try {
            boolean bl = false;
            int n3 = this.length();
            writer.write(91);
            if (n3 == 1) {
                try {
                    JSONObject.writeValue(writer, this.myArrayList.get(0), n, n2);
                }
                catch (Exception exception) {
                    throw new JSONException("Unable to write JSONArray value at index: 0", exception);
                }
            }
            else if (n3 != 0) {
                int n4 = n2 + n;
                for (int i = 0; i < n3; ++i) {
                    if (bl) {
                        writer.write(44);
                    }
                    if (n > 0) {
                        writer.write(10);
                    }
                    JSONObject.indent(writer, n4);
                    try {
                        JSONObject.writeValue(writer, this.myArrayList.get(i), n, n4);
                    }
                    catch (Exception exception) {
                        throw new JSONException("Unable to write JSONArray value at index: " + i, exception);
                    }
                    bl = true;
                }
                if (n > 0) {
                    writer.write(10);
                }
                JSONObject.indent(writer, n2);
            }
            writer.write(93);
            return writer;
        }
        catch (IOException iOException) {
            throw new JSONException(iOException);
        }
    }

    public List<Object> toList() {
        ArrayList<Object> arrayList = new ArrayList<Object>(this.myArrayList.size());
        for (Object object : this.myArrayList) {
            if (object == null || JSONObject.NULL.equals(object)) {
                arrayList.add(null);
                continue;
            }
            if (object instanceof JSONArray) {
                arrayList.add(((JSONArray)object).toList());
                continue;
            }
            if (object instanceof JSONObject) {
                arrayList.add(((JSONObject)object).toMap());
                continue;
            }
            arrayList.add(object);
        }
        return arrayList;
    }

    public boolean isEmpty() {
        return this.myArrayList.isEmpty();
    }
}

