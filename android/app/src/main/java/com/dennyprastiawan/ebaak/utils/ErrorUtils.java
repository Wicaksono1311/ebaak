package com.dennyprastiawan.ebaak.utils;

import com.dennyprastiawan.ebaak.API.ServiceGenerator;
import com.dennyprastiawan.ebaak.model.APIError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {
    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                ServiceGenerator.retrofit
                        .responseBodyConverter(APIError.class, new Annotation[0]);
        APIError error ;
        try {
            if(response.errorBody() != null) {
                error = converter.convert(response.errorBody());
            }  else {
                error = null;
            }
        } catch (IOException e) {
            return new APIError("error");
        }
        return error;
    }
}
