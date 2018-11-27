package com.books.notebasecore.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String dateStr = jp.getText();
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            try {
                long lt = new Long(dateStr);
                date = new Date(lt);
            } catch (Exception e2) {
                sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a", Locale.ENGLISH);
                try {
                    date = sdf.parse(dateStr);
                } catch (Exception e3) {

                }
            }

        }
        return date;
    }
}
