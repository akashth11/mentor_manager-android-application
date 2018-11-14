package com.example.ashht.mentormanager;

public class request {

    public String studentname, studentbranch;
    public String mentorname;
    public String request;

    public request(){}

    public request(String studentname, String studentbranch, String mentorname, String request) {
        this.studentname = studentname;
        this.studentbranch = studentbranch;
        this.mentorname = mentorname;
        this.request = request;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStudentbranch() {
        return studentbranch;
    }

    public void setStudentbranch(String studentbranch) {
        this.studentbranch = studentbranch;
    }

    public String getMentorname() {
        return mentorname;
    }

    public void setMentorname(String mentorname) {
        this.mentorname = mentorname;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}


