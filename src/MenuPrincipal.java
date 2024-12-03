import java.io.IOException;
import java.util.Scanner;
import java.util.*;

public class MenuPrincipal {
    private Inventario inventario;
    private String archivo;


    // Constructor que recibe el objeto Inventario
    public MenuPrincipal(Inventario inventario, String archivo) {
        this.inventario = inventario;  // Asigna el inventario recibido
        this.archivo = archivo;  // Asigna el archivo recibido
    }

    //Menu y opciones
    public void mostrarMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        //mirar lo que ingresa el usuario


        System.out.println(" - - - - - - - Menu Principal - - - - - - ");
        System.out.println("|Selecciona una opcion:                  |");
        System.out.println("|1. Agregar producto                     |");
        System.out.println("|2. Actualizar producto                  |");
        System.out.println("|3. Eliminar producto                    |");
        System.out.println("|4. Buscar por categoría                 |");
        System.out.println("|5. Generar reporte                      |");
        System.out.println("|6. Cantidad de productos por categoría  |");
        System.out.println("|7. Producto más caro                    |");
        System.out.println("|8. Salir                                |");
        System.out.println("------------------------------------------");
        int opcion = scanner.nextInt();

        scanner.nextLine();  // Limpiar el buffer

        //opciones del menu
        switch (opcion) {
            case 1:
                // Agregar producto
                agregarProducto(scanner);
                break;
            case 2:
                // Actualizar producto
                actualizarProducto(scanner);
                break;
            case 3:
                // Eliminar producto
                eliminarProducto(scanner);
                break;
            case 4:
                // Buscar por categoría
                buscarPorCategoria(scanner);
                break;
            case 5:
                // Generar reporte
                inventario.generarReporte("reporte_inventario.txt");
                break;
            case 6:
                // Cantidad de productos por categoría
                cantidadPorCategoria();
                break;
            case 7:
                // Producto mas caro
                productoMasCaro();
                break;
            case 8:
                // Salir
                System.out.println("vuelve pronto jeje");
                return;  // finalizo el programa
            default:
                System.out.println("Opcion incorrecta");
        }

        // Volver a mostrar el menú despues de seleccionar una opcion
        mostrarMenu();
    }

    // Métodos auxiliares para cada opción del menú
    private void agregarProducto(Scanner scanner) throws IOException {
        System.out.print("Digita el ID: ");
        int id = Integer.parseInt(scanner.nextLine()); // comvertirlo de string a int
        System.out.print("Digita un nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Digita una categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Digita un precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Digita una cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        inventario.agregarProducto(new Productos(id, nombre, categoria, precio, cantidad), archivo);
        System.out.println("Se agrego el producto correctamente");
    }

    private void actualizarProducto(Scanner scanner) throws IOException {
        System.out.print("Introduce el ID del producto que deseas actualizar: ");
        int id = Integer.parseInt(scanner.nextLine());

        //busca el producto por id en el inventario
        Productos productoExistente = inventario.obtenerProductoPorId(id);
        if (productoExistente == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        //solicito los datos del producto que se va a ctualizar
        System.out.print("Ingresa el nuevo nombre (dejalo vacio si no lo quieres cambiar): ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) {
            nombre = productoExistente.getNombre();  // Mantener el nombre original si no se proporciona uno nuevo
        }

        System.out.print("Ingresa la nueva categoría (dejalo vacio si no lo quieres cambiar): ");
        String categoria = scanner.nextLine();
        if (categoria.isEmpty()) {
            categoria = productoExistente.getCategoria();  // Mantener la categoría original
        }

        System.out.print("Ingresa un nuevo precio (dejalo vacio si no lo quieres cambiar): ");
        String precioStr = scanner.nextLine();
        double precio = productoExistente.getPrecio();  // Mantener el precio original por defecto
        if (!precioStr.isEmpty()) {
            precio = Double.parseDouble(precioStr);  // Convertir a double si se proporciona un nuevo precio
        }

        System.out.print("Ingresa una nueva cantidad (dejalo vacio si no lo quieres cambiar): ");
        String cantidadStr = scanner.nextLine();
        int cantidad = productoExistente.getCantidad();  // Mantener la cantidad original
        if (!cantidadStr.isEmpty()) {
            cantidad = Integer.parseInt(cantidadStr);  // Convertir a int si se proporciona una nueva cantidad
        }

        // Crear el nuevo producto con los datos actualizados
        Productos nuevoProducto = new Productos(id, nombre, categoria, precio, cantidad);

        // Reemplazar el producto viejo con el actualizado
        boolean actualizado = inventario.actualizarProducto(id, nombre, categoria, precio, cantidad);

        if (actualizado) {
            System.out.println("Producto actualizado exitosamente.");
            inventario.guardarDatosArchivo(archivo);  // Guardar los cambios en el archivo
        } else {
            System.out.println("Error al actualizar el producto.");
        }
    }


    private void eliminarProducto(Scanner scanner) throws IOException {
        System.out.print("Introduce ID del producto que deseas eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        scanner.nextLine(); // Limpiar el buffer

        if (inventario.eliminarProducto(id)) {
            System.out.println("Producto eliminado exitosamente");
            inventario.guardarDatosArchivo(archivo);  // Guardar los cambios en el archivo
        } else {
            System.out.println("No se encontro el producto");
        }
    }

    private void buscarPorCategoria(Scanner scanner) {
        System.out.print("Introduce categoría a buscar, recuerda que las categorias son: ");
        System.out.println("Tecnologia");
        System.out.println("Belleza");
        System.out.println("Calzado");
        System.out.println("Ropa");
        System.out.println("Alimentos");
        System.out.println("Ten presente que debes escribirlo tal y como esta en las opciones anteriores");
        String categoria = scanner.nextLine();
        List<Productos> productosCategoria = inventario.buscarPorCategoria(categoria);

        //Valida si hay oo no productos
        if (productosCategoria.isEmpty()) {
            System.out.println("No se encontraron productos para esta categoria");
        } else {
            for (Productos p : productosCategoria) { //recorre todos los productos
                System.out.println( "Los productos que pertenecen a la categoria que ingresaste: ");
                System.out.println( "ID: " + p.getId());
                System.out.println( "Nombre: " + p.getNombre());
                System.out.println( "Categoria: " + p.getCategoria());
                System.out.println( "Precio: $" + p.getPrecio());
                System.out.println( "Cantidad: " + p.getCantidad());
            }
        }
    }

    private void generarReporte() {
        inventario.generarReporte("reporte_inventario.txt"); //se guarda automaticamente en la carpeta del proyecto que ejecuto
        System.out.println("Se ha generado exitosamente tu reporte jeje mensaje aleja: acuerdate que este quedo en la carpeta raiz del proyecto");
    }

    private void cantidadPorCategoria() {
        inventario.cantidadPorCategoria();
    }

    private void productoMasCaro() {
        Productos productoCaro = inventario.obtenerProductoMasCaro();
        if (productoCaro != null) {
            //muestra cual es el producto mas caro
            System.out.println("El producto mas caro es: ");
            System.out.println("ID: " + productoCaro.getId());
            System.out.println("Nombre: " + productoCaro.getNombre());
            System.out.println("Categoria: " + productoCaro.getCategoria());
            System.out.println("Precio: $" + productoCaro.getPrecio());
            System.out.println("Cantidad: " + productoCaro.getCantidad());
        } else {
            System.out.println("No se encontraron productos");
        }
    }
}