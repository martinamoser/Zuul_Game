import java.util.ArrayList;
import java.util.Stack;
/**
 * Beschreiben Sie hier die Klasse Spieler.
 * Jeder Spieler hat einen Namen und einen aktuellen Raum
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Spieler
{
    private String spielername;
    private int tragkraft;
    private ArrayList<Gegenstand> meineGegenstaende;
    private Raum aktuellerRaum;
    //private Raum ehemaligerRaum;
    private Raum naechsterRaum;
    private Stack<Befehl> undoStack;

    /**
     * Konstruktor f�r Objekte der Klasse Spieler
     */
    public Spieler(String name, int kraft)
    {
        spielername=name;
        tragkraft=kraft;
        meineGegenstaende= new ArrayList<Gegenstand>();
        aktuellerRaum = null;  // das Spiel startet draussen
        naechsterRaum = null;
        undoStack = new Stack<Befehl>();
        //ehemaligerRaum = draussen; // zur�ck zur�ck f�hrt nach draussen
    }

    /**
     * Ein Beispiel einer Methode - ersetzen Sie diesen Kommentar mit Ihrem eigenen
     * 
     * @param  y    ein Beispielparameter f�r eine Methode
     * @return        die Summe aus x und y
     */
    
    public Raum gibAktuellenRaum()
    {
        
        return aktuellerRaum;
    }
        public Raum gibNaechstenRaum()
    {
        
        return naechsterRaum;
    }
        public void setzeNaechstenRaum(Raum raum)
    {
        
        naechsterRaum=raum;
    }
    public void setzeAktuellenRaum(Raum raum)
    {
        aktuellerRaum=raum;
    }
    public Stack<Befehl> gibundoStack()
    {
        return undoStack;
    }
    public ArrayList<Gegenstand> gibmeineGegenstaende()
    {
        return meineGegenstaende;
    }
    public void gibListeMeinerGegenstaende()
    {
        String meineListe="";
        for(Gegenstand gegenstand: meineGegenstaende)
        {
            meineListe+=gegenstand;
            
        }
        System.out.println(meineListe); //ruft toString aus Klasse Gegenstand auf
    }
    public String gibNameSpieler()
    {
        return spielername;
    }
    public void gegenstandAufnehmen(Gegenstand gegenstand)
    {   
        meineGegenstaende.add(gegenstand);
        aktuellerRaum.gibGegenst�nde().remove(gegenstand);
      }
          public void gegenstandAblegen(Gegenstand gegenstand)
    {   
        meineGegenstaende.remove(gegenstand);
        aktuellerRaum.gibGegenst�nde().add(gegenstand);
      }
    public int gibTragkraft()
     {
         return tragkraft;
     }
     public int gibGewichtListeGegenst�nde()
     {  
         int gewichtAlleGegenst�nde=0;
        for (Gegenstand gegenstand: meineGegenstaende)
        {
            gewichtAlleGegenst�nde += gegenstand.getGewicht();

        }

        return gewichtAlleGegenst�nde;
     }
     public void dasGesamtgewichtBetr�gt()
     {
         System.out.println("Das Gesamtgewicht betr�gt: "+gibGewichtListeGegenst�nde()+ " Einheiten.");
        }
        
     public void setzeNeueTragkraft()
     {
         int muffinBonus=10;
         tragkraft+=muffinBonus;
         System.out.println("Juhu, Gratulation, mit dem Muffin-Bonus betr�gt deine Tragkraft neu "+tragkraft+" Einheiten!");
        }
     public boolean take(String gegenstand)
     {  
        ArrayList<Gegenstand> liste = aktuellerRaum.gibGegenst�nde();
        if(liste.isEmpty())
        {
              System.out.println("In diesem Raum hat es momentan keine Gegenst�nde, "+spielername +".");
              return false  ;
        }
        
        
        for (Gegenstand g: liste)
        {
            if (g.getGegenstandBeschreibung().equalsIgnoreCase(gegenstand))
            {
                if(tragkraft<gibGewichtListeGegenst�nde()+g.getGewicht())
                {
                     System.out.println("Der Gegenstand ist zu schwer.");
                     return false;
                }
                
                gegenstandAufnehmen(g);
                return true;  
            }
        }

        System.out.println("Diesen Gegenstand gibt es hier nicht.");   
        return false;
    }
}
