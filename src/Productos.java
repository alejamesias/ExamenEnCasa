public class Productos {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int cantidad;

    //Creamos el contructor
    public Productos(int id, String nombre, String categoria, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    //Creamos el get y set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    //Aqui vot a mostrar el producto de forma completa con el metodo
    public String productoCompleto() {
        return "ID del Producto: " + id + ", Nombre del Producto: " + nombre + ", Categoría del Producto: " + categoria +
                ", Precio: " + precio + ", Cantidad: " + cantidad;
    }

}
