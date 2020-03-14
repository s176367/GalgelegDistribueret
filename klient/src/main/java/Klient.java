


import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import Server.GalgeLogikInterface;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Klient {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        GalgeLogikInterface spil = (GalgeLogikInterface) Naming.lookup("rmi://dist.saluton.dk:20077/logik");
        GameController gameController = new GameController();
        Scanner scanner = new Scanner(System.in);
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");


        System.out.println("Velkommen til galgespillet.");
        Boolean logIn = false;
        System.out.println("Log ind for at spille");
        while (!logIn) {
            Bruger bruger = null;
            System.out.println("Brugernavn: ");
            String brugernavn = scanner.next();
            System.out.println("Kode: ");
            String kode = scanner.next();
            try {
                bruger = ba.hentBruger(brugernavn, kode);
            } catch (IllegalArgumentException a) {
                System.out.println(a);
            }

            if (bruger != null) {
                logIn = true;
                System.out.println("Velkommen " + bruger.fornavn);
                System.out.println("Du er nu logget ind og spillet begynder!");
                gameController.start(spil);
            }
        }



        //GameControllerInterface controller = (GameControllerInterface) Naming.lookup("rmi://localhost:1099/controller");



    }
}
