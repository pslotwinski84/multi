package com.rad.multiplex.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class LocalDateTimeConverter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime>
{

  @Override
  public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context)
  {

	Instant instant=src.toInstant(ZoneOffset.ofHours(2));
    
    DateTimeFormatter fmt = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneOffset.UTC);
    return new JsonPrimitive(fmt.format(instant));
    
  }

	@Override
	public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		try {
			return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		} catch (RuntimeException e) {
			throw new JsonParseException("Unable to parse LocalDateTime", e);
		}
	}
}

