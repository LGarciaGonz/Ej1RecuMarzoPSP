package SEMAFOROS.EjemploTurnos;

import java.util.concurrent.Semaphore;

public class EjemploTurnosV2 extends Thread{

    public static void main(String[] args) {

        BuzonV2 l_Buzon = new BuzonV2();

        CarasV2 l_ObjRunnable1 = new CarasV2(l_Buzon);
        CarasV2 l_ObjRUnnable2 = new CarasV2(l_Buzon);
        CarasV2 l_ObjRunnable3 = new CarasV2(l_Buzon);
        CarasV2 l_ObjRUnnable4 = new CarasV2(l_Buzon);
        CarasV2 l_ObjRunnable5 = new CarasV2(l_Buzon);
        CarasV2 l_ObjRUnnable6 = new CarasV2(l_Buzon);

        ThreadGroup l_GrupoCarasV2 = new ThreadGroup("Grupocaras");
        ThreadGroup l_GrupoBancaV2 = new ThreadGroup("Grupobanca");


        Thread l_HiloCara1 = new Thread(l_GrupoCarasV2, l_ObjRunnable1);
        Thread l_HiloCara2 = new Thread(l_ObjRUnnable2);
        Thread l_HiloCara3 = new Thread(l_ObjRunnable3);
        Thread l_HiloCara4 = new Thread(l_ObjRUnnable4);
        Thread l_HiloCara5 = new Thread(l_ObjRunnable5);
        Thread l_HiloCara6 = new Thread(l_ObjRUnnable6);

        // Asignar prioridad.
        l_HiloCara1.setPriority(6);
        l_HiloCara2.setPriority(5);
        l_HiloCara1.setPriority(4);
        l_HiloCara2.setPriority(3);
        l_HiloCara1.setPriority(2);
        l_HiloCara2.setPriority(1);

        // Asignar nombre.
        l_HiloCara1.setName("1");
        l_HiloCara2.setName("2");
        l_HiloCara1.setName("3");
        l_HiloCara2.setName("4");
        l_HiloCara1.setName("5");
        l_HiloCara2.setName("6");

        l_HiloCara1.start();
        l_HiloCara2.start();
        l_HiloCara3.start();
        l_HiloCara4.start();
        l_HiloCara5.start();
        l_HiloCara6.start();

    }
}


class BancaV2 implements Runnable {

    private BuzonV2 a_Buzon;

    public BancaV2 (BuzonV2 p_Buzon) {
        this.a_Buzon = p_Buzon;
    }

    @Override
    public void run() {

        System.out.println("\n----[ Banca ]----\n");

    }
}

class CarasV2 extends Thread {

    private BuzonV2 a_Buzon;

    public CarasV2(BuzonV2 p_Buzon) {
        this.a_Buzon = p_Buzon;
    }

    @Override
    public void run() {

        System.out.println();

    }
}

class BuzonV2 {

    public Semaphore a_SemBanca = new Semaphore(1, true);
    public Semaphore a_SemCaras = new Semaphore(1, true);

}