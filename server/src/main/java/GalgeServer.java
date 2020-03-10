import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import io.javalin.Javalin;
import io.javalin.http.Context;


import java.rmi.Naming;

public class GalgeServer {
    public static Javalin app;

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void start() {
        if (app!=null) return;

        app = Javalin.create().start(7000);
        app.before(ctx -> System.out.println("Denne side startes nu med " + ctx.method()));
        app.config.addStaticFiles("website");

        //Rest
        app.config.enableCorsForAllOrigins();
        app.get("/rest/bruger/:brugernavn", GalgeServer::bruger);


        }

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
