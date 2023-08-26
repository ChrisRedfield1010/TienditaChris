/*- La primera para productos que sean comestibles y que deberá definir métodos para setear y
    obtener la fecha de vencimiento y las calorías. */
import java.time.LocalDate;

public interface Comestible extends Ganancia {
    LocalDate getFechaVencimiento();
    float getCalorias();
    void setFechaVencimiento(LocalDate fechaVencimiento);
    void setCalorias(float calorias);
}
