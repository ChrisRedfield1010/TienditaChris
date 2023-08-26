/*Crear un programa ApplicacionTienda que cree productos de distinto tipo y realice operaciones con ellos
(generar combinaciones de prueba que permitan probar los diferentes casos planteados en los puntos
anteriores). Cada vez que se realice una operación de compra/venta se deberán imprimir los datos de
stock de el/los productos involucrados y el saldo resultante en la caja luego de realizarla. Se valorará que
se ilustren todos los casos posibles mediante ejemplos y que cada ejemplo se pueda ejecutar en forma
independiente. */

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicacionTienda {
    public static void main(String[] args) throws Exception {
        
        Tienda tienditaChris = new Tienda("tienditaChris", 50, 25000.0);
// Bebida importada con porcentaje de descuento mayor al límite.
        Bebida unTinto = new Bebida("123", "Café Colombiano", 1, 
                                     100.0, 95.0, 81, 
                                     false, 0, true, 
                                     LocalDate.of(1991,10,10), 50);

// Bebida nacional con porcentaje de descuento menor al límite.
        Bebida unMateCocido = new Bebida("120", "Café Colombiano", 1, 
                                     100.0, 95.0, 2, 
                                     false, 0, true, 
                                     LocalDate.of(1991,10,10), 50);

// Producto de limpieza multiuso con ganancia dentro de sus límites (10%-25%).        
        Limpieza misterLimpieza = new Limpieza("128", "Mister Limpieza", 
                                               2, 12.0, 
                                               10.0, 1, 
                                               AplicacionLimpieza.COCINA); 

// Producto de limpieza multiuso con ganancia mayor a su límite.        
        Limpieza misterMusculo = new Limpieza("129", "Mister Limpieza", 
                                               2, 20.0, 
                                               10.0, 1, 
                                               AplicacionLimpieza.MULTIUSO); 

// Servilletas envasadas de papel importadas con mucha cantidad de Stock.
        Envasado servilletasPapel = new Envasado("124", "Servilletas de Papel", 
                                                 49, 50.0, 
                                                 48.0, 1, 
                                                 MaterialEnvase.LATA, true);

// Envasado comestible no importado con un porcentaje de descuento mayor a sus ganancias.        
        EnvasadoComestible twistos = new EnvasadoComestible("130", "Twistos", 
                                                 1, 41.0, 
                                                 40.0, 10, 
                                                 MaterialEnvase.PLASTICO, false, 
                                                 LocalDate.of(1991, 10, 10),
                                                 30.5f);
    
// Producto nacional EnvasadoComestible con precios de lujo superior a la caja.        
        EnvasadoComestible havanna = new EnvasadoComestible("150", "Alfajor Havanna", 
                                                 6, 5100.0, 
                                                 5000.0, 1, 
                                                 MaterialEnvase.PLASTICO, false, 
                                                 LocalDate.of(1991, 10, 10),
                                                 30.5f);
/// Llenando la collección...        
        tienditaChris.agregarProducto(unTinto);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(misterLimpieza);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(servilletasPapel); 
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(twistos);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(unTinto);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(unMateCocido);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(misterMusculo);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(misterLimpieza); 
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(twistos);
        System.out.println("-----------------------------------------------------------");

        tienditaChris.agregarProducto(havanna);
        System.out.println("-----------------------------------------------------------");

// Creo una lista para guardar los datos de la búsqueda de productos comestibles no importados con descuento menor a 11.
        List<String> listaComestibles = tienditaChris.obtenerComestiblesConMenorDescuento(11);
        System.out.println("Lista de comestibles NO importados con descuento: " + listaComestibles);
        System.out.println("-----------------------------------------------------------");

// Creo una lista para guardar los datos de la búsqueda de productos con ganancias inferiores al 80%.    
        List<String> listaProductosInferiores = tienditaChris.listarProductosConUtilidadesInferiores(80);
        System.out.println("Lista de productos que generan ganancias: ");
        for (String producto : listaProductosInferiores){
            System.out.println(producto);}

        System.out.println("-----------------------------------------------------------");

// Lleno el carrito de ventas con hasta 3 productos diferentes.
        Map<String, Integer> carritoVentas = new HashMap<>();
        carritoVentas.put("AC123", 6);
        carritoVentas.put("AZ128", 3);
        carritoVentas.put("AB130", 3);
        //mapVentas.put("AC120", 1); // Con este supera el límite de 3 productos diferentes.

// Hago la venta enviando el carrito que llené anteriormente.
        tienditaChris.venderProducto(carritoVentas);

        System.out.println("-----------------------------------------------------------");

// Repito la venta con los productos ya liquidados fuera de stock.
        tienditaChris.venderProducto(carritoVentas);
    }
}