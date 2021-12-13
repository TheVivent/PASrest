package pl.ias.pas.hotelroom.pasrest.endpoints;

import pl.ias.pas.hotelroom.pasrest.exceptions.ApplicationDaoException;
import pl.ias.pas.hotelroom.pasrest.managers.ReservationManager;
import pl.ias.pas.hotelroom.pasrest.model.Reservation;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Path("/reservation")
public class ReservationEndpoint {

    @Inject
    private ReservationManager reservationManager;

    // przykładowe zapytanie tworzące nowej rezerwacji, trza niestety uzupelniac
    // http POST localhost:8080/PASrest-1.0-SNAPSHOT/api/reservation uid=  rid=


    //CREATE\\
    @POST
    @Consumes("application/json")
    public Response createReservation(Reservation reservation) {
//        return Response.ok().build();

        UUID createdReservation;
        try {
            createdReservation = reservationManager.addReservation(reservation);
        } catch (ApplicationDaoException e) {
//            throw new WebApplicationException(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.created(URI.create("/reservation/" + createdReservation)).build();
    }

    //UPDATE\\
    @POST
    @Path("/{id}")
    @Consumes("application/json")
    public void updateReservation(@PathParam("id") String id, Reservation reservation) {
        try {
            reservationManager.updateReservation(reservationManager.getReservationById(UUID.fromString(id)), reservation);
        } catch (ApplicationDaoException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    //DELETE\\
    @DELETE
    @Path("/{id}")
//    @Consumes("application/json")
    public void archiveReservation(@PathParam("id") String id) {
        try {
            reservationManager.archiveReservation(UUID.fromString(id));
        } catch (ApplicationDaoException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    //READ\\
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Reservation getReservationById(@PathParam("id") String id) {
        try {
            return reservationManager.getReservationById(UUID.fromString(id));
        } catch (ApplicationDaoException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }

    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Reservation> getAllReservation() {
        return reservationManager.getAllReservations();
    }

    //BYID\\
    @GET
    @Path("/byID/{id}")
    @Produces("application/json")
    public List<Reservation> getActiveReservationByClient(@PathParam("id") String id, @QueryParam("client") boolean client, @QueryParam("active") boolean active) {
        try {
            return reservationManager.giveReservations(UUID.fromString(id), client, active);
        } catch (ApplicationDaoException e) {
            throw new WebApplicationException(e.getMessage());
        }
    }
//    @GET
//    @Path("/byClient/arch/{id}")
//    @Produces("application/json")
//    public List<Reservation> getArchiveReservationByClient(@PathParam("id") String id) {
//        try {
//            return resevationManager.giveReservations(UUID.fromString(id), true, true);
//        } catch (ApplicationDaoException e) {
//            throw new WebApplicationException(e.getMessage());
//        }
//    }
//
//    //ROOM\\
//    @GET
//    @Path("/byRoom/{id}")
//    @Produces("application/json")
//    public List<Reservation> getActiveReservationByRoom(@PathParam("id") String id) {
//        try {
//            return resevationManager.giveReservations(UUID.fromString(id), false, false);
//        } catch (ApplicationDaoException e) {
//            throw new WebApplicationException(e.getMessage());
//        }
//    }
//    @GET
//    @Path("/byRoom/arch/{id}")
//    @Produces("application/json")
//    public List<Reservation> getArchiveReservationByRoom(@PathParam("id") String id) {
//        try {
//            return resevationManager.giveReservations(UUID.fromString(id), false, true);
//        } catch (ApplicationDaoException e) {
//            throw new WebApplicationException(e.getMessage());
//        }
//    }


}
