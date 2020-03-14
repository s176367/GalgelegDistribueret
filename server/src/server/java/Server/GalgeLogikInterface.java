package Server;

import java.util.ArrayList;

public interface GalgeLogikInterface extends java.rmi.Remote {
ArrayList<String> getBrugteBogstaver() throws java.rmi.RemoteException;
String getSynligtOrd() throws java.rmi.RemoteException;
String getOrdet() throws java.rmi.RemoteException;
int getAntalForkerteBogstaver() throws java.rmi.RemoteException;
boolean erSidsteBogstavKorrekt() throws java.rmi.RemoteException;
boolean erSpilletVundet() throws java.rmi.RemoteException;
public boolean erSpilletTabt() throws java.rmi.RemoteException;
boolean erSpilletSlut() throws java.rmi.RemoteException;
void nulstil() throws java.rmi.RemoteException;
void opdaterSynligtOrd() throws java.rmi.RemoteException;
void gætBogstav(String bogstav) throws java.rmi.RemoteException;
void logStatus() throws java.rmi.RemoteException;
void hentOrdFraDr() throws Exception, java.rmi.RemoteException;

}
