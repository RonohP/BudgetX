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

public class RegistrationActivity extends AppCompatActivity {

    private EditText rEmail;
    private EditText rPassword;
    private Button btnReg;
    private TextView rSignin;

    private ProgressDialog rDialog;

    //Firebase...
    private FirebaseAuth rAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rDialog = new ProgressDialog(this);

        rAuth=FirebaseAuth.getInstance();

        registration();
    }

    private void registration(){
        rEmail = findViewById(R.id.email_register);
        rPassword = findViewById(R.id.password_register);
        btnReg = findViewById(R.id.btn_register);
        rSignin = findViewById(R.id.sign_in);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=rEmail.getText().toString().trim();
                String password=rPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    rEmail.setError("Email Required...");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    rPassword.setError("Password Required...");
                    return;
                }

                rDialog.setMessage("Processing...");
                rDialog.show();

                rAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            rDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else{
                            rDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        //Login Activity
        rSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

}