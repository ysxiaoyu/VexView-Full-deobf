import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Verifies the game-menu broadcast protocol of the 1.12.2 source:
 *   VexGameMenu.m4("x<##>url<##>title<##>line1<#>line2<#>line3")
 * must fill s3 = url, s4 = title, s6 = the three log lines.
 */
public final class GameMenuProbe {

    public static void main(String[] args) throws Exception {
        String payload = "x<##>https://www.vexview.top<##>UpdateTitle<##>"
                + "Update log line one<#>Update log line two<#>third line";
        Class<?> menu = Class.forName("lk.vexview.ui.menu.VexGameMenu");
        Method m4 = menu.getDeclaredMethod("m4", String.class);
        m4.setAccessible(true);
        try {
            m4.invoke(null, payload);
        } catch (Throwable t) {
            System.out.println("RESULT m4 threw: " + t.getCause());
            return;
        }
        System.out.println("RESULT url   = " + field(menu, "s3"));
        System.out.println("RESULT title = " + field(menu, "s4"));
        Object lines = field(menu, "s6");
        System.out.println("RESULT lines = " + lines);
        int size = lines instanceof List ? ((List<?>) lines).size() : -1;
        System.out.println("RESULT lineCount = " + size);
        System.out.println(size == 3 ? "RESULT PASS" : "RESULT FAIL");
    }

    private static Object field(Class<?> type, String name) throws Exception {
        Field f = type.getDeclaredField(name);
        f.setAccessible(true);
        return f.get(null);
    }
}
