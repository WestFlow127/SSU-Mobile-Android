package com.app.ssumobile.ssumobile_android.models;

import java.io.Serializable;

/**
 * Created by Weston on 11/18/2015.
 */
public class SchoolModel implements Serializable {

    public String building;
    public String dean;
    public String name;
    public String admin;
    public String assistant;
    public String id;
    @Override
    public String toString(){ return name; }
}
