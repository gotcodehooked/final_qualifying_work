package com.rseu.final_qualifying_work.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.model.Discipline;
import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Student;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.ViewHolder> {

    private List<Student> data;

    public GroupDetailAdapter(List<Student> data) {

        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row_item, parent, false);
        return new GroupDetailAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull GroupDetailAdapter.ViewHolder holder, int position) {

        final Student obj = data.get(position);
        assert obj != null;
        holder.tvStudentFirstName.setText(obj.getFirstName());
        holder.tvStudentSecondName.setText(obj.getSecondName());
        holder.data = obj;

        holder.ibStudentRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStudent(position);
            }
        });


    }


    private void deleteStudent(int position) {

        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());

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

        TextView tvStudentFirstName;
        TextView tvStudentSecondName;
        ImageButton ibStudentRemove;
        View mView;
        public Student data;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.student_row_item);
            ibStudentRemove = itemView.findViewById(R.id.ib_student_row_item);
            tvStudentFirstName = itemView.findViewById(R.id.tv_studentFirstName);
            tvStudentSecondName = itemView.findViewById(R.id.tv_StudentSecondName);

        }
    }
}
