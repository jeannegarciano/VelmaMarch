package com.thesis.velma;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by User on 2/26/2017.
 */

public class DateListView extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // storing string resources into Array
        String[] date_list = getResources().getStringArray(R.array.date_list);

        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, date_list));

        final ListView lv = getListView();


        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String newdate = (lv.getItemAtPosition(position).toString());

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), ConflictActivity.class);
                // sending data to new activity
                i.putExtra("newdate", newdate);
                startActivity(i);

            }
        });
    }

}

