package com.example.dwight.myapplication.ui.fragment.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import com.example.dwight.myapplication.R;
import com.example.dwight.myapplication.adapter.HomeAdapter;
import com.example.dwight.myapplication.helper.OnItemClickListener;
import com.example.dwight.myapplication.model.Article;
import com.example.dwight.myapplication.ui.BaseMainFragment;


public class HomeFragment extends BaseMainFragment implements Toolbar.OnMenuItemClickListener {
    private static final String TAG = "Fragmentation";

    private String[] mTitles = new String[]{
            "四川广元沉船船上人员名单确认 具体原因正调查",
            "英媒：中国人工成本激增 越来越多日企迁至越南",
            "习近平对谁提“情同手足”？"
    };

    private String[] mContents = new String[]{
            "微博截图人员名单#四川游船沉没#[最新进展：船上人员名单确认]4日晚23时记者获悉，船上18人名单已确认。三名获救者分别是杨东（男，29岁）、秦欢（男，27岁）、王明星（男，30岁）。当地村民介绍，事发时白龙湖水域突遇强烈阵风，船体偏右、船头朝下倾覆。具体原因正在调查中，这是2006年以来四川最严重的水上交通事故。",
            "日企担心中国经济放缓影响业绩。（资料图片）参考消息网6月5日报道英媒称，日本的最大航运公司正在越南投资12亿美元扩建一个集装箱码头，认定制造活动将从中国转向东南亚。据英国《金融时报》网站5月31日报道，在商船三井株式会社做出这一举动之前的三年里，随着中国的人工成本激增和中日两国争端造成不利影响，日本企业对\n",
            "“情同手足”“肝胆相照”……习近平主席在6月3日下午的一场外事活动中用了以上词汇，这在中国对外交往中并不多见。习近平主席这场外事活动的客人到底是谁？为何习主席会说出这样的话呢？（2016年6月3日，国家主席习近平在北京人民大会堂同柬埔寨国王西哈莫尼举行会谈。这是会谈前，习近平在人民大会堂东门外广场为西哈莫"
    };

    private Toolbar mToolbar;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        // Init Datas
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int index = (int) (Math.random() * 3);
            Article article = new Article(mTitles[index], mContents[index]);
            articleList.add(article);
        }
        mAdapter.setDatas(articleList);

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
