package com.example.employeeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeeapi.model.Employee;
import com.example.employeeapi.model.api.EmployeeAPI;
import com.example.employeeapi.url.URl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {
 TextView tvoutput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvoutput=findViewById(R.id.tvoutput);


        Retrofit retrofit = new    Retrofit.Builder()
                .baseUrl(URl.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();  tvoutput=findViewById(R.id.tvoutput);


        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listCall = employeeAPI.getAllEmployees();

        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error" + response.code(), Toast.LENGTH_SHORT).show();
                return;
                }

                List<Employee> empList = response.body();
                for(Employee employee : empList){
                    String data = "";
                    data += "Employee name : " + employee.getEmployee_name() + "\n";
                    data += "Employee age : " + employee.getEmployee_age() + "\n";
                    data += "Employee salary : " + employee.getEmployee_salary() + "\n";
                    data += "------------------ : "  + "\n";
                    tvoutput.append(data);
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.d("Mero msg", "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(MainActivity.this, "ERROR" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
