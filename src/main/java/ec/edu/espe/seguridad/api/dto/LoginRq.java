package ec.edu.espe.seguridad.api.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRq {

    private String username;
    private String pwd;
}
