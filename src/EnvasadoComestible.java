import java.time.LocalDate;
// Esta es una subclase de Envasado, para diferenciarla de los envasados que no son comestibles.
public class EnvasadoComestible extends Envasado implements Comestible {
    private LocalDate fechaVencimiento;
    private float calorias; 

    public EnvasadoComestible(String idDigitos, String descripcion, int cantidadStock, 
                                Double precioUnitario, Double costoUnitario, 
                                float porcentajeDescuento, MaterialEnvase tipoEnvase, 
                                boolean importado, LocalDate fechaVencimiento, float calorias) {
                                    
        super(idDigitos, descripcion, cantidadStock, precioUnitario, costoUnitario, porcentajeDescuento, 
                tipoEnvase, importado);
                
        this.fechaVencimiento = fechaVencimiento;
        this.calorias = calorias;
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
    public void setCalorias(float calorias) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCalorias'");
    }

    @Override
    public float getPorcentajeGanancia() {
        return super.getPorcentajeGanancia();
    }

    @Override
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFechaVencimiento'");
    }
    
}
