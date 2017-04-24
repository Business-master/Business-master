package com.ristone.businessasso.mvp.entity.response.base;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/23
 */
public class BaseRspObj<T> {

    private T body;
    private BaseRspHeader head;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public BaseRspHeader getHead() {
        return head;
    }

    public void setHead(BaseRspHeader head) {
        this.head = head;
    }

    @Override
    public String toString() {
        return "ResponseObj{" +
                "body=" + body +
                ", head=" + head +
                '}';
    }

}
