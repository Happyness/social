package se.kth.common;

import java.util.Date;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializer;

public class Converter
{
	private static Gson gson = null;
	
	private static Gson getSingleton()
	{
		JsonSerializer<Date> ser = new JsonSerializer<Date>() {
			  @Override
			  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext 
			             context) {
			    return src == null ? null : new JsonPrimitive(src.getTime());
			  }
			};
			JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
				  @Override
				  public Date deserialize(JsonElement json, Type typeOfT,
				       JsonDeserializationContext context) throws JsonParseException {
				    return json == null ? null : new Date(json.getAsLong());
				  }
				};
			
		if (gson == null) {
	        GsonBuilder builder = new GsonBuilder();
	        //builder.setDateFormat("yyyy-MM-dd");
	        //builder.setDateFormat(DateFormat.FULL, DateFormat.FULL);
	        builder.registerTypeAdapter(Date.class, ser);
	        builder.registerTypeAdapter(Date.class, deser);
	        builder.excludeFieldsWithoutExposeAnnotation();
	        gson = builder.create();
		}
		
		return gson;
	}
	
	public static String toJson(Object object)
	{
		return getSingleton().toJson(object);
	}
	
	public static <T> T fromJson(String json, Class<T> type)
	{
		return getSingleton().fromJson(json, type);
	}
	
	public static <T> T fromJsonToList(String json, Type type)
	{
		return getSingleton().fromJson(json, type);
	}
}
