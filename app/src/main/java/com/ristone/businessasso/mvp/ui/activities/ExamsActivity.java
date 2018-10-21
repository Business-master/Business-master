package com.ristone.businessasso.mvp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ristone.businessasso.R;
import com.ristone.businessasso.common.ApiConstants;
import com.ristone.businessasso.common.Constants;
import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.common.UsrMgr;
import com.ristone.businessasso.mvp.entity.AssisBean;
import com.ristone.businessasso.mvp.entity.QuestionnaireBean;
import com.ristone.businessasso.mvp.presenter.impl.AssisPresenterImpl;
import com.ristone.businessasso.mvp.presenter.impl.QuestionnairePresenterImpl;
import com.ristone.businessasso.mvp.ui.activities.base.BaseActivity;
import com.ristone.businessasso.mvp.ui.adapter.AssisAdapter;
import com.ristone.businessasso.mvp.ui.adapter.QuestionnaireAdapter;
import com.ristone.businessasso.mvp.view.AssisView;
import com.ristone.businessasso.mvp.view.QuestionnaireView;
import com.ristone.businessasso.utils.Const;
import com.ristone.businessasso.utils.NetUtil;
import com.ristone.businessasso.widget.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ristone.businessasso.mvp.entity.WanBean.WEBURL;

/**
 * 调查问卷页面
 */
public class ExamsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
        , QuestionnaireView, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnRecyclerViewItemClickListener{

    @BindView(R.id.img_act)
    ImageView mActImg;
    @BindView(R.id.img_topic)
    ImageView mTopicImg;
    @BindView(R.id.tv_title)
    TextView mTv;

    private String url;

    @Inject
    Activity mActivity;

    private QuestionnaireAdapter questionnaireAdapter;
    private List<QuestionnaireBean> questionnaireBeanList;
    @BindView(R.id.news_rv)
    RecyclerView rv_assis;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    @Inject
    QuestionnairePresenterImpl questionnairePresenterImpl;

    private int pageNum=1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_exams;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("调查问卷");
        showOrGoneSearchRl(View.GONE);
        mActImg.setVisibility(View.GONE);
        mTopicImg.setVisibility(View.VISIBLE);
        mTv.setText("调查问卷");
        mEmptyView.setVisibility(View.GONE);

        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));
    }

    private void initPresenter() {
//        questionnairePresenterImpl.setNewsTypeAndId(pageNum, Constants.numPerPage, "","");

        if(UsrMgr.getUseInfo()!=null )
        questionnairePresenterImpl.setNewsTypeAndId(pageNum, 100, UsrMgr.getUsePhone(),"");
        mPresenter = questionnairePresenterImpl;
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }



    private void initRecyclerView() {
        questionnaireBeanList = new ArrayList<>();

        rv_assis.setHasFixedSize(true);
        rv_assis.addItemDecoration(new RecyclerViewDivider(mActivity,
                LinearLayoutManager.VERTICAL, 2, getResources().getColor(R.color.gray)));
        rv_assis.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        rv_assis.setItemAnimator(new DefaultItemAnimator());


        questionnaireAdapter = new QuestionnaireAdapter(R.layout.layout_question_item,questionnaireBeanList);
        questionnaireAdapter.setOnLoadMoreListener(this);
        questionnaireAdapter.setOnRecyclerViewItemClickListener(this);


        rv_assis.setAdapter(questionnaireAdapter);
    }

    @Override
    public void onRefresh() {
       questionnairePresenterImpl.refreshData();
    }

    @Override
    public void onItemClick(View view, int i) {
//        url = ApiConstants.NETEAST_HOST+"dcwj-admin/dcwj/backSubject";
        url = questionnaireAdapter.getData().get(i).getUrl();
        if (url!=null){
            Intent intent = new Intent(ExamsActivity.this, WebActivity.class);
            intent.putExtra(WEBURL, url);
            startActivity(intent);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        questionnairePresenterImpl.loadMore();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMsg(String msg) {
        mProgressBar.setVisibility(View.GONE);
        // 网络不可用状态在此之前已经显示了提示信息
        if (NetUtil.isNetworkAvailable()) {
            Snackbar.make(rv_assis, msg, Snackbar.LENGTH_LONG).show();
        }
    }

    private void checkIsEmpty(List<QuestionnaireBean> newsSummary) {
        if (newsSummary != null && questionnaireAdapter.getData().size()>0) {
            rv_assis.setVisibility(View.VISIBLE);
        } else {
            rv_assis.setVisibility(View.GONE);
        }
    }

    @Override
    public void setQuestionnaireList(List<QuestionnaireBean> questionnaireList, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                questionnaireAdapter.setNewData(questionnaireList);
                checkIsEmpty(questionnaireList);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                checkIsEmpty(questionnaireList);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                if (questionnaireList == null){
                    return;
                }

            {
                questionnaireAdapter.notifyDataChangedAfterLoadMore(questionnaireList, false);
                Snackbar.make(rv_assis, getString(R.string.no_more), Snackbar.LENGTH_SHORT).show();
            }


            break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:

                break;
        }
    }
}
