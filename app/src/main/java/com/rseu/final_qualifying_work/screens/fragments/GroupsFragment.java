package com.rseu.final_qualifying_work.screens.fragments;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rseu.final_qualifying_work.DBOGroupImpl;
import com.rseu.final_qualifying_work.DBOperations;
import com.rseu.final_qualifying_work.DBOperationsImpl;
import com.rseu.final_qualifying_work.GroupType;
import com.rseu.final_qualifying_work.R;

import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.ExpandableAdapter;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class GroupsFragment extends Fragment {

    private final HashMap<String, List<Group>> resultsHashMap = new HashMap<>();
    private final List<String> groupTypeList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private Group removeGroup;
    private ExpandableListView expandableListView;
    private ExpandableAdapter expandableAdapter;
    DBOperations<Group> groupDBOperations;

    private RealmChangeListener realmListener;

    public GroupsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        floatingActionButton = view.findViewById(R.id.groupFloatingActionButton);
        expandableListView = view.findViewById(R.id.elv_groupFragment);
        expandableAdapter = new ExpandableAdapter(requireContext(), getGroupTypeList(), getResultsHashMap());
        expandableListView.setAdapter(expandableAdapter);
        groupDBOperations = new DBOGroupImpl();

        expandableListView = view.findViewById(R.id.elv_groupFragment);
        expandableAdapter = new ExpandableAdapter(requireContext(), getGroupTypeList(), getResultsHashMap());
        expandableListView.setAdapter(expandableAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Group group = expandableAdapter.getChild(groupPosition, childPosition);
                Group group1 = RealmService.getInstance().copyFromRealm(group);
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                String groupJson = gson.toJson(group1);

                GroupsFragmentDirections.ActionGroupsFragmentToGroupDetailFragment action =
                        GroupsFragmentDirections.actionGroupsFragmentToGroupDetailFragment();
                action.setGroupJson(groupJson);
                Navigation.findNavController(view).navigate(action);

                return true;
            }
        });

        expandableAdapter.deleteData(new ExpandableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Group item) {
                showAlertDialog(groupDBOperations, item);

            }
        });

        fabClick();

        return view;
    }


    private void showAlertDialog(DBOperations<Group> dbOperations, Group item) {
        View view_1;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        view_1 = LayoutInflater.from(requireContext()).inflate(R.layout.group_remove_alert_dialog, null);

        builder.setView(view_1);

        final AlertDialog alertDialog = builder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();

        TextView tvGroupName = view_1.findViewById(R.id.tv_alertDialogGroupName);
        tvGroupName.setText(item.getName());


        view_1.findViewById(R.id.btn_GroupRemoveAccept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbOperations.deleteData(RealmService.getInstance(), Group.class, "name", item.getName());
                expandableAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });

        view_1.findViewById(R.id.btn_groupRemoveCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private List<String> getGroupTypeList() {

        if (groupTypeList.size() == 0) {

            for (GroupType s : GroupType.values()) {
                groupTypeList.add(s.toString());
            }
        }
        return groupTypeList;
    }

    private HashMap<String, List<Group>> getResultsHashMap() {

        for (String s : getGroupTypeList()) {
            resultsHashMap.put(s, realmResults(s));
        }

        return resultsHashMap;
    }

    private List<Group> realmResults(String groupType) {

        RealmResults<Group> query = RealmService.getInstance().where(Group.class).equalTo("groupType", groupType).findAllAsync();

        return new ArrayList<>(query);
    }

    public void fabClick() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.groupDetailFragment);
            }
        });

    }


}

