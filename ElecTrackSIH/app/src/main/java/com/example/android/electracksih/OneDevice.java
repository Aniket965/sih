package com.example.android.electracksih;

/**
 * Created by NITANT SOOD on 31-03-2018.
 */

public class OneDevice {
    String applianceid;
    String roomid;
    String status;
    Long timestamp_UNIX;

    public OneDevice(String applianceid, String roomid, String status, Long timestamp_UNIX) {
        this.applianceid = applianceid;
        this.roomid = roomid;
        this.status = status;
        this.timestamp_UNIX = timestamp_UNIX;
    }

    public String getApplianceid() {
        return applianceid;
    }

    public void setApplianceid(String applianceid) {
        this.applianceid = applianceid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClock() {
        return timestamp_UNIX;
    }

    public void setClock(Long timestamp_UNIX) {
        this.timestamp_UNIX = timestamp_UNIX;
    }
}
