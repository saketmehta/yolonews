package yolonews.models

/**
 * @author saket.mehta
 */
data class Comment(
        val id: Int,
        val text: String,
        val userId: Int,
        val parentId: Int,
        val score: Int,
        val upvotes: Int,
        val createdTime: Long
)