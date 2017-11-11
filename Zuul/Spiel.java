import java.util.Stack;
import java.util.HashMap;
/**
 *  Dies ist die Hauptklasse der Anwendung "Die Welt von Zuul".
 *  "Die Welt von Zuul" ist ein sehr einfaches, textbasiertes
 *  Adventure-Game. Ein Spieler kann sich in einer Umgebung bewegen,
 *  mehr nicht. Das Spiel sollte auf jeden Fall ausgebaut werden,
 *  damit es interessanter wird!
 * 
 *  Zum Spielen muss eine Instanz dieser Klasse erzeugt werden und
 *  an ihr die Methode "spielen" aufgerufen werden.
 * 
 *  Diese Instanz erzeugt und initialisiert alle anderen Objekte
 *  der Anwendung: Sie legt alle Räume und einen Parser an und
 *  startet das Spiel. Sie wertet auch die Befehle aus, die der
 *  Parser liefert, und sorgt für ihre Ausführung.
 * 
 * @author  Michael Kölling und David J. Barnes
 * @version 31.07.2011
 */

public class Spiel 
{
    private HashMap<String, String> map;
    private Parser parser;

    private Spieler spieler;
    
        
    /**
     * Erzeuge ein Spiel und initialisiere die interne Raumkarte.
     */
    public Spiel() 
    {
        map = new HashMap<>();
        map.put("north", "south");
        map.put("south", "north");
        map.put("west", "east");
        map.put("east", "west");
        raeumeAnlegen();
        parser = new Parser();
        
        
    }

    /**
     * Erzeuge alle Räume und verbinde ihre Ausgänge miteinander.
     */
    private void raeumeAnlegen()
    {
        Raum draussen, hoersaal, cafeteria, labor, buero;
      
        // die Räume erzeugen
        draussen = new Raum("vor dem Haupteingang der Universität");
        hoersaal = new Raum("in einem Vorlesungssaal");
        cafeteria = new Raum("in der Cafeteria der Uni");
        labor = new Raum("in einem Rechnerraum");
        buero = new Raum("im Verwaltungsbüro der Informatik");
        
        //Gegenstände setzen
        draussen.setzeGegenstände(new Gegenstand("Messer", 1));
        draussen.setzeGegenstände(new Gegenstand("Hammer", 5));
        labor.setzeGegenstände(new Gegenstand("Sichel", 3));
        labor.setzeGegenstände(new Gegenstand("Grabstein", 15));
        buero.setzeGegenstände(new Gegenstand("Schaufel", 4));
        cafeteria.setzeGegenstände(new Gegenstand("Pistole", 10));
        
        hoersaal.setzeGegenstände(new Gegenstand("Säbel", 4));
        hoersaal.setzeGegenstände(new MagischerGegenstand("Muffin", 0, "erhöhtTragkraft"));
        
        
        // die Ausgänge initialisieren
        draussen.setzeAusgaenge("east", hoersaal);
        draussen.setzeAusgaenge("south", labor); 
        draussen.setzeAusgaenge("west", cafeteria);
        hoersaal.setzeAusgaenge("west", draussen);
        cafeteria.setzeAusgaenge("east", draussen);
        
        labor.setzeAusgaenge("north", draussen);
        labor.setzeAusgaenge("east", buero);
        buero.setzeAusgaenge("west", labor);
        
        spieler = new Spieler ("Hansi", 20); 
        spieler.setzeAktuellenRaum(draussen);

        
    }

    /**
     * Die Hauptmethode zum Spielen. Läuft bis zum Ende des Spiels
     * in einer Schleife.
     */
    public void spielen() 
    {   
             
        willkommenstextAusgeben();

        // Die Hauptschleife. Hier lesen wir wiederholt Befehle ein
        // und führen sie aus, bis das Spiel beendet wird.
                
        boolean beendet = false;
        while (! beendet) {
            Befehl befehl = parser.liefereBefehl();
            beendet = verarbeiteBefehl(befehl);
        }
        System.out.println("Danke für dieses Spiel. Auf Wiedersehen.");
    }

