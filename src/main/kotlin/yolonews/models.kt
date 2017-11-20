package yolonews

/**
 * @author saket.mehta
 */
data class User(
        val id: Int,
        val username: String,
        val password: String,
        val karma: Int = 0,
        val email: String = "",
        val createdTime: Long
)

data class Post(
        val id: Int,
        val title: String,
        val url: String,
        val text: String,
        val userId: Int,
        val score: Int,
        val rank: Int,
        val upvotes: Int,
        val downvotes: Int,
        val comments: Int,
        val createdTime: Long
)

data class Comment(
        val id: Int,
        val text: String,
        val userId: Int,
        val parentId: Int,
        val score: Int,
        val upvotes: Int,
        val createdTime: Long
)