package com.yhb.myyouhui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhb.myyouhui.R;
import com.yhb.myyouhui.adapter.MultiRefreshAdapter;
import com.yhb.myyouhui.baseadapter.interfaces.OnLoadMoreListener;
import com.yhb.myyouhui.callback.SearchCallback;
import com.yhb.myyouhui.model.ProductModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.provider.DataProvider;
import com.yhb.myyouhui.utils.CategoryUtil;
import com.yhb.myyouhui.utils.LineDecoration;

import java.util.List;

/**
 * Created by smk on 2017/11/21.
 */

public class ProductListFragment extends Fragment {
    RecyclerView recyclerView;
    LayoutInflater inflater;
    MultiRefreshAdapter mAdapter;
    TabLayout tl_sortType;
    CheckBox ck_onlyQuan;
    CheckBox ck_onlyTmall;
    SearchModel searchModel = new SearchModel();
    String searchType;
    Handler handler = new Handler();
    LinearLayout ll_tmall;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.product_fragment, container, false);
        this.inflater = inflater;
        tl_sortType = (TabLayout) view.findViewById(R.id.tl_sortType);
        ck_onlyQuan = (CheckBox) view.findViewById(R.id.ck_onlyQuan);
        ck_onlyTmall = (CheckBox) view.findViewById(R.id.ck_onlyTmall);
        ll_tmall= (LinearLayout) view.findViewById(R.id.ll_tmall);
        Bundle bundle = getArguments();
        searchType = bundle.getString("type");
        int position = bundle.getInt("position");

        if (searchType.equals("index")) {
            searchModel.setCategory(CategoryUtil.getVal(String.valueOf(position)));
            if (position==1||position==3) {
                ll_tmall.setVisibility(View.VISIBLE);
            }
            else {
                ll_tmall.setVisibility(View.INVISIBLE);
            }
        } else {
            searchModel.setKeyword(bundle.getString("keyword"));
        }
        searchModel.setSearchType(searchType);

        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);


        //初始化adapter
        mAdapter = new MultiRefreshAdapter(inflater.getContext(), null, true);
        //初始化EmptyView
        View emptyView = inflater.inflate(R.layout.empty_layout, (ViewGroup) recyclerView.getParent(), false);
        mAdapter.setEmptyView(emptyView);

        //初始化 开始加载更多的loading View
        mAdapter.setLoadingView(R.layout.load_loading_layout);
        //加载失败，更新footer view提示
        mAdapter.setLoadFailedView(R.layout.load_failed_layout);
        //加载完成，更新footer view提示
        mAdapter.setLoadEndView(R.layout.load_end_layout);

        //设置加载更多触发的事件监听
        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                   loadData();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineDecoration(inflater.getContext(), LineDecoration.VERTICAL_LIST));

        recyclerView.setAdapter(mAdapter);

        final ImageView fab = (ImageView) view.findViewById(R.id.iv_backtop);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (firstVisibleItem > 1) {
                    fab.setVisibility(View.VISIBLE);
                    //Show FAB
                } else {
                    //Hide FAB
                    fab.setVisibility(View.INVISIBLE);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  RecyclerView r = (RecyclerView) view.findViewById(R.id.recylerView);
               recyclerView.smoothScrollToPosition(0);
            }
        });

        TextView tv_onlyQuan = (TextView) view.findViewById(R.id.tv_onlyQuan);
        Drawable img = getResources().getDrawable(R.drawable.quan);
        img.setBounds(0, 0, 50, 50);
        tv_onlyQuan.setCompoundDrawables(img, null, null, null);

        TextView tv_onlyTmall = (TextView) view.findViewById(R.id.tv_onlyTmall);
        img = getResources().getDrawable(R.drawable.tmall);
        img.setBounds(0, 0, 40, 40);
        tv_onlyTmall.setCompoundDrawables(img, null, null, null);


        ck_onlyQuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSortType();
                search();
            }
        });
        ck_onlyTmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSortType();
                search();
            }
        });
        tl_sortType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tl_sortType.getSelectedTabPosition() == 3) {
                    isPriceDesc = true;
                    tab.setText("价格∨");
                }
                else {
                    tl_sortType.getTabAt(3).setText("价格");
                }
                setSortType();
                search();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tl_sortType.getSelectedTabPosition() == 3) {
                    isPriceDesc=!isPriceDesc;
                   if (isPriceDesc){
                       tab.setText("价格∨");
                   }
                   else {
                       tab.setText("价格∧");
                   }
                }
                setSortType();
                search();
            }
        });
        return view;
    }
private boolean isPriceDesc=false;
    private int sortType = 0;

    private void setSortType() {
        if (tl_sortType.getSelectedTabPosition() == 0) {
            sortType = 0;
        } else if (tl_sortType.getSelectedTabPosition() == 1) {
            sortType = 1;
        } else if (tl_sortType.getSelectedTabPosition() == 2) {
            sortType = 9;
        } else if (tl_sortType.getSelectedTabPosition() == 3) {
            if (isPriceDesc){
                sortType = 3;
            }
            else {
                sortType=4;
            }
        }
    }

    private void search() {
        searchModel.setSortType(sortType);
        searchModel.setOnlyQuan(ck_onlyQuan.isChecked());
        searchModel.setOnlyTmall(ck_onlyTmall.isChecked());
        searchModel.setPage(0);
        mAdapter.reset();
        loadData();
    }

    private void loadData() {

        DataProvider.search(searchModel, new SearchCallback() {
            @Override
            public void response(final List<ProductModel> data, final boolean isOK) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (searchModel.getPage() == 0) {
                            if (!isOK||data==null||data.size()==0) {

                                mAdapter.setLoadFail();
                                return;
                            }
                            mAdapter.setNewData(data);
                        } else {
                            if (data == null || data.size() == 0) {
                                mAdapter.loadEnd();
                            } else {
                                if (!isOK) {
                                    mAdapter.loadFailed();
                                    return;
                                }

                                mAdapter.setLoadMoreData(data);
                            }
                        }

                        if(data==null||data.size()<SearchModel.PAGE_SIZE){
                            mAdapter.loadEnd();
                            return;
                        }
                        searchModel.setPage(searchModel.getPage() + 1);
                    }

                });
            }
        });

    }

}
