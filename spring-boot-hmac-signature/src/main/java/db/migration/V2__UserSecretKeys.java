package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V2__UserSecretKeys extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        String sql = """
                CREATE TABLE user_secret_keys (
                     id BIGSERIAL,
                     user_id BIGINT NOT NULL,
                     hmac_secret_key VARCHAR NOT NULL,
                     client_id VARCHAR NOT NULL,
                     expire_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     abolished_at TIMESTAMP DEFAULT NULL,
                     UNIQUE(client_id),
                     PRIMARY KEY (id)
                );
                """;
        try (var statement = context.getConnection().createStatement()) {
            statement.execute(sql);
        }
    }
}
