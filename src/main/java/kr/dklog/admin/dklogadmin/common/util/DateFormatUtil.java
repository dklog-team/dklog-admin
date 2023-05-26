package kr.dklog.admin.dklogadmin.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {
    public static String toDateTime(LocalDateTime localDateTime){
        if(localDateTime != null){
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm"));
        }
        return null;
    }
}
