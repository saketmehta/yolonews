package yolonews.services

import redis.clients.jedis.JedisPool
import java.math.BigInteger
import java.security.SecureRandom

/**
 * @author saket.mehta
 */
class AuthService(private val jedisPool: JedisPool) {
    fun verifyToken(token: String) {
        jedisPool.resource.use {
            it.get("auth:$token")?.toInt() ?: throw Exception()
        }
    }

    fun issueToken(username: String, password: String): String {
        jedisPool.resource.use {
            val id = it.get("username.to.id:$username")
            val data = it.hget("user:$id", "password")
            if (password != data) {
                throw Exception()
            }
            val random = SecureRandom()
            val token = BigInteger(130, random).toString(32)
            it.set("auth:$token", id)
            return token
        }
    }

    fun revokeToken(token: String) {
        jedisPool.resource.use {
            it.del("auth:$token")
        }
    }
}