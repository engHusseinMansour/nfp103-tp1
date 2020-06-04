/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfp103.tp1.ex1;

/**
 *
 * @author hm
 */
public class Compteur extends Thread {

   private int max = 10;   
   private String name = "Cpt";
   private static int position = 1;


   public Compteur(){
   }

   public Compteur(String name){
       this.name = name;
   }

   public Compteur(String name,int max){
       this.name = name;
       this.max = max;
   }
   
   public void run(){
        for (int i = 0; i <= max; i++) {
            try {
                sleep((int)(Math.random() * 5000)); // Sleep for random number between 0 and 5000
            }
            catch(InterruptedException e) {
                System.err.println(name + " error:" + e.getMessage());
            }
            System.out.println(name + " : " + i);
        }
        System.out.println("*** " + name + " a fini de compter jusqu'à " + max + " en position " + position++);
   }
   
    public static void main(String[] args) {
        
        Compteur[] compteurs = {
                new Compteur("first"),
                new Compteur("second"),
                new Compteur("third"),
                new Compteur("fourth")
        };
        for (int i = 0; i < compteurs.length; i++) {
            compteurs[i].start();
        }
    }
}
