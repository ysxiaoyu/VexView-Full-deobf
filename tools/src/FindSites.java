import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Precisely locates every obfuscator string-decryption call site.
 *
 * The ciphertext is not always the instruction directly before the call (the
 * obfuscator interleaves unrelated pushes), so this tool runs a dataflow pass
 * (ASM SourceInterpreter) and asks which instruction produced the value that is
 * actually consumed by the call.
 *
 * Output: one JSON record per site {class, method, enc, plain}.
 * Decryption itself is done by the real decryptor bytecode (see DecryptRunner).
 */
public final class FindSites {

    static final String DEC_NAME = "pcdlk,pcdlona";
    static final String DEC_DESC = "(Ljava/lang/String;)Ljava/lang/String;";

    public static void main(String[] a) throws Exception {
        File in = new File(a[0]);
        File out = new File(a[1]);
        Map<String, byte[]> entries = readJar(in);
        List<String[]> sites = new ArrayList<String[]>();
        int failed = 0;

        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            try {
                ClassNode cn = new ClassNode();
                new ClassReader(e.getValue()).accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                for (Object o : cn.methods) {
                    MethodNode mn = (MethodNode) o;
                    if (mn.instructions.size() == 0) continue;
                    Frame<SourceValue>[] frames;
                    try {
                        Analyzer<SourceValue> an = new Analyzer<SourceValue>(new SourceInterpreter());
                        an.analyze(cn.name, mn);
                        frames = an.getFrames();
                    } catch (Throwable t) {
                        failed++;
                        continue;
                    }
                    AbstractInsnNode[] insns = mn.instructions.toArray();
                    for (int i = 0; i < insns.length; i++) {
                        AbstractInsnNode insn = insns[i];
                        if (insn.getOpcode() != Opcodes.INVOKESTATIC) continue;
                        MethodInsnNode mi = (MethodInsnNode) insn;
                        if (!DEC_NAME.equals(mi.name) || !DEC_DESC.equals(mi.desc)) continue;
                        Frame<SourceValue> f = (i < frames.length) ? frames[i] : null;
                        if (f == null || f.getStackSize() < 1) continue;
                        SourceValue sv = f.getStack(f.getStackSize() - 1);
                        String enc = null;
                        for (AbstractInsnNode producer : sv.insns) {
                            if (producer instanceof LdcInsnNode) {
                                Object cst = ((LdcInsnNode) producer).cst;
                                if (cst instanceof String) { enc = (String) cst; break; }
                            }
                        }
                        if (enc == null) continue;
                        sites.add(new String[]{cn.name, mn.name, mn.desc, enc, mi.owner});
                    }
                }
            } catch (Throwable t) {
                failed++;
            }
        }

        StringBuilder sb = new StringBuilder("[\n");
        boolean first = true;
        for (String[] s : sites) {
            if (!first) sb.append(",\n");
            first = false;
            sb.append("{\"class\":\"").append(esc(s[0])).append("\",\"method\":\"").append(esc(s[1]))
              .append("\",\"desc\":\"").append(esc(s[2])).append("\",\"enc\":\"").append(esc(s[3])).append("\",\"owner\":\"").append(esc(s[4])).append("\"}");
        }
        sb.append("\n]\n");
        Files.write(out.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("sites found: " + sites.size() + " methodsAnalysedFailed=" + failed);
    }

    static String esc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '"') sb.append("\\\"");
            else if (ch == '\\') sb.append("\\\\");
            else if (ch == '\n') sb.append("\\n");
            else if (ch == '\r') sb.append("\\r");
            else if (ch == '\t') sb.append("\\t");
            else if (ch < 0x20 || ch > 0x7e) sb.append(String.format("\\u%04x", (int) ch));
            else sb.append(ch);
        }
        return sb.toString();
    }

    static Map<String, byte[]> readJar(File f) throws IOException {
        Map<String, byte[]> out = new LinkedHashMap<String, byte[]>();
        ZipInputStream z = new ZipInputStream(new BufferedInputStream(new FileInputStream(f)));
        ZipEntry e;
        while ((e = z.getNextEntry()) != null) {
            if (e.isDirectory()) continue;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int r;
            while ((r = z.read(buf)) > 0) bo.write(buf, 0, r);
            out.put(e.getName(), bo.toByteArray());
        }
        z.close();
        return out;
    }
}

