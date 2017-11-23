package yolonews.models

import java.util.*

/**
 * @author saket.mehta
 */
data class User(
        val id: Long,
        val username: String,
        val password: String,
        val karma: Long = 0,
        val email: String = "",
        val createdTime: Long
) {
    companion object {
        fun fromMap(map: Map<String, String>): User {
            return User(
                    map["id"]!!.toLong(),
                    map["username"]!!,
                    map["password"]!!,
                    map["karma"]!!.toLong(),
                    map["email"]!!,
                    map["createdTime"]!!.toLong()
            )
        }

        fun toMap(user: User): Map<String, String> {
            val data = HashMap<String, String>()
            data.put("id", user.id.toString())
            data.put("username", user.username)
            data.put("password", user.password)
            data.put("karma", user.karma.toString())
            data.put("email", user.email)
            data.put("createdTime", user.createdTime.toString())
            return data
        }
    }
}

