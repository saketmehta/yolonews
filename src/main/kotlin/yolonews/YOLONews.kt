package yolonews

import io.dropwizard.Application
import io.dropwizard.Configuration
import io.dropwizard.setup.Environment
import org.hibernate.validator.constraints.NotEmpty
import redis.clients.jedis.JedisPool
import yolonews.api.AuthAPI
import yolonews.services.AuthService


/**
 * @author saket.mehta
 */
class YOLONews : Application<YOLONewsConfig>() {
    override fun run(config: YOLONewsConfig, environment: Environment) {
        // Initialise Redis
        val jedisPool = JedisPool(config.redisURL)

        // Resources
        environment.jersey().register(AuthAPI(AuthService(jedisPool)))

        // Filters
        val authFilter = AuthFilter(config.tokenHeaderPrefix!!, config.tokenQueryParamKey!!, AuthService(jedisPool))
        environment.jersey().register(authFilter)
    }
}

class YOLONewsConfig : Configuration() {
    @NotEmpty
    var redisURL: String? = null
    @NotEmpty
    val tokenHeaderPrefix: String? = null
    @NotEmpty
    val tokenQueryParamKey: String? = null
}

fun main(args: Array<String>) {
    YOLONews().run(*args)
}
