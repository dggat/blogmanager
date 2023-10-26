package blog.manager.presentation.rest.controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.nutzer.NutzerRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/nutzer")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class NutzerController {


    @Inject
    private NutzerRepository nutzerRepository;
    @Context
    private UriInfo uriInfo;

    @GET
    public Response gebeNutzerZurueck(@QueryParam("geschlecht") String geschlecht) {

        List<Nutzer> nutzers = new ArrayList<>();
        try {
            if (geschlecht == null) {
                nutzers = nutzerRepository.getAll();
            }else{
                nutzers = nutzerRepository.getNutzerByGeschlecht(geschlecht);
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.OK).entity(nutzers).build();
    }


    @POST
    public Response fuegeNutzerHinzu(@FormDataParam("email") String email,
                                     @FormDataParam("passwort") String passwort,
                                     @FormDataParam("geschlecht") String geschlecht,
                                     @FormDataParam("benutzername") String benutzername,
                                     @FormDataParam("geburtsdatum") String geburtsdatum) {

        if(email == null || passwort == null || geburtsdatum == null || geschlecht == null || benutzername == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        Nutzer nutzer = new Nutzer(email, passwort, geschlecht, benutzername, geburtsdatum);
        Integer nutzerId = null;

        try {
            nutzerId = nutzerRepository.createNutzer(nutzer);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        URI resourceLink = uriInfo.getAbsolutePathBuilder().path(String.valueOf(nutzerId)).build();
        return Response.status(Response.Status.CREATED).location(resourceLink).build();
    }
}
