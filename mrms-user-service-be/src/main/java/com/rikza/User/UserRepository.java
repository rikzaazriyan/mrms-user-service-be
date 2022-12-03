package com.rikza.User;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserRepository {
        @SqlUpdate("INSERT INTO mrms_user ("
                        + "mrms_user_id,mrms_username,mrms_password)"
                        + "VALUES (:mrms_user_id,:mrms_username,:mrms_password);")
        boolean addUser(@Bind("mrms_user_id") int mrms_user_id, @Bind("mrms_username") String mrms_username,
                        @Bind("mrms_password") String mrms_password);

        @SqlQuery("SELECT * FROM mrms_user "
                        + "WHERE mrms_user_id=:mrms_user_id;")
        @RegisterFieldMapper(User.class)
        User getUserById(@Bind("mrms_user_id") int mrms_user_id);

        @SqlQuery("SELECT MAX(mrms_user_id) FROM mrms_user")
        int getLastId();

        @SqlUpdate("UPDATE mrms_user "
                        + "SET mrms_username=:mrms_username,"
                        + "mrms_password=:mrms_password "
                        + "WHERE mrms_user_id=:mrms_user_id;")
        boolean updateUser(
                        @Bind("mrms_user_id") int mrms_user_id,
                        @Bind("mrms_username") String mrms_username,
                        @Bind("mrms_password") String mrms_password);

        @SqlUpdate("DELETE FROM mrms_user "
                        + "WHERE mrms_user_id=:mrms_user_id;")
        boolean deleteUserByUserId(
                        @Bind("mrms_user_id") int mrms_user_id);
}
