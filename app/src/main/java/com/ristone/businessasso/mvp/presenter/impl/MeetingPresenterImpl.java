package com.ristone.businessasso.mvp.presenter.impl;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.response.RspMeetingBean;
import com.ristone.businessasso.mvp.interactor.NewsInteractor;
import com.ristone.businessasso.mvp.interactor.impl.MeetingInteractorImpl;
import com.ristone.businessasso.mvp.presenter.NewsPresenter;
import com.ristone.businessasso.mvp.presenter.base.BasePresenterImpl;
import com.ristone.businessasso.mvp.view.MeetingView;
import com.socks.library.KLog;

import javax.inject.Inject;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/24
 */
public class MeetingPresenterImpl extends BasePresenterImpl<MeetingView,RspMeetingBean>
        implements NewsPresenter {

    private NewsInteractor<RspMeetingBean> mNewsInteractor;
    private int pageNum;
    private int numPerPage;
    private String title;
    private String typeId;
    private boolean mIsRefresh = true;
    private boolean misFirstLoad;
    private int status;
    @Inject
    public MeetingPresenterImpl(MeetingInteractorImpl newsInteractor) {
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
            mView.setMeetingList(null, loadType);
        }
    }

    @Override
    public void success(RspMeetingBean data) {
        misFirstLoad = true;
        KLog.a("ddd----success");
        if (data == null || data.getBody() == null) return;
        pageNum++;
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;

        if (mView != null) {
            mView.setMeetingList(data.getBody().getMeetingList(), loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void setNewsTypeAndId(int pageNum, int numPerPage,String title,int status) {
        this.pageNum = pageNum;
        this.numPerPage = numPerPage;
        this.title = title;
        this.status=status;
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
        mSubscription = mNewsInteractor.loadNews(this, pageNum, numPerPage,title,status);
    }
}
