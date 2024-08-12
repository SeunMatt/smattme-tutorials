package db.callback;

import com.smattme.springboot.hmacsignature.helpers.CryptoHelper;
import com.smattme.springboot.hmacsignature.helpers.SpringContext;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class FlywayAwareDatabaseSeeder implements Callback {

    private static final Logger logger = LoggerFactory.getLogger(FlywayAwareDatabaseSeeder.class);

    @Override
    public boolean supports(Event event, Context context) {
        return Event.AFTER_MIGRATE.equals(event);
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {

        try(var statement = context.getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

            var defaultEmail = "olusola.cypher@example.com";
            var secretKey = "karibuChangeMe";
            var password = new BCryptPasswordEncoder().encode(secretKey);

            var checkQuery = "SELECT id FROM users WHERE email = '%s'".formatted(defaultEmail);

            statement.execute(checkQuery);
            ResultSet resultSet = statement.getResultSet();

            //return if the seeder has already been executed
            if(resultSet.first()) {
                logger.info("Data seeded already");
                return;
            }

            var sql = """
                    INSERT INTO users (email, first_name, last_name, password) VALUES
                    ('%s', 'Olusola', 'Cypher', '%s')
                    """.formatted(defaultEmail, password);

            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet userCreationResultSet = statement.getGeneratedKeys();
            userCreationResultSet.first();
            long userId = userCreationResultSet.getLong("id");


            //seed sample secret key

            var encryptedSecret = "u72y271p8SZ+RDPwL5SK4RGXubHDvFxwYHLutpl7xGnFOVB4ZeDXi7bu";
            var userSecretSql = """
                    INSERT INTO user_secret_keys (user_id, hmac_secret_key, client_id) VALUES
                    ('%s', '%s', '%s')
                    """.formatted(userId, encryptedSecret, defaultEmail);
            statement.execute(userSecretSql);

            logger.info("Data seeded successfully");

        } catch (SQLException e) {
            logger.error("Exception occurred while seeding data", e);
            throw new RuntimeException(e);
        }
    }



    @Override
    public String getCallbackName() {
        return FlywayAwareDatabaseSeeder.class.getSimpleName();
    }
}
