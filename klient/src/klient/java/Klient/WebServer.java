package Klient;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class WebServer {
    public static Javalin app;

    public static void main(String[] args) throws Exception {
        start();
    }

    public static void start() throws Exception {
        if (app!=null) return;
        app = Javalin.create().start(7000);
        app.before(ctx -> {
            System.out.println("Javalin Server fik "+ctx.method()+" på " +ctx.url()+ " med query "+ctx.queryParamMap()+ " og form " +ctx.formParamMap());
        });

        //app.config.addStaticFiles("webside");
        app.config.addStaticFiles("webside");

        //Rest
        app.config.enableCorsForAllOrigins();

        app.get("/rest/bruger/:brugernavn", WebServer::bruger);
        app.get("/rest/:galgeleg", WebServer::galgeleg);
        app.get("/rest/:highscore", WebServer::highscore);
        }

    private static void galgeleg(Context context) throws RemoteException, MalformedURLException, NotBoundException {
        app.before(ctx -> System.out.println("Galgeleg åbnes"));

    }

    private static void highscore(Context context) {app.before(ctx -> System.out.println("Highscore åbnes"));}

    private static void bruger(Context ctx) throws Exception{
        String brugernavn = ctx.pathParam("brugernavn");
        String adgangskode = ctx.queryParam("adgangskode");
        Brugeradmin ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");

        if (adgangskode==null){
        Bruger bruger = ba.hentBrugerOffentligt(brugernavn);
        ctx.json(bruger);
        }else try{
        Bruger bruger = ba.hentBruger(brugernavn,adgangskode);
        ctx.json(bruger);
        }catch (Exception e){
            System.out.println(e);
            ctx.status(401).result("Unauthorised");
        }

    }
}
