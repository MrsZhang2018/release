package com.fanwe.hybrid.common;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;

/**
 * Created by Administrator on 2016/10/25.
 */

public class AppConfigParam
{
    public static int isShowingConfig()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getOpen_show_diaog();
        } else
        {
            return 0;
        }
    }
}
