package com.novembergave.bakingapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.recyclerviews.mainactivity.MainAdapter;

import java.util.List;

import static com.novembergave.bakingapp.utils.JsonUtils.parseBakingList;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private MainAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    List<Recipe> recipes = parseBakingList(this);
    recyclerView = findViewById(R.id.main_recycler_view);

    // Change how the recyclerView is displayed depending on the orientation
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    adapter = new MainAdapter(this::openActivity);
    recyclerView.setAdapter(adapter);
    adapter.setData(recipes);
  }

  private void openActivity(Recipe recipe) {
    startActivity(RecipeActivity.launchActivity(this, recipe));
  }
}
