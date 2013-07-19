package com.locky.daggerapp;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class MainActivity extends SherlockActivity {

    @Inject
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ObjectGraph objectGraph = ObjectGraph.create(SampleModule.class);
        objectGraph.inject(this);
        Log.v(null, name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
