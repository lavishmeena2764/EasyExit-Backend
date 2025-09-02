package com.easyexit.EasyExit.Entity;

import com.easyexit.EasyExit.Util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String name;
    @Indexed(unique = true)
    private String rollNo;
    private String password;
    private Role role;
    @DBRef
    private List<Form> passes = new ArrayList<>();

    public String getId() {
        return id != null ? id.toHexString() : null;
    }

    public List<Form> getPasses() {
        return passes;
    }

    public void setPasses(List<Form> passes) {
        this.passes = passes;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
