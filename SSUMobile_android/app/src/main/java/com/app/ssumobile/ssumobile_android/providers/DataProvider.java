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

    public  void getData(final boolean D, final boolean F, final boolean B, final boolean S){

        Thread runner1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if (D)
                    Djson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/department/?format=json");
                if (F)
                    Fjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/person/?format=json");
                if (B)
                    Bjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/building/?format=json");
                if (S)
                    Sjson = sendGet("https://moonlight.cs.sonoma.edu/api/v1/directory/school/?format=json");
            }
        });
        runner1.start();
        try{
            runner1.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        tryparseOutEvents(D, F, B, S);
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
    private void parseOutEvents(boolean D, boolean F, boolean B, boolean S) throws org.json.JSONException {
        System.out.println("in parseOutEvents()");

        if(D) {
            JSONArray DepJSON = new JSONArray(Djson);
            for (int i = 0; i < DepJSON.length(); i++) {
                Dep.add(jsonConverter.convertDeptJSONtoModel(DepJSON.getJSONObject(i)));
            }
        }
        if (F) {
            JSONArray FacJSON = new JSONArray(Fjson);
            for (int i = 0; i < FacJSON.length(); i++) {
                Fac.add(jsonConverter.convertPersonJSONtoModel(FacJSON.getJSONObject(i)));
            }
        }
        if(B) {
            JSONArray BuildingJSON = new JSONArray(Bjson);
            for (int i = 0; i < BuildingJSON.length(); i++) {
                Bui.add(jsonConverter.convertBuildJSONtoModel(BuildingJSON.getJSONObject(i)));
            }
        }
        if(S) {
            JSONArray SJSON = new JSONArray(Sjson);
            for (int i = 0; i < SJSON.length(); i++) {
                Sch.add(jsonConverter.convertJSONtoSchool(SJSON.getJSONObject(i)));
            }
        }
    }

    private void tryparseOutEvents(boolean D, boolean F, boolean B, boolean S){
        try {
            parseOutEvents(D, F, B, S);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
