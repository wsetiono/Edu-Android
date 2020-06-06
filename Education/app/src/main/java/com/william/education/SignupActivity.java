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
import com.william.education.Model.entityRegisterUser;
import com.william.education.Remote.IMyAPI;
import com.william.education.Remote.RetrofitClient;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity {

    IMyAPI iMyAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText editTxt_name, editTxt_email, editTxt_password;
    Button btn_signup;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Views
        editTxt_name = (EditText)findViewById(R.id.editTxt_name);
        editTxt_email = (EditText)findViewById(R.id.editTxt_email);
        editTxt_password = (EditText)findViewById(R.id.editTxt_password);
        btn_signup = (Button)findViewById(R.id.btn_signup);

        //Init API
        iMyAPI = RetrofitClient.getInstance().create(IMyAPI.class);

        sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);

        //Event
        btn_signup.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 final AlertDialog dialog = new SpotsDialog.Builder()
                         .setContext(SignupActivity.this)
                         .build();
                 dialog.show();

                 //Create User to Login
                 entityRegisterUser user = new entityRegisterUser(editTxt_name.getText().toString(), editTxt_email.getText().toString(), editTxt_password.getText().toString());

                 compositeDisposable.add(iMyAPI.registerUser(user)
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(new Consumer<entityDataUser>() {
                             @Override
                             public void accept(entityDataUser s) throws Exception {
//                                 Toast.makeText(SignupActivity.this, s, Toast.LENGTH_SHORT).show();
//                                 Intent myIntent = new Intent(SignupActivity.this, IndexActivity.class);
//                                 SignupActivity.this.startActivity(myIntent);

                                 SharedPreferences.Editor editor = sharedPref.edit();

                                 try {
                                     editor.putString("name", s.getName());
                                     editor.putString("email", s.getEmail());
                                     editor.putString("image", s.getImage());
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                 }

                                 //editor.commit();
                                 editor.apply();

//                                 Bundle parameters = new Bundle();
//                                 parameters.putString("name", s.getName());
//                                 parameters.putString("email", s.getEmail());
//                                 parameters.putString("image", s.getImage());

                                 Intent myIntent = new Intent(SignupActivity.this, IndexActivity.class);
//                                 myIntent.putExtra("dataUser" , parameters);
                                 SignupActivity.this.startActivity(myIntent);
                             }
                         }, new Consumer<Throwable>() {
                             @Override
                             public void accept(Throwable throwable) throws Exception {
                                 dialog.dismiss();
                                 Toast.makeText(SignupActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
