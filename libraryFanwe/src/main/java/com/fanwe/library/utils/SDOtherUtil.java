package com.fanwe.library.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.listener.SDNetStateListener;
import com.ta.util.netstate.TANetWorkUtil;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;

public class SDOtherUtil
{

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void copyText(CharSequence content)
    {
        if (getBuildVersion() < 11)
        {
            android.text.ClipboardManager clip = getOldClipboardManager();
            clip.setText(content);
        } else
        {
            android.content.ClipboardManager clip = getNewClipboardManager();
            clip.setPrimaryClip(ClipData.newPlainText(null, content));
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static CharSequence pasteText()
    {
        CharSequence content = null;
        if (getBuildVersion() < 11)
        {
            android.text.ClipboardManager clip = getOldClipboardManager();
            if (clip.hasText())
            {
                content = clip.getText();
            }
        } else
        {
            android.content.ClipboardManager clip = getNewClipboardManager();
            if (clip.hasPrimaryClip())
            {
                content = clip.getPrimaryClip().getItemAt(0).getText();
            }
        }
        return content;
    }

    @SuppressWarnings("deprecation")
    public static android.text.ClipboardManager getOldClipboardManager()
    {
        android.text.ClipboardManager clip = (android.text.ClipboardManager) SDLibrary.getInstance().getApplication()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return clip;
    }

    public static android.content.ClipboardManager getNewClipboardManager()
    {
        android.content.ClipboardManager clip = (android.content.ClipboardManager) SDLibrary.getInstance().getApplication()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return clip;
    }

    public static int getBuildVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    public static String hideMobile(String mobile)
    {
        String result = null;
        if (!TextUtils.isEmpty(mobile) && TextUtils.isDigitsOnly(mobile) && mobile.length() == 11)
        {
            result = mobile.substring(0, 3) + "****" + mobile.substring(7);
        }
        return result;
    }

    public static Type[] getType(Class<?> clazz)
    {
        Type[] types = null;
        if (clazz != null)
        {
            Type type = clazz.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            types = parameterizedType.getActualTypeArguments();
        }
        return types;
    }

    public static Type getType(Class<?> clazz, int index)
    {
        Type type = null;
        Type[] types = getType(clazz);
        if (types != null && index >= 0 && types.length > index)
        {
            type = types[index];
        }
        return type;
    }

    public static String build(Object... content)
    {
        return build(",", content);
    }

    public static String build(String separator, Object... content)
    {
        if (separator == null)
        {
            separator = "";
        }
        StringBuilder sb = new StringBuilder();
        if (content != null && content.length > 0)
        {
            for (Object obj : content)
            {
                if (obj != null)
                {
                    sb.append(obj.toString()).append(separator);
                }
            }
        }
        return sb.toString();
    }

    public static String buildDefaultString(String defaultText, String text, String before, String after)
    {
        String result = null;
        if (text.startsWith(before))
        {
            if (text.contains(after))
            {
                String removedBefore = text.replaceFirst(before, "");
                String start = removedBefore.substring(0, removedBefore.indexOf(after));
                String end = removedBefore.substring(removedBefore.indexOf(after) + after.length());

                result = start + defaultText + end;
            } else
            {
                result = text.replaceFirst(before, "") + defaultText;
            }
        } else if (text.startsWith(after))
        {
            result = defaultText + text.replaceFirst(after, "");
        } else
        {
            result = defaultText + text;
        }
        return result;
    }

    /**
     * 检查网络
     *
     * @param context
     * @param listener
     */
    public static void checkNet(Context context, SDNetStateListener listener)
    {
        TANetWorkUtil.netType type = TANetWorkUtil.getAPNType(context);
        switch (type)
        {
            case mobile:
                if (listener != null)
                {
                    listener.onMobile();
                }
                break;
            case noneNet:
                if (listener != null)
                {
                    listener.onNone();
                }
                break;
            case wifi:
                if (listener != null)
                {
                    listener.onWifi();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 把文件加到相册
     *
     * @param file
     */
    public static void scanFile(Context context, File file)
    {
        if (context != null && file != null && file.exists())
        {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
    }

    public static boolean isMiui()
    {
        String miuiName = getMiuiName();
        return !TextUtils.isEmpty(miuiName);
    }

    public static boolean isMiuiV8()
    {
        String miuiName = getMiuiName();
        return "V8".equals(miuiName);
    }

    public static String getMiuiName()
    {
        Properties prop = new Properties();
        try
        {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        String miuiName = prop.getProperty("ro.miui.ui.version.name", null);
        return miuiName;
    }

    /**
     * 去掉String尾部的换行
     * \@param c
     *
     * @return
     */
    public static String getTrimContent(String newMemo)
    {
        while (!TextUtils.isEmpty(newMemo) && newMemo.length() > 1 && newMemo.lastIndexOf("\n") == newMemo.length() - 1)
        {
            newMemo = newMemo.substring(0, newMemo.length() - 1);
        }
        return newMemo;
    }

}
