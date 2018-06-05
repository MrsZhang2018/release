package com.fanwe.o2o.model;

/**
 * Created by Administrator on 2017/2/7.
 */

public class UserCenterMessageDataModel
{
    private UserCenterMsgDeliveryModel delivery;
    private UserCenterMsgNoticeModel notify;
    private UserCenterMsgAccountModel account;
    private UserCenterMsgConfirmModel confirm;

    public UserCenterMsgAccountModel getAccount()
    {
        return account;
    }

    public void setAccount(UserCenterMsgAccountModel account)
    {
        this.account = account;
    }

    public UserCenterMsgConfirmModel getConfirm() {
        return confirm;
    }

    public void setConfirm(UserCenterMsgConfirmModel confirm) {
        this.confirm = confirm;
    }

    public UserCenterMsgDeliveryModel getDelivery() {
        return delivery;
    }

    public void setDelivery(UserCenterMsgDeliveryModel delivery) {
        this.delivery = delivery;
    }

    public UserCenterMsgNoticeModel getNotify() {
        return notify;
    }

    public void setNotify(UserCenterMsgNoticeModel notify) {
        this.notify = notify;
    }
}
