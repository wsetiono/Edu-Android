package com.william.education;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button btn_loginscreen;
    private Button btn_signupscreen;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_loginscreen = (Button) findViewById(R.id.btn_loginscreen);
        btn_signupscreen = (Button) findViewById(R.id.btn_signupscreen);

        sharedPref = getSharedPreferences("MY_KEY", Context.MODE_PRIVATE);
        if (sharedPref.getString("name",null) != null) {
            Log.d("USER", sharedPref.getAll().toString());
            btn_loginscreen.setText("Continue as " + sharedPref.getString("name", null));
        }
        else
        {
            Log.d("USER", sharedPref.getAll().toString());
            btn_loginscreen.setText("Login");
        }

        btn_loginscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharedPref.getString("name",null) != null)
                {
                    Intent myIntent = new Intent(MainActivity.this, IndexActivity.class);
//                    myIntent.putExtra("dataUser" , parameters);
                    MainActivity.this.startActivity(myIntent);
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
//                    myIntent.putExtra("dataUser" , parameters);
                    MainActivity.this.startActivity(myIntent);
                }


            }

        });

        btn_signupscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SignupActivity.class);
                MainActivity.this.startActivity(myIntent);
            }

        });

    }
}
