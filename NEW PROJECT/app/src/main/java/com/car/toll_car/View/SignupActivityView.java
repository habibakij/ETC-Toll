package com.car.toll_car.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.car.toll_car.Model.PostRequestModel;
import com.car.toll_car.Model.Retrofit.ApiClint;
import com.car.toll_car.Model.Retrofit.RetrofitClint;
import com.car.toll_car.R;
import com.car.toll_car.ViewModel.LoginViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivityView extends AppCompatActivity {
    LoginViewModel loginViewModel;
    private Button btnSignUp;
    private EditText inputName, inputNumber, inputEmail, inputPassword, inputRePassword;
    private ApiClint apiClint;
    private int count=0;
    private String name, mobile, email, password, rePassword;
    PostRequestModel postRequestModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupview);

        InitialView();
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        PostApi();
    }

    public void SignUpBtn(View view) {
        Validity();
        if (count>0){
            Toast.makeText(SignupActivityView.this, "All are OK", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignupActivityView.this, OCRActivityView.class));
            setNullEditText();
        }
    }

    private void Validity (){

        int checkValid= loginViewModel.CheckValidity(inputName.getText().toString(), inputNumber.getText().toString(),
                inputEmail.getText().toString(), inputPassword.getText().toString(), inputRePassword.getText().toString());
        Log.d("checkValid",String.valueOf(checkValid));

        if (checkValid == 1){
            inputName.setError("Must enter name");
        } else if (checkValid == 2){
            inputNumber.setError("Must enter mobile");
        } else if (checkValid == 3){
            inputPassword.setError("Password at least 6 char long");
        } else if (checkValid == 4){
            inputRePassword.setError("password must similar");
        } else if (checkValid == 6){
            count++;
        }
    }

    private void InitialView() {
        inputName= findViewById(R.id.input_name);
        inputNumber= findViewById(R.id.input_number);
        inputEmail= findViewById(R.id.input_email);
        inputPassword= findViewById(R.id.input_password);
        inputRePassword= findViewById(R.id.input_re_password);
        btnSignUp= findViewById(R.id.btn_signup);
    }

    private void PostApi(){

        apiClint= RetrofitClint.getRetrifitClint().create(ApiClint.class);

        Call<PostRequestModel> call= apiClint.post(inputName.getText().toString(),inputEmail.getText().toString(),
                inputPassword.getText().toString(), inputNumber.getText().toString());
        call.enqueue(new Callback<PostRequestModel>() {
            @Override
            public void onResponse(Call<PostRequestModel> call, Response<PostRequestModel> response) {
                if (!response.isSuccessful()){
                    Log.e("request code",String.valueOf(response.code()));
                    Toast.makeText(SignupActivityView.this, "server unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }

                PostRequestModel postRequestModel= response.body();
                int statusCode= response.code();
                String name= postRequestModel.getName();
                String email= postRequestModel.getEmail();
                String pass= postRequestModel.getPassword();
                String mobile= postRequestModel.getMobile();
            }

            @Override
            public void onFailure(Call<PostRequestModel> call, Throwable t) {
                Log.e("server failed",t.getMessage());
            }
        });
    }

    private void setNullEditText(){
        inputName.setText("");
        inputNumber.setText("");
        inputEmail.setText("");
        inputPassword.setText("");
        inputRePassword.setText("");
    }

}
