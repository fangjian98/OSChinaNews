package com.maxx.oschinanews.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.maxx.oschinanews.R;
import com.maxx.oschinanews.model.Blog;
import com.maxx.oschinanews.paging.BlogPagedListAdapter;
import com.maxx.oschinanews.paging.BlogViewModel;
import com.maxx.oschinanews.ui.MainActivity;
import com.maxx.oschinanews.utils.NetworkUtil;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);//设置OptionsMenu显示
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.swipe_refresh);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BlogPagedListAdapter adapter = new BlogPagedListAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        BlogViewModel blogViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(BlogViewModel.class);
        blogViewModel.blogPagedList.observe(getActivity(), new Observer<PagedList<Blog>>() {
            @Override
            public void onChanged(PagedList<Blog> blogs) {
                adapter.submitList(blogs);
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtil.isNetConnected(getActivity())) {
                    blogViewModel.refresh();
                } else {
                    Toast.makeText(getActivity(), "无网络连接", Toast.LENGTH_SHORT).show();
                }
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.token:
                String GET_CODE_URL = "https://www.oschina.net/action/oauth2/authorize?response_type=code&client_id=GCqPUbrIaoe7nUScRlet&redirect_uri=http://192.168.0.106/";
                NetworkUtil.openHtmlViewer(getActivity(),GET_CODE_URL);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
