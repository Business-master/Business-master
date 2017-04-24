package com.ristone.businessasso.mvp.presenter.impl;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.TopicBean;
import com.ristone.businessasso.mvp.entity.response.RspTopicsBean;
import com.ristone.businessasso.mvp.interactor.TopicInteractor;
import com.ristone.businessasso.mvp.interactor.impl.TopicsInteractorImpl;
import com.ristone.businessasso.mvp.presenter.TopicsPresenter;
import com.ristone.businessasso.mvp.presenter.base.BasePresenterImpl;
import com.ristone.businessasso.mvp.view.NewsView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
public class TopicsPresenterImpl extends BasePresenterImpl<NewsView<List<TopicBean>>, RspTopicsBean>
        implements TopicsPresenter {

    private TopicInteractor<RspTopicsBean> mNewsInteractor;
    private int pageNum;
    private int numPerPage;
    private String title;
    private String typeId;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public TopicsPresenterImpl(TopicsInteractorImpl newsInteractor) {
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
    public void success(RspTopicsBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setNewsList(data.getBody().getDisList(), loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage,String title) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.title = title;
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
        mSubscription = mNewsInteractor.loadNews(this, pageNum, numPerPage,title);
    }
}
