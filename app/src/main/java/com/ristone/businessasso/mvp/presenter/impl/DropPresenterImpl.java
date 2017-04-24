package com.ristone.businessasso.mvp.presenter.impl;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.CashBean;
import com.ristone.businessasso.mvp.entity.DropBean;
import com.ristone.businessasso.mvp.entity.response.RspDropBean;
import com.ristone.businessasso.mvp.interactor.DropInteractor;
import com.ristone.businessasso.mvp.interactor.DropInteractorImpl;
import com.ristone.businessasso.mvp.presenter.DropPresenter;
import com.ristone.businessasso.mvp.presenter.base.BasePresenterImpl;
import com.ristone.businessasso.mvp.view.NewsView;
import com.socks.library.KLog;

import java.util.List;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 */
public class DropPresenterImpl extends BasePresenterImpl<NewsView<List<DropBean>>, RspDropBean>
        implements DropPresenter {

    private DropInteractor<RspDropBean> mNewsInteractor;
    private int pageNum;
    private int numPerPage;
    private String title;
    private String typeId;
    private CashBean cashBean;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;

    @Inject
    public DropPresenterImpl(DropInteractorImpl newsInteractor) {
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
    public void success(RspDropBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setNewsList(data.getBody().getCashProfessorList(), loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage, CashBean cashBean) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.cashBean = cashBean;
    }

    @Override
    public void dropData(CashBean cashBean) {
        this.cashBean = cashBean;
        refreshData();
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
        mSubscription = mNewsInteractor.loadNews(this, pageNum, numPerPage, cashBean);
    }
}
