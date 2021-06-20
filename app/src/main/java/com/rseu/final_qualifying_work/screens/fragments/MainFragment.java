package com.rseu.final_qualifying_work.screens.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.rseu.final_qualifying_work.GroupType;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmApp;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Lesson;
import com.rseu.final_qualifying_work.screens.MainActivity;
import com.rseu.final_qualifying_work.screens.StartActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.User;


public class MainFragment extends Fragment {


    private TextView tvDateTime;
    private TextView tvName;
    private TextView tvGroupCount;
    private TextView tvLessonCount;
    private TextView tvReportCount;

    private CardView cardViewGroups;
    private CardView cardViewLessons;
    private CardView cardViewDiscipline;
    private CardView cardViewReport;
    private TextView tvDisciplineCount;
    private GoogleSignInAccount account;


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        account = GoogleSignIn.getLastSignedInAccount(requireContext());

        tvDisciplineCount = view.findViewById(R.id.tvMainFragmentDisciplineCount);
        tvGroupCount = view.findViewById(R.id.tvMainFragmentGroupCount);
        tvLessonCount = view.findViewById(R.id.tvMainFragmentLessonCount);
        tvReportCount = view.findViewById(R.id.tvMainFragmentReportCount);

        tvName = view.findViewById(R.id.tv_main_name);
        tvName.setText(account.getDisplayName());



        tvGroupCount.setText(String.valueOf(RealmService.getInstance().where(Group.class).count()));
        tvDisciplineCount.setText(String.valueOf(RealmService.getInstance().where(Discipline.class).count()));
        tvLessonCount.setText(String.valueOf(RealmService.getInstance().where(Lesson.class).count()));


        dateInit(view);
        cardInit(view);
        onClick();


        System.out.println("ALL USERS" + RealmApp.app.currentUser());
        return view;
    }

    private void dateInit(View view) {
        tvDateTime = view.findViewById(R.id.tv_main_date_time);
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd");
        tvDateTime.setText(formatForDateNow.format(date));
    }


    private void cardInit(View view) {
        cardViewGroups = view.findViewById(R.id.cardView_1);
        cardViewLessons = view.findViewById(R.id.cardView_2);
        cardViewDiscipline = view.findViewById(R.id.cardView_3);
        cardViewReport = view.findViewById(R.id.cardView_4);
    }


    public void onClick() {
        cardViewGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_groupsFragment);
            }
        });

        cardViewLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_lessonsFragment);
            }
        });
        cardViewDiscipline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_disciplineFragment);
            }
        });
        cardViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_reportFragment);
            }
        });
    }


}