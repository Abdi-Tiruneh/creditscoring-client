package com.dxvalley.creditscoring.exceptions;

public class exceptionUtils {

    public static String extractErrorMessage(String errorResponse) {
        int startIndex = errorResponse.indexOf("message\":\"");
        int endIndex = errorResponse.indexOf("\",\"requestPath\"");

        if (startIndex != -1 && endIndex != -1) {
            return errorResponse.substring(startIndex + 10, endIndex);
        }

        return "Error message extraction failed";
    }


}
