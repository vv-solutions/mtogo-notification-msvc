package dk.vv.mtogo.notification.msvc.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "order-api")
@Path("/api/order")
public interface OrderService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{orderId}")
    Response getOrder(@PathParam("orderId") int orderId);

}
