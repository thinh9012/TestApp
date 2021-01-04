package com.example.myapplication;

import android.os.Bundle;

import com.example.custom.PeopleAdapter;
import com.example.model.People;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleviewActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMain;
    private static String FILE_NAME = "temp-json.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);

        JsonData();

        // Create data
        List<People> lstPeople = createPeople();

        // Set data adapter
        PeopleAdapter peopleAdapter = new PeopleAdapter(this, lstPeople);
        recyclerViewMain = findViewById(R.id.recycleViewMain);
        recyclerViewMain.setAdapter(peopleAdapter);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewMain.setItemAnimator(new DefaultItemAnimator());
        peopleAdapter.getItemId(2);
//        recyclerViewMain.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private List<People> createPeople() {
        List<People> lstResult = new ArrayList<People>();
        lstResult.add(new People("avatar1", "Name 1", 18, 0xFFCCCCCC));
        lstResult.add(new People("avatar2", "Name 2", 19, 0xFF555555));
        lstResult.add(new People("avatar3", "Name 3", 20, 0xFFCCCCCC));

        return lstResult;
    }

    private void JsonData() {
        // Read json file
        String jsonStr = StringUtils.EMPTY;
        try
        {
            InputStream iStream = getApplicationContext().getAssets().open(FILE_NAME);
            int size = iStream.available();
            byte[] buffer = new byte[size];
            iStream.read(buffer);
            iStream.close();
            jsonStr = new String(buffer, "UTF-8");
        }
        catch (IOException ie)
        {
            ie.printStackTrace();
        }

        // Read json from json string
        try
        {
            JSONArray jsonArr = new JSONArray(jsonStr);
            for(int i=0; i<jsonArr.length(); i++)
            {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                String classify = jsonObj.getString("classify");
                JSONArray items = jsonObj.getJSONArray("items");
                for(int j=0; j<items.length(); j++)
                {
                    JSONObject item = items.getJSONObject(j);
                    String enName = item.getString("en");
                    String viName = item.getString("vi");
                }
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();
        }

    }

}