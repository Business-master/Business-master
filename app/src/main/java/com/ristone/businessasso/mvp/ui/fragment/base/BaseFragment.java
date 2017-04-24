package com.ristone.businessasso.mvp.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ristone.businessasso.App;
import com.ristone.businessasso.di.component.DaggerFragmentComponent;
import com.ristone.businessasso.di.component.FragmentComponent;
import com.ristone.businessasso.di.module.FragmentModule;
import com.ristone.businessasso.mvp.presenter.base.BasePresenter;

import butterknife.ButterKnife;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment{

    protected T mPresenter;

    private View mFragmentView;

    protected FragmentComponent mFragmentComponent;

    public abstract void initInjector();

    public abstract void initViews(View view);

    public abstract int getLayoutId();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            initViews(mFragmentView);
        }
        return mFragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onDestory();
        }
    }
}
