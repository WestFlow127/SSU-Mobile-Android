package com.app.ssumobile.ssumobile_android.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Weston on 10/16/2015.
 */
// An application model used to drive the Directory Activity view
public class DepartmentModel implements Serializable {

    public String ac;
    public String office;
    public String Created;
    public String site;
    public String Modified;
    public String phone;
    public String chair;
    public String id;
    public String building;
    public String buildingName;
    public String school;
    public String displayName;
    public String name;
    public String Deleted;

    ArrayList<FacStaffModel> facStaffList2 = new ArrayList<>();

    public ArrayList<FacStaffModel> getFacStaffList() {
        return facStaffList2;
    }
    public String toString(){ return displayName.equals("null") ? name : displayName; }


}
