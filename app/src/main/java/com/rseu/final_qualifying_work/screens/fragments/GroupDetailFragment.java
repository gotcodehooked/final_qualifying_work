package com.rseu.final_qualifying_work.screens.fragments;


import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rseu.final_qualifying_work.GroupType;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.GroupDetailAdapter;
import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Student;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class GroupDetailFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private EditText editTextGroupName;
    private Spinner spinner;
    private RecyclerView studentRecyclerView;
    private LinearLayoutManager layoutManager;
    private GroupDetailAdapter groupDetailAdapter;
    private List<Student> studentList = new ArrayList<>();
    private List<String> groupTypeList = new ArrayList<>();
    private String groupType;
    private View view;
    private Group group;
    private boolean displayMode =  false;


    public GroupDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            GroupDetailFragmentArgs args = GroupDetailFragmentArgs.fromBundle(getArguments());

            String groupJson = args.getGroupJson();

            if (!groupJson.equals("default")) {

                displayMode = true;
                GsonBuilder builder = new GsonBuilder();

                Gson gson = builder.create();

                group = gson.fromJson(groupJson, Group.class);

                if (group != null) {
                    editTextGroupName.setText(group.getName());
                    studentList.addAll(group.getStudentsList());
                    spinner.setSelection(getGroupTypeList().indexOf(group.getGroupType()));
                } else {
                    System.out.println(displayMode);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_group_detail, container, false);
        Toolbar toolbar = view.findViewById(R.id.groupDetail_toolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        viewInit(view);
        fabClick();

        return view;
    }

    private void viewInit(View view) {

        editTextGroupName = view.findViewById(R.id.ed_groupName);

        spinner = (Spinner) view.findViewById(R.id.sp_groupDetail);

        String[] strings = {"Очная форма", "Заочная форма", "Магистратура"};

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, strings);

        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groupType = getGroupTypeList().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        floatingActionButton = view.findViewById(R.id.fb_groupDetail);
        studentRecyclerView = view.findViewById(R.id.rv_groupDetail);
        layoutManager = new LinearLayoutManager(requireContext().getApplicationContext());
        groupDetailAdapter = new GroupDetailAdapter(studentList);
        studentRecyclerView.setLayoutManager(layoutManager);
        studentRecyclerView.setAdapter(groupDetailAdapter);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.group_detail_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem saveButton = menu.findItem(R.id.groupSaveMenu);

        if (!displayMode) {
            saveButton.setIcon(R.drawable.ic_baseline_check_24);
        } else {
            saveButton.setIcon(R.drawable.ic_round_edit_24);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!displayMode) {
            if (item.getItemId() == R.id.groupSaveMenu) {
                createGroup(groupName(), groupType, studentList);
                Navigation.findNavController(view).navigate(R.id.groupsFragment);
            }
        } else {
             if(item.getItemId() == R.id.groupSaveMenu){
                 item.setIcon(R.drawable.ic_baseline_check_24);
                 editAGroup(groupName(),groupType,studentList);
             }
        }
        return true;
    }

    private String groupName() {

        String groupName = null;

        if (editTextGroupName.getText().toString().length() == 0) {
            System.out.println("Lenght = 0");
        } else {
            groupName = editTextGroupName.getText().toString();

            return groupName;
        }

        return groupName;
    }

    private void fabClick() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }

    private List<String> getGroupTypeList() {

        for (GroupType s : GroupType.values()) {
            groupTypeList.add(s.toString());
        }

        return groupTypeList;
    }

    private void showAlertDialog() {
        View view_1;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        view_1 = LayoutInflater.from(requireContext()).inflate(R.layout.group_detail_alert_dialog, null);

        builder.setView(view_1);

        final EditText edFirstName = view_1.findViewById(R.id.ed_alertFirstName);
        final EditText edSecondName = view_1.findViewById(R.id.ed_alertSecondName);


        final AlertDialog alertDialog = builder.create();

        view_1.findViewById(R.id.btn_alertGroupDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Student student = new Student();

                if (edFirstName.getText() != null && edSecondName.getText() != null) {

                    student.setFirstName(edFirstName.getText().toString());

                    student.setSecondName(edSecondName.getText().toString());

                    studentList.add(student);

                }


                alertDialog.dismiss();
            }
        });

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();
    }

    private void createGroup(String groupName, String groupType, @NotNull List<Student> students) {

        Group groupSave = new Group();
        RealmList<Student> realmList = new RealmList<>();

        boolean isGroupExist = false;
        groupSave.setName(groupName);
        groupSave.setGroupType(groupType);
        realmList.addAll(students);
        groupSave.setStudentsList(realmList);

        RealmResults<Group> realmResults = RealmService.getInstance().where(Group.class).findAllAsync();

        System.out.println("ГРУППА  " + groupSave.getName() + "--" + groupSave.getGroupType());

        for (Group g : realmResults) {

            if (g.getName().equals(groupSave.getName())) {
                isGroupExist = true;

            }

        }


        if (!isGroupExist) {
            if ((groupSave.getName() != null) && (groupSave.getStudentsList().size()) != 0) {
                RealmService.getInstance().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(@NotNull Realm realm) {
                        realm.insert(groupSave);
                    }
                });
            } else {
                Toast.makeText(requireContext(), "Некоторые данные отсутствуют", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(requireContext(), " Такая группа уже существует", Toast.LENGTH_LONG).show();
        }
    }

    private void editAGroup(String groupName,String groupType, List<Student> students){



    }
}