package com.maxx.oschinanews.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.maxx.oschinanews.R;
import com.maxx.oschinanews.model.Blog;
import com.maxx.oschinanews.utils.NetworkUtil;

public class BlogPagedListAdapter extends PagedListAdapter<Blog,BlogPagedListAdapter.BlogViewHolder> {

    private Context context;

    //差分，只更新需要更新的元素，而不是整个刷新数据源
    private static final DiffUtil.ItemCallback<Blog> DIFF_CALLBACK = new DiffUtil.ItemCallback<Blog>() {
        @Override
        public boolean areItemsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
            // RecyclerView闪烁:当调用areItemsTheSame时总是返回false，即使比较的实例代表相同的对象(因为数据库每次更新都会给你新的实例)。
            // 要解决这个问题，您需要比较的不是它们是否是相同的实例，而是它们是否表示相同的对象。
            // return oldItem == newItem;
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
            return oldItem.equals(newItem);
        }
    };

    public BlogPagedListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new BlogViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        Blog blog = getItem(position);
        if(blog!=null){
            holder.title.setText(blog.title);
            holder.author.setText(blog.author);
            holder.pubDate.setText(blog.pubDate);
            holder.commentCount.setText(blog.commentCount+"评");
            holder.type.setText(getType(blog.type));
            holder.uri.setText("https://www.oschina.net/news/"+blog.id);
            holder.uri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetworkUtil.openHtmlViewer(context,holder.uri.getText().toString());
                }
            });
        }
    }

    //新闻类型 [0-链接新闻|1-软件推荐|2-讨论区帖子|3-博客|4-普通新闻|7-翻译文章]
    private String getType(long type){
        switch ((int) type){
            case 0:
                return "链接新闻";
            case 1:
                return "软件推荐";
            case 2:
                return "讨论区帖子";
            case 3:
                return "博客";
            case 4:
                return "普通新闻";
            case 7:
                return "翻译文章";

        }
        return "链接新闻";
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private TextView pubDate;
        private TextView commentCount;
        private TextView type;
        public TextView uri;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.author = itemView.findViewById(R.id.author);
            this.pubDate = itemView.findViewById(R.id.pub_date);
            this.commentCount = itemView.findViewById(R.id.comment_count);
            this.type = itemView.findViewById(R.id.type);
            this.uri = itemView.findViewById(R.id.blog_uri);
        }
    }
}
