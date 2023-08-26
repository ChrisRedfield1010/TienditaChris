/* Para los productos envasados:
● El identificador deberá respetar el formato ABXXX, donde XXX son dígitos. Por ejemplo,
AB112, AB345 serán identificadores válidos.
● Deberán contar con las propiedades de tipo de envase (plástico, vidrio o lata) y si es
importado o no. */

public class Envasado extends Producto {
    private MaterialEnvase tipoEnvase;
    private boolean importado;
    private static final float PORCENTAJE_DESCUENTO_MAXIMO = 20.0f;

    public Envasado(String idDigitos, String descripcion, int cantidadStock, Double precioUnitario, 
                        Double costoUnitario, float porcentajeDescuento, MaterialEnvase tipoEnvase, 
                        boolean importado) {
// Los caracteres máximos de cada clave son 5, 2 ya predefinidos según el producto y 3 numéricos.
        super("AB" + idDigitos.substring(0,3), descripcion, cantidadStock, 
                precioUnitario, costoUnitario, porcentajeDescuento);
        
        this.tipoEnvase = tipoEnvase;
        this.importado = importado;
    }

    public boolean isImportado() {
        return importado;
    }

    public float getPorcentajeGanancia() { // FIX no me acuerdo por qué dejé este método, ya está en la superclase, pero no voy a tener tiempo para revisarlo.
        return super.getPorcentajeGanancia();
    }
    
    @Override // Para setear el porcentaje de descuento primero se debe verificar que no exceda el límite, si lo hace, no se aplica.
    public void setPorcentajeDescuento(float porcentajeDescuento) {
        if (porcentajeDescuento <= PORCENTAJE_DESCUENTO_MAXIMO) {
            super.setPorcentajeDescuento(porcentajeDescuento);
        } else {
            System.out.println("El descuento para el envasado " + getIdProducto() + 
                                " excede el " + PORCENTAJE_DESCUENTO_MAXIMO + 
                                "%. El descuento no se aplicará.");
            super.setPorcentajeDescuento(0);
            super.setPrecioConDescuento(getPrecioUnitario());
        }
    }
}    

