package com.fanwe.o2o.event;

import com.fanwe.o2o.model.User_infoModel;
import com.sunday.eventbus.SDBaseEvent;

/**
 *购物车
 *
 * Created by luodong on 2017/1/4.
 */

public class EventLoginBack extends SDBaseEvent {
    public User_infoModel actModel;

    public EventLoginBack(User_infoModel actModel) {
        super(actModel, 10);
        this.actModel = actModel;
    }
}
