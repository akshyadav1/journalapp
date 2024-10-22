package net.engineeringdigest.journalapp.repo;

import net.engineeringdigest.journalapp.enteties.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String user);

    void deleteByUserName(String userName);
}
