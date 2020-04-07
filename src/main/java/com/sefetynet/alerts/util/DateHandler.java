package com.sefetynet.alerts.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateHandler extends StdDeserializer {

	public DateHandler() {
		this(null);
	}
		
	public DateHandler(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException, JsonProcessingException {

		String date = jsonParser.getText();
		
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			return df.parse(date);
		}catch(Exception e) {
			return null;
		}	
	}

}
