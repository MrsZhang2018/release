package com.fanwe.library.event;

/**
 * Created by Administrator on 2017/3/3.
 */

public class ELoginEvent {
  public boolean hasJump2Login;
  //public String tag;

  public ELoginEvent(boolean hasJump2Login) {
    this.hasJump2Login = hasJump2Login;
    //this.tag=tag;
  }
}
