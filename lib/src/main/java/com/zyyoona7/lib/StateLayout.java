package com.zyyoona7.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

/**
 * Created by zyyoona7 on 2017/9/7.
 * <p>
 * 布局多状态切换Layout
 * <p>
 * （布局中使用时只能在标签中包含一个子view，多了也会被remove）
 */

public class StateLayout extends ViewAnimator {
    private static final String TAG = "StateLayout";

    protected View mLoadingView;
    protected View mContentView;
    protected View mErrorView;
    protected View mEmptyView;
    protected View mCustomView;

    protected int mLoadingLayoutId;
    protected int mContentLayoutId;
    protected int mEmptyLayoutId;
    protected int mErrorLayoutId;
    protected int mCustomLayoutId;

    //是否设置了默认的布局
    private boolean hasDefaultLayout = false;

    private LayoutInflater mInflater;

    public StateLayout(Context context) {
        super(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout);
        mLoadingLayoutId = a.getResourceId(R.styleable.StateLayout_loading, 0);
        mContentLayoutId = a.getResourceId(R.styleable.StateLayout_content, 0);
        mEmptyLayoutId = a.getResourceId(R.styleable.StateLayout_empty, 0);
        mErrorLayoutId = a.getResourceId(R.styleable.StateLayout_error, 0);
        mCustomLayoutId = a.getResourceId(R.styleable.StateLayout_custom, 0);

        int state = a.getInt(R.styleable.StateLayout_default_state, -1);
        if (state != -1) {
            setDefaultDisplayState(state);
        }
        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!hasDefaultLayout) {
            //没有设置默认显示的布局时，如果没有设置contentLayoutId则显示第一个子view
            //如果设置了contentLayoutId则默认显示contentLayoutId对应的view
            if (mContentLayoutId == 0 && mContentView == null) {
                if (getChildCount() >= 1) {
                    mContentView = getChildAt(0);
                }
            } else {
                removeAllViews();
                setDefaultView(mContentLayoutId, mContentView);
            }
        } else {
            if (mContentLayoutId == 0 && mContentView == null) {
                if (getChildCount() >= 2) {
                    mContentView = getChildAt(1);
                }
            } else {
                //如果设置了默认显示content，则不添加
                if (mContentView == null)
                    inflateView(mContentLayoutId, mContentView);
            }
            if (getChildCount() > 2) {
                removeViews(2, getChildCount() - 2);
            }
        }

        Log.e(TAG, "onAttachedToWindow: " + getChildCount());
    }

    /**
     * 设置默认显示的状态
     *
     * @param state
     */
    public void setDefaultDisplayState(int state) {
        switch (state) {
            case 0:
                //loading
                setDefaultView(mLoadingLayoutId, mLoadingView);
                break;
            case 2:
                //empty
                setDefaultView(mEmptyLayoutId, mEmptyView);
                break;
            case 3:
                //error
                setDefaultView(mErrorLayoutId, mErrorView);
                break;
            case 4:
                //custom
                setDefaultView(mCustomLayoutId, mCustomView);
                break;
            case 1:
            default:
                //content
                setDefaultView(mContentLayoutId, mContentView);
                break;
        }
    }

    /**
     * inflate view
     *
     * @param layoutId
     * @return
     */
    private View inflateView(int layoutId, View currentView) {
        if (currentView != null) {
            return currentView;
        }
        if (layoutId != 0) {
            View view = mInflater.inflate(layoutId, this, false);
            initStateView(layoutId, view);
            return view;
        } else {
            return null;
        }
    }

    private void initStateView(int layoutId, View view) {
        if (layoutId == mLoadingLayoutId) {
            mLoadingView = view;
        } else if (layoutId == mContentLayoutId) {
            mContentView = view;
        } else if (layoutId == mEmptyLayoutId) {
            mEmptyView = view;
        } else if (layoutId == mErrorLayoutId) {
            mErrorView = view;
        } else if (layoutId == mCustomLayoutId) {
            mCustomView = view;
        }
    }

    /**
     * 将view 添加到0位置
     *
     * @param layoutId
     */
    private void setDefaultView(int layoutId, View currentView) {
        View defaultView = inflateView(layoutId, currentView);
        if (defaultView != null) {
            hasDefaultLayout = true;
            ViewGroup.LayoutParams lp = defaultView.getLayoutParams();
            if (lp == null) {
                lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            }
            addView(defaultView, 0, lp);
            //显示0位置的view
            setDisplayedChild(0);
        }
    }

    public void setLoadingLayoutId(@LayoutRes int layoutId) {
        mLoadingLayoutId = layoutId;
    }

    public void setLoadingView(View view) {
        if (view == null) {
            return;
        }
        mLoadingView = view;
    }

    public void setContentLayoutId(@LayoutRes int layoutId) {
        mContentLayoutId = layoutId;
    }

    public void setContentView(View view) {
        if (view == null) {
            return;
        }
        mContentView = view;
    }

    public void setEmptyLayoutId(@LayoutRes int layoutId) {
        mEmptyLayoutId = layoutId;
    }

    public void setEmptyView(View view) {
        if (view == null) {
            return;
        }
        mEmptyView = view;
    }

    public void setErrorLayoutId(@LayoutRes int layoutId) {
        mErrorLayoutId = layoutId;
    }

    public void setErrorView(View view) {
        if (view == null) {
            return;
        }
        mErrorView = view;
    }

    public void setCustomLayoutId(@LayoutRes int layoutId) {
        mCustomLayoutId = layoutId;
    }

    public void setCustomView(View view) {
        if (view == null) {
            return;
        }
        mCustomView = view;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getCustomView() {
        return mCustomView;
    }

    public void showLoading() {
        handleSwitchView(mLoadingView, mLoadingLayoutId);
    }

    public void showContent() {
        handleSwitchView(mContentView, mContentLayoutId);
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

    /**
     * 处理view的切换
     *
     * @param switchView
     * @param switchLayoutId
     */
    private void handleSwitchView(View switchView, int switchLayoutId) {
        if (switchView == null && switchLayoutId == 0) {
            return;
        }
        if (switchView != null) {
            switchView(switchView);
        } else {
            View view = inflateView(switchLayoutId, switchView);
            if (view != null) {
                switchView(view);
            }
        }
    }

    /**
     * 切换view
     *
     * @param displayView
     */
    private void switchView(View displayView) {
        if (displayView != getCurrentView()) {
            int index = indexOfChild(displayView);
            if (index != -1) {
                //如果已经存在要显示的view则直接显示
                setDisplayedChild(index);
            } else {
                //如果没有添加view然后显示，动态添加
                addView(displayView);
                int newIndex = indexOfChild(displayView);
                setDisplayedChild(newIndex);
            }
        }
    }
}
