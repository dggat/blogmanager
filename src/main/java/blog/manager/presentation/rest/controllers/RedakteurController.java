package blog.manager.presentation.rest.controllers;


import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.nutzer.NutzerRepository;
import blog.manager.domain.model.redakteur.Redakteur;
import blog.manager.domain.model.redakteur.RedakteurRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("/redakteure")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class RedakteurController {

    @Inject
    private RedakteurRepository redakteurRepository;
    @Context
    private UriInfo uriInfo;

    @GET
    public Response gebeRedakteurZurueck(@QueryParam("nachname") String nachname) {

        List<Redakteur> redakteurs = new ArrayList<>();
        try {
            if(nachname == null)
                redakteurs = redakteurRepository.getAll();
            else
                redakteurs = redakteurRepository.getRedakteurByNachmaeLike(nachname);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }
        return Response.status(Response.Status.OK).entity(redakteurs).build();
    }

    @POST
    public Response fuegeRedakteurHinzu(@FormDataParam("vorname") String vorname,
                                        @FormDataParam("nachname") String nachname,
                                        @FormDataParam("biographie") String biographie,
                                        @FormDataParam("email") String email,
                                        @FormDataParam("passwort") String passwort,
                                        @FormDataParam("geschlecht") String geschlecht,
                                        @FormDataParam("geburtsdatum") String geburtsdatum,
                                        @FormDataParam("benutzername") String benutzername){
        if(email == null || passwort == null || geburtsdatum == null || geschlecht == null || benutzername == null
            || vorname == null || nachname == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");


        Nutzer nutzer = new Nutzer(email, passwort, geschlecht, geburtsdatum, benutzername);
        Redakteur redakteur = new Redakteur(nutzer, vorname, nachname, biographie);
        Integer id = null;
        try {
            id = redakteurRepository.createRedakteur(nutzer, redakteur);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }
        URI resourceLink = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
        return Response.status(Response.Status.CREATED).location(resourceLink).build();
    }
}
