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

public class Register extends AppCompatActivity {
    private EditText txtfirstname,txtlastname,txtemail,txtpassword,txtconfirm;
     ProgressBar mprogress;
    private Button btnregister;
private TextView loginform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialise
        txtemail = findViewById(R.id.email);
        txtfirstname = findViewById(R.id.firstname);
        txtlastname = findViewById(R.id.lastname);
        txtpassword = findViewById(R.id.password);
        btnregister = findViewById(R.id.btnregister);
        txtconfirm = findViewById(R.id.confirmpassword);
        mprogress = findViewById(R.id.progress);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting values from the edit text
                final String firstname, email, lastname, password, confirmpassword;
                firstname = String.valueOf(txtfirstname.getText());
                lastname = String.valueOf(txtlastname.getText());
                email = String.valueOf(txtemail.getText());
                password = String.valueOf(txtpassword.getText());
                confirmpassword = String.valueOf(txtconfirm.getText());

       if (!firstname.equals("") && !lastname.equals("") && !email.equals("") && !password.equals("") && !confirmpassword.equals("")) {
//start progressBar first (set visibility to VISIBLE)
               mprogress.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //starting write and read data with URL parameters
                            //creating array for parameter
                            String[] field = new String[5];
                            field[0] = "fname";
                            field[1] = "lname";
                            field[2] = "contact";
                            field[3] = "password";
                            field[4] = "confirmpassword";

                            //creating array for data
                            String[] data = new String[5];
                            data[0] = firstname;
                            data[1] = lastname;
                            data[2] = email;
                            data[3] = password;
                            data[4] = confirmpassword;

                            PutData putData = new PutData("https://ecds.000webhostapp.com/save_user2.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    mprogress.setVisibility(View.GONE);

                                    String result = putData.getResult();
                                    //End ProgressBar(Set visibility to GONE

                                    if(result.equals("You have successfully registered")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),Login.class);
                                        startActivity(intent);
                                        finish();

                                    }else if(result.equals("password dont match, Try again")){
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


    }
}
