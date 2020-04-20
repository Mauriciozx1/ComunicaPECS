package com.mp.cpecs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mp.cpecs.ArasaacAPI.ArasaacAPIService;
import com.mp.cpecs.models.PictogramaRespuesta;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "Status API ";
    String locale = "es";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.arasaac.org/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void buscar(View view){
        EditText et = findViewById(R.id.myet);
        String searchText = et.getText().toString();
        if (searchText.isEmpty()){
            et.setError("Escriba antes de buscar...");
        }else{
            obtenerDatos(searchText);}
    }
    private void obtenerDatos(String searchText) {
        ArasaacAPIService service = retrofit.create(ArasaacAPIService.class);
        Call<List<PictogramaRespuesta>> call = service.obtenerListaArasaac(locale, searchText);
        call.enqueue(new Callback<List<PictogramaRespuesta>>() {
            @Override
            public void onResponse(Call<List<PictogramaRespuesta>> call, Response<List<PictogramaRespuesta>> response) {
                EditText et = findViewById(R.id.myet);
                ImageView iv = findViewById(R.id.img);
                if(response.body() == null){
                    et.setError("Palabra no encontrada en la API");
                }else {
                    List<PictogramaRespuesta> pr = response.body();
                    Log.i(TAG, "Conectado" + "-> " + pr.get(0).getId());
                    Picasso.get()
                            .load("https://api.arasaac.org/api/pictograms/" + pr.get(0).getId())
                            .into(iv);
                }
            }

            @Override
            public void onFailure(Call<List<PictogramaRespuesta>> call, Throwable t) {
                Log.e(TAG, "No conectado");
            }
        });

    }

}
