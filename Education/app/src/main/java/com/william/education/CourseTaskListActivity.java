package com.william.education;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.william.education.Adapter.CourseTaskAdapter;
import com.william.education.Model.CourseTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class CourseTaskListActivity extends AppCompatActivity {

    private ArrayList<CourseTask> courseTaskArrayList;
    private CourseTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_task_list);

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        String courseName = intent.getStringExtra("courseName");
        String courseContent = intent.getStringExtra("courseContent");
        String coursePrice = intent.getStringExtra("coursePrice");
        String coursePicture = intent.getStringExtra("coursePicture");


       getSupportActionBar().setTitle(courseName);

        TextView txtCourseName = (TextView) findViewById(R.id.txtCourseName);
        TextView txtCourseContent = (TextView) findViewById(R.id.txtCourseContent);
        TextView txtCoursePrice = (TextView) findViewById(R.id.txtCoursePrice);
        ImageView imgCoursePicture = (ImageView) findViewById(R.id.imgCoursePicture);

        txtCourseName.setText(courseName);
        txtCourseContent.setText(courseContent);
        txtCoursePrice.setText(coursePrice);
        Picasso.with(this).load(coursePicture).into(imgCoursePicture);

        courseTaskArrayList = new ArrayList<CourseTask>();
        adapter = new CourseTaskAdapter(this, courseTaskArrayList);

        ListView listView = (ListView) findViewById(R.id.course_task_list);
        listView.setAdapter(adapter);
//
//        listView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 3;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                return LayoutInflater.from(CourseTaskListActivity.this).inflate(R.layout.list_item_course_task, null);
//            }
//        });

        //Get Course Task List
        getCourseTasks(courseId);

    }

    private void getCourseTasks(String courseId) {
        String url = getString(R.string.API_URL) + "/api/projects/task/" + courseId;

        Log.d("WILLIAMSETIONOS",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("WILLIAMSETIONOS","SUKSES");
                        Log.d("WILLIAMSETIONOS", response.toString());

                        //Convert JSON data to JSON Array
                        JSONArray coursetasksJSONArray = null;
                        try{
                            coursetasksJSONArray = response.getJSONArray("projectTask");
                            Log.d("WILLIAMSETIONOS2",coursetasksJSONArray.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Convert JSON Array to Course Array
                        Gson gson = new Gson();
                        CourseTask[] coursetasks = gson.fromJson(coursetasksJSONArray.toString(), CourseTask[].class);
                        Log.d("WILLIAMSETIONOS3",coursetasks.toString());

                        //Refresh with uptodate data
                        courseTaskArrayList.clear();
                        courseTaskArrayList.addAll(new ArrayList<CourseTask>(Arrays.asList(coursetasks)));
                        Log.d("WILLIAMSETIONOS4",courseTaskArrayList.toString());

                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("WILLIAMSETIONO","FAIL");

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

}
