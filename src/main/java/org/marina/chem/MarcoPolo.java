package org.marina.chem;

public class MarcoPolo {


    public static class Monitor {

        private final Object word = new Object();
        private String w = "Polo";

    }


    static class Marco implements Runnable {
        private final Monitor td;
        private int count;


        public Marco(Monitor td, int count) {
            this.td = td;
            this.count = count;
        }

        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                synchronized (td.word) {
                    while (td.w == "Marco") {
                        try {
                            td.word.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        td.w = "Marco";
                        System.out.println("Marco");
                        td.word.notify();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }


    static class Polo implements Runnable {
        Monitor td;
        private int count;

        public Polo(Monitor td, int count) {
            this.td = td;
            this.count = count;
        }


        @Override
        public void run() {
            for (int i = 0; i < count; i++) {
                synchronized (td.word) {
                    while (td.w == "Polo") {
                        try {
                            td.word.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        td.w = "Polo";
                        System.out.println("Polo");
                        td.word.notify();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Monitor data = new Monitor();
        Thread t1 = new Thread(new Marco(data, 5));
        Thread t2 = new Thread(new Polo(data, 5));
        t2.start();
        t1.start();


    }
}

