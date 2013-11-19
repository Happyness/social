package se.kth.common;

import java.util.List;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Converter
{
	private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	public static String toJson(Object object)
	{
		return gson.toJson(object);
	}
	
	public static <T> T fromJson(String json, Class<T> type)
	{
		return gson.fromJson(json, type);
	}
	
	public static <T> T fromJsonToList(String json, Type type)
	{
		return gson.fromJson(json, type);
	}
}
