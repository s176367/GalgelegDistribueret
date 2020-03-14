package Klient;

import Database.GalgelegDTO;
import Server.GalgeLogikInterface;
import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import io.javalin.Javalin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
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
        app.config.addStaticFiles("webside");

        //Rest
        app.config.enableCorsForAllOrigins();
        //app.get("/rest/bruger/:brugernavn", GalgeServer::bruger);
        Klient klient = new Klient();
        app.get("/galgeleg", ctx ->  GameController.galgeleg(ctx,klient.returnInterface()));
        app.get("/highscore", GameController::highscore);


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
    public static void galgeleg(Context context, GalgeLogikInterface spil) throws RemoteException, MalformedURLException, NotBoundException, SQLException {

        String bogstav;
        String brugernavn;
        brugernavn = context.queryParam("brugernavn");
        String adgangskode;
        adgangskode = context.queryParam("adgangskode");
        bogstav = context.queryParam("gat");
        if (bogstav!=null) {
            spil.gætBogstav(bogstav);
        }

        if (spil.erSpilletSlut()) {
            if (spil.erSpilletVundet()){
                context.contentType("text/html; charset=utf-8").result("<html><body><h3>Spillet er slut, Du vandt TILLYKKE! </br> Ordet var: "+spil.getOrdet()+"</h3>" +
                        "<h2>Du mistede "+spil.getAntalForkerteBogstaver()+" liv!</h2>" +
                        "<p>Brugernavn:</p><form method=get><input name=brugernavn type=text ></form></br>" +
                        "                </br> <p>Tryk enter for at gå videre</p>" +
                        "</html></body>");
                if (brugernavn!=null){
                    Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
                    Bruger bruger = ba.hentBrugerOffentligt(brugernavn);
                    System.out.println(brugernavn);
                    if (bruger!=null){
                    GalgelegDTO dto = new GalgelegDTO();
                    dto.save(bruger.fornavn,spil.getAntalForkerteBogstaver());
                    spil.nulstil();

                    }

                }
            }else{
                context.contentType("text/html; charset=utf-8").result("<html><body><h3>Spillet er slut, Ordet var: "+spil.getOrdet()+"</h3>" +
                        "<h2>Du mistede kun "+spil.getAntalForkerteBogstaver()+" liv!</h2></br>" +
                        "<h4>Men tabte desværre spillet</h4>" +
                        "</html></body>");
            }
        } else {
            context.contentType("text/html; charset=utf-8").result("<html><body><form method=get> <p>" + spil.getSynligtOrd() + "</p><br/>" +
                    "<input name=gat type=text> </form>" +
                    "<br/> <p> Tryk enter for at gætte </p>"+
                    "</html>");
        }
    }
    private static void highscore(Context context) throws SQLException {
        GalgelegDTO dto = new GalgelegDTO();
        ArrayList<String> highscore = dto.getAll();
        String all = "";
        for (int i = 0; i < highscore.size(); i++) {
            all += highscore.get(i)+"\n";
        }
        context.result(all);

    }

}
