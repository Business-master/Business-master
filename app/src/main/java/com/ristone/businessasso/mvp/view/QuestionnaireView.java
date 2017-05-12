package com.ristone.businessasso.mvp.view;

import com.ristone.businessasso.common.LoadNewsType;
import com.ristone.businessasso.mvp.entity.AreaSeaBean;
import com.ristone.businessasso.mvp.entity.QuestionnaireBean;
import com.ristone.businessasso.mvp.view.base.BaseView;

import java.util.List;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/28
 * 问卷调查 的视图
 */
public interface QuestionnaireView extends BaseView{
    void setQuestionnaireList(List<QuestionnaireBean> questionnaireList, @LoadNewsType.checker int loadType);
}
