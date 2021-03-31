package ec.edu.espe.seguridad.model;

import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "usuarios")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String user;

    private String password;

    private Date dateLogin;

    private List<String> roles;
}
