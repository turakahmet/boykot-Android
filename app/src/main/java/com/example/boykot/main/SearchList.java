package com.example.boykot.main;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boykot.Adapters.RecyclerViewBoykotAdapter;
import com.example.boykot.Service.APIService;
import com.example.boykot.Utils.APIClient;
import com.example.boykot.pojo.BoykotMarka;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchList extends Activity{
    private static SearchList instance;
    private LinearLayoutManager linearLayoutManager;
    private List<BoykotMarka> tumBoykotListesi=new ArrayList<>();
    private RecyclerViewBoykotAdapter recyclerViewBoykotAdapter;
    private RecyclerView boykotRV;

    public static SearchList getInstance() {
        if (instance == null) {
            instance = new SearchList();
        }
        return instance;
    }

    public void makeAPICall(String searchText, Context fragmentContext, RecyclerView fragmentRecyclerView) {
        tumBoykotListesi.clear();
        APIService apiService= APIClient.getClient().create(APIService.class);
        Call<List<BoykotMarka>> listCall=apiService.getAutocompleteUrunAdi(searchText);
        listCall.enqueue(new Callback<List<BoykotMarka>>() {
            @Override
            public void onResponse(Call<List<BoykotMarka>> call, Response<List<BoykotMarka>> response) {
                Log.d("TAG",response.code()+"");
                if(response.isSuccessful()){
                    tumBoykotListesi.addAll(Objects.requireNonNull(response.body()));
                    recyclerViewBoykotAdapter=new RecyclerViewBoykotAdapter(fragmentContext,tumBoykotListesi);
                    linearLayoutManager = new LinearLayoutManager(fragmentContext, LinearLayoutManager.VERTICAL, false);
                    fragmentRecyclerView.setLayoutManager(linearLayoutManager);
                    fragmentRecyclerView.setAdapter(recyclerViewBoykotAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<BoykotMarka>> call, Throwable t) {
                Log.d("TAG",t.getMessage()+"");
            }
        });
    }
}
