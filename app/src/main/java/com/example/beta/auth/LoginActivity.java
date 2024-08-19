package com.example.beta.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.beta.MainActivity;
import com.example.beta.R;
import com.example.beta.dao.AppDataBase;
import com.example.beta.rest.ApiClient;
import com.example.beta.rest.ApiInterface;
import com.example.beta.rest.model.ApiResquestUserAuth;
import com.example.beta.rest.model.UserEntity;
import com.example.beta.rest.model.UserEntityDB;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText numTel;
    private EditText password;
    private ApiInterface apiInterface;
    private SharedPreferences sharedPreferences;
    private AppDataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

       numTel=findViewById(R.id.numTel);
       password=findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btnLogin);


           //pour enregistrer la base de donnée
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        //db= Room.databaseBuilder(getApplicationContext(), AppDataBase.class,"app_database").build();
        db=Room.databaseBuilder(getApplicationContext(),AppDataBase.class,"app_database").build();
        //Initialiser l'interface de l'API
       apiInterface = ApiClient.getClient().create(ApiInterface.class);
      // Pré-remplir le champ du numéro de téléphone
        String savedPhoneNumber = sharedPreferences.getString("numTel", "");
        numTel.setText(savedPhoneNumber);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numphone=numTel.getText().toString();
                String motpass=password.getText().toString();
                if (!numphone.isEmpty()&& !motpass.isEmpty() ){
                    if (isNetwordAvaible()){
                        loginUser(numphone,motpass);
                    }else {
                        LoginUserOffLine(numphone,motpass);
                    }
                }

                else

                {
                    Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private boolean isNetwordAvaible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();


    }


    private void loginUser(String numphone, String motpass) {
        UserEntity userEntity=new UserEntity(numphone,motpass);
        Call<ApiResquestUserAuth> call=apiInterface.userSingnIn(userEntity);
        call.enqueue(new Callback<ApiResquestUserAuth>() {
            @Override
            public void onResponse(@NonNull Call<ApiResquestUserAuth> call, @NonNull Response<ApiResquestUserAuth> response) {
                if (response.isSuccessful()&&response.body() !=null){
                        ApiResquestUserAuth authResponse = response.body();
                    Log.e("result_auth db",authResponse.getNumTel() );
                    // Traitez la réponse ici
                    Toast.makeText(LoginActivity.this, "Connexion réussie! Token: " , Toast.LENGTH_SHORT).show();

                    // il afficher en même tempd le token "+ authResponse.getAccess_token()"
                    // Toast.makeText(LoginActivity.this, "Connexion réussie! Token: " + authResponse.getAccess_token(), Toast.LENGTH_SHORT).show();

                    // Enregistrer l'état de connexion

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("est connecté", true);
                    editor.putString("accessToken", authResponse.getAccess_token());
                    editor.putString("numTel", authResponse.getNumTel());
                    editor.putString("motpass",motpass );
                    // editor.putString("numTel",phoneNumber); //Enregistrer le numéro de téléphone dans editText
                    editor.apply();
                    // Stocker l'utilisateur localement
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                         db.userDao().insert(new UserEntityDB(numphone,motpass));
                        }
                    }).start();






                    // Démarrer MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    // Terminer l'activité de connexion pour qu'elle ne soit pas accessible en arrière-plan
                }else {
                    Toast.makeText(LoginActivity.this, "Numéro de téléphone ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResquestUserAuth> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Erreur de connexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void LoginUserOffLine( final String numphone,  final  String motpass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserEntityDB user =db.userDao().getUser(numphone,motpass);
                Log.e("LoginUserOffLine", user.toString());
                if (user !=null){
                    // Connexion réussie
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                            // Enregistrer l'état de connexion
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("se connecté", true);
                            editor.apply();


                            // Démarrer MainActivity
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else {
                    //!connexion échouée
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Numéro de téléphone ou mot de passe incorrect", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        }).start();

    }
}