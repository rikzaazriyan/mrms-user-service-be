package com.rikza.Role;

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

@Path("/role")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoleResource {

    @Inject
    AgroalDataSource dataSource;

    public Jdbi jdbi;

    @PostConstruct
    public void init() {
        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @POST
    @Transactional
    public Response addRole(Role role) {
        boolean status = jdbi.withExtension(
                RoleRepository.class,
                dao -> dao.addRole(
                        role.getMrms_role_id(),
                        role.getMrms_role_name(),
                        role.getMrms_role_description()));

        CustomResponse cr = new CustomResponse();
        if (status) {
            cr.data = role;
            cr.message = "Add Role Success";
        } else
            cr.message = "Add Role Failed";
        return Response.ok(cr).build();
    }

    @PUT
    @Transactional
    public Response updateRole(Role role) {
        System.out.println(role.getMrms_role_id());
        boolean status = jdbi.withExtension(
                RoleRepository.class,
                dao -> dao.updateRole(
                        role.getMrms_role_id(),
                        role.getMrms_role_name(),
                        role.getMrms_role_description()));

        CustomResponse cr = new CustomResponse();
        if (status) {
            cr.message = "Update Role Success";
            cr.data = role;
        } else
            cr.message = "Update Role Failed";
        return Response.ok(cr).build();
    }

    @Path("/{mrms_role_id}")
    @DELETE
    public Response deleteRole(@PathParam("mrms_role_id") int mrms_role_id) {
        boolean status = jdbi.withExtension(
                RoleRepository.class,
                dao -> dao.deleteRoleByRoleId(mrms_role_id));

        CustomResponse cr = new CustomResponse();
        if (status)
            cr.message = "Delete Role Success";
        else
            cr.message = "Delete Role Failed";
        return Response.ok(cr).build();
    }

    @Path("/{mrms_role_id}")
    @GET
    public Response getRoleById(@PathParam("mrms_role_id") int mrms_role_id) {
        Role role = jdbi.withExtension(
                RoleRepository.class,
                dao -> dao.getRoleById(mrms_role_id));
        CustomResponse cr = new CustomResponse();
        cr.data = role;
        return Response.ok(cr).build();
    }

    public int getMaxUserId() {
        int maxId = jdbi.withExtension(
                RoleRepository.class,
                dao -> dao.getLastId());

        return maxId;
    }

}
