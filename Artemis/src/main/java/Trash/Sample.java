package Trash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Sample {
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh-mm-ss aa", Locale.ENGLISH);
		Date start= sdf.parse("10-12-2018 9-13-43 AM");
		System.out.println(start.getTime());
		
		Date end= sdf.parse("10-12-2018 9-30-35 AM");
		System.out.println(end.getTime());
		
		long difference=end.getTime()-start.getTime();
		System.out.println(difference);
		
	}

}
