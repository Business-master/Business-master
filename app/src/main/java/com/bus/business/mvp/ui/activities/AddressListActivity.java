package com.bus.business.mvp.ui.activities;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bus.business.R;
import com.bus.business.common.UsrMgr;
import com.bus.business.mvp.entity.PhoneBookBean;
import com.bus.business.mvp.entity.response.RspPhoneBookbean;
import com.bus.business.mvp.ui.activities.base.BaseActivity;
import com.bus.business.mvp.ui.adapter.SortAdapter;
import com.bus.business.mvp.view.CharacterParser;
import com.bus.business.mvp.view.ClearEditText;
import com.bus.business.mvp.view.PinyinComparator;
import com.bus.business.mvp.view.SideBar;
import com.bus.business.mvp.view.SortModel;
import com.bus.business.repository.network.RetrofitManager;
import com.bus.business.utils.TransformUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

public class AddressListActivity extends BaseActivity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    /**
     * ºº×Ö×ª»»³ÉÆ´ÒôµÄÀà
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * ¸ù¾ÝÆ´ÒôÀ´ÅÅÁÐListViewÀïÃæµÄÊý¾ÝÀà
     */
    private PinyinComparator pinyinComparator;
    @Inject
    Activity mActivity;

    private void initView() {
        //ÊµÀý»¯ºº×Ö×ªÆ´ÒôÀà
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //ÉèÖÃÓÒ²à´¥Ãþ¼àÌý
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //¸Ã×ÖÄ¸Ê×´Î³öÏÖµÄÎ»ÖÃ
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                phoneBookBeens.get(position).intentToNext(mActivity);
            }
        });

        SourceDateList = new ArrayList<>();

        // ¸ù¾Ýa-z½øÐÐÅÅÐòÔ´Êý¾Ý
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //¸ù¾ÝÊäÈë¿òÊäÈëÖµµÄ¸Ä±äÀ´¹ýÂËËÑË÷
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //µ±ÊäÈë¿òÀïÃæµÄÖµÎª¿Õ£¬¸üÐÂÎªÔ­À´µÄÁÐ±í£¬·ñÔòÎª¹ýÂËÊý¾ÝÁÐ±í
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * ÎªListViewÌî³äÊý¾Ý
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<PhoneBookBean> date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).getNiceName());
            sortModel.setAddress(date.get(i).getOrganizationName());
            //ºº×Ö×ª»»³ÉÆ´Òô
            String pinyin = characterParser.getSelling(date.get(i).getNiceName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // ÕýÔò±í´ïÊ½£¬ÅÐ¶ÏÊ××ÖÄ¸ÊÇ·ñÊÇÓ¢ÎÄ×ÖÄ¸
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * ¸ù¾ÝÊäÈë¿òÖÐµÄÖµÀ´¹ýÂËÊý¾Ý²¢¸üÐÂListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // ¸ù¾Ýa-z½øÐÐÅÅÐò
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    public void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void initViews() {
        setCustomTitle("通讯录");
        showOrGoneSearchRl(View.GONE);
        initView();
        getContactArray();
    }


    private List<PhoneBookBean> phoneBookBeens;

    private void getContactArray() {

        RetrofitManager.getInstance(1).getPhonesListObservable(UsrMgr.getUseId())
                .compose(TransformUtils.<RspPhoneBookbean>defaultSchedulers())
                .subscribe(new Subscriber<RspPhoneBookbean>() {
                    @Override
                    public void onCompleted() {
                        KLog.d();
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onNext(RspPhoneBookbean newsBean) {
                        KLog.d(newsBean.toString());
                        phoneBookBeens = newsBean.getBody().getPhoneBookList();
                        SourceDateList = filledData(phoneBookBeens);
                        Collections.sort(SourceDateList, pinyinComparator);
                        adapter.updateListView(SourceDateList);
                    }
                });
    }

}
