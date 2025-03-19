package com.easyexit.EasyExit.Services;

import com.easyexit.EasyExit.Entity.Form;
import com.easyexit.EasyExit.Entity.User;
import com.easyexit.EasyExit.Repository.FormRepository;
import com.easyexit.EasyExit.Repository.UserRepository;
import com.easyexit.EasyExit.Util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private FormRepository formRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Form newPass(Form form) {
        form.setStatus(Status.PENDING);
        formRepository.save(form);
        mongoTemplate.updateFirst(
                new Query(Criteria.where("rollNo").is(form.getRollNo())),
                new Update().push("passes",form),
                User.class
        );
        return form;
    }

    public List<Form> getHistory() {
        UserDetails user = authService.getCurrentUser();

        if (user == null) {
            throw new IllegalStateException("User is not authenticated");
        }

        List<Form> forms = formRepository.findAllByRollNo(user.getUsername());

        if (forms.isEmpty()) {
            return Collections.emptyList();
        }

        Collections.reverse(forms);
        return forms;
    }

    public Form getStatus() {
        List<Form> history = getHistory();
        if (history.isEmpty()) {
            throw new IllegalStateException("No recent outpass found.");
        }
        return history.get(0);
    }

    public User getProfile() {
        UserDetails user = authService.getCurrentUser();

        if (user == null) {
            throw new IllegalStateException("User is not authenticated");
        }

        User user1 = userRepository.findByRollNo(user.getUsername());
        if (user1 == null) {
            throw new IllegalStateException("User profile not found.");
        }
        user1.setPassword(null);
        return user1;
    }
}
