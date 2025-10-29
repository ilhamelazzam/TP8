package ma.ws.jaxrs.controllers;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import ma.ws.jaxrs.entities.Compte;
import ma.ws.jaxrs.entities.Comptes;
import ma.ws.jaxrs.repositories.CompteRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Path("/banque")
@RequiredArgsConstructor
public class CompteRestJaxRSAPI {

    private final CompteRepository compteRepository;

    // LISTE (JSON + XML)
    @GET @Path("/comptes")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getComptes() {
        List<Compte> all = compteRepository.findAll();
        return Response.ok(new Comptes(all)).build(); // wrapper pour XML
    }

    // UN SEUL (JSON + XML)
    @GET @Path("/comptes/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCompte(@PathParam("id") Long id) {
        return compteRepository.findById(id)
                .map(c -> Response.ok(c).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // CREATE (JSON + XML)
    @POST @Path("/comptes")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addCompte(Compte c) {
        return Response.ok(compteRepository.save(c)).build();
    }

    // UPDATE (JSON + XML)
    @PUT @Path("/comptes/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(@PathParam("id") Long id, Compte in) {
        return compteRepository.findById(id).map(c -> {
            c.setSolde(in.getSolde());
            c.setDateCreation(in.getDateCreation());
            c.setType(in.getType());
            return Response.ok(compteRepository.save(c)).build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // DELETE
    @DELETE @Path("/comptes/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (!compteRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        compteRepository.deleteById(id);
        return Response.noContent().build();
    }
}
