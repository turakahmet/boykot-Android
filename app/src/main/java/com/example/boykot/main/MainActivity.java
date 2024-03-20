package com.example.boykot.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.boykot.Adapters.CustomViewPager;
import com.example.boykot.Fragments.CosmeticFragment;
import com.example.boykot.Fragments.DrinkFragment;
import com.example.boykot.Fragments.FoodFragment;
import com.example.boykot.Fragments.HomeFragment;
import com.example.boykot.Fragments.HygieneFragment;
import com.example.boykot.R;
import com.example.boykot.Service.APIService;
import com.example.boykot.Utils.APIClient;
import com.example.boykot.databinding.ActivityMainBinding;
import com.example.boykot.databinding.CustomPopupBinding;
import com.example.boykot.pojo.BoykotMarka;
import com.example.boykot.pojo.Kategori;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CustomPopupBinding customPopupBinding;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        customPopupBinding=CustomPopupBinding.inflate(getLayoutInflater());
        viewPagerInitializer();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(binding.getRoot().getContext(), binding.getRoot().getContext().getString(R.string.barkodInfo),Toast.LENGTH_SHORT).show();
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Barkotu taramak için vizördeki dikdörtgenin içerisine yerleştirin.");
                integrator.setCameraId(0);
                integrator.setOrientationLocked(true);
                integrator.initiateScan();
                // FloatingActionButton tıklandığında yapılacak işlemler burada
                // Örneğin, yeni bir aktivite başlatma gibi
                // Intent intent = new Intent(MainActivity.this, YeniActivity.class);
                // startActivity(intent);
            }
        });
    }

    public void viewPagerInitializer(){
        ViewPager mViewPager = findViewById(R.id.main_activity_viewPager);
        TabLayout mTablayout = findViewById(R.id.main_activity_tableLayout);
        CustomViewPager mAdapter = new CustomViewPager(getSupportFragmentManager());
        mAdapter.addFragment(new HomeFragment(), Kategori.HEPSI.getEtiket(binding.getRoot().getContext()));
        mAdapter.addFragment(new FoodFragment(), Kategori.GIDA.getEtiket(binding.getRoot().getContext()));
        mAdapter.addFragment(new DrinkFragment(), Kategori.ICECEK.getEtiket(binding.getRoot().getContext()));
        mAdapter.addFragment(new HygieneFragment(), Kategori.TEMIZLIK.getEtiket(binding.getRoot().getContext()));
        mAdapter.addFragment(new CosmeticFragment(), Kategori.KOZMETIK.getEtiket(binding.getRoot().getContext()));
//        mAdapter.addFragment(new DrinkFragment(), Kategori.MEDYA.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.TEKNOLOJI.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.SIGARA.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.AKARYAKIT.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.ILAC.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.DIGER.getEtiket());
        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        Objects.requireNonNull(mTablayout.getTabAt(0)).select();
//        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                // Seçilen sekmenin pozisyonunu al
////                int selectedPosition = tab.getPosition();
////
////                // Bu pozisyona karşılık gelen fragmenti al
////                Fragment selectedFragment = mAdapter.getItem(selectedPosition);
////
////                // Eğer fragment null değilse ve fragmentin yaşam döngüsü çağrılmadıysa, onStart() metodunu çağır
////                if (selectedFragment != null ) {
////                    selectedFragment.onStart();
////                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                System.out.println("test");
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                System.out.println("test");
//
//            }
//        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Tarama iptal edildi", Toast.LENGTH_SHORT).show();
            } else {
                String barcode = result.getContents();
                APIService apiService= APIClient.getClient().create(APIService.class);
                Call<List<BoykotMarka>> listCall=apiService.getBoykotUrunByBarcot(barcode);
                listCall.enqueue(new Callback<List<BoykotMarka>>() {
                    @Override
                    public void onResponse(Call<List<BoykotMarka>> call, Response<List<BoykotMarka>> response) {
                        if(response.isSuccessful()){
                            mDialog=new Dialog(MainActivity.this);
                            mDialog.setContentView(R.layout.custom_popup);
                            customPopupBinding = CustomPopupBinding.bind(mDialog.findViewById(R.id.layoutPopUp)); // Popup layout dosyası ile bağlantı oluşturuluyor
                            Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            if (Objects.requireNonNull(response.body()).size() > 0) {
                                customPopupBinding.textPopUp.setText(getString(R.string.boykotUrun));
                                customPopupBinding.imagePopUp.setImageResource(R.drawable.back_hand);
                                mDialog.show();
                                Toast.makeText(MainActivity.this, "Bu ürün boykot listesinde mevcut", Toast.LENGTH_SHORT).show();

                            } else {
                                customPopupBinding.textPopUp.setText(getString(R.string.boykotUrunDegil));
                                customPopupBinding.imagePopUp.setImageResource(R.drawable.baseline_check_circle_24);
                                mDialog.show();
                                Toast.makeText(MainActivity.this, "Bu ürün boykot listesinde mevcut DEĞİL!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // onResponse içinde hata varsa
                            Toast.makeText(MainActivity.this, "Sunucu hatası: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<BoykotMarka>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "İnternet bağlantısı hatası: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    public ActivityMainBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }

    public Dialog getmDialog() {
        return mDialog;
    }

    public void setmDialog(Dialog mDialog) {
        this.mDialog = mDialog;
    }

}