package blog.manager.presentation.rest.controllers;


import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.chefredakteur.Chefredakteur;
import blog.manager.domain.model.chefredakteur.ChefredakteurRepository;
import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.redakteur.Redakteur;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/chefredakteure")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class ChefredakteurController {

    @Inject
    private ChefredakteurRepository chefredakteurRepository;
    @Context
    private UriInfo uriInfo;

    @POST
    public Response fuegeChefredakteurHinzu(@FormDataParam("telefonnummer") String telefonnumer,
                                        @FormDataParam("vorname") String vorname,
                                        @FormDataParam("nachname") String nachname,
                                        @FormDataParam("biographie") String biographie,
                                        @FormDataParam("email") String email,
                                        @FormDataParam("passwort") String passwort,
                                        @FormDataParam("geschlecht") String geschlecht,
                                        @FormDataParam("benutzername") String benutzername,
                                        @FormDataParam("geburtsdatum") String geburtsdatum){

        if(telefonnumer == null || vorname == null || nachname == null||email == null || passwort == null
                || geburtsdatum == null || geschlecht == null || benutzername == null || vorname == null
                || nachname == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");
        Nutzer nutzer = new Nutzer(email, passwort, geschlecht, geburtsdatum, benutzername);
        Redakteur redakteur = new Redakteur(nutzer, vorname, nachname, biographie);
        Chefredakteur chefredakteur = new Chefredakteur(nutzer, redakteur, telefonnumer, email);
        Integer id = null;

        try {
            id = chefredakteurRepository.createChefredakteur(nutzer, redakteur, chefredakteur);
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
