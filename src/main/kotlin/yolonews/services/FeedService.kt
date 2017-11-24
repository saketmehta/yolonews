package yolonews.services

import redis.clients.jedis.JedisPool
import yolonews.models.Post

/**
 * @author saket.mehta
 */
class FeedService(private val jedisPool: JedisPool) {
    fun fetchFeed(sort: String, start: Long = 0, count: Long = 30): List<Post> {
        TODO()
    }
}