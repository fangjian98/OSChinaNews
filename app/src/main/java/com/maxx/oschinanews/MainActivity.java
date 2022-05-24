package com.maxx.oschinanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.maxx.oschinanews.model.Blog;
import com.maxx.oschinanews.paging.BlogPagedListAdapter;
import com.maxx.oschinanews.paging.BlogViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}