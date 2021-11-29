package com.example.budgetx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText lEmail;
    private EditText lPassword;
    private Button btnLog;
    private TextView lForgotPass;
    private TextView lSignup;

    private ProgressDialog lDialog;

    //Firebase...
    private FirebaseAuth lAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lDialog = new ProgressDialog(this);

        lAuth = FirebaseAuth.getInstance();

        login();
    }

    private void login(){
        lEmail = findViewById(R.id.email_login);
        lPassword = findViewById(R.id.password_login);
        btnLog = findViewById(R.id.btn_login);
        lForgotPass = findViewById(R.id.forgot_password);
        lSignup = findViewById(R.id.signup_reg);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lEmail.getText().toString().trim();
                String password = lPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lEmail.setError("Email is Required...");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    lPassword.setError("Password is Required...");
                    return;
                }

                lDialog.setMessage("Processing...");
                lDialog.show();

                lAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            lDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            lDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //Forgot Password Reset Activity
        lForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetActivity.class));
            }
        });

        //Create Account Activity
        lSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });
    }

}