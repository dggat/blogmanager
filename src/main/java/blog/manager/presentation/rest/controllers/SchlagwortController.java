package blog.manager.presentation.rest.controllers;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.redakteur.Redakteur;
import blog.manager.domain.model.redakteur.RedakteurRepository;
import blog.manager.domain.model.schalgwort.Schlagwort;
import blog.manager.domain.model.schalgwort.SchlagwortRepository;

import java.util.ArrayList;
import java.util.List;

@Path("/schlagworte")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class SchlagwortController {

    @Inject
    private SchlagwortRepository schlagwortRepository;
    @Context
    private UriInfo uriInfo;

    @GET
    public Response gebeSchlagworteZurueck() {

        List<Schlagwort> schlagwortList = new ArrayList<>();
        try {
            schlagwortList = schlagwortRepository.getAll();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.OK).entity(schlagwortList).build();
    }
}
