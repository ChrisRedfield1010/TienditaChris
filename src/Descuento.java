/* - La segunda para productos a los que se pueda aplicar descuento y que deberá definir métodos
para setear y obtener el porcentaje de descuento y para obtener el precio de venta con
descuento. */
public interface Descuento {
    float getPorcentajeDescuento();
    void setPorcentajeDescuento(float porcentajeDescuento);
    Double getPrecioConDescuento();
    void setPrecioConDescuento(Double precioConDescuento);
}



