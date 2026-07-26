
/**
 *
 * @author abril
 */
package integrado.prog2.config;

public class ConfiguracionSistema {

    public static final String NOMBRE_SISTEMA = "Food Store - Sistema de Gestion";
    public static final String VERSION = "1.0.0";
    public static final String MONEDA = "$";

    public static void mostrarHeader() {
        System.out.println("==========================================");
        System.out.println("   " + NOMBRE_SISTEMA + " (v" + VERSION + ")");
        System.out.println("==========================================");
    }
}