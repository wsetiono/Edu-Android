package com.william.education;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.william.education.Remote.IMyAPI;
import com.william.education.Remote.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class IndexActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    IMyAPI iMyAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.icon_menu_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        iMyAPI = RetrofitClient.getInstance().create(IMyAPI.class);
        sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                       menuItem.setChecked(true);

                       mDrawerLayout.closeDrawers();

                       int id = menuItem.getItemId();
                       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                       if(id == R.id.nav_course) {
                           transaction.replace(R.id.content_frame, new CourseListFragment()).commit();
                       } else if (id == R.id.nav_logout) {

                           //logout

                           compositeDisposable.add(iMyAPI.logoutUser()
                                   .subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(new Consumer<String>() {
                                       @Override
                                       public void accept(String s) throws Exception {
                                           Toast.makeText(IndexActivity.this, s, Toast.LENGTH_SHORT).show();

                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            //editor.clear()
                                            editor.remove("name");
                                           editor.remove("email");
                                           editor.remove("image");

                                           editor.apply();

                                            finishAffinity();

                                           Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                           startActivity(myIntent);

                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           //dialog.dismiss();
                                           Toast.makeText(IndexActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   })
                           );

                       }

                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new CourseListFragment()).commit();

        //Get user's info
        SharedPreferences sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        View header = navigationView.getHeaderView(0);
        ImageView user_avatar = (ImageView) header.findViewById(R.id.user_avatar);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);

        user_name.setText(sharedPref.getString("name", ""));
        Picasso.with(this).load(sharedPref.getString("image","")).into(user_avatar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
