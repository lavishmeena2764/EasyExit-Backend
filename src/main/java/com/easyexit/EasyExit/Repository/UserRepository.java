package com.easyexit.EasyExit.Repository;

import com.easyexit.EasyExit.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByRollNo(String s);
}
