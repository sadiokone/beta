package com.example.beta.lot;

//import static com.google.android.material.button.MaterialButtonToggleGroup.CornerData.start;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.beta.R;
import com.example.beta.dao.LotDao;
import com.example.beta.db.LotDatabase;
import com.example.beta.rest.ApiClient;
import com.example.beta.rest.model.ApiResquestUserAuth;
import com.example.beta.rest.model.LotEntity;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LotFragment extends Fragment  {
    private RecyclerView recyclerView;
    private LotAdapter dataAdapter;
    private ProgressBar progressBar;
 private  LotDatabase db;
    private  LotDao lotDao;
    private SharedPreferences sharedPreferences;


   public LotFragment() {
        // Required empty public constructor
    }

//    //pour enregistrer la base de donnée ggggggggggggggggg
       // sharedPreferences = getSharedPreferences("MyPrefs1", MODE_PRIVATE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = LotDatabase.getDatabase(requireContext());
     lotDao = db.lotDao();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lot, container, false);
        recyclerView = view.findViewById(R.id.lotRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);


        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedToken = sharedPreferences.getString("accessToken", "Texte par défaut");
        Log.e("result local",savedToken);



      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      fetchData(view,savedToken);
        return view;
    }

    private void fetchData(View view,String savedToken) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        ApiInterfaceLot apiInterfaceLot = ApiClient.getLot(savedToken).create(ApiInterfaceLot.class);
        Call<List<LotEntity>> call = apiInterfaceLot.getLot("Bearer " + savedToken);


        call.enqueue(new Callback<List<LotEntity>>() {
            @Override
            public void onResponse(Call<List<LotEntity>> call, Response<List<LotEntity>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<LotEntity> lots = response.body();
                    new Thread(() -> {
                        lotDao.deleteAll();
                        for (LotEntity lotEntity : lots) {
                            lotDao.insert(lotEntity);
                        }

                    }).start();
                    dataAdapter = new LotAdapter(lots);
                    // dataAdapter = new LotAdapter(response.body());
                    //dataAdapter =new LotAdapter(lotEntities);

                    recyclerView.setAdapter(dataAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Données récupérées avec succès", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 403) {
                    Snackbar.make(view, "Forbidden: Access is denied. Check your API token and permissions.", +Snackbar.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    Snackbar.make(view, "Echec de la récupération des données. Code de réponse: " + response.code(), Snackbar.LENGTH_LONG).show();
                    Log.d("LotFragment", "Echec de la récupération si: " + response);
                }


                // Enregistrer l'état de connexion
/*
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putBoolean("est connecté", true);
    editor.putString("accessToken", authResponse.getAccess_token());
          editor.putString("numTel", authResponse.getNumTel());
          editor.putString("motpass", motpass);
           editor.putString("numTel",phoneNumber); //Enregistrer le numéro de téléphone dans editText
          editor.apply();*/
            }


            @Override
            public void onFailure(Call<List<LotEntity>> call, Throwable t) {


                // Handle error
                progressBar.setVisibility(View.GONE);
                Log.d("LotFragment", "Echec de la récupération dans onFailure: " + t.getMessage());
                Snackbar.make(view, "Error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();

                new Thread(() -> {
                    List<LotEntity> lots = lotDao.getAllLots();
                    requireActivity().runOnUiThread(() -> {
                        dataAdapter = new LotAdapter(lots);
                        recyclerView.setAdapter(dataAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
                }).start();
            }
        });


    }}