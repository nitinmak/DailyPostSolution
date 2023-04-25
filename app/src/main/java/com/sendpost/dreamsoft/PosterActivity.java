package com.sendpost.dreamsoft;

import static com.sendpost.dreamsoft.PosterPreviewActivity.postsModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sendpost.dreamsoft.ImageEditor.EditImageActivity;
import com.sendpost.dreamsoft.R;

import com.sendpost.dreamsoft.adapter.PostsAdapter;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class PosterActivity extends AppCompatActivity {

    List<PostsModel> list = new ArrayList<>();
    PostsAdapter postsAdapter;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager layoutManager;
    int pageCount = 0;
    String type,item_id;
    Context context;
    TextView title_tv;

    boolean loading = true;
    String post_type = "posts";
    boolean isVideo = false;

    HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Functions.setLocale(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE), this, PosterActivity.class,false);
        setContentView(R.layout.activity_poster);
        Log.d("dffdfdgfdgfdg",Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE));
        context = this;
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        title_tv = findViewById(R.id.title_tv);
        title_tv.setText(getIntent().getStringExtra("title"));
        type = getIntent().getStringExtra("type");
        isVideo = getIntent().getBooleanExtra("itemtype",false);

        if (type.equals("api")){
            findViewById(R.id.tabLayout).setVisibility(View.GONE);
            item_id = getIntent().getStringExtra("keyword");
            pageCount = 2;

        }else if (type.equals("greeting")){
            item_id = getIntent().getStringExtra("item_id");
            post_type = "greeting";
            type = "section";

        }else{
            item_id = getIntent().getStringExtra("item_id");
        }

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        postsAdapter = new PostsAdapter(this, list, (view, postsModels, main_position) -> {
            postsModel = postsModels;
            EditImageActivity.postModel = postsModel;

            startActivity(new Intent(context, EditImageActivity.class)
                    .putExtra("type",post_type.equals("greeting") ? "GreetingPosts" : "Posts")
                    .putExtra("path",Functions.getItemBaseUrl(postsModel.item_url)));

//                startActivity(new Intent(context, PosterPreviewActivity.class)
//                        .putExtra("type",post_type.equals("greeting") ? "GreetingPosts" : "Posts")
//                        .putExtra("itemtype",isVideo));

        });

        recyclerView.setAdapter(postsAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int pastVisiblesItems, visibleItemCount, totalItemCount;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    int[] firstVisibleItems = null;
                    firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                    if(firstVisibleItems != null && firstVisibleItems.length > 0) {
                        pastVisiblesItems = firstVisibleItems[0];
                    }

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            pageCount++;
                            getPosts();
                        }
                    }
                }
            }
        });

        getPosts();
    }


    private void getPosts() {
        if (pageCount == 0){
            list.clear();
            findViewById(R.id.shimmer_lay).setVisibility(View.VISIBLE);
        }
        getPostsFromServer();
    }

    private void getPostsFromServer() {
        if (isVideo){
            post_type = "video";
        }
        String lan = "";
        if(Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE).equals("all"))
            lan = "";
        else
            lan = Functions.getSharedPreference(this).getString(Variables.APP_LANGUAGE_CODE,Variables.DEFAULT_LANGUAGE_CODE);

        homeViewModel.getPostByPage(type,lan,post_type,item_id,"",pageCount).observe(this, new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                loading = false;
                if (homeResponse != null){
                    list.addAll(homeResponse.posts);
                    findViewById(R.id.shimmer_lay).setVisibility(View.GONE);
                    if (list.size() > 0){
                        findViewById(R.id.no_data_layout).setVisibility(View.GONE);
                    }else{
                        findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
                    }
                    postsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void finish(View view) {
        finish();
    }
}
