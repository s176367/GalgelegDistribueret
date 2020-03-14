
package Klient;

import Server.GalgeLogikInterface;
import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import io.javalin.http.Context;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class Klient {
    GameController gameController;


    public Klient() throws RemoteException {
        gameController = new GameController();

    }
    public GalgeLogikInterface returnInterface() throws RemoteException, NotBoundException, MalformedURLException {
        return (GalgeLogikInterface) Naming.lookup("rmi://dist.saluton.dk:20077/logik");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {

        GalgeLogikInterface spil = (GalgeLogikInterface) Naming.lookup("rmi://dist.saluton.dk:20077/logik");
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
        gameController.start(spil);
    }

    }

