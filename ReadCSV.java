package test;

import java.io.*;
import java.util.*;

public class ReadCSV {

	public static void main(String[] args) throws FileNotFoundException {
		List<String> arr = new ArrayList<String>();
		//HDFC-Input-Case1.csv
		//ICICI-Input-Case2.csv
		//Axis-Input-Case3.csv
		//IDFC-Input-Case4.csv
		Scanner sc = new Scanner(new File("C:\\Users\\Anshul\\Documents\\manuscripts\\Assignment\\Dot Net Developer\\HDFC-Input-Case1.csv"));  
		sc.useDelimiter(",");   //sets the delimiter pattern  
		while (sc.hasNext())  //returns a boolean value  
		{
			String temp = sc.next();
			arr.add(temp);  //find and returns the next complete token from this scanner  			
		}
		sc.close();  //closes the scanner  
		arr.remove(0);

		int flag = 0;
		if(arr.contains("Debit"))
			flag = 1;


		if(flag == 0)
		{

			for(int i = 1; i< arr.size();)
			{
				List<String> out = new ArrayList<String>();
				//Date TrancDicr Amt
				String tranc = "";
				String cardName = arr.get(4);

				if(arr.get(i).contains("Domestic Transactions"))
				{
					tranc = "Domestic Transactions";
				}
				if(arr.get(i).contains("International Transactions"))
				{
					tranc = "International Transactions";
				}
				if(arr.get(i).contains("-"))
				{
					out.add(arr.get(i));
					out.add(arr.get(i+1));
					if(arr.get(i+2).contains("cr"))
					{
						out.add("0");
						out.add((arr.get(i+2).split(" "))[0]);
					}
					else
					{
						out.add(arr.get(i+2));
						out.add("0");
					}
					if(tranc.equals("Domestic Transactions"))
					{
						out.add("INR");
					}
					else if(tranc.equals("International Transactions"))
					{
						out.add(arr.get(i+1).substring(arr.get(i+1).length()-3, arr.get(i+1).length()));
					}
					out.add(cardName);
					out.add(tranc);
					if(tranc.equals("Domestic Transactions"))
					{
						String[] t = arr.get(i+1).split(" ");
						out.add(t[t.length-2]);
					}
					else if(tranc.equals("International Transactions"))
					{
						out.add(arr.get(i+1).substring(arr.get(i+1).length()-3, arr.get(i+1).length()));
					}
					System.out.println(out);
				}
				i += 3;
			}
		}
	}

}
