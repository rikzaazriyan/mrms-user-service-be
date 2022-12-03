package com.rikza.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.rikza.model.CustomResponse;

import io.agroal.api.AgroalDataSource;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    AgroalDataSource dataSource;

    public Jdbi jdbi;

    @PostConstruct
    public void init() {
        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String coba() {
        return "coba";
    }

    @POST
    @Transactional
    public Response addUser(User user) {
        boolean status = jdbi.withExtension(
                UserRepository.class,
                dao -> dao.addUser(
                        user.getMrms_user_id(),
                        user.getMrms_username(),
                        user.getMrms_password()));

        CustomResponse cr = new CustomResponse();
        if (status) {
            cr.data = user;
            cr.message = "Add User Success";
        } else
            cr.message = "Add User Failed";
        return Response.ok(cr).build();
    }

    @PUT
    @Transactional
    public Response updateUser(User user) {
        System.out.println(user.getMrms_user_id());
        boolean status = jdbi.withExtension(
                UserRepository.class,
                dao -> dao.updateUser(
                        user.getMrms_user_id(),
                        user.getMrms_username(),
                        user.getMrms_password()));

        CustomResponse cr = new CustomResponse();
        if (status) {
            cr.message = "Update User Success";
            cr.data = user;
        } else
            cr.message = "Update User Failed";
        return Response.ok(cr).build();
    }

    @Path("/{mrms_user_id}")
    @DELETE
    public Response deleteUser(@PathParam("mrms_user_id") int mrms_user_id) {
        boolean status = jdbi.withExtension(
                UserRepository.class,
                dao -> dao.deleteUserByUserId(mrms_user_id));

        CustomResponse cr = new CustomResponse();
        if (status)
            cr.message = "Delete User Success";
        else
            cr.message = "Delete User Failed";
        return Response.ok(cr).build();
    }

    @Path("/{mrms_user_id}")
    @GET
    public Response getUserById(@PathParam("mrms_user_id") int mrms_user_id) {
        User user = jdbi.withExtension(
                UserRepository.class,
                dao -> dao.getUserById(mrms_user_id));
        CustomResponse cr = new CustomResponse();
        cr.data = user;
        return Response.ok(cr).build();
    }

    public int getMaxUserId() {
        int maxId = jdbi.withExtension(
                UserRepository.class,
                dao -> dao.getLastId());

        return maxId;
    }

}
