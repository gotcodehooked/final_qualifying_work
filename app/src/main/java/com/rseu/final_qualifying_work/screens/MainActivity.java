package com.rseu.final_qualifying_work.screens;



import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmApp;

import org.jetbrains.annotations.NotNull;




public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        final NavController navController = navHostFragment != null ? navHostFragment.getNavController() : null;

        BottomNavigationView bottomNavigationView =  findViewById(R.id.bottom_navigation);

        assert navController != null;
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



   }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}