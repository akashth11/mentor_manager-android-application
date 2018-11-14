package com.example.ashht.mentormanager;

public class requestresult {

    public String result;

    public String studentname;
    public  String mentorname;

    public requestresult(){

    }

    public requestresult(String result,String studentname, String mentorname) {
        this.result = result;
        this.studentname = studentname;
        this.mentorname = mentorname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getMentorname() {
        return mentorname;
    }

    public void setMentorname(String mentorname) {
        this.mentorname = mentorname;
    }
}
