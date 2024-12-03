import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Inventario inventario = new Inventario();
        inventario.cargaDeProductosDeArchivo("C:\\Users\\larrego\\Documents\\ExamenEnCasa\\src\\productos.txt");

        // Crear el objeto MenuPrincipal, pasando el inventario y la ruta del archivo
        MenuPrincipal menu = new MenuPrincipal(inventario, "C:\\Users\\larrego\\Documents\\ExamenEnCasa\\src\\productos.txt");

        // Mostrar el menú
        menu.mostrarMenu();
    }
}
