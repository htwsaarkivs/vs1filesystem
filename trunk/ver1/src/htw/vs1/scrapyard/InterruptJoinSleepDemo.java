package htw.vs1.scrapyard;//Java-Beispiele
//Dipl.-Ing.(FH) Michael Sauer DEA (UVigo)
//06.2014
//Demonstration von interrupt, join und sleep

import java.io.*;

public class InterruptJoinSleepDemo extends Thread {
    public static void main(String[] args) {
        Thread tInit = new InitThread();
        Thread tProg = new WorkThread(tInit);
		Thread tWecker = new InterruptThread(tProg);
		tWecker.start();
        tProg.start();
    	}
	}

class InitThread extends Thread{
	public void run(){
		for(int i=1; i<5; i++){
			System.out.println(i+".Sekunde");
			try{sleep(1000);}
			catch (InterruptedException e){System.out.println(e);}
			}
		System.out.println("Fertig");
		}

	}

class InterruptThread extends Thread{
	private Thread unterbrich; 
	public InterruptThread(Thread unterbrich){
		this.unterbrich=unterbrich;
		}
	public void run(){
		for(int i=12; i>0; i--){
			System.out.println("\t\t"+i+".Sekunde");
			try{sleep(1000);}
        	catch (InterruptedException e){System.out.println(e);}
			}
		unterbrich.interrupt();
		}
	}

class WorkThread extends Thread{
	private Thread warteAuf; 
	public WorkThread(Thread warteAuf){
		this.warteAuf=warteAuf;
		}
	public void run(){
		System.out.println("Start und Initialisierung");
		warteAuf.start();
		try{warteAuf.join();}
		catch (InterruptedException e){}
		System.out.println("Initialisierung abgeschlossen");
		System.out.println("Gehe schlafen und warte auf Wecken");
		try{sleep(50000);}
		catch (InterruptedException e){System.out.println(e);}
		System.out.println("Start warteAuf-Thread nochmal");
		warteAuf.start();
		System.out.println("Beende mich nun");
		}
	}