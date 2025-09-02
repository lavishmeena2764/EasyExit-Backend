package com.easyexit.EasyExit.Controllers;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Entity.User;
import com.easyexit.EasyExit.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/new")
    public ResponseEntity<?> newPass(@RequestBody Form form){
        if (form.getName() == null ||
                form.getRollNo() == null ||
                form.getWhere() == null ||
                form.getPurpose() == null ||
                form.getTransport() == null ||
                form.getDate() == null ||
                form.getSem() > 8 ||
                form.getSem() <= 0 ||
                form.getOutTime() == null) {
            return new ResponseEntity<>("\"Missing required fields! Please provide name, roll number, sem, destination, purpose, transport, date, and out time.\"",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Form>(studentService.newPass(form), HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Form>> getHistory(){
        return new ResponseEntity<List<Form>>(studentService.getHistory(),HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<Form> getStatus(){
        return new ResponseEntity<Form>(studentService.getStatus(),HttpStatus.OK);
    }

}
