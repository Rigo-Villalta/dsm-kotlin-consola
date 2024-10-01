// importar clases
package dsm.console

import java.io.File
import java.security.MessageDigest

import dsm.console.ArchivoManager
import dsm.console.CarritoDeCompras
import dsm.console.Producto
import dsm.console.Usuario
import dsm.console.Sistema


fun main() {
    val sistema = Sistema()
    val archivoManager = ArchivoManager()

    // Cargar usuarios y productos desde archivos
    sistema.usuarios.addAll(archivoManager.cargarUsuarios())
    sistema.productos.addAll(archivoManager.cargarProductos())

    // Menú principal
    while (true) {
        println("1. Iniciar sesión")
        println("2. Registrar usuario")
        println("3. Salir")
        print("Seleccione una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                print("Nombre de usuario: ")
                val username = readLine() ?: ""
                print("Contraseña: ")
                val password = readLine() ?: ""
                if (sistema.iniciarSesion(username, password)) {
                    println("Inicio de sesión exitoso.")
                    manejarSesion(sistema)
                } else {
                    println("Nombre de usuario o contraseña incorrectos.")
                }
            }
            2 -> {
                print("Nombre de usuario: ")
                val username = readLine() ?: ""
                print("Contraseña: ")
                val password = readLine() ?: ""
                print("¿Es administrador? (s/n): ")
                val isAdmin = readLine()?.toLowerCase() == "s"
                sistema.registrarUsuario(username, password, isAdmin)
                println("Usuario registrado con éxito.")
            }
            3 -> {
                // Guardar usuarios y productos antes de salir
                archivoManager.guardarUsuarios(sistema.usuarios)
                archivoManager.guardarProductos(sistema.productos)
                println("Saliendo del sistema...")
                return
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}

fun manejarSesion(sistema: Sistema) {
    while (sistema.usuarioActivo != null) {
        println("1. Mostrar productos")
        println("2. Agregar producto al carrito")
        println("3. Quitar producto del carrito")
        println("4. Mostrar carrito")
        println("5. Comprar")
        if (sistema.usuarioActivo?.isAdmin == true) {
            println("6. Modificar producto (admin)")
            println("7. Agregar producto (admin)")
        }
        println("8. Cerrar sesión")
        print("Seleccione una opción: ")
        when (readLine()?.toIntOrNull()) {
            1 -> sistema.mostrarProductos()
            2 -> {
                print("Nombre del producto: ")
                val nombreProducto = readLine() ?: ""
                print("Cantidad: ")
                val cantidad = readLine()?.toIntOrNull() ?: 0
                sistema.agregarProductoAlCarrito(nombreProducto, cantidad)
            }
            3 -> {
                print("Nombre del producto: ")
                val nombreProducto = readLine() ?: ""
                print("Cantidad: ")
                val cantidad = readLine()?.toIntOrNull() ?: 0
                sistema.quitarProductoDelCarrito(nombreProducto, cantidad)
            }
            4 -> sistema.mostrarCarrito()
            5 -> sistema.comprar()
            6 -> if (sistema.usuarioActivo?.isAdmin == true) {
                print("Nombre del producto: ")
                val nombreProducto = readLine() ?: ""
                print("Nuevo precio (dejar en blanco si no se desea cambiar): ")
                val nuevoPrecio = readLine()?.toDoubleOrNull()
                print("Nueva existencia (dejar en blanco si no se desea cambiar): ")
                val nuevaExistencia = readLine()?.toIntOrNull()
                sistema.modificarProducto(nombreProducto, nuevoPrecio, nuevaExistencia)
            }
            7 -> if (sistema.usuarioActivo?.isAdmin == true) {
                print("Nombre del producto: ")
                val nombreProducto = readLine() ?: ""
                print("Precio: ")
                val precio = readLine()?.toDoubleOrNull() ?: 0.0
                print("Existencia: ")
                val existencia = readLine()?.toIntOrNull() ?: 0
                sistema.agregarProducto(nombreProducto, precio, existencia)
            }
            8 -> {
                sistema.cerrarSesion()
                println("Sesión cerrada.")
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    }
}
