package com.ristone.businessasso.mvp.entity;

import com.ristone.businessasso.mvp.entity.response.base.BaseNewBean;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/12/22
 */
@Deprecated
public class LikeBean extends BaseNewBean {


    /**
     * id : 3
     * utime : 1482477376000
     * title : 测试新闻
     * status : 1
     * fmImg : /gsl/image/news/f95e9685430043e4a2d9ef17cb272382.jpg
     * types : 1
     * ctime : 1482473334000
     * areaId : null
     */

    private int id;
    private long utime;
    private String status;
    private String types;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "LikeBean{" +
                "id=" + id +
                ", utime=" + utime +
                ", status='" + status + '\'' +
                ", types='" + types + '\'' +
                '}';
    }
}
