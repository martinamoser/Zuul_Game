import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
/**
 * Diese Klasse modelliert R�ume in der Welt von Zuul.
 * 
 * Ein "Raum" repr�sentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen R�umen �ber Ausg�nge verbunden.
 * M�gliche Ausg�nge liegen im Norden, Osten, S�den und Westen.
 * F�r jede Richtung h�lt ein Raum eine Referenz auf den 
 * benachbarten Raum.
 * 
 * @author  Michael K�lling und David J. Barnes
 * @version 31.07.2011
 */
public class Raum 
{
    private String beschreibung;
    private HashMap<String, Raum> ausgaenge;
    private ArrayList<Gegenstand> gegenstaende; //teilt dem Raum Gegenst�nde zu
    
    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausg�nge.
     * @param beschreibung enth�lt eine Beschreibung in der Form
     *        "in einer K�che" oder "auf einem Sportplatz".
     */
    public Raum(String beschreibung) 
    {
        this.beschreibung = beschreibung;
        ausgaenge= new HashMap<String, Raum>();
        gegenstaende= new ArrayList<Gegenstand>();
    }

    /**
     * Definiere die Ausg�nge dieses Raums. Jede Richtung
     * f�hrt entweder in einen anderen Raum oder ist 'null'
     * (kein Ausgang).
     * @param norden Der Nordausgang.
     * @param osten Der Ostausgang.
     * @param sueden Der S�dausgang.
     * @param westen Der Westausgang.
     */
    public void setzeAusgaenge(String richtung, Raum nachbar) 
    {
        ausgaenge.put(richtung, nachbar);
    }
    public void setzeGegenst�nde(Gegenstand gegenstand)
    {
        gegenstaende.add(gegenstand);
    }

    public ArrayList<Gegenstand> gibGegenst�nde()
    {
        return gegenstaende;
    }

    /**
     * @return die Beschreibung dieses Raums.
     */
    public String gibBeschreibung()
    {
        return beschreibung;
    }
    
    public Raum gibAusgang(String richtung)
    {

        return ausgaenge.get(richtung);
    }

    public String gibAusgaengeAlsString()
    {
        String ergebnis="Ausg�nge:";
        Set<String> keys = ausgaenge.keySet();
        for(String ausgang: keys) {
            ergebnis +=" "+ausgang;
        }
        return ergebnis;

    }
    
    public String gibGegenstandRaumAlsString()
    {
        String gegenstandString="";
        for (Gegenstand gegenstand: gegenstaende)
        {
            gegenstandString+=gegenstand.toString();
        }
        return gegenstandString;
       
    }
    
 
    
    public String gibLangeBeschreibung()
    {
        return "Sie sind "+beschreibung+ ".\n" + gibAusgaengeAlsString()+ ".\n" + gibGegenstandRaumAlsString();
    }
    
   
}
