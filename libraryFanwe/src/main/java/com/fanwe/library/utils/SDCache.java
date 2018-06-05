package com.fanwe.library.utils;

import android.content.Context;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 本地缓存工具类
 * <p>
 * 如果已经缓存的对象的类的属性发生了变化并且serialVersionUID也变化了，则获取不到原先缓存的对象。
 * <p>
 * 如果仍然想获取到原先缓存的对象，则应该保持serialVersionUID不变(private static final long serialVersionUID = 0L);
 */
public class SDCache
{
    private static final String TAG = "SDCache";

    private static final String DEFAULT_CACHE_DIR = "sdcache";

    private static final String INT = "int_";
    private static final String DOUBLE = "double_";
    private static final String BOOLEAN = "boolean_";
    private static final String STRING = "string_";
    private static final String OBJECT = "object_";

    private static Object lock = new Object();
    private static File cacheDir;

    public synchronized static void init(Context context)
    {
        initCacheDir(context);
    }

    // int
    public static boolean setInt(String key, int value)
    {
        synchronized (lock)
        {
            ObjectOutputStream out = null;
            try
            {
                File file = createCacheFile(INT + key);
                out = getOutputStream(file);
                out.writeInt(value);
                return true;
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return false;
            } finally
            {
                closeQuietly(out);
            }
        }
    }

    public static int getInt(String key, int defaultValue)
    {
        synchronized (lock)
        {
            ObjectInputStream in = null;
            try
            {
                File file = getCacheFile(INT + key);
                in = getInputStream(file);
                return in.readInt();
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return defaultValue;
            } finally
            {
                closeQuietly(in);
            }
        }
    }

    public static <T extends Serializable> boolean removeInt(String key)
    {
        synchronized (lock)
        {
            return remove(INT + key);
        }
    }

    // double
    public static boolean setDouble(String key, double value)
    {
        synchronized (lock)
        {
            ObjectOutputStream out = null;
            try
            {
                File file = createCacheFile(DOUBLE + key);
                out = getOutputStream(file);
                out.writeDouble(value);
                return true;
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return false;
            } finally
            {
                closeQuietly(out);
            }
        }
    }

    public static double getDouble(String key, double defaultValue)
    {
        synchronized (lock)
        {
            ObjectInputStream in = null;
            try
            {
                File file = getCacheFile(DOUBLE + key);
                in = getInputStream(file);
                return in.readDouble();
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return defaultValue;
            } finally
            {
                closeQuietly(in);
            }
        }
    }

    public static <T extends Serializable> boolean removeDouble(String key)
    {
        synchronized (lock)
        {
            return remove(DOUBLE + key);
        }
    }

