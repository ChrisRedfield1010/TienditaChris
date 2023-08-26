import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
/* tendremos una clase que representará la Tienda con los siguientes atributos: nombre,
número máximo de productos en stock, saldo en la caja y una colección que contendrá los productos
que se encuentran en stock (estén o no habilitados para la venta). Dicha colección se sugiere que sea
implementada como un diccionario de manera tal de poder acceder de forma rápida al listado
correspondiente a cada uno de los tres tipos de producto. */
public class Tienda {
    private String nombre;
    private Integer stockMaximo;
    private double saldoCaja;
    private Map<String, Producto> productosStock;
    
    public Tienda(String nombre, Integer stockMaximo, double saldoCaja) {
        this.nombre = nombre;
        this.stockMaximo = stockMaximo;
        this.saldoCaja = saldoCaja;
        Map<String, Producto> inicializarMap = new HashMap<>();
        this.productosStock = inicializarMap;
    }

    public double getSaldoCaja() {
        return saldoCaja;
    }

    public void setSaldoCaja(double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }

/*CONSIDERACIONES ESPECIALES: Se deberán cumplir las siguientes consideraciones especiales:
- El porcentaje de ganancia de los productos comestibles no podrá superar el 20% y el de los
productos de limpieza no podrá ser menor del 10% ni mayor al 25%, excepto los de tipo ROPA y
MULTIUSO que no tendrán mínimo.*/

// Verifica el porcentaje de ganancia de cada producto para saber si se puede agregar porque algunos tienen un límite.
    public boolean verificarPorcentajeGanancia(Producto nombreProducto) {
        float porcentaje = nombreProducto.getPorcentajeGanancia();
        
        if (nombreProducto instanceof Comestible){ // Si implementa la interface Comestible tiene un límite de 20.
            return porcentaje <= 20;
        }
        else if (nombreProducto instanceof Limpieza){ // Si pertenece a la clase limpieza sus límites pueden variar dependiendo del tipo de aplicación del producto.
            AplicacionLimpieza tipoAplicacion = ((Limpieza)nombreProducto).getTipoAplicacion();
// TODO se podría hacer con un if más sencillo, pero para estrenar el switch...            
            switch (tipoAplicacion) {
                case ROPA: return porcentaje <= 25;
                case MULTIUSO: return porcentaje <= 25;
                default: return (porcentaje >= 10) && (porcentaje <= 25);
            } 
        }
        return true;
    } 
// Calcula el stock total sumando todos los productos del "diccionario" de productos.
    public Integer calcularStockTotal(){
        Integer stockTotal = 0;

        for (Producto producto : productosStock.values()) {
                    stockTotal += producto.getCantidadStock();
                }

        return stockTotal;
    }
// Calcula el precio total del producto multiplicandoló por la cantidad.
    public Double calcularPrecioTotal(Producto nombreProducto){
        Double precio = nombreProducto.getPrecioConDescuento();
        int cantidad = nombreProducto.getCantidadStock();
        return precio * cantidad;
    }
// Verifica si la caja tiene dinero disponible para realizar una operación de compra.
    public boolean verificarCaja(Double precioTotal){
        return saldoCaja >= precioTotal;
    }
// Verifica que la compra no se exceda de la capacidad máxima entre todos los productos del map.
    public boolean verificarStockMaximo(Producto nombreProducto) {
        int cantidadStock = nombreProducto.getCantidadStock();
        return stockMaximo >= calcularStockTotal() + cantidadStock;
    }
// Ejecuta la compra, si el producto ya estaba en el map le suma el stock y si no está, la agrega al diccionario como un nuevo producto. 
    public void realizarCompra(Producto nombreProducto){
        String idProducto = nombreProducto.getIdProducto();
        
        if (productosStock.containsKey(idProducto)) {
            int cantidad = nombreProducto.getCantidadStock();
            cantidad += productosStock.get(idProducto).getCantidadStock();
            productosStock.get(idProducto).setCantidadStock(cantidad);
        }
        else { productosStock.put(idProducto, nombreProducto);
        }
// Luego actualiza la caja y la disponibilidad del producto.
        nombreProducto.setDisponibilidad(true);
        saldoCaja -= calcularPrecioTotal(nombreProducto);       
    }
/* COMPRA - Se podrá agregar cualquier tipo de producto a la tienda, el cual deberá ser ubicado en el listado
que corresponda.
- Por defecto todos los productos agregados estarán disponibles para la venta. - Por cada producto
agregado se deberá actualizar el saldo en la caja (el importe total del producto resulta de la
multiplicación del número de unidades por el precio de cada una). Si el saldo en la caja NO resulta
suficiente para cubrir dicho monto entonces el producto no podrá ser agregado. En este caso se
deberá imprimir un mensaje, por ejemplo: “El producto no podrá ser agregado a la tienda por
saldo insuficiente en la caja”.
- Se deberá tener en cuenta es que no se podrán agregar mas productos a la tienda una vez que se
haya alcanzado el máximo de stock habilitado (sumando todas las unidades de todos los
productos). */
// Este es el método para agregar productos a la colección.
    public void agregarProducto(Producto nombreProducto){
        String idProducto = nombreProducto.getIdProducto();
        if (verificarCaja(calcularPrecioTotal(nombreProducto)) && (verificarStockMaximo(nombreProducto)) 
            && (verificarPorcentajeGanancia(nombreProducto))) {             
            realizarCompra(nombreProducto); // Si todo está OK realiza la compra.
            System.out.println("El producto fue agregado con éxito.");

/*Cada vez que se realice una operación de compra/venta se deberán imprimir los datos de
stock de el/los productos involucrados y el saldo resultante en la caja luego de realizarla.*/  

            int stockDisponible = productosStock.get(idProducto).getCantidadStock();
            String descripcion = productosStock.get(idProducto).getDescripcion();
            System.out.printf("Hay %d %s en el stock y $%.2f en la caja.%n", 
                                stockDisponible, descripcion, getSaldoCaja());
        }

        /* Decidí no crear excepciones para los mensajes de error
            para que no se consuman recursos de forma innecesaria. */

        else if (!verificarCaja(calcularPrecioTotal(nombreProducto))) {
            System.out.println("El producto " + idProducto + 
                                " no podrá ser agregado a la tienda por saldo insuficiente en la caja.");
        }
        else if (!verificarStockMaximo(nombreProducto)) { System.out.println( "El producto " + 
                 idProducto + " no podrá ser agregado a la tienda porque ha excedido el stock máximo.");
            }
        else { System.out.println("El producto " + idProducto + 
            " no podrá ser agregado a la tienda porque no está dentro de los límites de ganancias.");
        }
    }

