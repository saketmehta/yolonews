package yolonews.models

/**
 * @author saket.mehta
 */
data class Post(
        val id: Int = -1,
        val title: String = "",
        val url: String = "",
        val text: String = "",
        val userId: Int = -1,
        val score: Int = 0,
        val rank: Int = 0,
        val upvotes: Int = 0,
        val downvotes: Int = 0,
        val comments: Int = 0,
        val createdTime: Long = System.currentTimeMillis()
)