import java.io.*;
import java.net.*;
import java.util.*;

class server {
  public static void main(String[] args) {
    ServerSocket ss = null;
    try {

      ss = new ServerSocket(3000);
      ss.setReuseAddress(true);

      System.out.println();
      int i = 0;

      while (true) {
        System.out.println("Waiting for connection... ");

        Socket s = ss.accept();
        ++i;
        System.out.println("Connection established with client " + i + " : " + s.getInetAddress().getHostAddress());
        // create a new thread object
        ClientHandler clientSock = new ClientHandler(s);

        // This thread will handle the client
        // separately
        new Thread(clientSock).start();

      }

      /*  System.out.println("Updated file sent back to client.\n");

      // Deleting old file and renaming temp file

      File file = new File("temp.csv");
      File file2 = new File("data.csv");
      boolean delete = file2.delete();
      boolean success = file.renameTo(file2);
*/

      //s.close();

    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Lost Connection");

    } finally {
      if (ss != null) {
        try {
          ss.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // ClientHandler class
  private static class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket) {
      this.clientSocket = socket;
    }

    public void run() {
      DataInputStream din = null;
      DataOutputStream dout = null;

      try {
        din = new DataInputStream(clientSocket.getInputStream());
        dout = new DataOutputStream(clientSocket.getOutputStream());

        System.out.println("Waiting for client to send the file...");

        ///  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        byte b[] = new byte[20002];

        din.read(b, 0, b.length);
        System.out.println("File received from the client");

        FileOutputStream fr = new FileOutputStream("wines.csv");

        fr.write(b, 0, b.length);

        fr.close();
        System.out.println();

        DataInputStream input = new DataInputStream(System.in);

        // Creating the Updated Data file

        String quality_input = "";
        int counter = -1;
        float total = 0, rt, lowest_ph = 1000, highest_alcohol = -1, temp_ph, temp_alcohol;
        System.out.println("Enter the quality  : ");
        quality_input = input.readLine();
        String FILE_NAME = "Q" + quality_input + ".csv";

        File newFile = new File("wines.csv");
        String quality = "", ph = "", acid = "", alcohol = "";

        String textToAppend = "quality,pH,volatile acidity,alcohol\n";
        BufferedWriter wrt = new BufferedWriter(
          new FileWriter(FILE_NAME, true));

        wrt.write(textToAppend);
        wrt.close();

        try {
          Scanner x = new Scanner(new File("wines.csv"));
          x.useDelimiter("[,\n]");

          while (x.hasNext()) {

            counter = counter + 1;
            quality = x.next();
            ph = x.next();
            acid = x.next();
            alcohol = x.next();

            try {
              if (quality.equals(quality_input)) {
                temp_ph = Float.parseFloat(ph);
                temp_alcohol = Float.parseFloat(alcohol);
                if (temp_ph < lowest_ph) {
                  lowest_ph = temp_ph;
                }
                if (temp_alcohol > highest_alcohol) {
                  highest_alcohol = temp_alcohol;
                }
                BufferedWriter writer = new BufferedWriter(
                  new FileWriter(FILE_NAME, true));
                String text = quality + "," + ph + "," + acid + "," + alcohol + "\n";

                writer.write(text);
                writer.close();
              }
            } catch (Exception e) {
              System.out.println(e);
            }

          }

          x.close();

        } catch (Exception e) {

        }
        System.out.println();
        System.out.println("Lowest pH for quality " + quality_input + " = " + lowest_ph);
        System.out.println("Highest alcohol content for quality " + quality_input + " = " + highest_alcohol);

        //Sending updated file back to client
        FileInputStream fstr = new FileInputStream(FILE_NAME);
        byte sendToClient[] = new byte[20002];
        fstr.read(sendToClient, 0, sendToClient.length);
        dout.write(sendToClient, 0, sendToClient.length);
        dout.flush();
        fstr.close();

        System.out.println();

        System.out.println("Created and updated file " + FILE_NAME + " and sent it back to client");

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (dout != null) {
            dout.close();
          }
          if (din != null) {
            din.close();
            clientSocket.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}