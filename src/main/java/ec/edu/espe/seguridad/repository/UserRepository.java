package ec.edu.espe.seguridad.repository;

import ec.edu.espe.seguridad.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUser(String user);
}
