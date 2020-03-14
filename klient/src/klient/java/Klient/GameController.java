package Klient;

import Server.GalgeLogikInterface;
import io.javalin.Javalin;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import io.javalin.http.Context;


public class GameController extends UnicastRemoteObject{
    public static Javalin app;

    GalgeLogikInterface spil;

    public GameController() throws RemoteException{
    }


    public void start(GalgeLogikInterface galgeLogikInterface) throws RemoteException, NotBoundException, MalformedURLException {
        if (app!=null)return;

        app = Javalin.create().start(7000);
     //   app.before(ctx -> System.out.println("Denne side startes nu med " + ctx.method()));
        app.config.addStaticFiles("webside");

        //Rest
        app.config.enableCorsForAllOrigins();
        //app.get("/rest/bruger/:brugernavn", GalgeServer::bruger);
        Klient klient = new Klient();
        app.get("/galgeleg", ctx ->  GameController.galgeleg(ctx,klient.returnInterface()));
        app.get("/highscore", ctx -> ctx.result("Hello Gustav"));



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
    public static void galgeleg(Context context, GalgeLogikInterface spil) throws RemoteException, MalformedURLException, NotBoundException {
        //   app.before(ctx -> System.out.println("Galgeleg åbnes"));

        String bogstav;
        bogstav = context.queryParam("gat");
        if (bogstav!=null) {
            spil.gætBogstav(bogstav);
        }

        if (spil.erSpilletSlut()) {
            //context.result("Spillet er slut, ordet var: " + spil.getOrdet());
            context.contentType("text/html; charset=utf-8").result("<html><body><h3>Spillet er slut, ordet var: "+spil.getOrdet()+"</h3></html></body>");

        } else {



            context.contentType("text/html; charset=utf-8").result("<html><body><form method=get> <p>" + spil.getSynligtOrd() + "</p><br/>" +
                    "<input name=gat type=text> </form>" +
                    "<br/> <p> Tryk enter for at gætte </p>"+
                    "</html>");
        }
    }
    private static void highscore(Context context) {app.before(ctx -> System.out.println("Highscore åbnes"));}
}
