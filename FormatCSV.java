package com.discover.xchange;

import com.discover.xchange.exception.ReportableException;
import com.mchange.v2.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormatCSV {

  public void standardizeStatement(String inputFile, String outputFile){

    String[] axisHeaders = {"Date","Debit","Credit","Transaction Details"};
    String[] iciciHeaders = {"Date","Transaction Description","Debit","Credit"};
    String[] hdfcHeaders = {"Date","Transaction Description","Amount"};
    String[] idfcHeaders = {"Transaction Details","Date","Amount"};
    try{
      BufferedReader reader = new BufferedReader(new FileReader(inputFile));

      String line;
      int counter = 0;
      String headerType = null;
      boolean domesticTransaction = true;
      boolean internationalTransaction = false;
      String cardHolderName = null;
      String[] header = {"Date","Transaction Description","Debit","Credit","Currency","CardName","Transaction","Location"};
      List<String> output = new ArrayList<>(100);
      output.add(Arrays.asList(header).stream().collect(Collectors.joining(",")));
      while ((line = reader.readLine()) != null) {
        if(counter ==0){
          counter ++;
          continue;
        }else if(counter ==1){
          counter ++;
          String[] vals = line.split(",");
          String[] values = new String[vals.length];
          for (int  i=0; i<vals.length;i++){
            values[i] = vals[i].trim();
          }
          if(Arrays.equals(values, axisHeaders)){
            headerType = "Axis";
          }else if(Arrays.equals(values, iciciHeaders)){
            headerType = "ICICI";
          }else if(Arrays.equals(values, hdfcHeaders)){
            headerType = "HDFC";
          }else if(Arrays.equals(values, idfcHeaders)){
            headerType = "IDFC";
          }
          continue;
        }else{
          if(line.contains("Date")){
            continue;
          }
          String[] vals = line.split(",");
          Object[] values =  Arrays.asList(vals).stream().filter(val -> StringUtils.nonEmptyString(val)).toArray();
          if(values.length == 1 && values[0].toString().equalsIgnoreCase("International Transactions")){
            domesticTransaction = false;
            internationalTransaction = true;
            continue;

          }else if(values.length == 1 && !values[0].toString().equalsIgnoreCase("International Transactions")){
            cardHolderName = values[0].toString();
            continue;
          }else if(values.length == 0){
            continue;
          }else{
            switch (headerType){
              case "Axis":
                String[] axisArray = new String[8];
                axisArray[0] = vals[0];
                axisArray[1] = vals[3];
                axisArray[2] = vals[1].equalsIgnoreCase("") ? Double.valueOf("0").toString() : Double.valueOf(vals[1]).toString();
                axisArray[3] = vals[2].equalsIgnoreCase("") ? Double.valueOf("0").toString() : Double.valueOf(vals[2]).toString();
                populateFields(domesticTransaction, cardHolderName, vals[3], axisArray);
                output.add(Arrays.asList(axisArray).stream().collect(Collectors.joining(",")));
                break;
              case "HDFC":
                String[] hdfcArray = new String[8];
                hdfcArray[0] = vals[0];
                hdfcArray[1] = vals[1];
                parseAmount(vals[2], hdfcArray);
                populateFields(domesticTransaction, cardHolderName, vals[1], hdfcArray);
                output.add(Arrays.asList(hdfcArray).stream().collect(Collectors.joining(",")));
                break;
              case "ICICI":
                String[] iciciArray = new String[8];
                iciciArray[0] = vals[0];
                iciciArray[1] = vals[1];
                iciciArray[2] = vals[2].equalsIgnoreCase("") ? Double.valueOf("0").toString() : Double.valueOf(vals[2]).toString();
                if(vals.length == 3){
                  iciciArray[3] = Double.valueOf("0").toString();
                }else{
                  iciciArray[3] = vals[3].equalsIgnoreCase("") ? Double.valueOf("0").toString() : Double.valueOf(vals[3]).toString();
                }
                populateFields(domesticTransaction, cardHolderName, vals[1], iciciArray);
                output.add(Arrays.asList(iciciArray).stream().collect(Collectors.joining(",")));
                break;
              case "IDFC" :
                String[] idfcArray = new String[8];
                idfcArray[0] = vals[1];
                idfcArray[1] = vals[0];
                parseAmount(vals[2], idfcArray);
                populateFields(domesticTransaction, cardHolderName, vals[0], idfcArray);
                output.add(Arrays.asList(idfcArray).stream().collect(Collectors.joining(",")));
                break;
            }
          }
        }

      }
      BufferedWriter writer  = new BufferedWriter(new FileWriter(outputFile));
      for (String content:output) {
        writer.write(content);
      }
      writer.close();
      reader.close();
    }catch(Exception ex){
      System.out.println(ex);
    }
  }

  private void parseAmount(String amount, String[] array) {
    if (amount.contains("cr") || amount.contains("Cr")) {
      array[2] = Double.valueOf("0").toString();
      array[3] = amount.equalsIgnoreCase("") ? Double.valueOf("0").toString() : Double.valueOf(amount.split("\\s")[0]).toString();
    } else {
      array[3] = Double.valueOf("0").toString();
      array[2] = amount.equalsIgnoreCase("") ? Double.valueOf("0").toString() : Double.valueOf(amount.split("\\s")[0]).toString();
    }
  }

  private void populateFields(boolean domesticTransaction, String cardHolderName, String tranDetail, String[] axisArray) {
    if(domesticTransaction){
      String[] tranDetails = tranDetail.split("\\s");
      Object[] objs =  Arrays.asList(tranDetails).stream().filter(val -> StringUtils.nonEmptyString(val)).toArray();
      axisArray[4] = "INR";
      axisArray[6] = "Domestic";
      axisArray[7] =  objs[objs.length-1].toString();
    }else{
      String[] tranDetails =tranDetail.split("\\s");
      Object[] objs =  Arrays.asList(tranDetails).stream().filter(val -> StringUtils.nonEmptyString(val)).toArray();
      axisArray[4] = objs[objs.length-1].toString();
      axisArray[6] = "International";
      axisArray[7] =  objs[objs.length-2].toString();
    }
    axisArray[5] = cardHolderName;
  }
}

