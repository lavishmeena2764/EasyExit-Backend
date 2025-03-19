package com.easyexit.EasyExit.Controllers;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Services.GuardService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

class Body{
    int otp;
    String rollNo;
    public Body(int otp, String roll){
        this.otp = otp;
        this.rollNo = roll;
    }
    public int getOtp() {return this.otp;}
    public String getRollNo() {return this.rollNo;}
    public void setOtp(int otp){this.otp = otp;}
    public void setRollNo(String rollNo) {this.rollNo = rollNo;}
}

@RestController
@RequestMapping("/api/v1/guard")
public class GuardController {
    @Autowired
    private GuardService guardService;
    private Object obj = new Object();
    @GetMapping("/verified")
    public ResponseEntity<List<Form>> getVerifiedPasses(){
        return new ResponseEntity<List<Form>>(guardService.getVerified(), HttpStatus.OK);
    }

    @PutMapping("/verify/{id}")
    public Form verify(@PathVariable ObjectId id){
        return new ResponseEntity<Form>(guardService.verify(id), HttpStatus.OK).getBody();
    }

    @PostMapping("/find")
    public Form find(@RequestBody Body body){
        if (body.getRollNo() == null || body.getRollNo().trim().isEmpty()) return new ResponseEntity<Form>(HttpStatus.BAD_REQUEST).getBody();
        return new ResponseEntity<Form>(guardService.find(body.getOtp(), body.getRollNo()), HttpStatus.OK).getBody();
    }
}
