package com.lesnyg.myjdbcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
    private EditText et_name;
    private TextView tv_date;
    private TextView tv_rating;
    private Button btn_check;
    private String name;
    private String date;
    private String rating;
    static ResultSet resultSet;
    static Connection connection;
    private AsyncTask<String,String,String> mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        tv_date = findViewById(R.id.tv_date);
        tv_rating = findViewById(R.id.tv_rating);
        btn_check = findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                mTask = new MySyncTask().execute();
                Toast.makeText(MainActivity.this, date+","+rating, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void query() {
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://222.122.213.216/mashw08", "mashw08", "msts0850op");
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from Su_수급자기본정보 where 수급자명='"+name+"'");

            while (resultSet.next()){
                date = resultSet.getString(3);
                tv_date.setText(date);
                rating = resultSet.getString(4);
                tv_rating.setText(rating);

            }


            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MySyncTask extends AsyncTask<String,String,String>{

        protected void onPreExecute(){}
        @Override
        protected String doInBackground(String... strings) {
            if(isCancelled())
                return null;
            query();

            return null;

        }
        protected  void onPostExecute(String result){}

        protected  void onCancelled(){
            super.onCancelled();
        }

    }
}
