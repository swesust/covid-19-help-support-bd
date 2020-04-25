package com.example.covid19shahajjo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.models.TestCenter;

import java.util.List;

public class TestCenterListAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<TestCenter> list;

    public TestCenterListAdapter(Context context, int resource, List<TestCenter> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null){
            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(this.resource, parent, false);
        }

        TestCenter testCenter = list.get(position);

        TextView testCenterName = row.findViewById(R.id.test_center_item_name);
        TextView testCenterDistrict = row.findViewById(R.id.test_center_item_district);
        TextView testCenterContact =  row.findViewById(R.id.test_center_item_contact);

        testCenterName.setText(testCenter.Name);
        testCenterDistrict.setText(testCenter.District);
        testCenterContact.setText(testCenter.Contact);

        return row;
    }

    private LayoutInflater getLayoutInflater(){
        return (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
