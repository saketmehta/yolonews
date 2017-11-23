package yolonews

import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.dropwizard.Application
import io.dropwizard.setup.Bootstrap
import io.dropwizard.setup.Environment
import redis.clients.jedis.JedisPool
import yolonews.api.AuthAPI
import yolonews.api.UserAPI
import yolonews.services.AuthService
import yolonews.services.UserService


/**
 * @author saket.mehta
 */
class YOLONewsApp : Application<YOLONewsConfig>() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            YOLONewsApp().run(*args)
        }
    }

    override fun run(config: YOLONewsConfig, environment: Environment) {
        // Initialise Redis
        val jedisPool = JedisPool(config.redisURL)

        // Resources
        environment.jersey().register(AuthAPI(AuthService(jedisPool)))
        environment.jersey().register(UserAPI(UserService(jedisPool)))

        // Filters
        val authFilter = AuthFilter(config.tokenHeaderPrefix!!, config.tokenQueryParamKey!!, AuthService(jedisPool))
        environment.jersey().register(authFilter)
    }

    override fun initialize(bootstrap: Bootstrap<YOLONewsConfig>) {
        bootstrap.objectMapper.registerModule(KotlinModule())
    }
}