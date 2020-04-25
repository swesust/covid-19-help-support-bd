package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.adapters.TestCenterListAdapter;
import com.example.covid19shahajjo.models.TestCenter;
import com.example.covid19shahajjo.services.ServiceCallback;
import com.example.covid19shahajjo.services.TestCenterService;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestCenterActivity extends AppCompatActivity {

    private TextView areaSpinnerLabel;
    private Spinner areaSpinner;
    private ListView centerListView;

    private TestCenterService testCenterService;

    private final String LOGGER = "test_center_service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_center);
        setUserPreferableTitle();
        layoutComponentMapping();
        testCenterService = new TestCenterService();
        Logger.getLogger(LOGGER).log(Level.INFO, "onCreate called");
    }

    private void setUserPreferableTitle(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            setTitle("পরীক্ষা কেন্দ্র");
        }else{
            setTitle("Test Centers");
        }
    }

    private void layoutComponentMapping(){
        areaSpinnerLabel = (TextView) findViewById(R.id.test_center_area_spinner_label);
        areaSpinner = (Spinner) findViewById(R.id.test_center_area_spinner);
        centerListView = (ListView) findViewById(R.id.test_center_list);

        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            areaSpinnerLabel.setText("জেলা");
        }else{
            areaSpinnerLabel.setText("District");
        }
    }

    private void loadTestCenters(){
        Logger.getLogger(LOGGER).log(Level.INFO, "loadTestCenters called");
        testCenterService.getAllCenters(new ServiceCallback<List<TestCenter>>() {
            @Override
            public void onResult(List<TestCenter> list) {
                Logger.getLogger(LOGGER).log(Level.INFO, "Found: "+list.size());
                mapCentersOnView(list);
            }

            @Override
            public void onFailed(Exception exception) {
                Logger.getLogger(LOGGER).log(Level.INFO, "service failed called");
                Toast.makeText(getApplicationContext(), "Failed to load information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mapCentersOnView(List<TestCenter> testCenters){
        Logger.getLogger(LOGGER).log(Level.INFO, "Mapper called");
        TestCenterListAdapter adapter = new TestCenterListAdapter(this, R.layout.layout_test_center_item, testCenters);
        centerListView.setAdapter(adapter);
        centerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestCenter testCenter = testCenters.get(position);
                makeDialIntent(testCenter.Contact);
            }
        });
    }

    private void makeDialIntent(String number){
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:"+number));
        startActivity(dialIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.getLogger(LOGGER).log(Level.INFO, "onStart Called");
        loadTestCenters();
    }
}
