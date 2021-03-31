package ec.edu.espe.seguridad.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRq {

    private String user;
    private String token;
}
