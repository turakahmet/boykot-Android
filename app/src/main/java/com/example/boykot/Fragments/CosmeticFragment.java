package com.example.boykot.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boykot.Adapters.RecyclerViewBoykotAdapter;
import com.example.boykot.R;
import com.example.boykot.Service.APIService;
import com.example.boykot.Utils.APIClient;
import com.example.boykot.pojo.BoykotMarka;
import com.example.boykot.pojo.UrunTip;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CosmeticFragment extends Fragment {

    private RecyclerView boykotFoodRV;
    private APIService apiService;
    private List<BoykotMarka> tumBoykotListesi=new ArrayList<>();
    private RecyclerViewBoykotAdapter recyclerViewBoykotAdapter;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cosmetic, container, false);
        boykotFoodRV= view.findViewById(R.id.idRVCosmetic);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        apiService= APIClient.getClient().create(APIService.class);
        Call<List<BoykotMarka>> listCall=apiService.getAllBoykotUrunlerByType(UrunTip.KOZMETIK.getEtiket());
        listCall.enqueue(new Callback<List<BoykotMarka>>() {
            @Override
            public void onResponse(Call<List<BoykotMarka>> call, Response<List<BoykotMarka>> response) {
                Log.d("TAG",response.code()+"");
                if(response.isSuccessful()){
                    tumBoykotListesi.addAll(Objects.requireNonNull(response.body()));
                    recyclerViewBoykotAdapter=new RecyclerViewBoykotAdapter(getActivity(),tumBoykotListesi);
                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    boykotFoodRV.setLayoutManager(linearLayoutManager);
                    boykotFoodRV.setAdapter(recyclerViewBoykotAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<BoykotMarka>> call, Throwable t) {
                Log.d("TAG",t.getMessage()+"");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        tumBoykotListesi.clear();
    }
}