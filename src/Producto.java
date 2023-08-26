/* Un producto (sin importar el tipo que sea) deberá contar con las siguientes propiedades: identificador
alfanumérico de longitud 5, descripción del producto, cantidad en stock, precio por unidad (para la venta
al público) y costo por unidad (el valor al que se compró), y si está disponible para la venta. */

public class Producto implements Descuento {
    private String idProducto;
    private String descripcion;
    private int cantidadStock;
    private Double precioUnitario;
    private Double costoUnitario;
    private boolean disponibilidad;
    private float porcentajeGanancia;
    private float porcentajeDescuento;
    private Double precioConDescuento;
    
    public Producto(String idProducto, String descripcion, int cantidadStock, Double precioUnitario, 
                    Double costoUnitario, float porcentajeDescuento) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.cantidadStock = cantidadStock;
        this.precioUnitario = precioUnitario;
        this.costoUnitario = costoUnitario;
        this.porcentajeDescuento = porcentajeDescuento; //FIX no me acuerdo por qué dejé esta línea de código, creo que me olvidé de borrarla, pero no lo hago ahora porque si se rompe el código no voy a tener tiempo para hacer el testing.
        setPorcentajeDescuento(porcentajeDescuento);
        this.porcentajeGanancia = (float) ((precioUnitario - costoUnitario) / precioUnitario * 100);
        this.disponibilidad = false;    // Todos tienen disponibilidad false por default hasta que se agreguen a la colección.
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public Double getCostoUnitario() {
        return costoUnitario;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public float getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    @Override
    public float getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    @Override
    public Double getPrecioConDescuento() {
        return precioConDescuento;
    }

    @Override // Cuando se agrega el valor al porcentaje de descuento, también se agrega el precio con descuento en base a este porcentaje.
    public void setPorcentajeDescuento(float porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
        setPrecioConDescuento(precioUnitario * (1 - porcentajeDescuento / 100));
    }
    
    /*- El porcentaje de descuento de las bebidas no podrá superar el 15%, el de los envasados el 20% y el
    de los productos de limpieza el 25%. Si el descuento a aplicar genera pérdidas por el producto
    (su valor final de venta es inferior al valor por el que se compró) éste no se aplicará. Cuando esto
    suceda, imprimir un mensaje como por ejemplo “El descuento registrado para el producto
    AC123 no pudo ser aplicado”.*/
    @Override // Si el descuento genera pérdidas, no se aplica. Si no genera pérdidas se aplica al precio final.
    public void setPrecioConDescuento(Double precioConDescuento) {
        if (precioConDescuento < getCostoUnitario()) {
            System.out.println("El descuento registrado para el producto " 
                                + getIdProducto() + " no pudo ser aplicado porque genera pérdidas.");
            this.precioConDescuento = precioUnitario; // El precio con descuento pasa a ser el mismo que el precio sin descuento.
            this.porcentajeDescuento = 0; // El porcentaje pasa a ser 0, por más que lo hayan creado con algún valor diferente.
        }
        else { this.precioConDescuento = precioConDescuento; // Se aplica el descuento.
        } 
    }  
}