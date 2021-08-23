import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

class client {

  public static void main(String[] args) {
    try {
      Socket s = new Socket("localhost", 3000); // server IP , port
      System.out.println();
      System.out.println("Connection established with server");

      DataInputStream din = new DataInputStream(s.getInputStream());
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());

      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      File file = new File("wines.csv");
      FileWriter fileWritter;

      String line = "";

      // keep reading until "Over" is input
      //boolean headerPresent = false;
      DataInputStream input = new DataInputStream(System.in);

      // Creating and inputting data into csv
      System.out.println();
      System.out.println("Update the CSV file : (Type 'Over' when done updating)");
      int i=0;

      while (!line.equals("Over")) {
        try {
          fileWritter = new FileWriter(file.getName(), true);

          BufferedWriter bw = new BufferedWriter(fileWritter);
        //  if(i==0) {bw.write("\n");++i;}

         System.out.print("Enter the next row of data : ");
          line = input.readLine();

          if (!line.equals("Over")) bw.write("\n"+line);
          bw.close();

        } catch (IOException e) {
          System.out.println(e);
        }
      }

      

      System.out.println();

      System.out.println("File has been updated");


      // Reading data from the file created above

    String quality_input =""; int counter=-1; float total_ph=0,total_acidity=0,total_alcohol=0,temp_ph,temp_acidity,temp_alcohol;
    List<Float> phList = new ArrayList();
    List<Float> acidityList = new ArrayList();
    List<Float> alcoholList = new ArrayList();

   
    System.out.println("Enter the quality  : ");
    quality_input = input.readLine();

      File newFile = new File("wines.csv");
      String quality = "", ph = "", acid = "",alcohol="";
      try {
        Scanner x = new Scanner(new File("wines.csv"));
        x.useDelimiter("[,\n]");

        while (x.hasNext()) {
          counter=counter+1;
          quality = x.next();
          ph = x.next();
          acid = x.next();
          alcohol = x.next();


          try {
            if(quality.equals(quality_input))
            { temp_ph = Float.parseFloat(ph);
              temp_acidity = Float.parseFloat(acid);
              temp_alcohol = Float.parseFloat(alcohol);

              phList.add(temp_ph);
              acidityList.add(temp_acidity);
              alcoholList.add(temp_alcohol);

              total_ph = (float)(total_ph + (float)(temp_ph));
              total_acidity=(float)(total_acidity + (float)(temp_acidity));
              total_alcohol=(float)(total_alcohol + (float)(temp_alcohol));

          }
            
         
          } catch (Exception e) {
           // String textToAppend = name + "," + department + "," + salary + "\n";
     
          }

        }

        x.close();

      } catch (Exception e) {


      }
      Collections.sort(phList);
      Collections.sort(acidityList);
      Collections.sort(alcoholList);

      System.out.println();
      System.out.println("Mean pH = "+((float)(float)(total_ph)/(float)(counter)));
      System.out.println("Mean volatile acidity = "+((float)(float)(total_acidity)/(float)(counter)));
      System.out.println("Mean alcohol = "+((float)(float)(total_alcohol)/(float)(counter)));


      System.out.println();
      System.out.println("Median pH = "+(float)((phList.get(phList.size()/2) + phList.get(phList.size()/2 - 1))/2));
      System.out.println("Median volatile acidity = "+(float)((acidityList.get(acidityList.size()/2) + acidityList.get(acidityList.size()/2 - 1))/2));
      System.out.println("Median alcohol = "+(float)((alcoholList.get(alcoholList.size()/2) + alcoholList.get(alcoholList.size()/2 - 1))/2));

          // storing it into byte array and sending the byte array to server
          System.out.println();
          System.out.println("Sending file to server using sockets...");


      FileInputStream fr = new FileInputStream("wines.csv");
      byte b[] = new byte[20002];
      fr.read(b, 0, b.length);
      dout.write(b, 0, b.length); // writing to server
      dout.flush();
      fr.close();




      // Receiving the updated file from server and storing it into byte array
      // storing the data from the byte array into a new csv
      byte fromServer[] = new byte[20002];
      din.read(fromServer, 0, fromServer.length); // Reading from server

      System.out.println("Updated file received from the server");

      FileOutputStream fstr = new FileOutputStream("receivedFromServer.csv");
      fstr.write(fromServer, 0, fromServer.length);
      fstr.close();

      
      s.close();

    } catch (Exception e) {
      System.out.println("Lost Connection");
    }
  }
}