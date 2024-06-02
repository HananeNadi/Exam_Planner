package com.ensah.core.web.exceptions;



public class ErrorPerson {

    private String messsage;

    private int codeError;

    private String description;

    public ErrorPerson() {
    }


    public ErrorPerson(String messsage, int codeError, String description) {
        this.messsage = messsage;
        this.codeError = codeError;
        this.description = description;
    }


    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public int getCodeError() {
        return codeError;
    }

    public void setCodeError(int codeError) {
        this.codeError = codeError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}