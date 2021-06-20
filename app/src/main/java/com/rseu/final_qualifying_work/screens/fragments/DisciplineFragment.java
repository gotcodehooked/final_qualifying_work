package com.rseu.final_qualifying_work.screens.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rseu.final_qualifying_work.DBOperationsImpl;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.DisciplineAdapter;
import com.rseu.final_qualifying_work.model.Discipline;

import java.util.Objects;

public class DisciplineFragment extends Fragment {


    RecyclerView recyclerView;
    DisciplineAdapter disciplineAdapter;
    LinearLayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;


    private DBOperationsImpl<Discipline> dbOperations;

    public DisciplineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discipline, container, false);

        Toolbar toolbar = view.findViewById(R.id.discipline_toolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        activity.setSupportActionBar(toolbar);

        dbOperations = new DBOperationsImpl<>();

        floatingActionButton = view.findViewById(R.id.disciplineFloatingActionButton);
        recyclerView = view.findViewById(R.id.discipline_recyclerview);
        layoutManager = new LinearLayoutManager(requireContext().getApplicationContext());

        disciplineAdapter = new DisciplineAdapter(dbOperations.readData(RealmService.getInstance(), Discipline.class));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(disciplineAdapter);

        fabClick();

        return view;
    }


    public void fabClick() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }


    private void showAlertDialog() {
        View view_1;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        view_1 = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null);

        builder.setView(view_1);

        final EditText editText = view_1.findViewById(R.id.ed_alertFirstName);

        final AlertDialog alertDialog = builder.create();

        view_1.findViewById(R.id.btn_GroupRemoveAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Discipline discipline = new Discipline(editText.getText().toString());

                dbOperations.createData(RealmService.getInstance(), discipline);

                alertDialog.dismiss();
            }
        });

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}