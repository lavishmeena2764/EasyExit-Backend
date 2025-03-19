package com.easyexit.EasyExit.Repository;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Util.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends MongoRepository<Form, ObjectId> {

    List<Form> findAllByRollNo(String rollNo);

    List<Form> findAllByStatus(Status status);

    Form findByRollNoAndOtp(String rollNo, String otp);
}
