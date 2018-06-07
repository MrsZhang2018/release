package com.fanwe.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SDDateUtil
{
	public static final long MILLISECONDS_DAY = 1000 * 60 * 60 * 24;
	public static final long MILLISECONDS_HOUR = 1000 * 60 * 60;
	public static final long MILLISECONDS_MINUTES = 1000 * 60;
	public static final long MILLISECONDS_SECOND = 1000;

	public static long getDuringDay(long mss)
	{
		return mss / MILLISECONDS_DAY;
	}

	public static long getDuringHours(long mss)
	{
		return (mss % MILLISECONDS_DAY) / MILLISECONDS_HOUR;
	}

	public static long getDuringMinutes(long mss)
	{
		return (mss % MILLISECONDS_HOUR) / MILLISECONDS_MINUTES;
	}

	public static long getDuringSeconds(long mss)
	{
		return (mss % MILLISECONDS_MINUTES) / MILLISECONDS_SECOND;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss转毫秒
	 * 
	 * @param stringLong
	 * @return
	 */
	public static long yyyyMMddHHmmss2Mil(String stringLong)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date date = formatter.parse(stringLong);
			long mil = date.getTime();
			return mil;
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 毫秒转yyyy-MM-dd HH:mm:ss
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2yyyyMMddHHmmss(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 毫秒转HH
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2HH(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("HH");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 毫秒转MM-dd HH:mm:ss
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2MMddHHmmss(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 毫秒转 yyyy-MM-dd
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2yyyyMMdd(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 毫秒转 MM-dd
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2MMdd(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 毫秒转HH:mm:ss
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2HHmmss(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 毫秒转HH:mm
	 * 
	 * @param mil
	 * @return
	 */
	public static String mil2HHmm(long mil)
	{
		Date date = new Date(mil);
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 返回当前时间的yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getNow_yyyyMMddHHmmss()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * 
	 * @return 返回当前时间的yyyy-MM-dd字符串
	 */
	public static String getNow_yyyyMMdd()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		return dateString;
	}

	/**
	 * @return 返回当前时间的HH:mm:ss字符串
	 */
	public static String getNow_HHmmss()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	public static String formatDuringFrom(long timestamp)
	{
		long time = System.currentTimeMillis() - timestamp;

		if (time <= 0 && time < MILLISECONDS_MINUTES)
		{
			return "刚刚";
		} else if (time < MILLISECONDS_HOUR)
		{
			long min = getDuringMinutes(time);
			return min + "分钟前";
		} else if (time < MILLISECONDS_DAY)
		{
			try
			{
				String hhmm = mil2HHmm(timestamp);
				int hourTime = Integer.valueOf(hhmm.substring(0, 2));
				if (hourTime <= 0)
				{
					return "半夜" + hhmm;
				} else if (hourTime < 6)
				{
					return "凌晨" + hhmm;
				} else if (hourTime < 9)
				{
					return "早上" + hhmm;
				} else if (hourTime < 12)
				{
					return "上午" + hhmm;
				} else if (hourTime == 12)
				{
					return "中午" + hhmm;
				} else if (hourTime < 18)
				{
					return "下午" + hhmm;
				} else
				{
					return "晚上" + hhmm;
				}
			} catch (Exception e)
			{
				return getDuringHours(timestamp) + "小时前";
			}
		} else
		{
			long day = getDuringDay(time);
			if (day <= 1)
			{
				return "昨天" + mil2HHmm(timestamp);
			} else if (day <= 2)
			{
				return "前天" + mil2HHmm(timestamp);
			} else
			{
				return mil2MMddHHmmss(timestamp);
			}
		}

	}
}