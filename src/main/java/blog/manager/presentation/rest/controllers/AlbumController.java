package blog.manager.presentation.rest.controllers;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
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
import blog.manager.domain.model.album.Album;
import blog.manager.domain.model.album.AlbumRepository;
import blog.manager.domain.model.blogeintrag.Blogeintrag;

@Path("/alben")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class AlbumController {

    @Inject
    private AlbumRepository albumRepository;
    /*@Inject
    private BildRepository bildRepository;
    @Inject
    private BlogeintragRepository blogeintragRepository;*/
    @Context
    private UriInfo uriInfo;
    @Context
    SecurityContext securityContext;

    @GET
    public Response gebeAlbenZurueck(@QueryParam("titel")String titel,
                                     @QueryParam("schlagwort") String schlagwort,
                                     @QueryParam("durchschnittsbewertung") Double durchschnittsbewertung,
                                     @QueryParam("erstellungsdatum") String erstellungsdatum) {

        List<Album> alben = new ArrayList<>();
        try {


            if(titel == null && schlagwort == null && durchschnittsbewertung == null && erstellungsdatum == null) {
                alben = albumRepository.getAll();
            }
            else{
                alben = albumRepository.getByAllFilter(titel, schlagwort, durchschnittsbewertung, erstellungsdatum);
            }


        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError("Illegales Argument", e)).build();
        }catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIError(e.getMessage(), e)).build();
        }
        return Response.status(Response.Status.OK).entity(alben).build();
    }

    @RolesAllowed({"REDAKTEUR"})
    @POST
    public Response fuegeAlbumHinzu(@FormDataParam("bildid") Integer bildid,
                                     @FormDataParam("sichtbarkeit") Boolean sichtbarkeit,
                                     @FormDataParam("erstellungsdatum") String erstellungsdatum,
                                     @FormDataParam("aenderungsdatum") String aenderungsdatum,
                                     @FormDataParam("text") String text,
                                     @FormDataParam("titel") String titel) {

        if(bildid == null || sichtbarkeit == null || erstellungsdatum == null ||  text == null ||  titel == null)
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        Principal userPrincipal = securityContext.getUserPrincipal();

        Blogeintrag blogeintrag = new Blogeintrag(titel, text, erstellungsdatum, aenderungsdatum, userPrincipal.getName());
        Album album = new Album(blogeintrag, blogeintrag.getBlogeintragid(), sichtbarkeit);
        Integer id = null;
        try {
             id = albumRepository.createAlbum(blogeintrag, album, bildid);
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

    @Path("/{albumid}")
    @RolesAllowed({"CHEFREDAKTEUR"})
    @PATCH
    public Response eidtKommantarByBlogeintragId(@PathParam("albumid") Integer albumid,
                                                 @FormDataParam("titel") String titel,
                                                 @FormDataParam("sichtbarkeit") Boolean sichtbarkeit,
                                                 @FormDataParam("text") String text){
        Integer id = null;

        if (albumid == null )
            throw new BadRequestException("Es wurden Queryparameter erwartet!");

        if (titel == null && sichtbarkeit == null && text == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {

            id = albumRepository.editAlbum(albumid, sichtbarkeit, titel, text);
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
