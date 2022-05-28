package com.maxx.oschinanews;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.maxx.oschinanews.utils.NetworkUtil;

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
                if (NetworkUtil.isNetConnected(MainActivity.this)) {
                    blogViewModel.refresh();
                } else {
                    Toast.makeText(MainActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                }
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.token:
                String GET_CODE_URL = "https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=GCqPUbrIaoe7nUScRlet&redirect_uri=http://192.168.0.106/";
                NetworkUtil.openHtmlViewer(this,GET_CODE_URL);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}