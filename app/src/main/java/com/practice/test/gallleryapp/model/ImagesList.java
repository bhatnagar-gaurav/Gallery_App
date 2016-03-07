package com.practice.test.gallleryapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by gaurav_bhatnagar on 3/6/2016.
 * This Class is used to populate the response from the Server.
 */
public class ImagesList extends BasicResponse implements Serializable {
    public ArrayList<Images> imagesList;
}
