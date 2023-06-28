package com.rhs.extrato.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
@Slf4j
public class TimeUtils {
    public static String convertExcelDate(String excelDate) {
        int numericValue = Integer.parseInt(excelDate);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(numericValue);

        return dateFormat.format(date);

    }

    public static String convertHours(String time) {
        BigDecimal decimal = new BigDecimal(time);
        BigDecimal hoursDecimal = decimal.multiply(new BigDecimal(24));

        int hours = hoursDecimal.intValue();
        final BigDecimal multiply = hoursDecimal.remainder(BigDecimal.ONE).multiply(new BigDecimal(60));
        int minutes = multiply.intValue();
        int seconds = multiply.remainder(BigDecimal.ONE).multiply(new BigDecimal(60)).intValue();

        
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}