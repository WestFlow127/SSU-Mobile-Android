package com.app.ssumobile.ssumobile_android.providers;

import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;
import com.app.ssumobile.ssumobile_android.models.SchoolModel;

import org.json.JSONObject;

import java.io.Serializable;


public class ModelProvider implements Serializable {

    // get attributes of event string into an event
    public FacStaffModel convertPersonJSONtoModel(JSONObject s) throws org.json.JSONException{
        FacStaffModel currentContact = new FacStaffModel();

        currentContact.office = s.getString("office");
        currentContact.Created = s.getString("created");
        currentContact.site = s.getString("site");
        currentContact.Modified = s.getString("modified");
        currentContact.phone = s.getString("phone");
        currentContact.id = s.getString("id");
        currentContact.building = s.getString("building");
        currentContact.firstName = s.getString("first_name");
        currentContact.title = s.getString("title");
        currentContact.lastName = s.getString("last_name");
        currentContact.department = s.getString("department");
        currentContact.email = s.getString("email");

        return currentContact;
    }

    public BuildingModel convertBuildJSONtoModel(JSONObject s) throws org.json.JSONException{
        BuildingModel currentContact = new BuildingModel();

        currentContact.Created = s.getString("created");
        currentContact.Modified = s.getString("modified");
        currentContact.id = s.getString("id");
        currentContact.name = s.getString("name");
        currentContact.Deleted = s.getString("deleted");

        return currentContact;
    }

    public DepartmentModel convertDeptJSONtoModel(JSONObject s) throws org.json.JSONException{
        DepartmentModel currentContact = new DepartmentModel();

        currentContact.ac = s.getString("ac");
        currentContact.office = s.getString("office");
        currentContact.Created = s.getString("created");
        currentContact.site = s.getString("site");
        currentContact.Modified = s.getString("modified");
        currentContact.phone = s.getString("phone");
        currentContact.chair = s.getString("chair_name");
        currentContact.id = s.getString("id");
        currentContact.buildingName = s.getString("building_name");
        currentContact.building = s.getString("building");
        currentContact.school = s.getString("school");
        currentContact.displayName = s.getString("display_name");
        currentContact.name = s.getString("name");
        currentContact.Deleted = s.getString("deleted");

        return currentContact;
    }

    // get attributes of event string into an event
    public SchoolModel convertJSONtoSchool(JSONObject s) throws org.json.JSONException{
        SchoolModel currentSchool = new SchoolModel();

        currentSchool.building = s.getString("building_name");
        currentSchool.dean_name = s.getString("dean_name");
        currentSchool.admin_name = s.getString("admin_name");
        currentSchool.id = s.getString("id");
        currentSchool.name = s.getString("name");
        currentSchool.assistant= s.getString("assistant");

        return currentSchool;
    }
}
