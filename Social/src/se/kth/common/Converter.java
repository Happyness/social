package se.kth.common;

import java.util.List;

import com.google.gson.Gson;

public class Converter
{
	private static Gson instance = new Gson();
	
	public static String toJson(Object object)
	{
		return instance.toJson(object);
	}
	
	public static <T> T fromJson(String json, Class<T> type)
	{
		return instance.fromJson(json, type);
	}
	
	public static <T> T fromJsonList(String json, Class<T> type)
	{
		return instance.fromJson(json, type);
	}
}
