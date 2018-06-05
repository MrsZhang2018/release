package com.fanwe.o2o.utils;

import android.content.Context;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.fanwe.o2o.R;
import com.fanwe.o2o.app.App;

/**
 * Glide帮助类
 */
public class GlideUtil
{

    @Deprecated
    public static <T> DrawableTypeRequest<T> load(Context context, T model)
    {
        return load(model);
    }

    /**
     * 默认调用方法
     *
     * @param model String, byte[], File, Integer, Uri
     * @param <T>
     * @return
     */
    public static <T> DrawableTypeRequest<T> load(T model)
    {
        return (DrawableTypeRequest<T>) Glide.with(App.getApplication()).load(model)
                .placeholder(R.drawable.ic_o2o_default_pic)
                .error(R.drawable.ic_o2o_default_pic)
                .dontAnimate();
    }

    //---------以下为扩展方法------------

    @Deprecated
    public static <T> DrawableTypeRequest<T> loadHeadImage(Context context, T model)
    {
        return loadHeadImage(model);
    }

    /**
     * 加载用户头像方法
     *
     * @param model String, byte[], File, Integer, Uri
     * @param <T>
     * @return
     */
    public static <T> DrawableTypeRequest<T> loadHeadImage(T model)
    {
        return (DrawableTypeRequest<T>) load(model)
                .placeholder(R.drawable.ic_o2o_default_pic)
                .error(R.drawable.ic_o2o_default_pic)
                .dontAnimate();
    }
}
