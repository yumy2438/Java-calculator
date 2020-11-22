package conversionservice;

import java.math.BigInteger;
import java.util.MissingResourceException;
/*
 * 	The interface that provides the necessary methods for both getting number from text and reading number
 * from it's number format. 
 */
public interface ConversionService {
	public BigInteger convert_text_to_number(String text_number) throws MissingResourceException, Exception;
	public String convert_number_to_text(BigInteger number);
}
