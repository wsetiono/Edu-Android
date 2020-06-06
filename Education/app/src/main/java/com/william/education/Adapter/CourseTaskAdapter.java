package com.william.education.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.william.education.Model.CourseTask;
import com.william.education.R;

import java.util.ArrayList;

public class CourseTaskAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<CourseTask> courseTaskList;

    public CourseTaskAdapter(Activity activity, ArrayList<CourseTask> courseTaskList) {
        this.activity = activity;
        this.courseTaskList = courseTaskList;
    }


    @Override
    public int getCount() {
        return courseTaskList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseTaskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_course_task, null);
        }

        CourseTask courseTask = courseTaskList.get(position);

        TextView courseTaskTitle  = (TextView) convertView.findViewById(R.id.coursetask_title);
        TextView courseTaskNote  = (TextView) convertView.findViewById(R.id.coursetask_note);

        courseTaskTitle.setText(courseTask.getTitle());
        courseTaskNote.setText(courseTask.getNote());

        return  convertView;
    }
}
