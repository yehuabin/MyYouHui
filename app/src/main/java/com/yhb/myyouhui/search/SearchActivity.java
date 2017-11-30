package com.yhb.myyouhui.search;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yhb.myyouhui.BaseActivity;
import com.yhb.myyouhui.R;
import com.yhb.myyouhui.model.HotKeyModel;
import com.yhb.myyouhui.model.SearchModel;
import com.yhb.myyouhui.utils.HttpUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    private EditText et_search;
    private TextView tv_tip;
    private MyListView listView;
    private TextView tv_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);

    private SQLiteDatabase db;
    private BaseAdapter adapter;
private TagFlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 初始化控件
        initView();
        showSoftInputFromWindow(SearchActivity.this, et_search);
        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    search();
                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Log.d("afterTextChanged", "afterTextChanged: "+s.toString().trim());

                if (s.toString().trim().length() == 0) {

                    tv_tip.setVisibility(View.VISIBLE);
                    queryData("");
                    return;
                }
                hideClear();
               try{
                   showSuggest();
               }
               catch (Exception e){

               }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                search();
            }
        });

        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？

        // 第一次进入查询所有的历史记录
        queryData("");

        mViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.iv_back:
                        finish();
                        break;
                    case R.id.iv_search:
                        search();
                        break;
                }
            }
        }, R.id.iv_back, R.id.iv_search);
      final LayoutInflater  mInflater = LayoutInflater.from(this);
        mFlowLayout=mViewHolder.get(R.id.id_flowlayout);
        mFlowLayout.setAdapter(new TagAdapter<HotKeyModel>(SearchModel.HOTKEY_LIST)
        {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyModel s)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.hot_item,
                        mFlowLayout, false);
                tv.setText(s.getKeyword());
                return tv;
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                et_search.setText(SearchModel.HOTKEY_LIST.get(position).getKeyword());
                search();
                return true;
            }
        });
    }

    private void showSuggest() {
        String tempName = et_search.getText().toString();
        // 根据tempName去模糊查询数据库中有没有数据
        //queryData(tempName);
        HttpUtil.Request("https://suggest.taobao.com/sug?code=utf-8&q=" + tempName, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (et_search.getText().length() == 0) {
                    return;
                }
                String json = response.body().string();
                Gson gson = new Gson();
                final SuggestModel suggestModel = gson.fromJson(json, SuggestModel.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        List<String> data = new ArrayList<String>();

                        for (int i = 0; i < suggestModel.getResult().size(); i++) {
                            data.add(suggestModel.getResult().get(i).get(0));
                        }
                        listView.setAdapter(new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, data));

                        adapter.notifyDataSetChanged();

                        tv_tip.setVisibility(View.GONE);
                    }
                });


            }
        });
    }

    private void hideClear() {
        mViewHolder.get(R.id.ll_clear).setVisibility(View.GONE);
    }

    private void showClear() {
        mViewHolder.get(R.id.ll_clear).setVisibility(View.VISIBLE);
    }

    private void search() {
        String keyword = et_search.getText().toString().trim();
        if (keyword.length() == 0) {
            toastLong(getResources().getString(R.string.search_text));
            return;
        }
        // 先隐藏键盘
//        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
        boolean hasData = hasData(keyword);
        if (!hasData) {
            insertData(keyword);
           // queryData("");
        }
        et_search.setSelection(keyword.length());
        // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
        openActivity(ResultActivity.class, "keyword", keyword);

    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        if (cursor.getCount() == 0) {
            hideClear();
        } else {
            showClear();
        }
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void initView() {
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (MyListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
    }

    public void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_search.requestFocus();
        showSuggest();
    }
}
