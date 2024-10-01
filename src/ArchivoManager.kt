package dsm.console

import java.io.File

class ArchivoManager {
    val usuariosFile = File("usuarios.txt")
    val productosFile = File("productos.txt")

    fun guardarUsuarios(usuarios: List<Usuario>) {
        usuariosFile.writeText(usuarios.joinToString("\n") { "${it.username},${it.hashedPassword},${it.isActive},${it.isAdmin}" })
    }

    fun cargarUsuarios(): List<Usuario> {
        return if (usuariosFile.exists()) {
            usuariosFile.readLines().map {
                val (username, hashedPassword, isActive, isAdmin) = it.split(",")
                Usuario(username, hashedPassword, isActive.toBoolean(), isAdmin.toBoolean())
            }
        } else {
            emptyList()
        }
    }

    fun guardarProductos(productos: List<Producto>) {
        productosFile.writeText(productos.joinToString("\n") { "${it.nombre},${it.precio},${it.existencia}" })
    }

    fun cargarProductos(): List<Producto> {
        return if (productosFile.exists()) {
            productosFile.readLines().map {
                val (nombre, precio, existencia) = it.split(",")
                Producto(nombre, precio.toDouble(), existencia.toInt())
            }
        } else {
            emptyList()
        }
    }
}
