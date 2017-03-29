package com.stravaapi.komwatcher.bugfix;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import javastrava.json.impl.gson.serializer.ZonedDateTimeSerializer;

public class ZonedDateTimeSerializerBugFix extends ZonedDateTimeSerializer {

	/**
	 * Override the ootb serializer since it will truncate and seconds with value of 0 when serializing to JSON
	 */
	@Override
	public JsonElement serialize(final ZonedDateTime src, final Type srcType, final JsonSerializationContext context) {
		return new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
