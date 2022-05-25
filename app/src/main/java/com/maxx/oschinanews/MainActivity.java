package com.maxx.oschinanews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.maxx.oschinanews.model.Blog;
import com.maxx.oschinanews.paging.BlogPagedListAdapter;
import com.maxx.oschinanews.paging.BlogViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipe_refresh);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BlogPagedListAdapter adapter = new BlogPagedListAdapter(this);
        recyclerView.setAdapter(adapter);

        BlogViewModel blogViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(BlogViewModel.class);
        blogViewModel.blogPagedList.observe(this, new Observer<PagedList<Blog>>() {
            @Override
            public void onChanged(PagedList<Blog> blogs) {
                adapter.submitList(blogs);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                blogViewModel.refresh();
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}