    // Calcula el impuesto agregado si el producto es importado, si no lo es, devuelve 0.
    public double agregarImpuesto(Producto nombreProducto) {
        double precioUnitario = nombreProducto.getPrecioUnitario();
        double impuesto = 0.0;
        if (nombreProducto instanceof Bebida) {
            return ((Bebida) nombreProducto).isImportado() ? precioUnitario * 0.10 : impuesto;
        }
        else if (nombreProducto instanceof Envasado) {
            return ((Envasado) nombreProducto).isImportado() ? precioUnitario * 0.10 : impuesto;
        }
        else { return impuesto;
        }
    }
/* VENTA - Cada venta podrá incluir como máximo 3 productos de cualquier tipo y hasta 10 unidades de cada
uno.
- Para cada venta se deberá imprimir el detalle de la misma incluyendo los siguientes datos:
identificador, descripción, cantidad de unidades y precio de venta por unidad para cada producto
y al final el total de la venta.
Ejemplo de la impresión de una venta:
AB122 CAFÉ 2 x 1,50
AB455 ACEITE 2 x 20
TOTAL VENTA: 64,50
- En el caso de que el número de unidades vendidas de alguno de los productos sea mayor al que se
tenga en stock, se venderá solo el número de unidades disponibles y se deberá actualizar el
producto para que quede fuera de venta. Además, si se da el caso, se deberá agregar el siguiente
mensaje informativo luego del detalle de la venta “Hay productos con stock disponible menor al
solicitado”.
- Solo podrán venderse productos que estén habilitados para la venta, caso contrario el producto
deberá ser eliminado de la venta y mostrar un mensaje al respecto. Por ejemplo: “El producto
AB122 CAFÉ no se encuentra disponible”.*/

// Este es el método para vender los productos que llegan en un carrito de compra con un máximo de 3 productos diferentes.

