package conversiontest;

import java.math.BigInteger;
import java.util.Locale;
import java.util.MissingResourceException;

import conversionservice.ConversionService;
import conversionservice.impl.ConversionServiceImpl;

public class ConversionTest {
	public static void main(String[] args) throws MissingResourceException, Exception
	{
		ConversionService conversionService = new ConversionServiceImpl(new Locale("tr","TR"));
		BigInteger x = new BigInteger("3000000000");
		BigInteger y = new BigInteger("3050000000");
		BigInteger one = new BigInteger("1");
		int counter = 0;
		while(y.compareTo(x) == 1)
		{
			String text = conversionService.convert_number_to_text(x);
			BigInteger number = conversionService.convert_text_to_number(text);
			if (!number.equals(new BigInteger(""+x)))
			{
				System.out.println("x:"+x);
				System.out.println("text:"+text);
				System.out.println("number:"+number);
			}
			
			x = x.add(one);
			counter+=1;
		}
		/*for(;x<new BigInteger("3000000000");x+=1)
		{
			String text = conversionService.convert_number_to_text(new BigInteger(""+x));
			BigInteger number = conversionService.convert_text_to_number(text);
			if (!number.equals(new BigInteger(""+x)))
			{
				System.out.println("x:"+x);
				System.out.println("text:"+text);
				System.out.println("number:"+number);
			}
			if(x%100000 == 0)
			{
				System.out.println("----hhphp");
				System.out.println("x:"+x);
				System.out.println("text:"+text);
				System.out.println("number:"+number);
			}
		}*/
	}

}
