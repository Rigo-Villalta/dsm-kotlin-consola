package dsm.console

class Sistema {
    val usuarios: MutableList<Usuario> = mutableListOf()
    val productos: MutableList<Producto> = mutableListOf()
    var usuarioActivo: Usuario? = null
    val carritoDeCompras: CarritoDeCompras = CarritoDeCompras()

    fun registrarUsuario(username: String, password: String, isAdmin: Boolean = false) {
        usuarios.add(Usuario(username, password, isActive = false, isAdmin = isAdmin))
    }

    fun iniciarSesion(username: String, password: String): Boolean {
        val usuario = usuarios.find { it.username == username }
        if (usuario != null && usuario.checkPassword(password)) {
            usuarios.forEach { it.isActive = false }
            usuario.isActive = true
            usuarioActivo = usuario
            return true
        }
        return false
    }

    fun cerrarSesion() {
        usuarioActivo?.isActive = false
        usuarioActivo = null
    }

    fun agregarProducto(nombre: String, precio: Double, existencia: Int) {
        productos.add(Producto(nombre, precio, existencia))
    }

    fun agregarProductoAlCarrito(nombreProducto: String, cantidad: Int) {
        val producto = productos.find { it.nombre == nombreProducto }
        if (producto != null && producto.existencia >= cantidad) {
            carritoDeCompras.agregarProducto(producto, cantidad)
            producto.existencia -= cantidad
        } else {
            println("Producto no disponible o cantidad insuficiente.")
        }
    }

    fun quitarProductoDelCarrito(nombreProducto: String, cantidad: Int) {
        val producto = productos.find { it.nombre == nombreProducto }
        if (producto != null) {
            carritoDeCompras.quitarProducto(producto, cantidad)
            producto.existencia += cantidad
        } else {
            println("Producto no encontrado en el carrito.")
        }
    }

    fun comprar() {
        val items = carritoDeCompras.getItems()
        for ((producto, cantidad) in items) {
            producto.existencia -= cantidad
        }
        carritoDeCompras.setItems(mutableMapOf())
        println("Compra realizada con éxito.")
    }

    fun mostrarCarrito() {
        val items = carritoDeCompras.getItems()
        if (items.isEmpty()) {
            println("El carrito está vacío.")
        } else {
            println("Carrito de compras:")
            for ((producto, cantidad) in items) {
                println("${producto.nombre} - Cantidad: $cantidad - Subtotal: ${producto.precio * cantidad}")
            }
            println("Total: ${carritoDeCompras.calcularTotal()}")
        }
    }

    fun mostrarProductos() {
        if (productos.isEmpty()) {
            println("No hay productos disponibles.")
        } else {
            println("Productos disponibles:")
            for (producto in productos) {
                println("${producto.nombre} - Precio: ${producto.precio} - Existencia: ${producto.existencia}")
            }
        }
    }

    fun modificarProducto(nombreProducto: String, nuevoPrecio: Double? = null, nuevaExistencia: Int? = null) {
        val producto = productos.find { it.nombre == nombreProducto }
        if (producto != null) {
            if (nuevoPrecio != null) {
                producto.precio = nuevoPrecio
            }
            if (nuevaExistencia != null) {
                producto.existencia = nuevaExistencia
            }
            println("Producto modificado con éxito.")
        } else {
            println("Producto no encontrado.")
        }
    }
}
