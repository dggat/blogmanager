package blog.manager.presentation.rest.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.UserRepository;
import blog.manager.domain.model.bild.Bild;
import blog.manager.domain.model.bild.BildRepository;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;

@Path("/bilder")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class BildController {

    @Inject
    private BildRepository bildRepository;
    @Inject
    UserRepository userRepository;
    @Inject
    BlogeintragRepository blogeintragRepository;
    @Context
    private SecurityContext securityContext;
    @Context
    private UriInfo uriInfo;

    @RolesAllowed({"REDAKTEUR"})
    @GET
    public Response gebeBilderZurueck(@QueryParam("bezeichnung") String bezeichnung,
                                      @QueryParam("aufnahmeort") String aufnahmeort) throws SQLException {

        List<Bild> bildList = new ArrayList<>();
        try {
            if(bezeichnung != null && aufnahmeort == null)
                bildList = bildRepository.getBilderBezeichnung(bezeichnung);
            else if(bezeichnung == null && aufnahmeort != null)
                bildList = bildRepository.getBilderByAufnahmeort(aufnahmeort);
            else
                bildList = bildRepository.getAll();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.OK).entity(bildList).build();
    }

    @POST
    public Response createBild(@FormDataParam("pfad") String pfad,
                               @FormDataParam("bezeichnung") String bezeichnung,
                               @FormDataParam("aufnahmeort") String aufnahmeort){
        Bild bild = new Bild(bezeichnung, aufnahmeort, pfad);
        Integer id = null;
        if(pfad == null || bezeichnung == null || aufnahmeort == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");
        try {
            id = bildRepository.createBild(bild);

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }
        return Response.status(Response.Status.CREATED).entity(id).build();
    }


    @Path("/{bildid}")
    @RolesAllowed({"REDAKTEUR"})
    @DELETE
    public Response deleteBild(@PathParam("bildid") Integer bildid){

        if(bildid == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        try {
            bildRepository.deleteBild(bildid);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();

    }
}
