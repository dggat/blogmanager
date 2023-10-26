package blog.manager.presentation.rest.controllers;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/personal")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class PersonalController {

    @GET
    public Response gebePersonalZurueck() {
        return Response.seeOther(URI.create("http://localhost:8080/nutzer"))
                .status(Response.Status.MOVED_PERMANENTLY).build();
    }
}