    // boolean
    public static boolean setBoolean(String key, boolean value)
    {
        synchronized (lock)
        {
            ObjectOutputStream out = null;
            try
            {
                File file = createCacheFile(BOOLEAN + key);
                out = getOutputStream(file);
                out.writeBoolean(value);
                return true;
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return false;
            } finally
            {
                closeQuietly(out);
            }
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue)
    {
        synchronized (lock)
        {
            ObjectInputStream in = null;
            try
            {
                File file = getCacheFile(BOOLEAN + key);
                in = getInputStream(file);
                return in.readBoolean();
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return defaultValue;
            } finally
            {
                closeQuietly(in);
            }
        }
    }

    public static <T extends Serializable> boolean removeBoolean(String key)
    {
        synchronized (lock)
        {
            return remove(BOOLEAN + key);
        }
    }

    // string
    public static boolean setString(String key, String value)
    {
        synchronized (lock)
        {
            String realKey = STRING + key;
            if (value == null)
            {
                return remove(realKey);
            }

            ObjectOutputStream out = null;
            try
            {
                File file = createCacheFile(realKey);
                out = getOutputStream(file);
                out.writeUTF(value);
                return true;
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return false;
            } finally
            {
                closeQuietly(out);
            }
        }
    }

    public static String getString(String key, String defaultValue)
    {
        synchronized (lock)
        {
            ObjectInputStream in = null;
            try
            {
                File file = getCacheFile(STRING + key);
                in = getInputStream(file);
                return in.readUTF();
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return defaultValue;
            } finally
            {
                closeQuietly(in);
            }
        }
    }

    public static <T extends Serializable> boolean removeString(String key)
    {
        synchronized (lock)
        {
            return remove(STRING + key);
        }
    }

    // object
    public static <T extends Serializable> boolean setObject(T model)
    {
        synchronized (lock)
        {
            ObjectOutputStream out = null;
            try
            {
                File file = createCacheFile(OBJECT + model.getClass().getName());
                out = getOutputStream(file);
                out.writeObject(model);
                return true;
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return false;
            } finally
            {
                closeQuietly(out);
            }
        }
    }

    public static <T extends Serializable> T getObject(Class<T> clazz)
    {
        synchronized (lock)
        {
            ObjectInputStream in = null;
            try
            {
                File file = getCacheFile(OBJECT + clazz.getName());
                in = getInputStream(file);
                return (T) in.readObject();
            } catch (Exception e)
            {
                Log.e(TAG, e.toString());
                return null;
            } finally
            {
                closeQuietly(in);
            }
        }
    }

    public static <T extends Serializable> boolean removeObject(Class<T> clazz)
    {
        synchronized (lock)
        {
            if (clazz == null)
            {
                return false;
            }
            return remove(OBJECT + clazz.getName());
        }
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    private static boolean remove(String key)
    {
        synchronized (lock)
        {
            File file = getCacheFile(key);
            return file.delete();
        }
    }

    /**
     * 清除所有缓存
     */
    public static void clear()
    {
        synchronized (lock)
        {
            if (cacheDir == null)
            {
                return;
            }
            File[] arrFile = cacheDir.listFiles();
            if (arrFile == null)
            {
                return;
            }

            for (File file : arrFile)
            {
                deleteFileOrDir(file);
            }
        }
    }

    //-------------------------------------------------------------------------

    /**
     * 初始化缓存目录
     *
     * @param context
     */
    private static void initCacheDir(Context context)
    {
        if (cacheDir == null)
        {
            File dir = context.getExternalCacheDir();
            if (dir == null)
            {
                dir = context.getCacheDir();
            }
            cacheDir = new File(dir, DEFAULT_CACHE_DIR);
        }

        makesureDirExist();
    }

    private static void makesureDirExist()
    {
        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }
    }

    /**
     * 获得真实的key
     *
     * @param key
     * @return
     */
    private static String createRealKey(String key)
    {
        return MD5Util.MD5(key);
    }

    /**
     * 根据key获得缓存文件
     *
     * @param key
     * @return
     */
    private static File getCacheFile(String key)
    {
        if (cacheDir == null)
        {
            Log.e(TAG, "please invoke init(context) before put and get");
            return null;
        }
        makesureDirExist();
        File file = new File(cacheDir, createRealKey(key));
        return file;
    }

    /**
     * 创建缓存文件
     *
     * @param key
     * @return
     */
    private static File createCacheFile(String key) throws Exception
    {
        File file = getCacheFile(key);
        if (!file.exists())
        {
            file.createNewFile();
        }
        return file;
    }

    private static ObjectOutputStream getOutputStream(File file) throws Exception
    {
        return new ObjectOutputStream(new FileOutputStream(file));
    }

    private static ObjectInputStream getInputStream(File file) throws Exception
    {
        return new ObjectInputStream(new FileInputStream(file));
    }

    private static boolean deleteFileOrDir(File path)
    {
        if (path == null || !path.exists())
        {
            return true;
        }
        if (path.isFile())
        {
            return path.delete();
        }
        File[] files = path.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

    private static void closeQuietly(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            } catch (Throwable ignored)
            {
            }
        }
    }

}
