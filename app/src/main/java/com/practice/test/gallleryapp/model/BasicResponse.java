package com.practice.test.gallleryapp.model;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 * Name : BasicResponse Class
 * Purpose : This is base class for the response sent from the server.
 */
public class BasicResponse {
    public int returncode;
    public int action;
    public String message;

    public BasicResponse() {
        returncode = -1;
        message = "Unexpected Error";
    }
}
