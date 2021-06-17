package com.rseu.final_qualifying_work.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.RealmService;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class DisciplineAdapter extends RealmRecyclerViewAdapter<Discipline, DisciplineAdapter.ViewHolder>   {

    OrderedRealmCollection<Discipline> data;
    public DisciplineAdapter(@Nullable @org.jetbrains.annotations.Nullable OrderedRealmCollection<Discipline> data) {
        super(data,true);
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discipline_row_item,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        final Discipline obj = getItem(position);
        assert obj != null;
        holder.tvDisciplineName.setText(obj.getDisciplineName());
        holder.data = obj;

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmService.getInstance().beginTransaction();
                data.deleteFromRealm(position);
                RealmService.getInstance().commitTransaction();
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDisciplineName;
        View mView;
        ImageButton imageButton;
        public Discipline data;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.group_row_item);
            tvDisciplineName = itemView.findViewById(R.id.tvDisciplineNameRowItem);
            imageButton = itemView.findViewById(R.id.ib_deleteDisciplineRowItem);

        }
    }

}
