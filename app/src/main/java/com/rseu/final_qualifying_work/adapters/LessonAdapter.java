package com.rseu.final_qualifying_work.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Lesson;
import com.rseu.final_qualifying_work.model.Student;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class LessonAdapter extends RealmRecyclerViewAdapter<Lesson, LessonAdapter.ViewHolder> {


    private final OrderedRealmCollection<Lesson> data;
    boolean isExpanded = false;
    public LessonAdapter(@Nullable @org.jetbrains.annotations.Nullable OrderedRealmCollection<Lesson> data) {
        super(data,true);
        this.data = data;
        //notifyDataSetChanged();
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_expandable_row_item, parent, false);
        return new LessonAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull LessonAdapter.ViewHolder holder, int position) {


        final Lesson obj = data.get(position);
        assert obj != null;
        holder.data = obj;


        holder.tvLessonName.setText(obj.getLessonName());
        holder.tvDateTime.setText(obj.getDateTime());

        obj.setExpanded(false);

        holder.tvLessonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(!obj.isExpanded()){
                        holder.constraintLayout.setVisibility(View.VISIBLE);
                    }else {
                        holder.constraintLayout.setVisibility(View.GONE);
                    }
                    obj.setExpanded(!obj.isExpanded());
            }
        });




    }




    @Override
    public int getItemCount() {

        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLessonName;
        TextView tvDateTime;
        TextView tvAdentencyTrue;
        TextView getTvAdentencyFalse;
        ConstraintLayout constraintLayout;
        View mView;
        public Lesson data;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.lesson_expandable_row_item);

            tvLessonName = itemView.findViewById(R.id.tv_LessonName_row_item);
            tvDateTime = itemView.findViewById(R.id.tv_lessonDateTime);
            tvAdentencyTrue = itemView.findViewById(R.id.tv_attendency_true);
            getTvAdentencyFalse = itemView.findViewById(R.id.tv_attendency_false);
            constraintLayout = itemView.findViewById(R.id.expandableLayout);



        }
    }
}
