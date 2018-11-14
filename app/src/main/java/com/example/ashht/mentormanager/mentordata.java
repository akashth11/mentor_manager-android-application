package com.example.ashht.mentormanager;
public class mentordata {

    public String mentor_email;
    public String mentor_password;
    public String mentorName;
    public String mentorSpecialization;
    public String req;

    public mentordata(){

    }

    public mentordata(String Request)
    {
        this.req = Request;
    }

    public mentordata(String mentorName, String Email, String mentorSpecialization, String Password) {
        this.mentor_email = Email;
        this.mentor_password = Password;
        this.mentorName = mentorName;
        this.mentorSpecialization = mentorSpecialization;

    }

    public String getMentor_email() {
        return mentor_email;
    }

    public void setMentor_email(String mentor_email) {
        this.mentor_email = mentor_email;
    }

    public String getMentor_password() {
        return mentor_password;
    }

    public void setMentor_password(String mentor_password) {
        this.mentor_password = mentor_password;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorSpecialization() {
        return mentorSpecialization;
    }

    public void setMentorSpecialization(String mentorSpecialization) {
        this.mentorSpecialization = mentorSpecialization;
    }
}
