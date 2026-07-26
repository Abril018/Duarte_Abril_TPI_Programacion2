package integrado.prog2.entities;

public class Producto extends Base {
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    public Producto(Long id, String nombre, double precio, String descripcion, int stock, String imagen, Categoria categoria) {
        super(id);
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = stock > 0; 
        this.categoria = categoria;
    }

 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getStock() { return stock; }
    public void setStock(int stock) { 
        this.stock = stock;
        this.disponible = stock > 0; 
    }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "ID: " + getId() + 
               " | Producto: " + nombre + 
               " | Precio: $" + precio + 
               " | Stock: " + stock + 
               " | Categoría: " + (categoria != null ? categoria.getNombre() : "Sin Categoría");
    }
}