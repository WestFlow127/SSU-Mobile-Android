package com.app.ssumobile.ssumobile_android.providers;

import com.app.ssumobile.ssumobile_android.models.BuildingModel;
import com.app.ssumobile.ssumobile_android.models.DepartmentModel;
import com.app.ssumobile.ssumobile_android.models.FacStaffModel;
import com.app.ssumobile.ssumobile_android.models.SchoolModel;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by WestFlow on 10/5/2017.
 */

public class DataProvider implements Serializable {
    private String Djson = null;
    private String Fjson = null;
    private String Bjson = null;
    private String Sjson = null;
    private ModelProvider jsonConverter = new ModelProvider();


    public ArrayList<DepartmentModel> Dep = new ArrayList<>();
    public ArrayList<FacStaffModel> Fac = new ArrayList<>();
    public ArrayList<BuildingModel> Bui = new ArrayList<>();
    public ArrayList<SchoolModel> Sch = new ArrayList<>();

    public  void getData(){

        Thread runner1 = new Thread(new Runnable() {
            @Override
            public void run() {
                    Djson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/department/?format=json");
                    Fjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/person/?format=json");
                    Bjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/building/?format=json");
                    Sjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/school/?format=json");
            }
        });
        runner1.start();
        try{
            runner1.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        tryparseOutEvents();
    }

    private String sendGet(String url){
        try {
            final String USER_AGENT = "Mozilla/5.0";

            URL obj = new URL(url);

            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");  // optional default is GET
            con.setRequestProperty("User-Agent", USER_AGENT); //add request header
            con.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } catch (Throwable t) {
            System.out.println(t.getCause().toString());
        }
        return null;
    }
    // parse out events from body
    private void parseOutEvents() throws org.json.JSONException {
        System.out.println("in parseOutEvents()");
        JSONArray DepJSON = new JSONArray(Djson);
        for (int i = 0; i < DepJSON.length(); i++) {
            Dep.add(jsonConverter.convertDeptJSONtoModel(DepJSON.getJSONObject(i)));
        }
        JSONArray FacJSON = new JSONArray(Fjson);
        for (int i = 0; i < FacJSON.length(); i++) {
            Fac.add(jsonConverter.convertPersonJSONtoModel(FacJSON.getJSONObject(i)));
        }
        JSONArray BuildingJSON = new JSONArray(Bjson);
        for (int i = 0; i < BuildingJSON.length(); i++) {
            Bui.add(jsonConverter.convertBuildJSONtoModel(BuildingJSON.getJSONObject(i)));
        }
        JSONArray SJSON = new JSONArray(Sjson);
        for (int i = 0; i < SJSON.length(); i++) {
            Sch.add(jsonConverter.convertJSONtoSchool(SJSON.getJSONObject(i)));
        }
    }

    private void tryparseOutEvents(){
        try {
            parseOutEvents();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
