package com.alexkorovyansky.mblock.ui.activities;

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.alexkorovyansky.mblock.R;
import com.alexkorovyansky.mblock.app.base.MbLockActivity;

/**
 * LoginActivity
 *
 * @author Alex Korovyansky <korovyansk@gmail.com>
 */
public class LoginActivity extends MbLockActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
}
