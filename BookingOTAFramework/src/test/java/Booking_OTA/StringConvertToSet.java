package Booking_OTA;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringConvertToSet 

{

	public static Set<String> StringConverter(String source)
	{
		
		 String newsource = Pattern.compile(",", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(source).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource1 = Pattern.compile("Hotels", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource2 = Pattern.compile("hotel ", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource1).replaceAll(Matcher.quoteReplacement(""));
	     String newsource3 = Pattern.compile(" Hotel", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource2).replaceAll(Matcher.quoteReplacement(""));
	     String newsource4 = Pattern.compile("The ", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource3).replaceAll(Matcher.quoteReplacement(""));
	     String newsource5 = Pattern.compile(" The", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource4).replaceAll(Matcher.quoteReplacement(""));
	     String newsource6 = Pattern.compile(" Motel", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource5).replaceAll(Matcher.quoteReplacement(""));
	     String newsource7 = Pattern.compile("Motel ", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource6).replaceAll(Matcher.quoteReplacement(""));
	     String newsource8 = Pattern.compile("By", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource7).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource9 = Pattern.compile("Inn ", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource8).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource10 = Pattern.compile(" Inn", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource9).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource11 = Pattern.compile("AND ", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource10).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource12 = Pattern.compile(" AND", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource11).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource13 = Pattern.compile("& ", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource12).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource14 = Pattern.compile(" &", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource13).replaceAll(Matcher.quoteReplacement(" "));
	     String newsource15 = Pattern.compile("-", Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(newsource14).replaceAll(Matcher.quoteReplacement(" "));

	     String[] s=newsource15.split(" ");
	     Set<String>sourcedata=Arrays.stream(s).collect(Collectors.toSet());
	     sourcedata.remove("");
		return sourcedata;
		
	}
	
}
