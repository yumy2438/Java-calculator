package conversionservice.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import conversionservice.ConversionService;
import conversionutils.ConversionUtils;
/*
 * Implementation of ConversionService interface.
 */
public class ConversionServiceImpl implements ConversionService{
	private ArrayList<String> big_numbers_reading;
	private ConversionUtils conversionUtilsObject = null;
	private Locale current_locale;
	
	public ConversionServiceImpl(Locale current_locale)
	{
		this.current_locale = current_locale;
		ResourceBundle current_bundle = ResourceBundle.getBundle("NumberReadingsBundle", current_locale);
		this.conversionUtilsObject = new ConversionUtils(current_locale);
		String[] big_numbers = ConversionUtils.BIG_NUMBERS;
		this.big_numbers_reading = new ArrayList<String>();
		for(String big_number: big_numbers)
		{
			big_numbers_reading.add(current_bundle.getString(big_number));
		}
	}

	@Override
	public BigInteger convert_text_to_number(String number_reading) throws java.util.MissingResourceException, Exception
	{
		/* 	This method converts the number given in text form into number form.
		 * 	It is valid for languages that reads the number from the biggest digit to smallest.
		 */
		BigInteger result = new BigInteger("0");
		int reading_minus_length = conversionUtilsObject.convert_number_into_text("-").length();
		boolean is_minus = is_minus_text(number_reading);
		if (is_minus) number_reading = number_reading.substring(reading_minus_length);
		for(String big_number: this.big_numbers_reading)
		{
			if(number_reading.contains(big_number))
			{
				int index = number_reading.indexOf(big_number);
				if (number_reading.substring(index+big_number.length()).contains(big_number)) throw new java.util.MissingResourceException("", "", "");
				String[] split_by_big_number_text = number_reading.split(big_number);
				if(split_by_big_number_text.length > 1) number_reading = split_by_big_number_text[1];
				else number_reading = "";
				String before_big_number_text = "";
				if(! (split_by_big_number_text.length == 0))  before_big_number_text = split_by_big_number_text[0].trim();
				int number;
				if(before_big_number_text.equals("")) number = 1; //Special case: 1000 tr
				else number = this.conversionUtilsObject.get_number(before_big_number_text);
				BigInteger intermediate_result = new BigInteger(number+(""+conversionUtilsObject.convert_bignumtexts_into_numbers(big_number)).substring(1));
				result = result.add(intermediate_result);
			}
		}
		number_reading = number_reading.trim();
		if(!number_reading.equals(""))
		{
			String rest_number = "" + conversionUtilsObject.get_number(number_reading);
			result = result.add(new BigInteger(rest_number));
		}
		if(is_minus) return result.multiply(new BigInteger("-1"));
		return result;
	}

	@Override
	public String convert_number_to_text(BigInteger number) {
		/* 	This method converts the number into text form by looking the digits from biggest to smallest.
		 * 	It is valid for languages that read the numbers in that way.
		 * 	There is a special case for TR. 1000 and 100 are not read as one thousand/one hundred in TR. It
		 * is only thousand/hundred.
		 */
		if(number.equals(new BigInteger("0"))) return conversionUtilsObject.convert_number_into_text("0");
		String number_st = "" + number;
		boolean is_minus = is_minus_number(number);
		if(is_minus) number_st = number_st.substring(1);
		int begin_index = 0;
		int end_index = get_end_index(number_st);
		int big_number_index = get_big_number_index(number_st);
		StringBuilder result_sb = new StringBuilder("");
		while(end_index <= number_st.length() && big_number_index<big_numbers_reading.size())
		{
			String st = (conversionUtilsObject.get_reading_of_3_digit_number(number_st.substring(begin_index, end_index))).trim();
			String big_number = big_numbers_reading.get(big_number_index);
			if(current_locale.getLanguage().equals("tr") && big_number.equals("bin") && st.equals("bir")) st = " ";
			if (!st.equals("")) result_sb.append(" " + (st + " " + big_number).trim());
			big_number_index += 1;
			begin_index = end_index;
			end_index += 3;
		}
		result_sb.append(" " + conversionUtilsObject.get_reading_of_3_digit_number(number_st.substring(begin_index,end_index)));
		if(is_minus) return conversionUtilsObject.convert_number_into_text("-") + " " + result_sb.toString().trim();
		return result_sb.toString().trim();
	}
	
	private int get_end_index(String number_st)
	{
		/* calculates the end index of the biggest digit by splitting number as three-digits.
		 * For example: 11,130,404 -> The number in the biggest digit is 11. End index is 2.*/
		int length_number_st = number_st.length();
		int end_index = length_number_st%3;
		if(end_index == 0) end_index=3;
		return end_index;
	}
	
	private int get_big_number_index(String number_st)
	{
		/* calculates the index of the biggest digit in given number.
		 * For example: 1,000,000,000 the biggest digit is billions digit.*/ 
		int length_number_st = number_st.length();
		int big_number_index = ((int) (length_number_st/3))-1;
		if (length_number_st%3 != 0) big_number_index +=1;
		big_number_index = big_numbers_reading.size() - big_number_index;
		return big_number_index;
	}

	private boolean is_minus_text(String number_reading)
	{
		String reading_minus = conversionUtilsObject.convert_number_into_text("-");
		return (number_reading.split(" ")[0]).equals(reading_minus);
	}
	
	private boolean is_minus_number(BigInteger number)
	{
		String number_st = "" + number;
		return number_st.subSequence(0, 1).equals("-");	
	}
}
