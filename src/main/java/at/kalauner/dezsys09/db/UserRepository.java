package at.kalauner.dezsys09.db;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, String> {
}