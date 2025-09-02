package com.easyexit.EasyExit.Entity;

import com.easyexit.EasyExit.Util.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.Nullable;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "forms")
public class Form {
    @Id
    private ObjectId id;
    private String name;
    private String rollNo;
    private int sem;
    private String where;
    private String purpose;
    private String transport;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime outTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private Status status;
    @Nullable
    private String otp;
    private String rejectReason;

    public String getId() {
        return id != null ? id.toHexString() : null;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public int getSem() {
        return sem;
    }

    public String getWhere() {
        return where;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getTransport() {
        return transport;
    }

    public LocalTime getOutTime() {
        return outTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public String getOtp() {
        return otp;
    }

    @Nullable
    public String getRejectReason() {
        return rejectReason;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setOutTime(LocalTime outTime) {
        this.outTime = outTime;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
