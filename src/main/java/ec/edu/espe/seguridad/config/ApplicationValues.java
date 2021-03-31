package ec.edu.espe.seguridad.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationValues {

    private final String mongoHost;
    private final String mongoDb;
    private final String username;
    private final String pwd;

    @Autowired
    public ApplicationValues(
            @Value("${security.mongo.host}") String mongoHost,
            @Value("${security.mongo.db}") String mongoDb,
            @Value("${security.mongo.username}") String username,
            @Value("${security.mongo.pwd}") String pwd) {
        this.mongoHost = mongoHost;
        this.mongoDb = mongoDb;
        this.username = username;
        this.pwd = pwd;
    }

}
