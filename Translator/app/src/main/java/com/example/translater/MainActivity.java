package com.example.translater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
private TextView tvTextTrans;
private EditText etText;
private Button btnTranslate;


    private final String URL = "https://translate.yandex.com";
    private final String KEY = "trnsl.1.1.20151210T140339Z.7a97f1c54bad842e.3a05d473053e420227feld451f3b16eb5d56cb70";

    private Gson gson = new GsonBuilder().create();

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private Link interf = retrofit.create(Link.class);


   public class QueryTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            Map<String,String> mapJson = new HashMap<>();
            mapJson.put("key",KEY);
            mapJson.put("text",etText.getText().toString());
            mapJson.put("lang","en-ru");

            Call<Object> call = interf.translate(mapJson);

            try {
                Response<Object> response = call.execute();
                Map<String,String> map = gson.fromJson(response.body().toString(),Map.class);
                for(Map.Entry e : map.entrySet()){
                    if(e.getKey().equals("text"))
                        return e.getValue().toString();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
             tvTextTrans.setText(s);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextTrans = (TextView) findViewById(R.id.tvTextTrans);
        etText = (EditText)findViewById(R.id.etText);
        btnTranslate = (Button)findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new QueryTask().execute(URL);
            }
        });

    }
}
