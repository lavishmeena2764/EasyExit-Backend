package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Repository.FormRepository;
import com.easyexit.EasyExit.Util.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GuardService {
    @Autowired
    private FormRepository formRepository;

    public Form find(int otp, String rollNo){
        return formRepository.findByRollNoAndOtp(rollNo,String.valueOf(otp));
    }

    public List<Form> getVerified() {
        return formRepository.findAllByStatus(Status.VERIFIED);
    }

    public Form verify(ObjectId id) {
        Optional<Form> form = formRepository.findById(id);
        if(form.isPresent()){
            form.get().setStatus(Status.VERIFIED);
            form.get().setOtp(null);
            return formRepository.save(form.get());
        }
        throw new RuntimeException("Form not found!");
    }
}
