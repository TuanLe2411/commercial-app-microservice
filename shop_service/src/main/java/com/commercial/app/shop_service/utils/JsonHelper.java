package com.commercial.app.shop_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

public class JsonHelper {
    public static String toString(Object o) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(o);
    }

    public static <T extends Object> T toObject(String input, Class<T> parsedType) {
        Gson gson = new Gson();
        return (T) gson.fromJson(input, parsedType);
    }
}
