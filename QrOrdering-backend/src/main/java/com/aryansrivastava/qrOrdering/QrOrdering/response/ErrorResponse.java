package com.aryansrivastava.qrOrdering.QrOrdering.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime dateTime;
    private String message;
    private String details;
    private int statusCode;
}