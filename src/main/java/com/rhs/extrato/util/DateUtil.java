package com.rhs.extrato.util;

import lombok.experimental.UtilityClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtil {
    public static String convertExcelDate(String excelDate) {
        int numericValue = Integer.parseInt(excelDate);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(numericValue);
        return dateFormat.format(date);

    }
}
