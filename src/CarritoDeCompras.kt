package dsm.console

class CarritoDeCompras {
    private var items: MutableMap<Producto, Int> = mutableMapOf()

    fun agregarProducto(producto: Producto, cantidad: Int) {
        items[producto] = items.getOrDefault(producto, 0) + cantidad
    }

    fun quitarProducto(producto: Producto, cantidad: Int) {
        val currentCantidad = items[producto] ?: 0
        if (currentCantidad > cantidad) {
            items[producto] = currentCantidad - cantidad
        } else {
            items.remove(producto)
        }
    }

    fun calcularTotal(): Double {
        var total = 0.0
        for ((producto, cantidad) in items) {
            total += producto.precio * cantidad
        }
        return total
    }

    fun calcularSubtotales(): Map<Producto, Double> {
        return items.mapValues { (producto, cantidad) -> producto.precio * cantidad }
    }

    fun getItems(): Map<Producto, Int> {
        return items.toMap()
    }

    fun setItems(nuevosItems: MutableMap<Producto, Int>) {
        items = nuevosItems
    }
}
