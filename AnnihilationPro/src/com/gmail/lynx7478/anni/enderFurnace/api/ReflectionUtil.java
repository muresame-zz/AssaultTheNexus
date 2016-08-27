package com.gmail.lynx7478.anni.enderFurnace.api;

import java.lang.reflect.Field;

public class ReflectionUtil
{
	public static void setSuperValue(Object instance, String fieldName,
			Object value) throws Exception
	{
		Field field = instance.getClass().getSuperclass()
				.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}

	public static void setValue(Object instance, String fieldName, Object value)
			throws Exception
	{
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, value);
	}
}
