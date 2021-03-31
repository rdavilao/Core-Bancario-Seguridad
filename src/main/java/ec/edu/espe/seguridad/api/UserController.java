package ec.edu.espe.seguridad.api;

import ec.edu.espe.seguridad.api.dto.LoginRq;
import ec.edu.espe.seguridad.api.dto.UserRq;
import ec.edu.espe.seguridad.exception.CredentialInvalidException;
import ec.edu.espe.seguridad.exception.DocumentNotFoundException;
import ec.edu.espe.seguridad.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/corebancario/seguridad")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Autenticación de ususarios",
            notes = "Se accede al sistema dependiendo del ROL del usuario, "
            + "devuelve un token para utilizarlo en header (Authorization)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Acceso autorizado"),
            @ApiResponse(code = 400, message = "Credenciales incorrectas"),
            @ApiResponse(code = 404, message = "Usuario incorrecto")})
    public ResponseEntity<UserRq> login(@RequestBody LoginRq login) {
        try {
            return ResponseEntity
                    .ok(userService.login(login.getUsername(), login.getPwd()));
        } catch (DocumentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CredentialInvalidException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
