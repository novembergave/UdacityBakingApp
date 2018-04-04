package com.novembergave.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.novembergave.bakingapp.recyclerviews.mainactivity.MainAdapter;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private MainAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = findViewById(R.id.main_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new MainAdapter(this::openActivity);
    recyclerView.setAdapter(adapter);
    adapter.setData();
  }

  private void openActivity(int i) {
    startActivity(RecipeActivity.launchActivity(this));
  }
}
