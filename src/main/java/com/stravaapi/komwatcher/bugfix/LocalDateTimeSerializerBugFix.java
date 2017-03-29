package com.stravaapi.komwatcher.bugfix;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;

import javastrava.json.impl.gson.serializer.LocalDateTimeSerializer;

public class LocalDateTimeSerializerBugFix extends LocalDateTimeSerializer {

	/**
	 * Override the ootb serializer since it will truncate and seconds with value of 0 when serializing to JSON
	 */
	@Override
	public JsonElement serialize(final LocalDateTime src, final Type srcType, final JsonSerializationContext context) {
		return new JsonPrimitive(src.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME));
	}
}
