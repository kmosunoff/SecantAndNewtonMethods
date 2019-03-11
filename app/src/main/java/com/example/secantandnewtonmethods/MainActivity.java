package com.example.secantandnewtonmethods;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
    implements GraphFragment.OnFragmentInteractionListener,
                SolutionFragment.OnFragmentInteractionListener {

    private ActionBar toolBar;

    final Fragment graphFragment = new GraphFragment();
    final Fragment solutionFragment = new SolutionFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = graphFragment;
            switch (item.getItemId()) {
                case R.id.navigation_graph:
                    toolBar.setTitle(R.string.title_home);
                    fragment = graphFragment;
                    break;
                case R.id.navigation_dashboard:
                    toolBar.setTitle(R.string.title_dashboard);
                    fragment = solutionFragment;
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolBar = getSupportActionBar();
        toolBar.setTitle(R.string.title_home);
        fragmentManager.beginTransaction().replace(R.id.frame_container, graphFragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
