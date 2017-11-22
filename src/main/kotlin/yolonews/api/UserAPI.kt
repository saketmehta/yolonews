package yolonews.api

import javax.ws.rs.Consumes
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * @author saket.mehta
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class UserAPI {
}