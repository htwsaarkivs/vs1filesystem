package htw.vs1.scrapyard;// Java-Beispiele
// Prof. Dr. H. G. Folz
// 1999
//
// zwei Threads, die abwechselnd auf die Standardausgabe schreiben

public class PingPong extends Thread {
   String word;	// auszugebendes Wort
   int delay;	// Wartezeit

   PingPong(String whatToSay, int delayTime) {
      word = whatToSay;
      delay = delayTime;
   }

   public void run() {
      try {
         for (int i = 0; i < 25; ++i) {
            System.out.println(word + " ");
            Thread.sleep(delay);	// warten
         }
      } catch (InterruptedException e) {
           return;
      }
   }
 
   public static void main(String[] args) {
      PingPong ping = new PingPong("ping", 250);
      PingPong pong = new PingPong("       PONG",750);
      //PingPong peng = new PingPong("               peng",250);
      ping.start();
      pong.start();
      //peng.start();
   }
}

// Ausgabe: 
// ping PONG ping ping PONG ping ping ping PONG ......
