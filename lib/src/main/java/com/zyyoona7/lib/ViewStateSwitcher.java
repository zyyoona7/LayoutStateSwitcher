package com.zyyoona7.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by zyyoona7 on 2017/9/7.
 * <p>
 * 布局多状态切换
 */

public class ViewStateSwitcher {

    protected View mLoadingView;
    protected View mContentView;
    protected View mErrorView;
    protected View mEmptyView;
    protected View mCustomView;

    protected View mCurrentView;

    protected int mLoadingLayoutId;
    protected int mEmptyLayoutId;
    protected int mErrorLayoutId;
    protected int mCustomLayoutId;

    protected LayoutInflater mInflater;

    private ViewGroup mViewParent;
    private ViewGroup.LayoutParams mLayoutParams;
    private int mViewIndex;

    public ViewStateSwitcher(View contentView) {
        if (contentView == null) {
            return;
        }
        this.mContentView = contentView;
        mInflater = LayoutInflater.from(contentView.getContext());
    }


    private void initValue() {
        if (mContentView == null) {
            return;
        }
        final ViewParent viewParent = mContentView.getParent();

        if (viewParent != null && viewParent instanceof ViewGroup) {
            if (mContentView != null) {
                mViewParent = (ViewGroup) viewParent;
                mViewIndex = mViewParent.indexOfChild(mContentView);
                mLayoutParams = mContentView.getLayoutParams();
                if (mLayoutParams == null) {
                    mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                }
                this.mCurrentView = mContentView;
            } else {
                throw new IllegalArgumentException("content view must be not null");
            }
        } else {
            throw new IllegalStateException("content view must have a non-null ViewGroup viewParent");
        }
    }

    public void showLoading() {
        handleSwitchView(mLoadingView, mLoadingLayoutId);
    }

    public void showEmpty() {
        handleSwitchView(mEmptyView, mEmptyLayoutId);
    }

    public void showError() {
        handleSwitchView(mErrorView, mErrorLayoutId);
    }

    public void showCustom() {
        handleSwitchView(mCustomView, mCustomLayoutId);
    }

    public void showContent() {
        handleSwitchView(mContentView, 0);
    }

    /**
     * 处理切换view的逻辑
     *
     * @param switchView
     * @param switchId
     */
    private void handleSwitchView(View switchView, int switchId) {
        if (switchView == null) {
            if (switchId <= 0) {
                return;
            } else {
                switchView = mInflater.inflate(switchId, null);
            }
        }
        switchView(switchView);
    }

    /**
     * 切换view
     *
     * @param newView
     */
    private void switchView(View newView) {
        if (mViewParent == null) {
            initValue();
        }

        //如果不是当前的view
        if (mCurrentView != newView) {
            mViewParent.removeViewAt(mViewIndex);
            mViewParent.addView(newView, mViewIndex, mLayoutParams);
        }
    }

    public View getCurrentView() {
        return mCurrentView;
    }

    public Context getContext() {
        return mContentView.getContext();
    }
}
