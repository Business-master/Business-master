package com.ristone.businessasso.mvp.entity;

/**
 * Created by ATRSnail on 2017/5/18.
 */

public class Versions {
    VersionBean latestVersion;

    int isUpdate;



    public VersionBean getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(VersionBean latestVersion) {
        this.latestVersion = latestVersion;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    @Override
    public String toString() {
        return "Versions{" +
                "latestVersion=" + latestVersion +
                ", isUpdate=" + isUpdate +
                '}';
    }
}
