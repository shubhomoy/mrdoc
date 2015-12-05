package com.bitslate.mrdoc.MrDocObjects;

import java.util.ArrayList;

/**
 * Created by shubhomoy on 5/12/15.
 */
public class Clinic {
    public int id;
    public String name;
    public float x_coord;
    public float y_coord;
    public String address;
    public String email;
    public String description;
    public String photo;
    ArrayList<Doctor> doctors;
    ArrayList<Facility> facilities;
    
    public class Facility {
        public String facility;
    }
}
