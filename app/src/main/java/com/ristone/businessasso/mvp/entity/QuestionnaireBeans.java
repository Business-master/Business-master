package com.ristone.businessasso.mvp.entity;

import java.util.List;

/**
 * Created by ATRSnail on 2017/5/10.
 */

public class QuestionnaireBeans {

    List<QuestionnaireBean> questionnaireList;

    public List<QuestionnaireBean> getQuestionnaireList() {
        return questionnaireList;
    }

    public void setQuestionnaireList(List<QuestionnaireBean> questionnaireList) {
        this.questionnaireList = questionnaireList;
    }

    @Override
    public String toString() {
        return "QuestionnaireBeans{" +
                "questionnaireList=" + questionnaireList +
                '}';
    }
}
