package com.course.eyecataractsap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    private EditText txtemail, txtpassword;
    ProgressBar mprogressbar;
    private Button loginbtn;
    //    private TextView googlebtn;
    private TextView registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //initialise
        txtemail = findViewById(R.id.loginEmail);
        txtpassword = findViewById(R.id.loginPassword);
        mprogressbar = findViewById(R.id.progressbar);

        loginbtn = findViewById(R.id.login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting values from the edit text
                final String  email, password;
                email = String.valueOf(txtemail.getText());
                password = String.valueOf(txtpassword.getText());


                if (!email.equals("") && !password.equals("") ) {
//start progressBar first (set visibility to VISIBLE)
                    mprogressbar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //starting write and read data with URL parameters
                            //creating array for parameter
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";


                            //creating array for data
                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;


                            PutData putData = new PutData("https://ecds.000webhostapp.com/login2.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    mprogressbar.setVisibility(View.GONE);

                                    String result = putData.getResult();
                                    //End ProgressBar(Set visibility to GONE

                                    if(result.equals("Your have successfully logged in")){
                                        //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else if(result.equals("Invalid login Details, Try Again Please")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    }else{
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                                    }

                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });





        registerbtn = findViewById(R.id.register);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }
}