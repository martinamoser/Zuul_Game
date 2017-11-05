import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
/**
 * Diese Klasse modelliert Räume in der Welt von Zuul.
 * 
 * Ein "Raum" repräsentiert einen Ort in der virtuellen Landschaft des
 * Spiels. Ein Raum ist mit anderen Räumen über Ausgänge verbunden.
 * Mögliche Ausgänge liegen im Norden, Osten, Süden und Westen.
 * Für jede Richtung hält ein Raum eine Referenz auf den 
 * benachbarten Raum.
 * 
 * @author  Michael Kölling und David J. Barnes
 * @version 31.07.2011
 */
public class Raum 
{
    private String beschreibung;
    private HashMap<String, Raum> ausgaenge;
    private ArrayList<Gegenstand> gegenstaende; //teilt dem Raum Gegenstände zu
    
    /**
     * Erzeuge einen Raum mit einer Beschreibung. Ein Raum
     * hat anfangs keine Ausgänge.
     * @param beschreibung enthält eine Beschreibung in der Form
     *        "in einer Küche" oder "auf einem Sportplatz".
     */
    public Raum(String beschreibung) 
    {
        this.beschreibung = beschreibung;
        ausgaenge= new HashMap<String, Raum>();
        gegenstaende= new ArrayList<Gegenstand>();
    }

    /**
     * Definiere die Ausgänge dieses Raums. Jede Richtung
     * führt entweder in einen anderen Raum oder ist 'null'
     * (kein Ausgang).
     * @param norden Der Nordausgang.
     * @param osten Der Ostausgang.
     * @param sueden Der Südausgang.
     * @param westen Der Westausgang.
     */
    public void setzeAusgaenge(String richtung, Raum nachbar) 
    {
        ausgaenge.put(richtung, nachbar);
    }
    public void setzeGegenstände(Gegenstand gegenstand)
    {
        gegenstaende.add(gegenstand);
    }

    public ArrayList<Gegenstand> gibGegenstände()
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
        String ergebnis="Ausgänge:";
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
