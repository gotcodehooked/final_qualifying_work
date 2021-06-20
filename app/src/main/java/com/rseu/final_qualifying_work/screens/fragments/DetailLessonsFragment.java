package com.rseu.final_qualifying_work.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rseu.final_qualifying_work.DBOperations;
import com.rseu.final_qualifying_work.DBOperationsImpl;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.LessonDetailAdapter;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Lesson;
import com.rseu.final_qualifying_work.model.Student;
import com.rseu.final_qualifying_work.spinner.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LessonDetailAdapter lessonDetailAdapter;
    private Group group;
    private View view;
    private Toolbar toolbar;
    private List<Boolean> attendanceAndMark;
    private boolean displayMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detail_lessons, container, false);


        toolbar = view.findViewById(R.id.include_lesson_detail_toolbar);


        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        spinnerGroupsDetail = view.findViewById(R.id.sp_LessonGroupDetail);
        spinnerDisciplineDetail = view.findViewById(R.id.sp_LessonDisciplineDetail);
        tvLessonName = view.findViewById(R.id.tv_lessonName);
        imageButton = view.findViewById(R.id.ib_updateLessonName);


        groupNameList = new ArrayList<>();
        disciplineNameList = new ArrayList<>();


        //DB operations
        DBOperations<Group> groupDBOperations = new DBOperationsImpl<>();
        DBOperations<Discipline> disciplineDBOperations = new DBOperationsImpl<>();
        realmResultsDiscipline = disciplineDBOperations.readData(RealmService.getInstance(), Discipline.class);
        realmResultsGroup = groupDBOperations.readData(RealmService.getInstance(), Group.class);


        recyclerView = view.findViewById(R.id.rv_LessonDetail);
        linearLayoutManager = new LinearLayoutManager(requireContext().getApplicationContext());


        if (realmResultsGroup.size() > 0) {
            alertDialog(nameListGroup(groupNameList), spinnerGroupsDetail);

        }


        if (realmResultsDiscipline.size() > 0) {
            alertDialog(nameListDiscipline(disciplineNameList), spinnerDisciplineDetail);
        }


        nameGenerate();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.lesson_detail_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveButton = menu.findItem(R.id.lesson_save_item);
        saveButton.setIcon(R.drawable.ic_baseline_check_24);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!displayMode) {
            if (item.getItemId() == R.id.lesson_save_item) {

                createLesson(getGroupName(), getDisciplineName(), lessonName);
                Navigation.findNavController(view).navigate(R.id.lessonsFragment);
            }
        } else {
            toolbar.setTitle("Редактирование занятия");
            if (item.getItemId() == R.id.groupSaveMenu) {
                item.setIcon(R.drawable.ic_baseline_check_24);


//                editTextGroupName.setEnabled(true);
//                spinner.setEnabled(true);
//                floatingActionButton.setEnabled(true);

                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //editAGroup(groupName(),groupType,studentList);
                        //  Navigation.findNavController(view).navigate(R.id.groupsFragment);

                        return true;
                    }
                });

            }
        }
        return true;
    }


    private void nameGenerate() {

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupName != null && disciplineName != null && groupName.length() != 0 && disciplineName.length() != 0) {
                    int lessonCount = getLessonsCount() + 1;
                    tvLessonName.setText(groupName + " " + disciplineName + " " + lessonCount );

                    lessonName = tvLessonName.getText().toString();

                    for (Group g : realmResultsGroup) {

                        if (groupName.equals(g.getName())) {
                            group = g;
                        }
                    }

                    lessonDetailAdapter = new LessonDetailAdapter(getStudentList(group));
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(lessonDetailAdapter);

                    isSelected = false;
                    spinnerGroupsDetail.setBackground(getResources().getDrawable(R.drawable.spinner_customization_good));
                    spinnerDisciplineDetail.setBackground(getResources().getDrawable(R.drawable.spinner_customization_good));
                }
            }
        });
    }

    private int getLessonsCount() {
        String name = getDisciplineName();
        return RealmService.getInstance().where(Lesson.class).equalTo("disciplineName",name).findAll().size();
    }

    private String getDisciplineName() {
        String name = (String) spinnerDisciplineDetail.getSelectedItem();
        return name;
    }

    private String getGroupName(){
        String name = (String) spinnerGroupsDetail.getSelectedItem();
        return name;
    }

    private List<Student> getStudentList(Group group) {
        return new ArrayList<>(group.getStudentsList());
    }


    private void createLesson(String groupName, String disciplineName, String name) {
        DBOperations<Lesson> lessonDBOperations = new DBOperationsImpl<>();
        Lesson lessonSave = new Lesson();

        Group groupSave = RealmService.getInstance().where(Group.class).equalTo("name", groupName).findFirst();


        boolean isLessonExist = false;

        if(lessonName != null){


        lessonSave.setGroup(groupSave);
        lessonSave.setDisciplineName(disciplineName);
        lessonSave.setDateTime(dateInit());

        lessonSave.setLessonName(name);
        lessonSave.setNumberLesson(getLessonsCount() + 1);

        }



        RealmResults<Lesson> realmResults = RealmService.getInstance().where(Lesson.class).findAllAsync();


        for (Lesson lesson : realmResults) {
            if (lesson.getLessonName().equals(lessonSave.getLessonName())) {
                isLessonExist = true;
            }

        }

        if (!isLessonExist) {

            if (lessonSave.getLessonName() != null) {

                lessonDBOperations.createData(RealmService.getInstance(), lessonSave);
            } else {
                Toast.makeText(requireContext(), "Некоторые данные отсутствуют", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(requireContext(), " Такая занятие уже создано", Toast.LENGTH_LONG).show();
        }


    }

    private String dateInit() {

       return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
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


        if (isSelected) {


            searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (searchableSpinner.getId() == R.id.sp_LessonGroupDetail) {

                        groupName = arrayAdapter.getItem(position);

                    } else if (searchableSpinner.getId() == R.id.sp_LessonDisciplineDetail) {


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

