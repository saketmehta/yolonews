package yolonews.services

import redis.clients.jedis.JedisPool
import yolonews.models.Post

/**
 * @author saket.mehta
 */
class PostService(private val jedisPool: JedisPool) {
    fun createPost(post: Post): Long {
        TODO("not implemented")
    }

    fun fetchPost(id: Long): Post {
        TODO("not implemented")
    }
}