package blog.manager.presentation.rest.controllers;



import org.glassfish.jersey.media.multipart.FormDataParam;

import blog.manager.application.exceptions.APIError;
import blog.manager.domain.model.blogeintrag.Blogeintrag;
import blog.manager.domain.model.produktrezension.Produktrezension;
import blog.manager.domain.model.produktrezension.ProduktrezensionRepository;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Path("/produktrezensionen")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class ProduktrezensionController {

    @Inject
    private ProduktrezensionRepository produktrezensionRepository;

    @Context
    private UriInfo uriInfo;
    @Context
    SecurityContext securityContext;

    @GET
    public Response gebeProdukrezensionZurueck(@QueryParam("fazit") String fazit,
                                               @QueryParam("beschreibung") String beschreibung) {

        List<Produktrezension> produktrezensionList = new ArrayList<>();

        try {
            if(fazit != null)
                produktrezensionList = produktrezensionRepository
                        .getAllProduktrezensionByFazitLike(fazit);
            if(beschreibung != null)
                produktrezensionList = produktrezensionRepository
                        .getAllProduktrezensionByBeschreibungLike(beschreibung);
            if (fazit == null && beschreibung == null)
                produktrezensionList = produktrezensionRepository.getAll();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }

        return Response.status(Response.Status.OK).entity(produktrezensionList).build();
    }

    @RolesAllowed({"REDAKTEUR"})
    @POST
    public Response fuegeProduktrezensionHinzu(@FormDataParam("beschreibung") String beschreibung,
                                    @FormDataParam("fazit") String fazit,
                                    @FormDataParam("erstellungsdatum") String erstellungsdatum,
                                    @FormDataParam("aenderungsdatum") String aenderungsdatum,
                                    @FormDataParam("text") String text,
                                    @FormDataParam("titel") String titel) {

        if(beschreibung == null || fazit == null || erstellungsdatum == null ||  text == null ||  titel == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        Principal userPrincipal = securityContext.getUserPrincipal();

        Blogeintrag blogeintrag = new Blogeintrag(titel, text, erstellungsdatum, aenderungsdatum, userPrincipal.getName());
        Produktrezension produktrezension = new Produktrezension(blogeintrag, beschreibung, fazit);
        Integer id = null;
        try {
            id = produktrezensionRepository.craeteProduktrezension(blogeintrag, produktrezension);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError("Illegales Argument", e)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new APIError(e.getMessage(), e)).build();
        }

        URI resourceLink = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
        return Response.status(Response.Status.CREATED).location(resourceLink).build();
    }
}
