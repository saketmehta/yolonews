package yolonews.services

import redis.clients.jedis.JedisPool
import yolonews.models.User

/**
 * @author saket.mehta
 */
class UserService(private val jedisPool: JedisPool) {
    fun fetchUser(id: Long): User {
        jedisPool.resource.use {
            val data = it.hgetAll("user:" + id)
            return User.fromMap(data)
        }
    }

    fun createUser(user: User): User {
        jedisPool.resource.use {
            val exists = it.exists("username.to.id:" + user.username?.toLowerCase())
            if (!exists) {
                val id = it.incr("users.count")
                val createdUser = user.copy(id = id)
                it.hmset("user:" + id, User.toMap(createdUser))
                it.set("username.to.id:" + user.username, id.toString())
                return createdUser
            } else {
                throw Exception()
            }
        }
    }

    fun updateUser(user: User) {
        jedisPool.resource.use {
            it.hset("user:${user.id}", "email", user.email)
        }
    }
}