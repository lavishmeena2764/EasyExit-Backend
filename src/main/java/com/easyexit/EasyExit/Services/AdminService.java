package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Repository.FormRepository;
import com.easyexit.EasyExit.Util.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private static final SecureRandom random = new SecureRandom();
    @Autowired
    private OTPService otpService;
    @Autowired
    private FormRepository formRepository;

    public List<Form> approvedPasses(){
        List<Form> forms = formRepository.findAllByStatus(Status.APPROVED);
        Collections.reverse(forms);
        return forms;
    }

    public List<Form> rejectedPasses(){
        List<Form> forms = formRepository.findAllByStatus(Status.REJECTED);
        Collections.reverse(forms);
        return forms;
    }

    public List<Form> pendingPasses(){
        List<Form> forms = formRepository.findAllByStatus(Status.PENDING);
        Collections.reverse(forms);
        return forms;
    }

    public Form reject(ObjectId id, String reason) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found!");
        }
        System.out.println(reason);
        form.get().setStatus(Status.REJECTED);
        form.get().setRejectReason(reason);
        try{
            otpService.sendReasonByEmail(form.get().getRollNo()+"@iiita.ac.in", form.get(), reason);
            return formRepository.save(form.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the form", e);
        }
    }

    public Form approve(ObjectId id) {
        Optional<Form> form = formRepository.findById(id);
        if (form.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found!");
        }
        form.get().setStatus(Status.APPROVED);
        String otp = generateOtp();
        form.get().setOtp(otp);
        try{
            otpService.sendOtpByEmail(form.get().getRollNo()+"@iiita.ac.in", form.get(), otp);
            return formRepository.save(form.get());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending OTP", e);
        }
    }
    public String generateOtp() {
        int otp = 100_000 + random.nextInt(900_000);
        return String.valueOf(otp);
    }
}
