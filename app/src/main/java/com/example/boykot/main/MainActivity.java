package com.example.boykot.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
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
    private CustomViewPager mAdapter;

    private Context fragmentContext;

    private RecyclerView fragmentRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        customPopupBinding=CustomPopupBinding.inflate(getLayoutInflater());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                integrator.setBeepEnabled(false);
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
        mAdapter = new CustomViewPager(getSupportFragmentManager());
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
        mAdapter.getItem(0).getContext();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Sayfa kaydırılırken tetiklenir
            }

            @Override
            public void onPageSelected(int position) {
                // Yeni bir sayfa seçildiğinde tetiklenir
                Fragment selectedFragment = mAdapter.getItem(position);
                String fragmentName = selectedFragment.getClass().getSimpleName();
                Log.d("TAG", "Current Fragment: " + fragmentName);

                // Context kullanmak isterseniz
                fragmentContext = selectedFragment.getContext();
                if (selectedFragment instanceof HomeFragment) {
                    HomeFragment homeFragment = (HomeFragment) selectedFragment;
                    View homeView = homeFragment.getRootView();
                    if (homeView != null) {
                        // Örneğin, fragment içindeki bir RecyclerView'ı bulabilirsiniz
                        fragmentRecyclerView = homeView.findViewById(R.id.idRVBoykot);
                        // RecyclerView ile ilgili işlemler yapabilirsiniz
                    }
                }else if(selectedFragment instanceof FoodFragment){
                    FoodFragment foodFragment = (FoodFragment) selectedFragment;
                    View foodView = foodFragment.getRootView();
                    if (foodView != null) {
                        // Örneğin, fragment içindeki bir RecyclerView'ı bulabilirsiniz
                        fragmentRecyclerView = foodView.findViewById(R.id.idRVFood);
                        // RecyclerView ile ilgili işlemler yapabilirsiniz
                    }
                }else if(selectedFragment instanceof DrinkFragment){
                    DrinkFragment drinkFragment = (DrinkFragment) selectedFragment;
                    View drinkView = drinkFragment.getRootView();
                    if (drinkView != null) {
                        // Örneğin, fragment içindeki bir RecyclerView'ı bulabilirsiniz
                        fragmentRecyclerView = drinkView.findViewById(R.id.idRVDrink);
                        // RecyclerView ile ilgili işlemler yapabilirsiniz
                    }
                }else if(selectedFragment instanceof HygieneFragment){
                    HygieneFragment hygieneFragment = (HygieneFragment) selectedFragment;
                    View hygieneView = hygieneFragment.getRootView();
                    if (hygieneView != null) {
                        // Örneğin, fragment içindeki bir RecyclerView'ı bulabilirsiniz
                        fragmentRecyclerView = hygieneView.findViewById(R.id.idRVHygiene);
                        // RecyclerView ile ilgili işlemler yapabilirsiniz
                    }
                }else if(selectedFragment instanceof CosmeticFragment){
                    CosmeticFragment cosmeticFragment = (CosmeticFragment) selectedFragment;
                    View cosmeticView = cosmeticFragment.getRootView();
                    if (cosmeticView != null) {
                        // Örneğin, fragment içindeki bir RecyclerView'ı bulabilirsiniz
                        fragmentRecyclerView = cosmeticView.findViewById(R.id.idRVCosmetic);
                        // RecyclerView ile ilgili işlemler yapabilirsiniz
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Sayfa kaydırma durumu değiştiğinde tetiklenir
            }
        });
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.search_bar) {
//            // Burada search_list.xml adındaki sayfayı açabilirsiniz
//            Intent intent = new Intent(this, SearchList.class);
//            startActivity(intent);
//            return true;
//        }
        if (fragmentContext == null) {
            fragmentContext =mAdapter.getItem(0).getContext();
            fragmentRecyclerView = ((HomeFragment)mAdapter.getItem(0)).getRootView().findViewById(R.id.idRVBoykot);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu with items using MenuInflator
        getMenuInflater().inflate(R.menu.search_bar,menu);
        // Initialise menu item search bar
        // with id and take its object
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        Objects.requireNonNull(searchView).setQueryHint(getString(R.string.search));
        // attach setOnQueryTextListener
        // to search view defined above
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Override onQueryTextSubmit method which is call when submit query is searched
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                return false;
            }

            // This method is overridden to filter the adapter according
            // to a search query when the user is typing search
            @Override
            public boolean onQueryTextChange(String searchText) {
                    SearchList searchList = SearchList.getInstance();
                    searchList.makeAPICall(searchText,fragmentContext,fragmentRecyclerView);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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