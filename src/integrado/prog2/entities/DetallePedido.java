package integrado.prog2.entities;

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.producto = producto;
        calcularSubtotal();
    }

    public void calcularSubtotal() {
        if (producto != null) {
            this.subtotal = cantidad * producto.getPrecio();
        } else {
            this.subtotal = 0.0;
        }
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        calcularSubtotal();
    }

    public Double getSubtotal() { return subtotal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
        calcularSubtotal();
    }

    @Override
    public String toString() {
        return "Producto: " + (producto != null ? producto.getNombre() : "N/A") + 
               " x" + cantidad + 
               " | Subtotal: $" + subtotal;
    }
}