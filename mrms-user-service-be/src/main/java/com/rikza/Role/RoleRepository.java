package com.rikza.Role;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface RoleRepository {
    @SqlUpdate("INSERT INTO mrms_role ("
            + "mrms_role_id,mrms_role_name,mrms_role_description)"
            + "VALUES (:mrms_role_id,:mrms_role_name,:mrms_role_description);")
    boolean addRole(@Bind("mrms_role_id") int mrms_role_id, @Bind("mrms_role_name") String mrms_role_name,
            @Bind("mrms_role_description") String mrms_role_description);

    @SqlQuery("SELECT * FROM mrms_role "
            + "WHERE mrms_role_id=:mrms_role_id;")
    @RegisterFieldMapper(Role.class)
    Role getRoleById(@Bind("mrms_role_id") int mrms_role_id);

    @SqlQuery("SELECT MAX(mrms_role_id) FROM mrms_role")
    int getLastId();

    @SqlUpdate("UPDATE mrms_role "
            + "SET mrms_role_name=:mrms_role_name,"
            + "mrms_role_description=:mrms_role_description "
            + "WHERE mrms_role_id=:mrms_role_id;")
    boolean updateRole(
            @Bind("mrms_role_id") int mrms_role_id,
            @Bind("mrms_role_name") String mrms_role_name,
            @Bind("mrms_role_description") String mrms_role_description);

    @SqlUpdate("DELETE FROM mrms_role "
            + "WHERE mrms_user_id=:mrms_user_id;")
    boolean deleteRoleByRoleId(
            @Bind("mrms_role_id") int mrms_role_id);
}
