package com.zyyoona7.layoutstateswitcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.zyyoona7.lib.StateLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private StateLayout mViewSwitcher;
    private Button mLoadingBtn;
    private Button mContentBtn;
    private Button mEmptyBtn;
    private Button mErrorBtn;

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewSwitcher = (StateLayout) findViewById(R.id.view_switcher);
        mLoadingBtn = (Button) findViewById(R.id.btn_loading);
        mContentBtn = (Button) findViewById(R.id.btn_content);
        mEmptyBtn = (Button) findViewById(R.id.btn_empty);
        mErrorBtn = (Button) findViewById(R.id.btn_error);
        mLoadingBtn.setOnClickListener(this);
        mContentBtn.setOnClickListener(this);
        mErrorBtn.setOnClickListener(this);
        mEmptyBtn.setOnClickListener(this);
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_content1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_loading:
                mViewSwitcher.showLoading();
                break;
            case R.id.btn_content:
                mViewSwitcher.showContent();
                break;
            case R.id.btn_empty:
                mViewSwitcher.showEmpty();
                break;
            case R.id.btn_error:
                mViewSwitcher.showError();
                break;
        }
    }
}
