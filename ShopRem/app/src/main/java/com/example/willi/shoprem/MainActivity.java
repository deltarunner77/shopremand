package com.example.willi.shoprem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getParent()!=null){
            AutoCompleteTextView autocompleteView = findViewById(R.id.autoComplete);
            autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getParent(), R.layout.autocomplete_list_item));
        }



    }
}
