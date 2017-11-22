package yolonews.models

/**
 * @author saket.mehta
 */
data class User(
        val id: Int,
        val username: String,
        val password: String,
        val karma: Long = 0,
        val email: String = "",
        val createdTime: Long
) {
    companion object {
        fun fromMap(map: Map<String, String>): User {
            return User(
                    map["id"]!!.toInt(),
                    map["username"]!!,
                    map["password"]!!,
                    map["karma"]!!.toLong(),
                    map["email"]!!,
                    map["createdTime"]!!.toLong()
            )
        }
    }
}

