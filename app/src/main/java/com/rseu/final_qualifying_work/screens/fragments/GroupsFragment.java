package com.rseu.final_qualifying_work.screens.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rseu.final_qualifying_work.GroupType;
import com.rseu.final_qualifying_work.R;

import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.adapters.ExpandableAdapter;
import com.rseu.final_qualifying_work.model.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class GroupsFragment extends Fragment {

    private final HashMap<String, List<Group>> resultsHashMap = new HashMap<>();
    private final List<String> groupTypeList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    private Group removeGroup;
    private ExpandableListView expandableListView;
    private ExpandableAdapter expandableAdapter;

    public GroupsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_groups, container, false);

//        Toolbar toolbar = view.findViewById(R.id.discipline_toolbar);
//        setHasOptionsMenu(true);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        assert activity != null;
//        activity.setSupportActionBar(toolbar);

        floatingActionButton = view.findViewById(R.id.groupFloatingActionButton);

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

                Toast.makeText(requireContext(), groupJson, Toast.LENGTH_SHORT).show();
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
                removeClick(item);
                expandableAdapter.notifyDataSetChanged();
            }
        });


        fabClick();

        return view;
    }


    private void removeClick(Group item) {

        RealmService.getInstance().beginTransaction();

        Group group = RealmService.getInstance().where(Group.class).equalTo("name", item.getName()).findFirst();

        if (group != null) {
            System.out.println("REALM RESULT :   " + group.getName());
            group.deleteFromRealm();
        }


        RealmService.getInstance().commitTransaction();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.group_action_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_sort_cases);

    }
}

