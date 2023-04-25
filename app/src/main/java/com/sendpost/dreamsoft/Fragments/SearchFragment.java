package com.sendpost.dreamsoft.Fragments;

import static com.sendpost.dreamsoft.PosterPreviewActivity.postsModel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.sendpost.dreamsoft.ImageEditor.EditImageActivity;
import com.sendpost.dreamsoft.adapter.PostsAdapter;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.PosterPreviewActivity;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.responses.HomeResponse;
import com.sendpost.dreamsoft.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    public SearchFragment() {
    }

    Context context;
    RecyclerView recycler;
    EditText search_edit;
    View view;
    HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        this.view  = view;
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        search_edit = view.findViewById(R.id.search_edit);
        recycler = view.findViewById(R.id.recycler);
        setPostsAdapter();
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    callSearchPost(search_edit.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    List<PostsModel> posts_list = new ArrayList<>();
    private void callSearchPost(String s) {
        Functions.showLoader(context);
        homeViewModel.getPostByPage("","","","",s,0).observe(this, new Observer<HomeResponse>() {
            @Override
            public void onChanged(HomeResponse homeResponse) {
                Functions.cancelLoader();
                posts_list.clear();
                if (homeResponse != null){
                    if (homeResponse.posts.size() > 0){
                        posts_list.addAll(homeResponse.posts);
                        view.findViewById(R.id.no_data_layout).setVisibility(View.GONE);
                    }else {
                        view.findViewById(R.id.no_data_layout).setVisibility(View.VISIBLE);
                    }
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    PostsAdapter searchAdapter;

    private void setPostsAdapter() {
        searchAdapter = new PostsAdapter(context, posts_list, (view, postsModels, main_position) -> {
          PosterPreviewActivity.postsModel = postsModels;
            EditImageActivity.postModel = postsModel;

//            startActivity(
//                    new Intent(context, PosterPreviewActivity.class)
//                    .putExtra("type", "Posts"));

            startActivity(new Intent(context, EditImageActivity.class)
                    .putExtra("type", "Posts")
                    .putExtra("path",Functions.getItemBaseUrl(postsModel.item_url)));
        });

        recycler.setAdapter(searchAdapter);

    }
}