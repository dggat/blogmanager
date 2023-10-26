package blog.manager.presentation.rest.controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.UserRepository;
import blog.manager.domain.model.blogeintrag.Blogeintrag;
import blog.manager.domain.model.blogeintrag.BlogeintragRepository;
import blog.manager.domain.model.kommentar.Kommentar;
import blog.manager.domain.model.kommentar.KommentarForAnwender;
import blog.manager.domain.model.kommentar.KommentarRepository;
import blog.manager.domain.model.schalgwort.Schlagwort;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/blogeintraege")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class BlogeintragController {

    @Inject
    private BlogeintragRepository blogeintragRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    KommentarRepository kommentarRepository;

    @Context
    private SecurityContext securityContext;
    @Context
    private UriInfo uriInfo;


    @Path("/{blogeintragid}/schlagworte")
    @GET
    public Response getSchlagworteByBlogeintragId(@PathParam("blogeintragid") Integer blogeintrageid) {

        List<Schlagwort> schlagwortList = new ArrayList<>();
        if(blogeintrageid == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        try {
            schlagwortList = blogeintragRepository.getSchalworteByBlogeintragId(blogeintrageid);
        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.OK).entity(schlagwortList).build();
    }

    @Path("/{blogeintragid}/schlagworte")
    @RolesAllowed({"REDAKTEUR"})
    @POST
    public Response setSchlagwortByBlogeintragId(@PathParam("blogeintragid") Integer blogeintragid,
                                                  @FormDataParam("bezeichnung") String bezeichnung){
        Integer id = null;
        if(blogeintragid == null || bezeichnung == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");
        try {

            id = blogeintragRepository.setSchlagwort(blogeintragid, bezeichnung);

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    @Path("/{blogeintragid}/kommentare")
    @GET
    public Response getKommentareByBlogeintragId(@PathParam("blogeintragid") Integer blogeintragid){

        if(blogeintragid == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        List<KommentarForAnwender> kommentarList = new ArrayList<>();
        try {
            kommentarList = blogeintragRepository.getAllKommentarByBlogeintragId(blogeintragid);
        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.OK).entity(kommentarList).build();
    }

    @Path("/{blogeintragid}/bewertung")
    @RolesAllowed({"NUTZER"})
    @POST
    public Response setBewertingByBlogeintragId(@PathParam("blogeintragid") Integer blogeintragid,
                                                    @FormDataParam("punktzahl") Integer punktzahl){

        Principal userPrincipal = securityContext.getUserPrincipal();
        String name = userPrincipal.getName();

        Integer id = null;
        if(blogeintragid == null || punktzahl == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");
        try {
            id = blogeintragRepository.setBewertung(name, blogeintragid, punktzahl);

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }
        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    @Path("/{blogeintragid}/kommentare")
    @RolesAllowed({"NUTZER"})
    @POST
    public Response createKommantar(@PathParam("blogeintragid") Integer blogeintragid,
                                                    @FormDataParam("text") String text) {

        if(blogeintragid == null || text == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        Principal userPrincipal = securityContext.getUserPrincipal();
        String name = userPrincipal.getName();

        Integer id = null;

        try {

            id = blogeintragRepository.setKommentar(name, blogeintragid, text);

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.CREATED).entity(id).build();
    }


    @Path("/{blogeintragid}")
    @RolesAllowed({"CHEFREDAKTEUR"})
    @DELETE
    public Response deleteBlogeintragId(@PathParam("blogeintragid") Integer blogeintragid) {

        if(blogeintragid == null )
            throw new BadRequestException("Es wurden Queryparameter erwartet!");
        try {
            blogeintragRepository.deleteBlogeintrag(blogeintragid);

        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
