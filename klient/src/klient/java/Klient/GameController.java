package Klient;

import Server.GalgeLogikInterface;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class GameController extends UnicastRemoteObject{

    GalgeLogikInterface spil;

    public GameController() throws RemoteException{
    }

    public void start(GalgeLogikInterface galgeLogikInterface) throws RemoteException, NotBoundException, MalformedURLException {
        spil = galgeLogikInterface;
        Scanner scanner = new Scanner(System.in);
        try {
            spil.hentOrdFraDr();
            System.out.println(spil.getOrdet());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kunne ikke hente ord fra dr da deres service er nede. \n Der hentes lokale ord istedet");
            spil.nulstil();
        }


        while (!spil.erSpilletSlut()) {
            System.out.println("Skriv et bogstav: ");
            String bogstav = scanner.next();
            while (bogstav.length() != 1 || spil.getSynligtOrd().contains(bogstav)) {
                System.out.println("Ugyldigt input.");
                bogstav = scanner.next();
            }

            spil.gætBogstav(bogstav);

            if (spil.erSidsteBogstavKorrekt()) {
                System.out.println("Korrekt");
                System.out.println(spil.getSynligtOrd());
                System.out.println("______________________________________ \n");
            } else {
                System.out.println("Forkert");
                System.out.println(spil.getSynligtOrd());
                System.out.println("Liv tilbage: " + (7 - spil.getAntalForkerteBogstaver()));
                System.out.println("______________________________________ \n");
            }
        }

        if (spil.erSpilletVundet()) {
            System.out.println("Du vandt spillet");
        } else if (spil.erSpilletTabt()) {
            System.out.println("Du tabte");
            System.out.println("Ordet var: " + spil.getOrdet());
        }
    }
}
