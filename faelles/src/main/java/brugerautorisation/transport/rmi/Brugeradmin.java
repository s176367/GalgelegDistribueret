package brugerautorisation.transport.rmi;

import brugerautorisation.data.Bruger;

@SuppressWarnings("NonAsciiCharacters")
public interface Brugeradmin extends java.rmi.Remote {
	/**
	 * Henter alle en brugers data
	 * @return et Bruger-objekt med alle data
	 */
	Bruger hentBruger(String brugernavn, String adgangskode) throws java.rmi.RemoteException;

	/**
	 * Ændrer en brugers adgangskode
	 * @return et Bruger-objekt med alle data
	 */
	Bruger ændrAdgangskode(String brugernavn, String glAdgangskode, String nyAdgangskode) throws java.rmi.RemoteException;

	/**
	 * Sender en email til en bruger
	 * @param brugernavn Brugeren, som emailen skal sendes til
	 * @param emne Emnet - teksten DIST: bliver foranstillet i mailen
	 * @param tekst Brødteksten - teksten 'Sendt fra xxxx ' bliver tilføjet  i mailen
	 * @throws java.rmi.RemoteException Hvis der sker en fejl i transport eller på serveren
	 */
	void sendEmail(String brugernavn, String adgangskode, String emne, String tekst) throws java.rmi.RemoteException;

	void sendGlemtAdgangskodeEmail(String brugernavn, String følgetekst) throws java.rmi.RemoteException;

	/**
	 * Giver mulighed for at gemme et ekstra felt for brugeren. Det kunne f.eks. være at en Galgeleg-backend ønskede at gemme hvor mange point brugeren har, til en highscoreliste
	 * @param brugernavn Brugeren det drejer sig om. Adgangskode skal være korrekt, dvs det er ikke muligt at hente felter for brugere, der ikke er logget ind.
	 * @param feltnavn Navnet på feltet. Brug dit studie- eller gruppenummer som præfix, f.eks. "g22_galgeleg_point"
	 * @param værdi Værdien er et vilkårligt objekt, f.eks. 223 (Integer) eller "223" (String)
	 * @throws java.rmi.RemoteException Hvis der sker en fejl i transport eller på serveren
	 */
	void setEkstraFelt(String brugernavn, String adgangskode, String feltnavn, Object værdi) throws java.rmi.RemoteException;

	/**
	 * Aflæser et ekstra felt. Se setEkstraFelt
	 */
	Object getEkstraFelt(String brugernavn, String adgangskode, String feltnavn) throws java.rmi.RemoteException;

        /**
	 * Fjern en brugers ekstrafelter
	 * @param brugernavn Brugeren det drejer sig om. Adgangskode skal være korrekt, dvs det er ikke muligt at slette felter for brugere, der ikke er logget ind.
	 * @throws java.rmi.RemoteException Hvis der sker en fejl i transport eller på serveren
	 */
	void fjernAlleEkstraFelter(String brugernavn, String adgangskode) throws java.rmi.RemoteException;

	/**
	 * Henter en brugers offentlige data
	 * @return et Bruger-objekt med de offentlige data (brugernavn, fornavn, efternavn, email, campusnetId, studieretning, sidst aktiv)
	 */
	Bruger hentBrugerOffentligt(String brugernavn) throws java.rmi.RemoteException;

}
