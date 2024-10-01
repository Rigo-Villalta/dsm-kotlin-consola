package dsm.console

import java.security.MessageDigest

class Usuario(val username: String, password: String, var isActive: Boolean = false, var isAdmin: Boolean = false) {
    val hashedPassword: String = hashPassword(password)

    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun checkPassword(password: String): Boolean {
        return hashedPassword == hashPassword(password)
    }
}
