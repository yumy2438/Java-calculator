package conversionutils;

import java.math.BigInteger;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConversionUtils {
	public static final String[] BIG_NUMBERS =  {"1000000000000000000000000000000", 
			"1000000000000000000000000000", "1000000000000000000000000","1000000000000000000000", 
			"1000000000000000000", "1000000000000000","1000000000000", "1000000000", "1000000", "1000"};
	private Locale current_locale;
    private ResourceBundle number_readings;
    
	public ConversionUtils(Locale current_locale)
	{
		this.current_locale = current_locale;
		this.number_readings = ResourceBundle.getBundle("NumberReadingsBundle", current_locale);
	}
	
	public int convert_text_into_numbers(String text) throws MissingResourceException
	{
		int number =  Integer.parseInt(number_readings.getString(text.trim().toLowerCase()));
		return number;
	}
	
	public BigInteger convert_bignumtexts_into_numbers(String text) throws MissingResourceException
	{
		return new BigInteger(number_readings.getString(text.trim().toLowerCase()));
	}
	
	private int get_2_digit_number_from_reading(String number_reading) throws MissingResourceException
	{
		String[] number_readings = number_reading.trim().toLowerCase().split(" ");
		int tens_digit = convert_text_into_numbers(number_readings[0]);
		if (tens_digit<10 || tens_digit>99) throw new MissingResourceException("","","");//checking 2-digit
		if (number_readings.length > 1)
		{
			int ones_digit = convert_text_into_numbers(number_readings[1]);
			if(ones_digit>10) throw new MissingResourceException("", "", "");
			return tens_digit+ones_digit;
		}
		return tens_digit;
	}
	
	private int get_3_digit_number_from_reading(String number_reading) throws MissingResourceException//üç yüz seksen üç, yüz iki, yüz seksen üç
	{
		String reading_100 = this.number_readings.getString("100");
		if (!number_reading.contains(reading_100)) throw new java.util.MissingResourceException("a", "b", "c");
		String [] number_readings = number_reading.trim().toLowerCase().split(reading_100);
		switch (number_readings.length)
		{
			case 0:
				return 100;
			case 1:
				int hundreds = convert_text_into_numbers(number_readings[0]);
				if(hundreds>10) throw new MissingResourceException("", "", "");
				return hundreds*100;
			case 2:
				int rest_number = 0;
				try
				{
					rest_number = get_2_digit_number_from_reading(number_readings[1]);
				}
				catch (java.util.MissingResourceException e)
				{
					rest_number = convert_text_into_numbers(number_readings[1]);
				}
				if(!number_readings[0].equals("")) //yüz üç
				{
					return rest_number+convert_text_into_numbers(number_readings[0])*100;
				}
				return 100+rest_number;
		}
		
		return 0;
	}
	
	public int get_number(String number_reading) throws MissingResourceException
	{
		int number;
		try
		{
			number = get_3_digit_number_from_reading(number_reading);
		}
		catch(Exception e1)
		{
			try
			{
				number = get_2_digit_number_from_reading(number_reading);
			}
			catch(Exception e2)
			{
				try
				{
					number = convert_text_into_numbers(number_reading);	
				}
				catch(Exception e3)
				{
					throw new MissingResourceException("", "", "");
				}
			}
			
		}
		return number;
	}


	public String convert_number_into_text(String number_text)
	{
		try
		{
			return number_readings.getString(number_text.trim().toLowerCase());
		}
		catch(Exception e)
		{
			return "";
		}
	}

	
	private String get_reading_of_2_digit_number(String number) throws MissingResourceException
	{
		String reading_0 = number_readings.getString("0");
		if(this.current_locale.getLanguage().equals("en") && number.substring(0,1).equals("1")) return convert_number_into_text(number);
		String tens = convert_number_into_text("" +Integer.parseInt(number.substring(0, 1))*10);
		String digit = convert_number_into_text(number.substring(1,2));
		if (digit.equals(reading_0)) digit = "";
		if (tens.equals(reading_0)) tens = "";
		return (tens+ " "+digit).trim();
	}

	public String get_reading_of_3_digit_number(String number) throws MissingResourceException
	{
		String reading_0 = number_readings.getString("0");
		int digit_count = number.length();
		switch(digit_count)
		{
			case 0:
				return "";
			case 1:
				String digit = convert_number_into_text(number);
				if (digit.equals(reading_0)) return "";
				return digit;
			case 2:
				return get_reading_of_2_digit_number(number);
			case 3:
				String hundreds = convert_number_into_text(number.substring(0, 1));
				StringBuilder result_sb = new StringBuilder("");
				//turkish case
				if(this.current_locale.equals(new Locale("tr","TR")) 
						&& hundreds.equals(number_readings.getString("1"))) result_sb.append(number_readings.getString("100"));
				//
				else if (!hundreds.equals(reading_0)) result_sb.append(hundreds+" " +number_readings.getString("100"));
				result_sb.append(" "+get_reading_of_2_digit_number(number.substring(1,3)));
				return (result_sb.toString()).trim();
			default:
				throw new MissingResourceException("", "", "");
		}
	}
}
