package com.rseu.final_qualifying_work.screens.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.DisciplineAdapter;
import com.rseu.final_qualifying_work.model.Discipline;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisciplineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisciplineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    DisciplineAdapter disciplineAdapter;
    LinearLayoutManager layoutManager;
    private FloatingActionButton floatingActionButton;


    public DisciplineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisciplineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisciplineFragment newInstance(String param1, String param2) {
        DisciplineFragment fragment = new DisciplineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        floatingActionButton = view.findViewById(R.id.disciplineFloatingActionButton);



        recyclerView = view.findViewById(R.id.discipline_recyclerview);
        layoutManager = new LinearLayoutManager(requireContext().getApplicationContext());
        disciplineAdapter = new DisciplineAdapter(RealmService.getInstance().where(Discipline.class).findAllAsync());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(disciplineAdapter);




        fabClick();

        return view;
    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.discipline_app_bar ,menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem searchItem = menu.findItem(R.id.discipline_bar_search);

        SearchView searchView = new SearchView(requireActivity());
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        searchItem.setActionView(searchView);



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

        final EditText editText= view_1.findViewById(R.id.ed_alertFirstName);


        final AlertDialog alertDialog = builder.create();

        view_1.findViewById(R.id.btn_alertGroupDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Discipline discipline = new Discipline(editText.getText().toString());

                RealmService.getInstance().executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(@NotNull Realm realm) {
                        realm.insert(discipline);
                    }
                });

                alertDialog.dismiss();
            }
        });

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
      // RealmService.getInstance().close();
    }
}