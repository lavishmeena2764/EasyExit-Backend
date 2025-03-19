package com.easyexit.EasyExit.Controllers;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Services.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

class RejectRequest {
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/approved")
    public ResponseEntity<List<Form>> getApprovedPasses(){
        return new ResponseEntity<List<Form>>(adminService.approvedPasses(), HttpStatus.OK);
    }

    @GetMapping("/rejected")
    public ResponseEntity<List<Form>> getRejectedPasses(){
        return new ResponseEntity<List<Form>>(adminService.rejectedPasses(), HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Form>> getPendingPasses(){
        return new ResponseEntity<List<Form>>(adminService.pendingPasses(), HttpStatus.OK);
    }

    @PutMapping("/reject/{id}")
    public Form rejectPass(@PathVariable ObjectId id, @RequestBody RejectRequest req){
        if (req.getReason() == null || req.getReason().trim().isEmpty()) return new ResponseEntity<Form>(HttpStatus.BAD_REQUEST).getBody();
        return new ResponseEntity<Form>(adminService.reject(id, req.getReason()), HttpStatus.OK).getBody();
    }

    @PutMapping("/approve/{id}")
    public Form approvePass(@PathVariable ObjectId id){
        return new ResponseEntity<Form>(adminService.approve(id), HttpStatus.OK).getBody();
    }
}
