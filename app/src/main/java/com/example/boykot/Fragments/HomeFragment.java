package com.example.boykot.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boykot.Adapters.RecyclerViewBoykotAdapter;
import com.example.boykot.R;
import com.example.boykot.Service.APIService;
import com.example.boykot.Utils.APIClient;
import com.example.boykot.pojo.BoykotMarka;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView boykotRV;
    private APIService apiService;
    private List<BoykotMarka> tumBoykotListesi=new ArrayList<>();
    private RecyclerViewBoykotAdapter recyclerViewBoykotAdapter;
    private LinearLayoutManager linearLayoutManager;
    private View view;
    public HomeFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        boykotRV= view.findViewById(R.id.idRVBoykot);
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        searchItem.setVisible(true);
        // SearchView'i configure etmek için gerekli işlemleri yapabilirsiniz

        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onStart() {
        super.onStart();
        tumBoykotListesi.clear();
        apiService= APIClient.getClient().create(APIService.class);
        Call<List<BoykotMarka>> listCall=apiService.getAllBoykotUrunler();
        listCall.enqueue(new Callback<List<BoykotMarka>>() {
            @Override
            public void onResponse(Call<List<BoykotMarka>> call, Response<List<BoykotMarka>> response) {
                Log.d("TAG",response.code()+"");
                if(response.isSuccessful()){
                    tumBoykotListesi.addAll(Objects.requireNonNull(response.body()));
                    recyclerViewBoykotAdapter=new RecyclerViewBoykotAdapter(getActivity(),tumBoykotListesi);
                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    boykotRV.setLayoutManager(linearLayoutManager);
                    boykotRV.setAdapter(recyclerViewBoykotAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<BoykotMarka>> call, Throwable t) {
                Log.d("TAG",t.getMessage()+"");
            }
        });
    }

    public View getRootView() {
        return view;
    }
}