import java.io.*;
import java.util.*;

public class Inventario {

    //Creo una lista donde me va a guardar los prouctos
    private List<Productos> productos;

    // Constructor
    public Inventario() {
        //Creo una arraylist de productos
        productos = new ArrayList<>();
    }
    //Creo el metodo para cargar los productos del archivo
        public void cargaDeProductosDeArchivo(String archivo) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    // Procesar cada línea
                    String[] datos = linea.split(",");
                    int id =Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String categoria = datos[2];
                    double precio = Double.parseDouble(datos[3]);
                    int cantidad = Integer.parseInt(datos[4]);
                    productos.add(new Productos(id, nombre, categoria, precio, cantidad));
                }
            } catch (IOException e) {
                System.out.println("Error al cargar el archivo: " + e.getMessage());
            }
        }

    //este metodo me ayuda a guardar productos en el archivo
        public void guardarDatosArchivo(String archivo) throws IOException {

            File file = new File(archivo);  // Crea el archivo a partir de la ruta
            System.out.println("Guardando datos en: " + file.getAbsolutePath());  // Imprime la ruta absoluta del archivo

            // Si el archivo no existe, intenta crearlo
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("El archivo no existía, se ha creado uno nuevo.");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                for (Productos producto : productos) {
                    bw.write(producto.getId() + "," + producto.getNombre() + "," + producto.getCategoria() + "," + producto.getPrecio() + "," + producto.getCantidad());
                    bw.newLine();
                }
            }
        }

    //esto me sirve para agregar un producto y guardarlo de una vez en mi archivo
        public void agregarProducto(Productos producto, String archivo) throws IOException{
            productos.add(producto); //lo agrego
            guardarDatosArchivo(archivo);
        }

        //Buscar productos por id
        public Productos buscarPorId(int id) {
            for (Productos producto : productos) {
                if (producto.getId() == id) {
                    return producto;
                }
            }
            System.out.println("No fue posible encontrar el producto:( ");// Si no se encuentra el producto
            return null;
        }

        //actualizar producto
        public boolean actualizarProducto(int id, String nombreNuevo, String categoriaNueva, double precioNuevo, int cantidadNueva) {
            Productos producto = buscarPorId(id);
            if(producto != null) {
                    producto.setNombre(nombreNuevo);// puedo cambiar el nombre
                    producto.setCategoria(categoriaNueva); // la categoria
                    producto.setPrecio(precioNuevo);// el precio
                    producto.setCantidad(cantidadNueva); // y la cantidad

                System.out.println("Producto actualizado correctamente");
                return true;
                } else //mensaje por si no es posible encontrar el producto que le pase
                System.out.println("No se encontro el producto");
                 return false;
        }

         public Productos obtenerProductoPorId(int id) {
            for (Productos producto : productos) {
            if (producto.getId() == id) {
                return producto;  // Retorna el producto encontrado
            }
        }
        return null;  // Si no se encuentra el producto, devuelve null
    }

        //eliminar producto por ID
        public boolean eliminarProducto(int id) {
            boolean eliminado = productos.removeIf(producto -> producto.getId() == id);// si coincide con el id que le pase, lo eliminara
            return eliminado; //devuelve true si se elimino y false sino
        }

        //buscar un producto por categoria
        public List<Productos> buscarPorCategoria(String categoria) { //creo una lista donde voy a guardar los productos filtrados
        List<Productos> productosDeCategoria = new ArrayList<>();
        for (Productos producto : productos) {
            if (producto.getCategoria().equals(categoria)) { //si la categoria es igual a la que consulte se me guarda en la lista creada
                productosDeCategoria.add(producto);
            }
        }
        return productosDeCategoria; //devuelvo la lista de productos de esa categoria
        }

        //calcular producto mas caro

        public Productos obtenerProductoMasCaro() {
        if (productos.isEmpty()) {
            return null;  // recorre la lista y mira si hay contenido o no, si no hay devulve null
        }
        Productos productoMasCaro = productos.get(0); // se asigna el primer producto a la variable y toma el puesto del mas caro
        for (Productos producto : productos) {
            if (producto.getPrecio() > productoMasCaro.getPrecio()) { //traigo el precio de los productos y lo comparo con el que tomo el puesto del mas caro
                productoMasCaro = producto; // si llega a haber un producto mas caro, se reasigna a la variable de arriba
            }
        }
        return productoMasCaro;
        }

        //cantidad total de productos por categoria
        public void cantidadPorCategoria() {
            int cantidadTecnologia = 0;
            int cantidadRopa = 0;
            int cantidadAlimentos = 0;
            int cantidadBelleza = 0;
            int cantidadCalzado = 0;

            //recorret la lista de productos
            for (Productos producto : productos) {
                String categoria = producto.getCategoria();

                //aumenta la cantidad en cada categoria
                if (categoria.equals("Tecnologia")) {
                    cantidadTecnologia += producto.getCantidad();
                }
                else if (categoria.equals("Ropa")) {
                    cantidadRopa += producto.getCantidad();
                }
                else if (categoria.equals("Alimentos")) {
                    cantidadAlimentos += producto.getCantidad();
                }
                else if (categoria.equals("Belleza")) {
                    cantidadBelleza += producto.getCantidad();
                }
                else if (categoria.equals("Calzado")) {
                    cantidadCalzado += producto.getCantidad();
                }
            }

            //imprimir la cantidad por categoria
            System.out.println("Cantidad de productos de Tecnologia: " + cantidadTecnologia);
            System.out.println("Cantidad de productos de Ropa: " + cantidadRopa);
            System.out.println("Cantidad de productos de Alimentos: " + cantidadAlimentos);
            System.out.println("Cantidad de productos de Belleza: " + cantidadAlimentos);
            System.out.println("Cantidad de productos de Calzado: " + cantidadAlimentos);
        }

        //generar reporte
        public void generarReporte(String archivo) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
                //Formato del reporte
                writer.write("Producto  | Categoría | Unidades Totales | Valor Total");
                writer.newLine();

                //calcular el valor total del inventario
                double valorTotalInventario = 0;

                for (Productos producto : productos) {
                    //valor total de cada producto
                    double valorTotalProducto = producto.getPrecio() * producto.getCantidad();

                    //se suma al valor total del inventario
                    valorTotalInventario += valorTotalProducto;

                    // Escribir los datos del producto en el archivo
                    // eso de % me sirve para organizar el texto a derecha o izquierda en el reporte, chat gpt jeje
                    writer.write(String.format("%-10s | %-10s | %-15d | $%.2f",
                            producto.getNombre(),
                            producto.getCategoria(),
                            producto.getCantidad(),
                            valorTotalProducto));
                    writer.newLine();
                }

                //valor total del inventario, el formato me sirve para redondear valores, chat cpt 2.0
                writer.write("Valor total del inventario es: $" + String.format("%.2f", valorTotalInventario));
            } catch (IOException e) {
                System.out.println("Error al generar el reporte: " + e.getMessage());
        }

        }
    }

