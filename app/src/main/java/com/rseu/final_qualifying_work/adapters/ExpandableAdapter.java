package com.rseu.final_qualifying_work.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.model.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private HashMap<String, List<Group>> collection;
    private final List<String> groupList;
    private OnItemClickListener mOnItemClickListener;

    public ExpandableAdapter(Context context, List<String> groupList, HashMap<String, List<Group>> collection) {


        this.context = context;
        this.collection = collection;
        this.groupList = groupList;


    }

    @Override
    public int getGroupCount() {
        return collection.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return Objects.requireNonNull(collection.get(groupList.get(groupPosition))).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Group getChild(int groupPosition, int childPosition) {
        return collection.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        List<String> list = new ArrayList<>();
        list.add("Очная форма обучения");
        list.add("Заочная форма обучения");
        list.add("Магистратура");

        String groupName = list.get(groupPosition);
        String count = Integer.toString(collection.get(groupList.get(groupPosition)).size());

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.elv_row_item, null);

        }

        TextView groupType = convertView.findViewById(R.id.tv_elvGroupType);
        groupType.setText(groupName);

        TextView groupCount = convertView.findViewById(R.id.tv_elvRowItemGroupCount);
        groupCount.setText(count);

        return convertView;
    }



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View childView, ViewGroup parent) {
       String group = getChild(groupPosition,childPosition).getName();
       String studentsCount = Integer.toString(getChild(groupPosition,childPosition).getStudentsList().size());

       if(childView == null){
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           childView = inflater.inflate(R.layout.row_group_item, null);

       }

       TextView groupName = childView.findViewById(R.id.tv_studentFirstName);
       groupName.setText(group);

       TextView groupCount = childView.findViewById(R.id.tv_StudentSecondName);
        groupCount.setText(studentsCount);


        ImageButton imageButton = childView.findViewById(R.id.ib_group_remove);


       Group groupRemove = getChild(groupPosition,childPosition);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"REMOVE GROUP " + groupRemove.getName() + "   "+ groupPosition+"   "+ Integer.toString(childPosition), Toast.LENGTH_LONG).show();
                 Objects.requireNonNull(collection.get(groupList.get(groupPosition))).remove(groupRemove);
                 mOnItemClickListener.onItemClick(groupRemove);

            }
        });





//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                System.out.println("COLLECTION SIZE :  " + collection.get(groupList.get(groupPosition)).size());
//                collection.get(groupList.get(groupPosition)).remove(groupRemove);
//
//                System.out.println("COLLECTION SIZE :  " + collection.get(groupList.get(groupPosition)).size());

//            }
//        });

        return childView;
    }


    public void deleteData(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface OnItemClickListener {
        void onItemClick(Group item);
    }

}
