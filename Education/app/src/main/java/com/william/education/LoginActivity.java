package com.william.education;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.william.education.Model.entityDataUser;
import com.william.education.Model.entityLoginUser;
import com.william.education.Remote.IMyAPI;
import com.william.education.Remote.RetrofitClient;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    IMyAPI iMyAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText editTxt_name, editTxt_email, editTxt_password;
    Button btn_login;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Views
        editTxt_email = (EditText)findViewById(R.id.editTxt_email);
        editTxt_password = (EditText)findViewById(R.id.editTxt_password);
        btn_login = (Button)findViewById(R.id.btn_login);

        //Init API
        iMyAPI = RetrofitClient.getInstance().create(IMyAPI.class);

        sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        //Event
        btn_login.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 final AlertDialog dialog = new SpotsDialog.Builder()
                         .setContext(LoginActivity.this)
                         .build();
                 dialog.show();

                 //Create User to Login
                 entityLoginUser user = new entityLoginUser(editTxt_email.getText().toString(), editTxt_password.getText().toString());

                 compositeDisposable.add(iMyAPI.loginUser(user)
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(new Consumer<entityDataUser>() {
                                     @Override
                             public void accept(entityDataUser s) throws Exception {
                                 //Toast.makeText(LoginActivity.this, s.getName(), Toast.LENGTH_SHORT).show();

                                         SharedPreferences.Editor editor = sharedPref.edit();

                                         try {
                                            editor.putString("name", s.getName());
                                            editor.putString("email", s.getEmail());
                                            editor.putString("image", s.getImage());
                                         } catch (Exception e) {
                                            e.printStackTrace();
                                         }

                                         editor.apply();

                                 Intent myIntent = new Intent(LoginActivity.this, IndexActivity.class);
                                 LoginActivity.this.startActivity(myIntent);

                             }
                         }, new Consumer<Throwable>() {
                             @Override
                             public void accept(Throwable throwable) throws Exception {
                                dialog.dismiss();
                                 Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         })
                 );

             }

        }

        );

    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
