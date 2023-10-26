package blog.manager.presentation.rest.controllers;


import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.kommentar.Kommentar;
import blog.manager.domain.model.kommentar.KommentarRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Path("/kommentare")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class KommenatrController {

    @Inject
    private KommentarRepository kommentarRepository;
    @Context
    private UriInfo uriInfo;
    @Context
    SecurityContext securityContext;

    @RolesAllowed({"NUTZER"})
    @GET
    public Response gebeKommentarZurueck(@QueryParam("text") String text) {


        Principal userPrincipal = securityContext.getUserPrincipal();
        String name = userPrincipal.getName();

        List<Kommentar> kommentarList = new ArrayList<>();
        try {
            if (text != null) {
                kommentarList = kommentarRepository.getAlleKommentareByText(name, text);
            }else{
                kommentarList = kommentarRepository.getAllByNutzerEmail(name);
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }
        if(kommentarList.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(kommentarList).build();
    }

    @Path("/{kommentarid}")
    @RolesAllowed({"NUTZER"})
    @PATCH
    public Response eidtKommantarByBlogeintragId(@PathParam("kommentarid") Integer kommentarid,
                                                 @FormDataParam("text") String text){

        Principal userPrincipal = securityContext.getUserPrincipal();
        String name = userPrincipal.getName();

        Integer id = null;
        if(kommentarid == null || text == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");
        try {

            id = kommentarRepository.editKommentar(text, name, kommentarid);

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }






}
