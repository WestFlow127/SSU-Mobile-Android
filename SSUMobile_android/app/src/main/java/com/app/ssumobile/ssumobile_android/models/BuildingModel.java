package com.app.ssumobile.ssumobile_android.models;

import java.io.Serializable;

/**
 * Created by Weston on 11/18/2015.
 */
public class BuildingModel implements Serializable {

    public String Deleted;
    public String Modified;
    public String Created;
    public String id;
    public String name;

    @Override
    public String toString(){ return name; }
}
