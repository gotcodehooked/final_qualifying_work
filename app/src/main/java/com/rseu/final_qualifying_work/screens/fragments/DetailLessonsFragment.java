package com.rseu.final_qualifying_work.screens.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rseu.final_qualifying_work.DBOperations;
import com.rseu.final_qualifying_work.DBOperationsImpl;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Lesson;
import com.rseu.final_qualifying_work.spinner.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;


public class DetailLessonsFragment extends Fragment {


    private RealmResults<Group> realmResultsGroup;
    private RealmResults<Discipline> realmResultsDiscipline;
    private List<String> groupNameList;
    private List<String> disciplineNameList;


    private SearchableSpinner spinnerGroupsDetail;
    private SearchableSpinner spinnerDisciplineDetail;


    private TextView tvLessonName;
    private ImageButton imageButton;

    private String lessonName = "";
    private String groupName = "";
    private String disciplineName = "";
    private boolean isSelected = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_lessons, container, false);

        spinnerGroupsDetail = view.findViewById(R.id.sp_LessonGroupDetail);
        spinnerDisciplineDetail = view.findViewById(R.id.sp_LessonDisciplineDetail);
        tvLessonName = view.findViewById(R.id.tv_lessonName);
        imageButton = view.findViewById(R.id.ib_updateLessonName);
        groupNameList = new ArrayList<>();
        disciplineNameList = new ArrayList<>();

        DBOperations<Group> groupDBOperations = new DBOperationsImpl<>();
        DBOperations<Discipline> disciplineDBOperations = new DBOperationsImpl<>();

        realmResultsDiscipline = disciplineDBOperations.readData(RealmService.getInstance(), Discipline.class);
        realmResultsGroup = groupDBOperations.readData(RealmService.getInstance(), Group.class);


        if (realmResultsGroup.size() > 0) {
            alertDialog(nameListGroup(groupNameList), spinnerGroupsDetail);

        }

        if (realmResultsDiscipline.size() > 0) {
            alertDialog(nameListDiscipline(disciplineNameList), spinnerDisciplineDetail);
        }

        int lessonsCount = RealmService.getInstance().where(Lesson.class).findAll().size();


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (groupName != null && disciplineName != null && groupName.length() != 0 && disciplineName.length() != 0) {
                    tvLessonName.setText(groupName + "_" + disciplineName + " " + lessonsCount);


                    lessonName = tvLessonName.getText().toString();
                    isSelected = false;
                }


            }

        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.lesson_detail_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveButton = menu.findItem(R.id.lesson_save_item);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return true;
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

        searchableSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_customization_bad));


        if(isSelected){



        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (searchableSpinner.getId() == R.id.sp_LessonGroupDetail) {

                        groupName = arrayAdapter.getItem(position);
                        searchableSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_customization_good));
                    } else if (searchableSpinner.getId() == R.id.sp_LessonDisciplineDetail) {


                        searchableSpinner.setBackground(getResources().getDrawable(R.drawable.spinner_customization_good));
                        disciplineName = arrayAdapter.getItem(position);
                    }
                }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    }
}

