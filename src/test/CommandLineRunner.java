package test;

import com.aipsys.barcode.encoder.*;


public final class CommandLineRunner {

  private CommandLineRunner()
  {
  }

  public static void main(String[] args) throws Exception {

     DataMatrix dm = new DataMatrix();
     //dm.setTextData("datamatrix");
    // dm.setBinaryData();
     dm.Encode2JPEGFile("c:\\test.jpg");
  }

}