package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    
    public void addDetallePedido(int cantidad, Double subtotal, Producto producto) {
        Long detalleId = (long) (detalles.size() + 1);
        DetallePedido detalle = new DetallePedido(detalleId, cantidad, producto);
        detalles.add(detalle);
        calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);
        if (detalle != null) {
            detalles.remove(detalle);
            calcularTotal();
        }
    }

    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        this.total = suma;
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Double getTotal() { return total; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<DetallePedido> getDetalles() { return detalles; }

    @Override
    public String toString() {
        return "ID Pedido: " + getId() + 
               " | Fecha: " + fecha + 
               " | Cliente: " + (usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "Sin usuario") + 
               " | Estado: " + estado + 
               " | Forma Pago: " + formaPago + 
               " | Total: $" + total;
    }
}