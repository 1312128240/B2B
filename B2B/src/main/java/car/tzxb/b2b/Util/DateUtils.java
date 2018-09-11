package car.tzxb.b2b.Util;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import car.myview.CountDownTimerView;

/**
 * Created by Administrator on 2018/1/13 0013.
 */

public class DateUtils {

     public  final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 字符串转时间
     * @param str
     * @param format
     * @return
     */
    public  Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }
    /**
     * 获得天数差
     * @param begin
     * @param end
     * @return
     */
  public  long getDayDiff(Date begin, Date end){
        long day = 1;
        if(end.getTime() < begin.getTime()){
            day = -1;
        }else if(end.getTime() == begin.getTime()){
            day = 1;
        }else {
            day += (end.getTime() - begin.getTime())/(24 * 60 * 60 * 1000) ;
        }
        return day;
    }


    /**
     * 转化时间输入时间与当前时间的间隔
     *
     * @param timestamp
     * @return
     */
    public String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
        String timeStr = null;
        if (timeGap > 24 * 60 * 60) {// 1天以上
            timeStr = timeGap / (24 * 60 * 60) + "天前";
        } else if (timeGap > 60 * 60) {// 1小时-24小时
            timeStr = timeGap / (60 * 60) + "小时前";
        } else if (timeGap > 60) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 比较时间大小
     * @param begin
     * @param end
     * @return
     */
    public  int compareDate(String begin, String end) {
        DateFormat df = new SimpleDateFormat(FORMAT);
        try {
            Date beginDate = df.parse(begin);
            Date endDate = df.parse(end);
            if (beginDate.getTime() < endDate.getTime()) {
                return 1;
            } else if (beginDate.getTime() > endDate.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 当前时间的字符串形式
     * @return
     */
    public  String cuttentStr(){
          SimpleDateFormat format = new SimpleDateFormat(FORMAT);
          String t=format.format(new Date());
          return t;
      }

    /**
     * 获得当前时间的毫秒数
     * 详见{@link System#currentTimeMillis()}
     * @return
     */
    public  long millis() {
        return System.currentTimeMillis();
    }


      /*
       *  date要转换的date类型的时间
      */
    public  long dateToLong(Date date) {
        return date.getTime();
    }
    /*
     * 毫秒转化时分秒毫秒
     */
    public  List<Long> formatDuring(long mss) {
        List<Long> timeList=new ArrayList<>();
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        timeList.add(days);
        timeList.add(hours);
        timeList.add(minutes);
        timeList.add(seconds);

        return timeList;
    }

    public void initTime(CountDownTimerView view, String endTime) {
        String currentTimeStr = cuttentStr();
        int result = compareDate(currentTimeStr, endTime);
        if (result == 1) {
            Date dtae2 = str2Date(endTime, FORMAT);
            long difTime =dateToLong(dtae2) -millis();
            List<Long> timeList = formatDuring(difTime);
            long l1 = timeList.get(0);
            long l2 = timeList.get(1);
            long l3 = timeList.get(2);
            long l4 = timeList.get(3);
            view.setTime((int) l1, (int) l2, (int) l3, (int) l4);
            view.start();
        } else {
            view.setTime(0, 0, 0, 0);
            view.start();
        }
    }

}
