package com.berlin.berlinreview;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.berlin.berlinreview.network.DataFactoryInetntService;

public class MainActivity extends AppCompatActivity implements
        ReviewListFragment.Callbacks {
    int counter = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);

        //TODO network check and Load Offline here

        loadData();

    }


    public void loadData() {
        String url = getResources().getString(R.string.url_part_one) + String.valueOf(counter) + getResources().getString(R.string.url_part_two);
        DataFactoryInetntService.startActionDownload(this, url);
    }

    @Override
    public void onItemSelected(long id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        if (R.id.action_favorite == item.getItemId()) {
            result = true;
            showDialog();
        }

        return result;
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //  unregisterReceiver(receiver);
    }

    void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = AddReviewDialogFrament.newInstance();
        newFragment.show(ft, "dialog");
    }

}
