package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("routeId")
    @Expose
    private Integer routeId;
    @SerializedName("routeName")
    @Expose
    private String routeName;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", routeName='" + routeName + '\'' +
                ", delStatus=" + delStatus +
                '}';
    }
}
