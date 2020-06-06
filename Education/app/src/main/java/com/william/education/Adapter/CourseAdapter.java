package com.william.education.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.william.education.CourseTaskListActivity;
import com.william.education.Model.Course;
import com.william.education.R;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {

    private Activity activity;

    public CourseAdapter(Activity activity, ArrayList<Course> courseList) {
        this.activity = activity;
        this.courseList = courseList;
    }

    private ArrayList<Course> courseList;

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_course, null);
        }

        final Course course = courseList.get(position);
        TextView courseName = (TextView) convertView.findViewById(R.id.course_name);
        ImageView courseLogo = (ImageView) convertView.findViewById(R.id.course_logo);


        courseName.setText(course.getName());
        Picasso.with(activity.getApplicationContext()).load(course.getPicture()).fit().into(courseLogo);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SETIONOS",course.getName());
                Log.d("SETIONOS",course.getId().toString());

                Intent intent = new Intent(activity, CourseTaskListActivity.class);
                intent.putExtra("courseId", course.getId().toString());
                intent.putExtra("courseName", course.getName());
                intent.putExtra("courseContent", course.getContent());
                intent.putExtra("coursePrice", course.getPrice().toString());
                intent.putExtra("coursePicture", course.getPicture());

                activity.startActivity(intent);
            }
        });

        return convertView;
    }
}
