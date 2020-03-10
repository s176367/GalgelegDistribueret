package brugerautorisation.transport.soap;

import brugerautorisation.data.Bruger;

import javax.jws.WebMethod;
import javax.jws.WebService;

@SuppressWarnings("NonAsciiCharacters")
@WebService
public interface Brugeradmin
{
	/**
	 * Henter alle en brugers data
	 * @return et Bruger-objekt med alle data
	 */
	@WebMethod
    Bruger hentBruger(String brugernavn, String adgangskode);

	/**
	 * Ændrer en brugers adgangskode
	 * @return et Bruger-objekt med alle data
	 */
	@WebMethod
    Bruger ændrAdgangskode(String brugernavn, String glAdgangskode, String nyAdgangskode);

	/**
	 * Sender en email til en bruger
	 * @param brugernavn Brugeren, som emailen skal sendes til
	 * @param emne Emnet - teksten DIST: bliver foranstillet i mailen
	 * @param tekst Brødteksten - teksten 'Sendt fra xxxx ' bliver tilføjet  i mailen
	 */
	@WebMethod
    void sendEmail(String brugernavn, String adgangskode, String emne, String tekst);

	@WebMethod
    void sendGlemtAdgangskodeEmail(String brugernavn, String følgetekst);

	/**
	 * Giver mulighed for at gemme et ekstra felt for brugeren. Det kunne f.eks. være at en Galgeleg-backend ønskede at gemme hvor mange point brugeren har, til en highscoreliste
	 * @param brugernavn Brugeren det drejer sig om. Adgangskode skal være korrekt, dvs det er ikke muligt at hente felter for brugere, der ikke er logget ind.
	 * @param feltnavn Navnet på feltet. Brug dit studie- eller gruppenummer som præfix, f.eks. "g22_galgeleg_point"
	 * @param værdi Værdien er et vilkårligt objekt, f.eks. 223 (Integer) eller "223" (String)
	 */
	@WebMethod
    void setEkstraFelt(String brugernavn, String adgangskode, String feltnavn, Object værdi);

	/**
	 * Aflæser et ekstra felt. Se setEkstraFelt
	 */
	@WebMethod
    Object getEkstraFelt(String brugernavn, String adgangskode, String feltnavn);

        /**
	 * Fjern en brugers ekstrafelter
	 * @param brugernavn Brugeren det drejer sig om. Adgangskode skal være korrekt, dvs det er ikke muligt at slette felter for brugere, der ikke er logget ind.
	 */
	@WebMethod
    void fjernAlleEkstraFelter(String brugernavn, String adgangskode);

	/**
	 * Henter en brugers offentlige data
	 * @return et Bruger-objekt med de offentlige data (brugernavn, fornavn, efternavn, email, campusnetId, studieretning, sidst aktiv)
	 */
	@WebMethod
    Bruger hentBrugerOffentligt(String brugernavn);


}