    /**
     * Einen Begrüßungstext für den Spieler ausgeben.
     */
    private void willkommenstextAusgeben()
    {
        System.out.println();
        System.out.println(spieler.gibNameSpieler()+", willkommen zu Zuul!");
        System.out.println("Zuul ist ein neues, unglaublich langweiliges Spiel.");
        System.out.println("Tippen sie 'help', wenn Sie Hilfe brauchen.");
        System.out.println();
        raumInfoAusgeben();

    }

    /**
     * Verarbeite einen gegebenen Befehl (führe ihn aus).
     * @param befehl Der zu verarbeitende Befehl.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    public boolean verarbeiteBefehl(Befehl befehl,boolean undo) 
    {
        boolean moechteBeenden = false;

        if(befehl.istUnbekannt()) {
            sagenDassBefehlUnbekannt();
            return false;
        }
        String befehlswort = befehl.gibBefehlswort();
        if (befehlswort.equals("help")) {
            hilfstextAusgeben();
        }
        else if (befehlswort.equals("go")) {
            wechsleRaum(befehl,undo);
        }
        else if (befehlswort.equals("quit")) {
            moechteBeenden = beenden(befehl);
        }
        else if (befehlswort.equals("look")) {
            umsehen();
        }
        else if (befehlswort.equals("eat")) {
        essen(befehl);
        }
        else if (befehlswort.equals("back")) {
            Stack<Befehl> stack = spieler.gibundoStack() ;
            
            if( stack.size() > 0 )
            {
               verarbeiteBefehl(stack.pop(),true);
            }
            else
            { 
                System.out.println("Sie sind wieder am Anfang des Spiels!");
            }
            
        }
        else if (befehlswort.equals("take")) {
        take(befehl,undo);
        }
        else if (befehlswort.equals("drop")) {
        drop(befehl,undo);
        }
        else if (befehlswort.equals("status")) {
        spieler.gibListeMeinerGegenstaende();
        spieler.dasGesamtgewichtBeträgt();
        }
        else if (befehlswort.equals("muffin")) {
        
        }
        
        for( Befehl b : spieler.gibundoStack() )
        {
            System.out.println( b.gibBefehlswort() + " " + b.gibZweitesWort() ) ;
        }
        
        return moechteBeenden;
    }

    public boolean verarbeiteBefehl(Befehl befehl) 
    {
        return verarbeiteBefehl( befehl, false ) ;
    }
    
    // Implementierung der Benutzerbefehle:

    /**
     * Gib Hilfsinformationen aus.
     * Hier geben wir eine etwas alberne und unklare Beschreibung
     * aus, sowie eine Liste der Befehlswörter.
     */
    private void hilfstextAusgeben() 
    {
        System.out.println("Sie haben sich verlaufen. Sie sind allein.");
        System.out.println("Sie irren auf dem Unigelände herum.");
        System.out.println();
        System.out.println("Ihnen stehen folgende Befehle zur Verfügung:");
        parser.zeigeBefehle();
    }
    private void take(Befehl befehl,boolean undo)
    {
        boolean zweitesWortBekannt=false;
        if(!befehl.hatZweitesWort()) {
            sagenDassBefehlUnbekannt();
        } else {
        if(spieler.take(befehl.gibZweitesWort()) && !undo)
        spieler.gibundoStack().push( new Befehl("drop",befehl.gibZweitesWort()) ) ;
            } 

    }
    
        private void drop(Befehl befehl,boolean undo)
    {
        boolean zweitesWortBekannt=false;
        if(!befehl.hatZweitesWort()) {
            sagenDassBefehlUnbekannt();
        } else if(spieler.gibmeineGegenstaende().isEmpty())
        {
            System.out.println(spieler.gibNameSpieler()+", du hast im Moment gar keine Gegenstände.");
        }
        //wenn der Befehl gültig ist, testen, ob Gegenstand zu schwer
            else {
            for (int i=0; i<spieler.gibmeineGegenstaende().size(); i++)
            {
                if (spieler.gibmeineGegenstaende().get(i).getGegenstandBeschreibung().equalsIgnoreCase(befehl.gibZweitesWort()))
                {
                  spieler.gegenstandAblegen(spieler.gibmeineGegenstaende().get(i));
                  if( !undo )
                  {
                     spieler.gibundoStack().push( new Befehl("take",befehl.gibZweitesWort()) ) ;
                  }
                  zweitesWortBekannt=true;
                } 
            }
        } 
        if (zweitesWortBekannt==false&&!spieler.gibmeineGegenstaende().isEmpty())
                {
                    System.out.println("Diesen Gegenstand gibt es nicht.");
                }
               /* if (befehl.gibZweitesWort().equalsIgnoreCase("maffin"))
                {
                    System.out.println("Ein Maffin kann nicht mehr abgelegt werden.");
                } */
    }

