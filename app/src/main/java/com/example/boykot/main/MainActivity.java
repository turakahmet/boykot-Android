package com.example.boykot.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.boykot.Adapters.CustomViewPager;
import com.example.boykot.Fragments.CosmeticFragment;
import com.example.boykot.Fragments.HomeFragment;
import com.example.boykot.Fragments.FoodFragment;
import com.example.boykot.Fragments.DrinkFragment;
import com.example.boykot.Fragments.HygieneFragment;
import com.example.boykot.R;
import com.example.boykot.pojo.Kategori;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPagerInitializer();
    }
    public void viewPagerInitializer(){
        ViewPager mViewPager = findViewById(R.id.main_activity_viewPager);
        TabLayout mTablayout = findViewById(R.id.main_activity_tableLayout);

        CustomViewPager mAdapter = new CustomViewPager(getSupportFragmentManager());
        mAdapter.addFragment(new HomeFragment(), Kategori.HEPSI.getEtiket());
        mAdapter.addFragment(new FoodFragment(), Kategori.GIDA.getEtiket());
        mAdapter.addFragment(new DrinkFragment(), Kategori.ICECEK.getEtiket());
        mAdapter.addFragment(new HygieneFragment(), Kategori.TEMIZLIK.getEtiket());
        mAdapter.addFragment(new CosmeticFragment(), Kategori.KOZMETIK.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.MEDYA.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.TEKNOLOJI.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.SIGARA.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.AKARYAKIT.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.ILAC.getEtiket());
//        mAdapter.addFragment(new DrinkFragment(), Kategori.DIGER.getEtiket());
        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);
        Objects.requireNonNull(mTablayout.getTabAt(0)).select();
    }


}