    public void venderProducto(Map<String, Integer> mapVentas) {
        if (mapVentas.size() > 3) {
            System.out.println("No se pueden vender más de 3 productos en una sola venta.");
            return;
        }
        
        double totalVenta = 0;
        boolean estaDisponible = true; // Flag para tirar la advertencia de que no hubo stock suficiente para realizar la venta completamente.
        List<String> listaEliminarVentas = new ArrayList<>(); // Lista para agregar todos los productos que ya no estén disponibles, para quitarlos del carrito de venta, como lo pide el enunciado.
        List<String> listaResumen = new ArrayList<>(); // Lista de todos los productos vendidos para poder hacer un resumen al final de todas las ventas.

        System.out.println("Detalle de la venta:");
// Recorro el carrito de ventas para verificar si están disponibles o si no superan el máximo de stock permitido para comprar de cada producto que es 10.
        for (String idProducto : mapVentas.keySet()) {
            Integer cantidadVendida = mapVentas.get(idProducto);
            Producto producto = productosStock.get(idProducto);
            String descripcionProducto = producto.getDescripcion();
            int stockDisponible = producto.getCantidadStock();
            Double precioConDescuento = producto.getPrecioConDescuento();

            if (!producto.isDisponibilidad()) {
                listaEliminarVentas.add(idProducto); // Lo agrego a la lista para eliminarlos luego.
                System.out.println("El producto " + idProducto + " " + descripcionProducto
                                     + " no se encuentra disponible.");
                continue;
            }
// La cantidad vendida de cada producto no puede ser mayor a 10, si lo es, pasa al siguiente producto del carrito.
            if (cantidadVendida > 10) {
                System.out.println("No se pueden vender más de 10 unidades del producto " + idProducto);
                continue;
            }
// Si el stock alcanza, se realiza la venta, se actualiza el stock y si llega a 0, el producto pasa a estar indisponible.        
            if (stockDisponible >= cantidadVendida) {
                producto.setCantidadStock(stockDisponible - cantidadVendida);
                if (stockDisponible == 0) {
                    producto.setDisponibilidad(false);
                }
            }
// Si el stock no alcanza, sólo se vende lo que hay, se actualiza el stock y el producto pasa a estar indisponible.
            else { 
                producto.setCantidadStock(0);
                cantidadVendida = stockDisponible;
                producto.setDisponibilidad(false);
                estaDisponible = false;
            }
/* - Para los productos importados se aplicará un impuesto del 10% sobre el precio de venta.*/
/// Decidí agregar los impuestos en cada venta para que no interfieran con las ganancias al instanciar cada objeto si se las sumaba en el precio final.
            precioConDescuento += agregarImpuesto(producto);
            

/// TODO Acá debería actualizar la caja sumandolé el precio total de lo que se vendió, pero no lo pide en las consignas. 

        System.out.printf("%s %s %d x %.2f%n", idProducto, descripcionProducto, cantidadVendida, 
                            precioConDescuento);
            totalVenta += cantidadVendida * precioConDescuento;
            listaResumen.add(idProducto); // Agrego el producto para hacer el resumen al finalizar la operación de venta.
        }
  
        System.out.printf("TOTAL VENTA: %.2f%n", totalVenta); 

        if (!estaDisponible){
            System.out.println("Hay productos con stock disponible menor al solicitado.");
        }
        /*Cada vez que se realice una operación de compra/venta se deberán imprimir los datos de
        stock de el/los productos involucrados y el saldo resultante en la caja luego de realizarla.*/

        for (String idProducto : listaResumen) {
            int stockDisponible = productosStock.get(idProducto).getCantidadStock();
            String descripcion = productosStock.get(idProducto).getDescripcion();
            System.out.printf("Quedan %d %s en el stock y $%.2f en la caja.%n", 
                                stockDisponible, descripcion, getSaldoCaja());
        }

        // Elimino las ventas dónde el producto no está disponible.
        for (String idProducto : listaEliminarVentas){
            mapVentas.remove(idProducto);
        }
    }   
        /*Se desea conocer la lista de productos comestibles NO importados cuyo descuento sea menor a un
determinado porcentaje. Para ello se pide implementar un método llamado
obtenerComestiblesConMenorDescuento(porcentaje_descuento) que devolverá una lista de productos
comestibles (descripción) NO importados cuyo descuento sea menor al porcentaje pasado como
parámetro.
La lista deberá devolverse ordenada en forma ascendente de acuerdo con el precio de venta y con todas
las descripciones en mayúsculas.
Un resultado válido podría ser: “ACEITE, VINO, ARROZ” 
BONUS (opcional): No utilizar bucles (for, while, do-while) ni tampoco condicionales (if-else) para
resolver el punto anterior, sino apoyarse en la API de Streams de Java.*/
    
