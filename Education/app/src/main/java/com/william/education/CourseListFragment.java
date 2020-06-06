package com.william.education;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.william.education.Adapter.CourseAdapter;
import com.william.education.Model.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends Fragment {

    private ArrayList<Course> courseArrayList;
    private CourseAdapter adapter;

    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        courseListView.setAdapter(new BaseAdapter() {
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
//                return LayoutInflater.from(getActivity()).inflate(R.layout.list_item_course, null);
//            }
//        });
//
//        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getContext(), CourseTaskListActivity.class);
//                startActivity(intent);
//            }
//        });

        courseArrayList = new ArrayList<Course>();
        adapter = new CourseAdapter(this.getActivity(), courseArrayList);

        ListView courseListView = (ListView) getActivity().findViewById(R.id.course_list);
        courseListView.setAdapter(adapter);

        //Get list of courses
        getCourses();
    }

    private void getCourses() {
        String url = getString(R.string.API_URL) + "/api/projects";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("COURSES LIST", response.toString());

                        //Convert JSON data to JSON Array
                        JSONArray coursesJSONArray = null;
                        try{
                            coursesJSONArray = response.getJSONArray("courses");
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Convert JSON Array to Course Array
                        Gson gson = new Gson();
                        Course[] courses = gson.fromJson(coursesJSONArray.toString(), Course[].class);

                        //Refresh ListView with up-to-date data
                        courseArrayList.clear();
                        courseArrayList.addAll(new ArrayList<Course>(Arrays.asList(courses)));
                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);

    }

}
