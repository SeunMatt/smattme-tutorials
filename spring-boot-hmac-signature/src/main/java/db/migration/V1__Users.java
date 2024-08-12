package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V1__Users extends BaseJavaMigration {


    @Override
    public void migrate(Context context) throws Exception {
        String sql = """
                CREATE TABLE users (
                     id BIGSERIAL,
                     email VARCHAR NOT NULL,
                     password VARCHAR NOT NULL,
                     first_name VARCHAR NOT NULL,
                     last_name VARCHAR NOT NULL,
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     abolished_at TIMESTAMP DEFAULT NULL,
                     PRIMARY KEY (id)
                );
                """;
        try (var statement = context.getConnection().createStatement()) {
            statement.execute(sql);
        }
    }
}