    public List<String> obtenerComestiblesConMenorDescuento(float porcentajeDescuento) {
        return productosStock.values().stream()
            .filter(producto -> producto instanceof Comestible) // Filtra todos los productos para que sólo queden los que implementen la interface Comestible. 
            .map(producto -> (Comestible) producto) // Agrega esos productos comestibles al flujo de datos.
            .filter(comestible -> { // Filtra los productos para que sólo queden los NO importados y con un porcentaje de descuento menor al parámetro enviado.
                if (comestible instanceof Bebida) { // verifico si es una bebida o un envasado comestible.
                    return (!((Bebida) comestible).isImportado() && 
                            ((Bebida) comestible).getPorcentajeDescuento() < porcentajeDescuento);
                } else if (comestible instanceof EnvasadoComestible) {
                    return (!((EnvasadoComestible) comestible).isImportado() && 
                    ((EnvasadoComestible) comestible).getPorcentajeDescuento() < porcentajeDescuento);
                }
                return false;
            })
            .map(producto -> (Producto) producto) // Agrego esos productos al flujo de datos.
            .sorted(Comparator.comparing(Producto::getPrecioUnitario)) // Los ordeno de forma ascendente según su precio.
            .map(Producto::getDescripcion) //Agrego al flujo de datos la descripción de cada producto.
            .map(String::toUpperCase) // Paso la descripción a mayúsculas.
            .collect(Collectors.toList()); // Convierto la colección para que el método devuelva los productos en una lista.
    }
/*También se desea conocer los productos que de cualquier tipo que estén generando ganancias inferiores
a un porcentaje determinado y la cantidad de ellos en stock. Para ello deberá implementar un método
llamado listarProductosConUtilidadesInferiores(porcentaje_utilidad). El resultado de esta será una lista
que indique el código, la descripción y la cantidad en stock de cada producto resultante, e imprimirlo.*/
    public List<String> listarProductosConUtilidadesInferiores(float porcentaje_utilidad){
        return productosStock.values().stream()
            .filter(producto -> producto.getPorcentajeGanancia() < porcentaje_utilidad) // Filtro los productos con menor porcentaje de ganancia que el enviado como parámetro.
            .map(producto -> String.format("Código: %s, Descripción: %s, Cantidad en stock: %d", 
            producto.getIdProducto(), producto.getDescripcion(), producto.getCantidadStock())) // Agrego el resumen al flujo de datos.
            .collect(Collectors.toList()); // Devuelvo el flujo de datos como una lista.
    }
}     
