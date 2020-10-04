package com.example.healthcare;

public class RequestInsert {
    private String name;
    private String id;
    private String email;
    private Integer mobile;
    private String presc;

    public RequestInsert() {

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getMobile() {
        return mobile;
    }
    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }
    public String getPresc() {
        return presc;
    }
    public void setPresc(String presc) {
        this.presc = presc;
    }
}
//    public RequestInsert(String name, String id, String email, Integer mobile) {
//        this.name = name;
//        this.id = id;
//        this.email = email;
//        this.mobile = mobile;
//    }