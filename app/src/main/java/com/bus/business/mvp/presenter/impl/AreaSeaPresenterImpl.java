package com.bus.business.mvp.presenter.impl;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.response.RspAreaSeaBean;
import com.bus.business.mvp.entity.response.RspAssisBean;
import com.bus.business.mvp.interactor.NewsInteractor;
import com.bus.business.mvp.interactor.impl.AreaSeaInteractorImpl;
import com.bus.business.mvp.interactor.impl.AssisInteractorImpl;
import com.bus.business.mvp.presenter.NewsPresenter;
import com.bus.business.mvp.presenter.base.BasePresenterImpl;
import com.bus.business.mvp.view.AreaSeaView;
import com.bus.business.mvp.view.AssisView;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * Created by ATRSnail on 2017/1/19.
 */

public class AreaSeaPresenterImpl extends BasePresenterImpl<AreaSeaView,RspAreaSeaBean> implements NewsPresenter {

    private NewsInteractor<RspAreaSeaBean> mNewsInteractor;
    private int pageNum;
    private int numPerPage;
    private String title;
    private String areaCode="";
    private String chambreCode="";
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public AreaSeaPresenterImpl(AreaSeaInteractorImpl newsInteractor) {
        this.mNewsInteractor = newsInteractor;
    }

    @Override
    public void onCreate() {
        if (mView != null) {
            loadNewsData();
        }
    }

    @Override
    public void beforeRequest() {
        if (!misFirstLoad) {
            mView.showProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if (mView != null) {
            int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_ERROR : LoadNewsType.TYPE_LOAD_MORE_ERROR;
            mView.setAreaSeaBeanList(null, loadType);
        }
    }

    @Override
    public void success(RspAreaSeaBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setAreaSeaBeanList(data.getBody().getResourceList(), loadType);
            mView.hideProgress();
        }
    }



    private void loadNewsData() {
        mSubscription = mNewsInteractor.loadNews(this, pageNum, numPerPage,title,areaCode,chambreCode);
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage, String title,int status) {

    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.title = title;
        this.areaCode =areaCode;
        this.chambreCode = chambreCode;
    }

    @Override
    public void refreshData() {
        pageNum = 1;
        mIsRefresh = true;
        loadNewsData();
    }

    @Override
    public void loadMore() {
        mIsRefresh = false;
        loadNewsData();
    }
}

