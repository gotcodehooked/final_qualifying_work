package com.rseu.final_qualifying_work.screens.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rseu.final_qualifying_work.DBOperations;
import com.rseu.final_qualifying_work.DBOperationsImpl;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.DisciplineAdapter;
import com.rseu.final_qualifying_work.adapters.LessonAdapter;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Lesson;
import com.rseu.final_qualifying_work.spinner.SearchableSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableListDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;


public class LessonsFragment extends Fragment {


    private SearchableSpinner spinnerGroups;
    private SearchableSpinner spinnerDiscipline;

    private Dialog dialog;
    private List<String> groupNameList;
    private List<String> disciplineNameList;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RealmResults<Group> realmResultsGroup;
    private RealmResults<Discipline> realmResultsDiscipline;
    private String disciplineName;
    private RealmResults<Lesson> lessonRealmResults;
    private LessonAdapter lessonAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        spinnerGroups = view.findViewById(R.id.sp_LessonFragmentGroup);
        spinnerDiscipline = view.findViewById(R.id.sp_LessonFragmentDiscipline);
        floatingActionButton = view.findViewById(R.id.fb_lessonsFragment);


        groupNameList = new ArrayList<>();
        disciplineNameList = new ArrayList<>();
        DBOperations<Group> groupDBOperations = new DBOperationsImpl<>();
        DBOperations<Discipline> disciplineDBOperations = new DBOperationsImpl<>();
        realmResultsDiscipline = disciplineDBOperations.readData(RealmService.getInstance(), Discipline.class);
        realmResultsGroup = groupDBOperations.readData(RealmService.getInstance(), Group.class);

        recyclerView = view.findViewById(R.id.rv_lesson);

        linearLayoutManager = new LinearLayoutManager(requireContext().getApplicationContext());


        if (realmResultsGroup.size() > 0) {
            alertDialog(nameListGroup(groupNameList), spinnerGroups);
        }

        if (realmResultsDiscipline.size() > 0) {
            alertDialog(nameListDiscipline(disciplineNameList), spinnerDiscipline);
        }



//
//        if(isDisciplineName(spinnerDiscipline.getSelectedItem().toString())){
//            lessonRealmResults = realmResults(spinnerDiscipline.getSelectedItem().toString());
//
//        }
//
//
//
//        lessonAdapter = new LessonAdapter(lessonRealmResults);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(lessonAdapter);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(lessonAdapter);




        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_lessonsFragment_to_detailLessonsFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private boolean isDisciplineName(String disciplineName){
        if (disciplineName != null){
            return true;
        }else {

            Toast.makeText(requireContext(),"Заполните данные", Toast.LENGTH_SHORT).show();

            return false;
        }
    }

    private RealmResults<Lesson> realmResults(String disciplineName){


        if(spinnerDiscipline != null){
           lessonRealmResults = RealmService.getInstance().where(Lesson.class).equalTo("disciplineName",disciplineName).findAll();
           return lessonRealmResults;
        }else {
            return null;
        }

    }

    private List<String> nameListGroup(List<String> list) {
        for (Group g : realmResultsGroup) {
            list.add(g.getName());
        }
        return list;
    }

    private List<String> nameListDiscipline(List<String> list) {
        for (Discipline d : realmResultsDiscipline) {
            if (d.getDisciplineName() != null && d.getDisciplineName().length() > 0) {
                list.add(d.getDisciplineName());
            }

        }
        return list;
    }

    private void alertDialog(List<String> list, SearchableSpinner searchableSpinner) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item, list);
        searchableSpinner.setAdapter(arrayAdapter);


        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(searchableSpinner.getId() == spinnerDiscipline.getId()){
                    Toast.makeText(requireContext(), searchableSpinner.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
                    disciplineName = searchableSpinner.getSelectedItem().toString();
                    lessonRealmResults = realmResults(searchableSpinner.getSelectedItem().toString());
                    lessonAdapter = new LessonAdapter(lessonRealmResults);
                    recyclerView.setAdapter(lessonAdapter);
                    lessonAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(requireContext(),"KEKUS",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

