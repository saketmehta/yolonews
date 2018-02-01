package com.yolonews.api

import yolonews.services.FeedService
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

/**
 * @author saket.mehta
 */
@Path("/feed")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class FeedAPI(private val feedService: FeedService) {
    @GET
    @Path("/latest")
    fun fetchLatestFeed(): Response {
        val feed = feedService.fetchFeed("latest")
        return Response.ok(feed).build()
    }

    @GET
    @Path("/top")
    fun fetchTopFeed(): Response {
        val feed = feedService.fetchFeed("top")
        return Response.ok(feed).build()
    }

    @GET
    @Path("/{sort}/{start}/{count}")
    fun fetchPosts(@PathParam("sort") sort: String,
                   @PathParam("start") start: Long,
                   @PathParam("count") count: Long
    ): Response {
        if (sort !in listOf("latest", "top")) throw BadRequestException()
        if (start < 0 || count < 0 || count > 100) throw BadRequestException()
        val feed = feedService.fetchFeed(sort, start, count)
        return Response.ok(feed).build()
    }
}
