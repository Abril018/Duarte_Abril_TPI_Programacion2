
/**
 *
 * @author abril
 */
package integrado.prog2.config;

public class ConexionDB {

    private static ConexionDB instancia;
    private String url;
    private String usuario;
    private boolean conectada;

    // Constructor privado (Patrón Singleton básico)
    private ConexionDB() {
        this.url = "jdbc:mysql://localhost:3306/food_store_db";
        this.usuario = "admin";
        this.conectada = false;
    }

    // Método para obtener la única instancia de la configuración
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public void conectar() {
        this.conectada = true;
        System.out.println("[CONFIG] Conexion a la base de datos " + url + " establecida correctamente.");
    }

    public void desconectar() {
        this.conectada = false;
        System.out.println("[CONFIG] Conexion a la base de datos cerrada.");
    }

    public boolean isConectada() {
        return conectada;
    }

    public String getUrl() {
        return url;
    }
}