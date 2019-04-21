package com.albums.myalbums.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.albums.myalbums.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            setTitle(getString(R.string.users));
            UserFragment fragment = UserFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, UserFragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            setTitle(getString(R.string.users));
            UserFragment fragment = (UserFragment)getSupportFragmentManager().findFragmentByTag(UserFragment.TAG);
            if(fragment == null){
                fragment = UserFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment, UserFragment.TAG).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
