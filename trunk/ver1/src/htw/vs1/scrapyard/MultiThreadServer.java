package htw.vs1.scrapyard;// Datei: MultiThreadServer.java

import java.net.*;
import java.io.*;

public class MultiThreadServer
{
   // Port der Serveranwendung
   public static final int SERVER_PORT = 10001;
   // Name dieses Threads. Es wird dadurch markiert, welche 
   // Ausgaben auf der Konsole von diesem Thread stammen.
   private static final String klassenname = "MainThread";

   public static void main (String[] args)
   {
      try
      {
         // Erzeugen der Socket/binden an Port/Wartestellung
         ServerSocket socket = new ServerSocket (SERVER_PORT);

         while (true)
         {
            // Ab hier ist der Server "scharf" geschaltet
            // und wartet auf Verbindungen von Clients
            print (klassenname + ":\tWarten auf Verbindungen ...");

            // im Aufruf der Methode accept() verharrt die
            // Server-Anwendung solange, bis eine Verbindungs-
            // anforderung eines Client eingegangen ist.
            // Ist dies der Fall, so wird die Anforderung akzeptiert
            Socket client = socket.accept();

            print (klassenname + ":\tVerbunden mit: " +
               client.getInetAddress().getHostName() +
               " Port: " + client.getPort());

            // Thread erzeugen, der Kommunikation
            // mit Client übernimmt
            new WorkerThread (client).start();
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
   
   // Diese Methode print() dient dazu, dass die beiden Threads
   // MainThread und WorkerThread beim konkurrierenden Zugriff auf 
   // die Konsole mit System.out.println() synchronisiert werden.
   public static synchronized void print (String nachricht)
   {
      System.out.println (nachricht);
   }
}

class WorkerThread extends Thread
{
   private Socket client;
   // Name dieses Threads
   private final String klassenname = "WorkerThread";

   public WorkerThread (Socket client)
   {
      this.client = client;
   }

   public void run()
   {
      try
      {
         // Erzeugen eines Puffers und einlesen des Namens
         byte[] b = new byte[128];
         InputStream input = client.getInputStream();

         // Warten auf Daten
         while (input.available() == 0);

         // Nachricht auslesen
         input.read (b);
         String clientName = new String (b);
         MultiThreadServer.print (
            klassenname + ":\tName empfangen: " + clientName);

         // Zufällige Zeit warten (0-5 sec.)
         sleep ((long) (Math.random() * 5000));

         // Begrüßung senden
         OutputStream output = client.getOutputStream();

         MultiThreadServer.print (
            klassenname + ":\tSende Antwort an Client " +
            clientName);

         byte[] antwort = ("Hallo " + clientName).getBytes();
         output.write (antwort);

         // Verbindung beenden
         client.close();
         MultiThreadServer.print (
            klassenname + ":\tClient erfolgreich bedient ...");
      }
      catch (Exception e)
      {
         // Wenn ein Fehler auftritt ...
         e.printStackTrace();
      }
   }
}
