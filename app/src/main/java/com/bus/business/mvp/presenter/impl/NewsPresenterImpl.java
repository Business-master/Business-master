package com.bus.business.mvp.presenter.impl;

import com.bus.business.common.LoadNewsType;
import com.bus.business.mvp.entity.response.RspNewsBean;
import com.bus.business.mvp.entity.response.base.BaseNewBean;
import com.bus.business.mvp.interactor.NewsInteractor;
import com.bus.business.mvp.interactor.impl.NewsInteractorImpl;
import com.bus.business.mvp.presenter.NewsPresenter;
import com.bus.business.mvp.presenter.base.BasePresenterImpl;
import com.bus.business.mvp.view.NewsView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView<List<BaseNewBean>>, RspNewsBean>
        implements NewsPresenter {

    private NewsInteractor<RspNewsBean> mNewsInteractor;
    private int pageNum;
    private int numPerPage;
    private String title;
    private String typeId;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public NewsPresenterImpl(NewsInteractorImpl newsInteractor) {
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
            mView.setNewsList(null, loadType);
        }
    }

    @Override
    public void success(RspNewsBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setNewsList(data.getBody().getNewsList(), loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage,String title,int status) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.title = title;
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage, String title, String areaCode, String chambreCode) {

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

    private void loadNewsData() {
        mSubscription = mNewsInteractor.loadNews(this, pageNum, numPerPage,title,-1);
    }
}
