package com.rseu.final_qualifying_work.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rseu.final_qualifying_work.R;
import com.rseu.final_qualifying_work.model.Student;

import org.jetbrains.annotations.NotNull;

import java.util.List;



public class LessonDetailAdapter extends RecyclerView.Adapter<LessonDetailAdapter.ViewHolder> {

    private List<Student> data;

    public LessonDetailAdapter(List<Student> data) {

        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_row_detail_lesson, parent, false);
        return new LessonDetailAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull LessonDetailAdapter.ViewHolder holder, int position) {

        final Student obj = data.get(position);
        assert obj != null;
        holder.tvStudentFirstName.setText(obj.getFirstName());
        holder.tvStudentSecondName.setText(obj.getSecondName());
        holder.edGroupMark.setText(holder.edGroupMark.getText());

        holder.data = obj;



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
        CheckBox checkBox;
        EditText edGroupMark;

        View mView;
        public Student data;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            mView = itemView.findViewById(R.id.lesson_detail_students_row_item);
            edGroupMark = itemView.findViewById(R.id.ed_DetailLessonMark);
            checkBox = itemView.findViewById(R.id.checkBoxDetailLesson);
            tvStudentFirstName = itemView.findViewById(R.id.tv_studentNameDetailLesson);
            tvStudentSecondName = itemView.findViewById(R.id.tv_StudentSecondNameDetailLesson);

        }
    }
}
