package com.rseu.final_qualifying_work.screens.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
    private TextView spinnerDiscipline;
    private TextView tvSpinnerGroups;
    private Dialog dialog;
    private List<String> groupNameList;
    private List<String> disciplineNameList;
    private FloatingActionButton floatingActionButton;

    private TextView tvSpinnerItem;
    private RealmResults<Group> realmResultsGroup;
    private RealmResults<Discipline> realmResultsDiscipline;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        spinnerGroups = (SearchableSpinner) view.findViewById(R.id.sp_LessonGroup);

        tvSpinnerGroups = view.findViewById(R.id.tv_LessonGroup);
     //   spinnerDiscipline = view.findViewById(R.id.sp_LessonDiscipline);
        floatingActionButton = view.findViewById(R.id.fb_lessonsFragment);
        groupNameList = new ArrayList<>();

        disciplineNameList = new ArrayList<>();

        DBOperations<Group> groupDBOperations = new DBOperationsImpl<>();
        DBOperations<Discipline> disciplineDBOperations = new DBOperationsImpl<>();

        realmResultsDiscipline = disciplineDBOperations.readData(RealmService.getInstance(),Discipline.class);
        realmResultsGroup = groupDBOperations.readData(RealmService.getInstance(),Group.class);


        if(realmResultsGroup.size() > 0){
            alertDialog(nameListGroup(groupNameList));
        }

//        if(realmResultsDiscipline.size() > 0){
//            alertDialog(nameListDiscipline(disciplineNameList));
//        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_lessonsFragment_to_detailLessonsFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private List<String> nameListGroup(List<String> list) {
        for (Group g : realmResultsGroup){
            list.add(g.getName());
        }
        return list;
    }

    private List<String> nameListDiscipline(List<String> list) {
        for (Discipline d : realmResultsDiscipline){
            if(d.getDisciplineName() != null && d.getDisciplineName().length() > 0){
                list.add(d.getDisciplineName());
            }

        }
        return list;
    }

    private void alertDialog(List<String> list){

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item,list);

                spinnerGroups.setAdapter(arrayAdapter);
                spinnerGroups.setTitle("Выберите группу");
                spinnerGroups.setPositiveButton("Закрыть");

                spinnerGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

    }
}

