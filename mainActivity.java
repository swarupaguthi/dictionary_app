package com.example.dictionaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dictionaryapp.api.DictionaryApi;
import com.example.dictionaryapp.api.RetrofitClient;
import com.example.dictionaryapp.models.WordResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etWord;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWord = findViewById(R.id.etWord);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String word = etWord.getText().toString().trim();
            if (word.isEmpty()) {
                etWord.setError("Enter a word");
                return;
            }
            searchWord(word);
        });
    }

    private void searchWord(String word) {
        DictionaryApi api = RetrofitClient.getRetrofitInstance().create(DictionaryApi.class);
        Call<List<WordResponse>> call = api.getWordMeaning(word);
        call.enqueue(new Callback<List<WordResponse>>() {
            @Override
            public void onResponse(Call<List<WordResponse>> call, Response<List<WordResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WordResponse wordResponse = response.body().get(0);
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("wordData", wordResponse);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Word not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WordResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
