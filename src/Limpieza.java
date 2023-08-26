  /*Para los productos de limpieza:
● El identificador deberá respetar el formato AZXXX, donde XXX son dígitos. Por ejemplo,
AZ112, AZ564 serán identificadores válidos.
● Deberán contar con la propiedad tipo de aplicación que tendrá 4 valores posibles
(COCINA, PISOS, ROPA, MULTIUSO).*/ 
public class Limpieza extends Producto implements Ganancia {
    private AplicacionLimpieza tipoAplicacion;
    private static final float PORCENTAJE_DESCUENTO_MAXIMO = 25.0f;

    public Limpieza(String idDigitos, String descripcion, int cantidadStock, Double precioUnitario, 
                    Double costoUnitario, float porcentajeDescuento, AplicacionLimpieza tipoAplicacion) {
// Los caracteres máximos de cada clave son 5, 2 ya predefinidos según el producto y 3 numéricos.
        super("AZ" + idDigitos.substring(0,3), descripcion, cantidadStock, 
              precioUnitario, costoUnitario, porcentajeDescuento);
             
        this.tipoAplicacion = tipoAplicacion;
    }

    public AplicacionLimpieza getTipoAplicacion() {
      return tipoAplicacion;
    }

    @Override // Para setear el porcentaje de descuento primero se debe verificar que no exceda el límite, si lo hace, no se aplica.
    public void setPorcentajeDescuento(float porcentajeDescuento) {
      if (porcentajeDescuento <= PORCENTAJE_DESCUENTO_MAXIMO) {
        super.setPorcentajeDescuento(porcentajeDescuento);
    } else {
        System.out.println("El descuento para el producto de limpieza " + getIdProducto() + 
                            " excede el " + PORCENTAJE_DESCUENTO_MAXIMO + 
                            "%. El descuento no se aplicará.");
                            super.setPorcentajeDescuento(0);
                                super.setPrecioConDescuento(getPrecioUnitario());
      }
    }

    @Override
    public float getPorcentajeGanancia() {
      return super.getPorcentajeGanancia(); // Llama a la superclase, se tiene que agregar sí o sí aunque se herede, porque está implementando una interface.
    }
} 
 

