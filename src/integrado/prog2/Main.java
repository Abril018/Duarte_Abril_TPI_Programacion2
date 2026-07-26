package integrado.prog2;

import integrado.prog2.config.ConfiguracionSistema;
import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.*;
import integrado.prog2.enums.*;
import integrado.prog2.exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Categoria> categorias = new ArrayList<>();
    private static List<Producto> productos = new ArrayList<>();
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Pedido> pedidos = new ArrayList<>();

    private static long idCategoriaCount = 1;
    private static long idProductoCount = 1;
    private static long idUsuarioCount = 1;
    private static long idPedidoCount = 1;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ConexionDB.getInstancia().conectar();
        cargarDatosPrueba();

        int opcion = -1;

        do {
            ConfiguracionSistema.mostrarHeader();
            
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 0:
                        System.out.println("Gracias por usar " + ConfiguracionSistema.NOMBRE_SISTEMA + ". Saliendo...");
                        ConexionDB.getInstancia().desconectar();
                        break;
                    default:
                        System.out.println("Opcion fuera de rango. Reintente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero valido.");
            }
        } while (opcion != 0);
    }

    private static void menuCategorias() {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE CATEGORIAS ---");
            System.out.println("1. Listar categorias");
            System.out.println("2. Crear categoria");
            System.out.println("3. Editar categoria");
            System.out.println("4. Eliminar categoria (Baja Logica)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
                switch (op) {
                    case 1:
                        listarCategorias();
                        break;
                    case 2:
                        crearCategoria();
                        break;
                    case 3:
                        editarCategoria();
                        break;
                    case 4:
                        eliminarCategoria();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (op != 0);
    }

    private static void listarCategorias() {
        System.out.println("\n-- Listado de Categorias Activas --");
        boolean hayRegistros = false;
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                System.out.println(c);
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay categorias cargadas.");
        }
    }

    private static void crearCategoria() throws ValidacionNegocioException {
        System.out.print("Ingrese nombre de la categoria: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            throw new ValidacionNegocioException("El nombre no puede estar vacio.");
        }

        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                throw new ValidacionNegocioException("Ya existe una categoria activa con el nombre '" + nombre + "'.");
            }
        }

        System.out.print("Ingrese descripcion: ");
        String desc = scanner.nextLine().trim();

        Categoria nueva = new Categoria(idCategoriaCount++, nombre, desc);
        categorias.add(nueva);
        System.out.println("Categoria creada exitosamente con ID: " + nueva.getId());
    }

    private static void editarCategoria() throws EntidadNoEncontradaException, ValidacionNegocioException {
        listarCategorias();
        System.out.print("Ingrese el ID de la categoria a editar: ");
        long id = Long.parseLong(scanner.nextLine());

        Categoria c = buscarCategoriaPorId(id);

        System.out.print("Ingrese nuevo nombre (Enter para mantener '" + c.getNombre() + "'): ");
        String nombre = scanner.nextLine().trim();
        if (!nombre.isEmpty()) {
            c.setNombre(nombre);
        }

        System.out.print("Ingrese nueva descripcion (Enter para mantener): ");
        String desc = scanner.nextLine().trim();
        if (!desc.isEmpty()) {
            c.setDescripcion(desc);
        }

        System.out.println("Categoria actualizada con exito.");
    }

    private static void eliminarCategoria() throws EntidadNoEncontradaException {
        listarCategorias();
        System.out.print("Ingrese el ID de la categoria a eliminar: ");
        long id = Long.parseLong(scanner.nextLine());

        Categoria c = buscarCategoriaPorId(id);

        System.out.print("Esta seguro que desea eliminar la categoria '" + c.getNombre() + "'? (S/N): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            c.setEliminado(true);
            System.out.println("Categoria eliminada logicamente.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    private static Categoria buscarCategoriaPorId(long id) throws EntidadNoEncontradaException {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("No se encontro la Categoria activa con ID " + id);
    }

    private static void menuProductos() {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE PRODUCTOS ---");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto (Baja Logica)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
                switch (op) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        crearProducto();
                        break;
                    case 3:
                        editarProducto();
                        break;
                    case 4:
                        eliminarProducto();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numerico valido.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (op != 0);
    }

    private static void listarProductos() {
        System.out.println("\n-- Listado de Productos --");
        boolean hay = false;
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                System.out.println(p);
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("No hay productos cargados.");
        }
    }

    private static void crearProducto() throws ValidacionNegocioException, EntidadNoEncontradaException {
        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) throw new ValidacionNegocioException("El nombre no puede estar vacio.");

        System.out.print("Precio (" + ConfiguracionSistema.MONEDA + "): ");
        double precio = Double.parseDouble(scanner.nextLine());
        if (precio < 0) throw new ValidacionNegocioException("El precio no puede ser menor a 0.");

        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());
        if (stock < 0) throw new ValidacionNegocioException("El stock no puede ser menor a 0.");

        System.out.print("Descripcion: ");
        String desc = scanner.nextLine();

        System.out.print("Imagen (URL/Ruta): ");
        String img = scanner.nextLine();

        listarCategorias();
        System.out.print("Ingrese ID de la Categoria asociada: ");
        long idCat = Long.parseLong(scanner.nextLine());
        Categoria cat = buscarCategoriaPorId(idCat);

        Producto p = new Producto(idProductoCount++, nombre, precio, desc, stock, img, cat);
        productos.add(p);
        System.out.println("Producto creado con exito con ID: " + p.getId());
    }

    private static void editarProducto() throws EntidadNoEncontradaException, ValidacionNegocioException {
        listarProductos();
        System.out.print("ID del producto a editar: ");
        long id = Long.parseLong(scanner.nextLine());
        Producto p = buscarProductoPorId(id);

        System.out.print("Nuevo precio (actual: " + ConfiguracionSistema.MONEDA + p.getPrecio() + ", Enter para mantener): ");
        String inputPrecio = scanner.nextLine();
        if (!inputPrecio.isEmpty()) {
            double precio = Double.parseDouble(inputPrecio);
            if (precio < 0) throw new ValidacionNegocioException("El precio no puede ser negativo.");
            p.setPrecio(precio);
        }

        System.out.print("Nuevo stock (actual: " + p.getStock() + ", Enter para mantener): ");
        String inputStock = scanner.nextLine();
        if (!inputStock.isEmpty()) {
            int stock = Integer.parseInt(inputStock);
            if (stock < 0) throw new ValidacionNegocioException("El stock no puede ser negativo.");
            p.setStock(stock);
        }

        System.out.println("Producto actualizado correctamente.");
    }

    private static void eliminarProducto() throws EntidadNoEncontradaException {
        listarProductos();
        System.out.print("ID del producto a eliminar: ");
        long id = Long.parseLong(scanner.nextLine());
        Producto p = buscarProductoPorId(id);

        System.out.print("Confirmar baja logica de '" + p.getNombre() + "'? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            p.setEliminado(true);
            System.out.println("Producto dado de baja.");
        }
    }

    private static Producto buscarProductoPorId(long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("Producto activo no encontrado con ID " + id);
    }

    private static void menuUsuarios() {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE USUARIOS ---");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario (Baja Logica)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
                switch (op) {
                    case 1:
                        listarUsuarios();
                        break;
                    case 2:
                        crearUsuario();
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        eliminarUsuario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (op != 0);
    }

    private static void listarUsuarios() {
        System.out.println("\n-- Listado de Usuarios --");
        boolean hay = false;
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                System.out.println(u);
                hay = true;
            }
        }
        if (!hay) System.out.println("No hay usuarios registrados.");
    }

    private static void crearUsuario() throws ValidacionNegocioException {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();

        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();
        if (mail.isEmpty()) throw new ValidacionNegocioException("El mail es obligatorio.");

        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                throw new ValidacionNegocioException("El mail '" + mail + "' ya esta registrado.");
            }
        }

        System.out.print("Celular: ");
        String cel = scanner.nextLine().trim();
        System.out.print("Contrasena: ");
        String pass = scanner.nextLine().trim();

        System.out.println("Seleccione Rol: 1. ADMIN | 2. USUARIO");
        int r = Integer.parseInt(scanner.nextLine());
        Rol rol = (r == 1) ? Rol.ADMIN : Rol.USUARIO;

        Usuario u = new Usuario(idUsuarioCount++, nombre, apellido, mail, cel, pass, rol);
        usuarios.add(u);
        System.out.println("Usuario creado con exito. ID: " + u.getId());
    }

    private static void editarUsuario() throws EntidadNoEncontradaException, ValidacionNegocioException {
        listarUsuarios();
        System.out.print("ID del usuario a editar: ");
        long id = Long.parseLong(scanner.nextLine());
        Usuario u = buscarUsuarioPorId(id);

        System.out.print("Nuevo mail (actual: " + u.getMail() + ", Enter para mantener): ");
        String mail = scanner.nextLine().trim();
        if (!mail.isEmpty()) {
            for (Usuario aux : usuarios) {
                if (!aux.isEliminado() && !aux.getId().equals(u.getId()) && aux.getMail().equalsIgnoreCase(mail)) {
                    throw new ValidacionNegocioException("El mail ya pertenece a otro usuario.");
                }
            }
            u.setMail(mail);
        }

        System.out.println("Usuario actualizado correctamente.");
    }

    private static void eliminarUsuario() throws EntidadNoEncontradaException {
        listarUsuarios();
        System.out.print("ID del usuario a eliminar: ");
        long id = Long.parseLong(scanner.nextLine());
        Usuario u = buscarUsuarioPorId(id);

        System.out.print("Confirmar eliminacion de '" + u.getNombre() + "'? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            u.setEliminado(true);
            System.out.println("Usuario eliminado logicamente.");
        }
    }

    private static Usuario buscarUsuarioPorId(long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("Usuario activo no encontrado con ID " + id);
    }

    private static void menuPedidos() {
        int op = -1;
        do {
            System.out.println("\n--- GESTION DE PEDIDOS ---");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar Estado / Forma de Pago");
            System.out.println("4. Eliminar pedido (Baja Logica)");
            System.out.println("0. Volver al menu principal");
            System.out.print("Seleccione: ");

            try {
                op = Integer.parseInt(scanner.nextLine());
                switch (op) {
                    case 1:
                        listarPedidos();
                        break;
                    case 2:
                        crearPedido();
                        break;
                    case 3:
                        actualizarEstadoPagoPedido();
                        break;
                    case 4:
                        eliminarPedido();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (op != 0);
    }

    private static void listarPedidos() {
        System.out.println("\n-- Listado de Pedidos --");
        boolean hay = false;
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                System.out.println(p);
                for (DetallePedido d : p.getDetalles()) {
                    System.out.println("   └─ " + d);
                }
                hay = true;
            }
        }
        if (!hay) System.out.println("No hay pedidos registrados.");
    }

    private static void crearPedido() throws EntidadNoEncontradaException, ValidacionNegocioException {
        listarUsuarios();
        System.out.print("Ingrese el ID del Usuario que realiza el pedido: ");
        long idUsr = Long.parseLong(scanner.nextLine());
        Usuario u = buscarUsuarioPorId(idUsr);

        System.out.println("Seleccione Forma de Pago: 1. TARJETA | 2. TRANSFERENCIA | 3. EFECTIVO");
        int fp = Integer.parseInt(scanner.nextLine());
        FormaPago formaPago = FormaPago.EFECTIVO;
        if (fp == 1) formaPago = FormaPago.TARJETA;
        else if (fp == 2) formaPago = FormaPago.TRANSFERENCIA;

        Pedido nuevoPedido = new Pedido(idPedidoCount++, u, formaPago);

        boolean agregarMas = true;
        while (agregarMas) {
            listarProductos();
            System.out.print("Ingrese ID del producto a agregar: ");
            long idProd = Long.parseLong(scanner.nextLine());
            Producto prod = buscarProductoPorId(idProd);

            System.out.print("Ingrese cantidad: ");
            int cant = Integer.parseInt(scanner.nextLine());
            if (cant <= 0) {
                throw new ValidacionNegocioException("La cantidad debe ser mayor a 0.");
            }

            if (prod.getStock() < cant) {
                throw new ValidacionNegocioException("Stock insuficiente. Stock actual de " + prod.getNombre() + ": " + prod.getStock());
            }

            prod.setStock(prod.getStock() - cant);
            nuevoPedido.addDetallePedido(cant, cant * prod.getPrecio(), prod);

            System.out.print("Desea agregar otro producto al pedido? (S/N): ");
            agregarMas = scanner.nextLine().equalsIgnoreCase("S");
        }

        pedidos.add(nuevoPedido);
        System.out.println("Pedido creado con exito! ID Pedido: " + nuevoPedido.getId() + " - Total: " + ConfiguracionSistema.MONEDA + nuevoPedido.getTotal());
    }

    private static void actualizarEstadoPagoPedido() throws EntidadNoEncontradaException {
        listarPedidos();
        System.out.print("ID del pedido a actualizar: ");
        long id = Long.parseLong(scanner.nextLine());

        Pedido p = null;
        for (Pedido aux : pedidos) {
            if (aux.getId().equals(id) && !aux.isEliminado()) {
                p = aux;
                break;
            }
        }
        if (p == null) throw new EntidadNoEncontradaException("Pedido no encontrado.");

        System.out.println("Nuevo Estado: 1. PENDIENTE | 2. CONFIRMADO | 3. TERMINADO | 4. CANCELADO");
        int est = Integer.parseInt(scanner.nextLine());
        switch (est) {
            case 1: p.setEstado(Estado.PENDIENTE); break;
            case 2: p.setEstado(Estado.CONFIRMADO); break;
            case 3: p.setEstado(Estado.TERMINADO); break;
            case 4: p.setEstado(Estado.CANCELADO); break;
        }

        System.out.println("Estado del pedido actualizado a: " + p.getEstado());
    }

    private static void eliminarPedido() throws EntidadNoEncontradaException {
        listarPedidos();
        System.out.print("ID del pedido a eliminar: ");
        long id = Long.parseLong(scanner.nextLine());

        Pedido p = null;
        for (Pedido aux : pedidos) {
            if (aux.getId().equals(id) && !aux.isEliminado()) {
                p = aux;
                break;
            }
        }
        if (p == null) throw new EntidadNoEncontradaException("Pedido no encontrado.");

        System.out.print("Confirmar eliminacion logica del pedido ID " + p.getId() + "? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            p.setEliminado(true);
            System.out.println("Pedido dado de baja.");
        }
    }

    private static void cargarDatosPrueba() {
        Categoria c1 = new Categoria(idCategoriaCount++, "Hamburguesas", "Las mejores hamburguesas caseras");
        Categoria c2 = new Categoria(idCategoriaCount++, "Bebidas", "Gaseosas y aguas frias");
        categorias.add(c1);
        categorias.add(c2);

        Producto p1 = new Producto(idProductoCount++, "Hamburguesa Doble Queso", 4500.0, "Doble carne, doble cheddar", 20, "img1.png", c1);
        Producto p2 = new Producto(idProductoCount++, "Coca Cola 500ml", 1200.0, "Bebida refrescante", 50, "img2.png", c2);
        productos.add(p1);
        productos.add(p2);

        Usuario u1 = new Usuario(idUsuarioCount++, "Abril", "Duarte", "abril@mail.com", "3421234567", "1234", Rol.ADMIN);
        usuarios.add(u1);
    }
}