package stockApp.repository;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import stockApp.model.Role;


public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
}
