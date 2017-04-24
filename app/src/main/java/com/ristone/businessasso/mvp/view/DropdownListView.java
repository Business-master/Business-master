package com.ristone.businessasso.mvp.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.ristone.businessasso.R;
import com.socks.library.KLog;

import java.util.LinkedList;
import java.util.List;

import static com.ristone.businessasso.mvp.ui.fragment.DropDownFragment.TYPE_NAME;

/**
 * 专家 -- 筛选框列表
 */
public class DropdownListView extends ScrollView {
    public LinearLayout linearLayout;

    public DropdownItemObject current;

    List<? extends DropdownItemObject> list;

    public DropdownButton button;
    public DropdownButton buttonOne;

    public DropdownListView(Context context) {
        this(context, null);
    }

    public DropdownListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_list, this, true);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
    }

    private String str;
    public void flush() {
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                DropdownListItemView itemView = (DropdownListItemView) view;
                DropdownItemObject data = (DropdownItemObject) itemView.getTag();
                if (data == null) {
                    return;
                }
                boolean checked = data == current;
                String suffix = data.getSuffix();
                itemView.bind(TextUtils.isEmpty(suffix) ? data.text : data.text + suffix, checked);
                if (checked) {
                    str = i == 0 ? subStr(data.getValue()) : data.text;
                    button.setText(str);
                    buttonOne.setText(str);
                }
            }
        }
    }

    private String subStr(String str) {
        String result = str.substring(0, str.indexOf("@")) + "@";
        KLog.a("result--->" + result);
        for (int i = 0; i <4; i++) {
            if (result.equals(TYPE_NAME[i])) {
                switch (i) {
                    case 0:
                        result = "抵押类型";
                        break;
                    case 1:
                        result = "还款方式";
                        break;
                    case 2:
                        result = "贷款期限";
                        break;
                    case 3:
                        result = "贷款额度";
                        break;

                }
                break;
            }
        }
        return result;
    }

    public void bindList(List<? extends DropdownItemObject> list,
                         DropdownButton button,
                         final Container container,
                         int selectedId
    ) {
        current = null;
        this.list = list;
        this.button = button;
        LinkedList<View> cachedDividers = new LinkedList<>();
        LinkedList<DropdownListItemView> cachedViews = new LinkedList<>();
        for (int i = 0, n = linearLayout.getChildCount(); i < n; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof DropdownListItemView) {
                cachedViews.add((DropdownListItemView) view);
            } else {
                cachedDividers.add(view);
            }
        }
        KLog.a("*****init1");
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        boolean isFirst = true;
        for (DropdownItemObject item : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                View divider = cachedDividers.poll();
                if (divider == null) {
                    divider = inflater.inflate(R.layout.dropdown_tab_list_divider, linearLayout, false);
                }
                linearLayout.addView(divider);
            }
            DropdownListItemView view = cachedViews.poll();
            if (view == null) {
                view = (DropdownListItemView) inflater.inflate(R.layout.dropdown_tab_list_item, linearLayout, false);
            }
            view.setTag(item);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    DropdownItemObject data = (DropdownItemObject) v.getTag();
                    if (data == null) return;
                    DropdownItemObject oldOne = current;
                    current = data;
                    flush();
                    container.hide();
                    if (oldOne != current) {
                        container.onSelectionChanged(DropdownListView.this);
                    }
                }
            });
            linearLayout.addView(view);
            if (item.id == selectedId && current == null) {
                current = item;
            }
        }
        KLog.a("*****init2");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getVisibility() == VISIBLE) {
                    container.hide();
                } else {
                    container.show(DropdownListView.this);
                }
            }
        });

        if (current == null && list.size() > 0) {
            current = list.get(0);
        }
        flush();
    }

    public void bindList(List<? extends DropdownItemObject> list,
                         final DropdownButton button,
                         DropdownButton buttonOne,
                         final Container container,
                         int selectedId
    ) {
        this.buttonOne = buttonOne;
        bindList(list,button, container, selectedId);
        buttonOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                container.onTouchToTop();
                button.performClick();
            }
        });
    }


    public static interface Container {
        void show(DropdownListView listView);

        void hide();

        void onSelectionChanged(DropdownListView view);

        void onTouchToTop();
    }


}
