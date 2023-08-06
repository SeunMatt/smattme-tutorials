package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

public class V2__Posts extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        var sql = """
                CREATE TABLE posts (
                     id SERIAL,
                     author_id INT NOT NULL,
                     post TEXT NOT NULL,
                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     PRIMARY KEY (id)
                );
                """;
        try(var statement = context.getConnection().createStatement()) {
            statement.execute(sql);
        }
    }
}
