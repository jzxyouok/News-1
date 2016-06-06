package com.example.dwight.myapplication.ui.fragment.home;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import com.example.dwight.myapplication.R;
import com.example.dwight.myapplication.adapter.HomeAdapter;
import com.example.dwight.myapplication.helper.OnItemClickListener;
import com.example.dwight.myapplication.model.Article;
import com.example.dwight.myapplication.model.article_demo;
import com.example.dwight.myapplication.ui.BaseMainFragment;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xmlpull.v1.XmlPullParserException;


public class HomeFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "Fragmentation";
    private Toolbar mToolbar;
    public List<article_demo> articleList;
    private FloatingActionButton mFab;
    private RecyclerView mRecy;
    private HomeAdapter mAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        articleList = data();
        initView(view);


        return view;
    }


    @Override
    protected FragmentAnimator onCreateFragmentAnimation() {
        // 默认不改变
//         return super.onCreateFragmentAnimation();
        // 在进入和离开时 设定无动画
        return new DefaultNoAnimator();
    }

    private List<article_demo> data(){
        final List <article_demo> list = new ArrayList<>();
        BmobQuery <Article> query = new BmobQuery<Article>();
        query.findObjects(this.getContext(), new FindListener<Article>() {
            @Override

            public void onSuccess(List<Article> object) {
                // TODO Auto-generated method stub
                Toast.makeText(_mActivity, "查询成功：共" + object.size() + "条数据。", Toast.LENGTH_SHORT).show();

                for (Article gameScore : object) {
                    list.add(new article_demo(gameScore.getTitle(),gameScore.getTitle()));
                    //获得playerName的信息
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });
        return list;
    }
    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

        mToolbar.setTitle(R.string.home);
        initToolbarNav(mToolbar, true);
        mToolbar.inflateMenu(R.menu.home);
        mToolbar.setOnMenuItemClickListener(this);

        mAdapter = new HomeAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 5) {
                    mFab.hide();
                } else if (dy < -5) {
                    mFab.show();
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                start(DetailFragment.newInstance(mAdapter.getItem(position).getTitle()));
            }
        });


        mAdapter.setDatas(articleList);

        // Init Datas




        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * 类似于 Activity的 onNewIntent()
     */
    @Override
    protected void onNewBundle(Bundle args) {
        super.onNewBundle(args);

        Toast.makeText(_mActivity, args.getString("from"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_hierarchy:
                _mActivity.showFragmentStackHierarchyView();
                _mActivity.logFragmentStackHierarchy(TAG);
                break;
            case R.id.action_anim:
                final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
                popupMenu.inflate(R.menu.home_pop);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_anim_veritical:
                                _mActivity.setFragmentAnimator(new DefaultVerticalAnimator());
                                Toast.makeText(_mActivity, "设置全局动画成功! 竖向", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_anim_horizontal:
                                _mActivity.setFragmentAnimator(new DefaultHorizontalAnimator());
                                Toast.makeText(_mActivity, "设置全局动画成功! 横向", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.action_anim_none:
                                _mActivity.setFragmentAnimator(new DefaultNoAnimator());
                                Toast.makeText(_mActivity, "设置全局动画成功! 无", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
                break;
        }
        return true;
    }

}
