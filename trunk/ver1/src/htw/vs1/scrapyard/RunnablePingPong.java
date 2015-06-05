package htw.vs1.scrapyard;// Java-Beispiele
// Prof. Dr. H. G. Folz
// 1999
//
// Das PingPong-Beispiel mit Hilfe des Interface Runnable
// implementiert

public class RunnablePingPong implements Runnable {
   String word;	// auszugebendes Wort
   int delay;	// Wartezeit

   RunnablePingPong(String whatToSay, int delayTime) {
      word = whatToSay;
      delay = delayTime;
   }

   public void run() {
      try {
         for (int i = 0; i < 20; ++i) {
            System.out.println(word + " ");
            Thread.sleep(delay);	// warten
         }
      } catch (InterruptedException e) {
            return;
      }
   }

   public static void main(String[] args) {
      Runnable r1 = new RunnablePingPong("      ping", 33);
      Runnable r2 = new RunnablePingPong("PONG",100);
      Thread t1 = new Thread(r1);
      Thread t2 = new Thread(r2);
      t1.start();
      t2.start();
   }
}

// Ausgabe:
// ping PONG ping ping PONG ping ping ping PONG ......
