package app.monoline.domain.user

import app.monoline.adapters.Repository
import app.monoline.util.logger

data class User(var id: Long = -1, var email: String, var password: String)

data class LoginResult(val success: Boolean, val user: User?, val message: String = "")

interface UserRepository : Repository<User, Long> {

    fun findByEmail(email: String): User?

}

class UserService(private val users: UserRepository) {
    private val log by logger()

    fun findByEmail(email: String): User? {
        return users.findByEmail(email)
    }

    fun authenticate(email: String, password: String): LoginResult {
        return try {
            require(password.length >= 8) { "Password length is too short (${password.length} vs 8)." }
            val user = requireNotNull(users.findByEmail(email)) { "Invalid username/password combination" }
            LoginResult(success = true, user = user)
        } catch (t: Throwable) {
            log.error("An error occurred in UserService.authenticateUser", t)
            LoginResult(success = false, user = null, message = t.stackTraceToString())
        }
    }

}
