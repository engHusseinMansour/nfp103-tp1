/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfp103.tp1.ex5;

/**
 *
 * @author hm
 */
public class Trieur extends Thread {

    private int[] t;
    private int debut, fin;
    private Trieur parent;
    private int nbNotify = 0;

    public Trieur(int[] t) {
        this(null, t, 0, t.length - 1);
    }

    public Trieur(Trieur parent, int[] t, int debut, int fin) {
        this.parent = parent;
        this.t = t;
        this.debut = debut;
        this.fin = fin;
        start();
    }

    public synchronized void notifier() {
        this.nbNotify++;
        this.notifyAll();
    }

    public void run() {
        if (fin - debut < 2) {
            if (t[debut] > t[fin]) {
                echanger(debut, fin);
            }
        } else {
            int milieu = debut + (fin - debut) / 2;
            synchronized (this) {
                try {
                    while (nbNotify < 2) {
                        wait();
                    }
                } catch (InterruptedException e) {
                }
            }
            triFusion(debut, fin);
        }
        if (parent != null) {
            parent.notifier();
        }
    }

    private void echanger(int i, int j) {
        int valeur = t[i];
        t[i] = t[j];
        t[j] = valeur;
    }

    private void triFusion(int debut, int fin) {
        int[] tFusion = new int[fin - debut + 1];
        int milieu = (debut + fin) / 2;
        int i1 = debut,
                i2 = milieu + 1;
        int iFusion = 0;
        while (i1 <= milieu && i2 <= fin) {
            if (t[i1] < t[i2]) {
                tFusion[iFusion++] = t[i1++];
            } else {
                tFusion[iFusion++] = t[i2++];
            }
        }
        if (i1 > milieu) {
            for (int i = i2; i <= fin;) {
                tFusion[iFusion++] = t[i++];
            }
        } else {
            for (int i = i1; i <= milieu;) {
                tFusion[iFusion++] = t[i++];
            }
        }
        for (int i = 0, j = debut; i <= fin - debut;) {
            t[j++] = tFusion[i++];
        }
    }

    public static void main(String[] args) {
        int[] t = {5, 8, 3, 2, 7, 10, 1};
        Trieur trieur = new Trieur(t);
        try {
            trieur.join();
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < t.length; i++) {
            System.out.print(t[i] + " ; ");
        }
        System.out.println();
    }
}
