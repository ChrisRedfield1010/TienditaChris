    /*Para las bebidas:
● El identificador deberá respetar el formato ACXXX, donde XXX son dígitos. Por ejemplo,
AC112, AC564 serán identificadores válidos.
● Deberán contar con una propiedad que indique si es alcohólica o no, y en caso de que lo
sea, deberá contar con la propiedad graduación alcohólica (porcentaje de alcohol que
posee), y si es importado o no.*/

import java.time.LocalDate;

public class Bebida extends Producto implements Comestible{
    private boolean esAlcoholica;
    private float graduacionAlcoholica;
    private boolean importado;
    private LocalDate fechaVencimiento;
    private float calorias; 
    private static final float PORCENTAJE_DESCUENTO_MAXIMO = 15.0f;

    public Bebida(String idDigitos, String descripcion, int cantidadStock, Double precioUnitario, 
                    Double costoUnitario, float porcentajeDescuento, boolean esAlcoholica, 
                    float graduacionAlcoholica, boolean importado, LocalDate fechaVencimiento, 
                    float calorias) {
// Los caracteres máximos de cada clave son 5, 2 ya predefinidos según el producto y 3 numéricos.
        super("AC" + idDigitos.substring(0, 3), descripcion, cantidadStock, 
                precioUnitario, costoUnitario, porcentajeDescuento);
        
        this.esAlcoholica = esAlcoholica;
// Si la bebida es alcohólica, le pone la graduación asignada por parámetro, pero sino, le agrega 0.
        if (esAlcoholica) {
            this.graduacionAlcoholica = graduacionAlcoholica;
        }
        else { this.graduacionAlcoholica = 0;
        }

        this.importado = importado;
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calorias;
    }

    public boolean isImportado() {
        return importado;
    }

    @Override
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public float getCalorias() {
        return calorias;
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFechaVencimiento'");
    }

    @Override
    public void setCalorias(float calorias) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCalorias'");
    }
    
    @Override // Para setear el porcentaje de descuento primero se debe verificar que no exceda el límite, si lo hace, no se aplica.
    public void setPorcentajeDescuento(float porcentajeDescuento) {
        if (porcentajeDescuento <= PORCENTAJE_DESCUENTO_MAXIMO) {
            super.setPorcentajeDescuento(porcentajeDescuento);
        } else {
            System.out.println("El descuento para la bebida " + getIdProducto() + 
                                " excede el " + PORCENTAJE_DESCUENTO_MAXIMO + 
                                "%. El descuento no se aplicará.");
                                super.setPorcentajeDescuento(0);
                                super.setPrecioConDescuento(getPrecioConDescuento());
        }
    }
}
