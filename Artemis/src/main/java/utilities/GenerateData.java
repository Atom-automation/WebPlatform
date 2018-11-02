package utilities;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

public class GenerateData {
	
	Faker Generate = new Faker(new Locale("en-IND"));
	private Random random = new Random(System.currentTimeMillis());
	
	public String getData(String TestData) {
		String iData;
		if (TestData.trim().contains("mock:")) {
			String[] iTestData = TestData.trim().split(":");
			switch(iTestData[1].trim().toLowerCase()){
				case "email":
					iData = Generate.internet().emailAddress();
					break;
				case "emailubp":
					 String emailAddress="(4"+Generate.number().randomNumber(3,true)+")gopinath.rajaram@unionbankph.com";
					iData = emailAddress;
					break;
				case "nameubp":
					iData = "AutoWeb"+Generate.number().randomNumber(3,true);
					break;
				case "nameupdateubp":
					iData = "AutoWebupdate"+Generate.number().randomNumber(3,true);
					break;

				case "name":
					iData = Generate.name().fullName();
					break;
				case "username":
					iData = Generate.name().username();
					break;
				case "mobile":
					iData = Generate.phoneNumber().cellPhone();
					break;
				case "landline":
					iData = Generate.phoneNumber().phoneNumber();
					break;
				case "url":
					iData = Generate.internet().url();
					break;
				case "card":
					iData = cardgenerator("441125",16);
					break;
				default:
					System.out.println("Artemis WARNING: User hasn't specified the mock data catergory which is avaialble, please fix the test data or add the data catergory in the code.");
					iData = TestData;
			}
		}else {
			iData = TestData;
		}
		return iData;	
	}
	
	public String cardgenerator(String bin, int length) {

        int randomNumberLength = length - (bin.length() + 1);

        StringBuilder builder = new StringBuilder(bin);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        // Do the Luhn algorithm to generate the check digit.
        int checkDigit = this.getCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
    }


    private int getCheckDigit(String number) {


        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(number.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        // The check digit is the number required to make the sum a multiple of
        // 10.
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }
	
}
