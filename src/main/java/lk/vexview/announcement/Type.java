/*
 * Deobfuscated from the shipped VexView 2.7.3 jar.
 *
 * In the original binary this was an obfuscated enum whose constants had been
 * renamed to s1/s3/s4 while keeping the logical names WARN/NORMAL/DOWNLOAD in
 * the class initialiser. Re-expressed as a real enum: the declaration order is
 * exactly the original ordinal order (WARN=0, NORMAL=1, DOWNLOAD=2) and the old
 * obfuscated field names survive as aliases so every call site stays untouched.
 */
package lk.vexview.announcement;

public enum Type {
    WARN,
    NORMAL,
    DOWNLOAD;

    /** original obfuscated name of {@link #WARN} */
    public static final Type s3 = WARN;
    /** original obfuscated name of {@link #NORMAL} */
    public static final Type s1 = NORMAL;
    /** original obfuscated name of {@link #DOWNLOAD} */
    public static final Type s4 = DOWNLOAD;
}
