package main;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Array {

	public static void main(String[] args) throws Exception {
		
		List<String> dates=new ArrayList<String>();
		dates.add("20 Oct 2052");
		dates.add("25 May 1912");
		Date [] arrayDate= new Date[dates.size()];
        List<String> dateList=new ArrayList<String>();
        
        BigInteger bi=new BigInteger("100000000000000000000000000000000000000000000000000000");
        System.out.println(bi);
        
        dateList=sortDates(dates);
    /*    DateFormat df=new SimpleDateFormat("dd MMM yyyy");
       for(int i=0;i<dates.size();i++) {
           Date date=df.parse(dates.get(i));
           arrayDate[i]=date;
       }
        
        Arrays.sort(arrayDate);
        for(int i=0;i<arrayDate.length;i++) {
            String stringDate=df.format(arrayDate[i]);
            dateList.add(stringDate);
            
        }*/
        
        for(int i=0;i<dateList.size();i++) {
        	System.out.println(dateList.get(i));
        }
	}
	
	public static List<String> sortDates(List<String> dates) throws Exception {
		Date [] arrayDate= new Date[dates.size()];
        List<String> dateList=new ArrayList<String>();
        try {
            
        
        DateFormat df=new SimpleDateFormat("dd MMM yyyy");
       for(int i=0;i<dates.size();i++) {
           Date date=df.parse(dates.get(i));
           arrayDate[i]=date;
       }
        
        Arrays.sort(arrayDate);
        for(int i=0;i<arrayDate.length;i++) {
            String stringDate=df.format(arrayDate[i]);
            dateList.add(stringDate);
        }
        
    }
        
        catch(Exception e) {
            e.printStackTrace();
        }
        return dateList;
		
	}
}
