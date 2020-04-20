package com.mp.cpecs.ArasaacAPI;

import com.mp.cpecs.models.PictogramaRespuesta;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArasaacAPIService {
    @GET("pictograms/{locale}/search/{searchText}")
    Call<List<PictogramaRespuesta>> obtenerListaArasaac(
            @Path("locale") String locale,
            @Path("searchText") String searchText);
}
