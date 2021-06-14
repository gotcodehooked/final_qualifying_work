package com.rseu.final_qualifying_work.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.model.Group;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GroupTestAdapter extends RecyclerView.Adapter<GroupTestAdapter.ViewHolder> {


    private final List<Group> groupList;

    public GroupTestAdapter(List<Group> groupList){
        this.groupList = groupList;
    }

    @NonNull
    @NotNull
    @Override
    public GroupTestAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_group_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        final Group group = groupList.get(position);


    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvGroupName;
        TextView tvGroupCount;
        View mView;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mView = itemView.findViewById(R.id.group_row_item);
            tvGroupName = itemView.findViewById(R.id.tv_studentFirstName);
            tvGroupCount = itemView.findViewById(R.id.tv_StudentSecondName);
        }
    }
}