    /**
     * Versuche, in eine Richtung zu gehen. Wenn es einen Ausgang gibt,
     * wechsele in den neuen Raum, ansonsten gib eine Fehlermeldung
     * aus.
     */
    private void wechsleRaum(Befehl befehl,boolean undo) 
    {
        if(!befehl.hatZweitesWort()) {
            // Gibt es kein zweites Wort, wissen wir nicht, wohin...
            System.out.println("Wohin möchten Sie gehen?");
            return;
        }

        String richtung = befehl.gibZweitesWort();

        // Wir versuchen, den Raum zu verlassen.
        spieler.setzeNaechstenRaum(spieler.gibAktuellenRaum().gibAusgang(richtung));

        if (spieler.gibNaechstenRaum() == null) {
            System.out.println("Dort ist keine Tür!");
        }
        else {
            
            //ehemaligerRaum = aktuellerRaum; //ehemaligerRaum für back speichern
            if( !undo )
            {
                spieler.gibundoStack().push(new Befehl("go", map.get(richtung) ));
            }
            spieler.setzeAktuellenRaum(spieler.gibNaechstenRaum());
            //System.out.println(richtung);
            raumInfoAusgeben();
           
            }
            
        }
        
        /*
        public void zurückgehen(Befehl befehl)
        {        
            if(befehl.hatZweitesWort()) 
            {
            sagenDassBefehlUnbekannt();
            } else
            { if (spieler.gibehemaligeRaeume().empty())
                {
                    System.out.println("Sie befinden sich wieder am Anfang des Spiels");
                } else
                {
                    spieler.setzeAktuellenRaum(spieler.gibehemaligeRaeume().pop());
                    raumInfoAusgeben();
                }
            }
               
            
            
             else {
              aktuellerRaum=ehemaligerRaum;
              raumInfoAusgeben();
            } 
            
        } */
        private void raumInfoAusgeben()
        {
        System.out.println(spieler.gibAktuellenRaum().gibLangeBeschreibung());
        
    }

    /**
     * "quit" wurde eingegeben. Überprüfe den Rest des Befehls,
     * ob das Spiel wirklich beendet werden soll.
     * @return 'true', wenn der Befehl das Spiel beendet, 'false' sonst.
     */
    public boolean beenden(Befehl befehl) 
    {
        if(befehl.hatZweitesWort()) {
            System.out.println("Was soll beendet werden?");
            return false;
        }
        else {
            return true;  // Das Spiel soll beendet werden.
        }
    }
    
    public void umsehen()
    {
        System.out.println(spieler.gibAktuellenRaum().gibLangeBeschreibung());
    }
    //Befehl eat oder eat muffin
    
    
    public void essen(Befehl befehl)
      { boolean maffinImRaum=false;
          if(!befehl.hatZweitesWort()) 
          {
          System.out.println("Sie sind ein Fressack, än Guete!");
        } 
        /*
        else if (!befehl.gibZweitesWort().equalsIgnoreCase("maffin"))
        {
            sagenDassBefehlUnbekannt();
        } else
        {
            for (int i=0; i<spieler.gibAktuellenRaum().gibGegenstände().size(); i++)
            {//falls das Magic Maffin im Raum ist:
                if (spieler.gibAktuellenRaum().gibGegenstände().get(i).getGegenstandBeschreibung().equalsIgnoreCase(befehl.gibZweitesWort()))
                {
                   spieler.setzeNeueTragkraft();
                   maffinImRaum=true;
 
                }
            }
            if(maffinImRaum==false)
                {
                    System.out.println("Sorry, hier gibt es kein Maffin!");
                }
         }
*/
    }
    
    
    public void sagenDassBefehlUnbekannt()
    {
        System.out.println("Ich weiss nicht, was Sie meinen...");
    }
}
