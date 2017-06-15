package com.ristone.businessasso.mvp.presenter.impl;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.QuestionnaireBean;
import com.ristone.businessasso.mvp.entity.response.RspNewsBean;
import com.ristone.businessasso.mvp.entity.response.RspQuestionnaireBean;
import com.ristone.businessasso.mvp.interactor.PolicyInteractor;
import com.ristone.businessasso.mvp.interactor.impl.NewsInteractorImpl;
import com.ristone.businessasso.mvp.interactor.impl.QuestionnaireInteractorImpl;
import com.ristone.businessasso.mvp.presenter.PolicyPresenter;
import com.ristone.businessasso.mvp.presenter.base.BasePresenterImpl;
import com.ristone.businessasso.mvp.view.NewsView;
import com.ristone.businessasso.mvp.view.QuestionnaireView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;


public class QuestionnairePresenterImpl extends BasePresenterImpl<QuestionnaireView, RspQuestionnaireBean>
        implements PolicyPresenter {

    private PolicyInteractor<RspQuestionnaireBean> mNewsInteractor;
    private int pageNum;
    private int numPerPage;
    private String title;
    private String types;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public QuestionnairePresenterImpl(QuestionnaireInteractorImpl newsInteractor) {
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
            mView.setQuestionnaireList(null, loadType);
        }
    }

    @Override
    public void success(RspQuestionnaireBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setQuestionnaireList(data.getBody().getQuestionnaireList(), loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage,String title,String types) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.title = title;
        this.types = types;
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
        mSubscription = mNewsInteractor.loadNews(this, pageNum, numPerPage,title,types);
    }
}
