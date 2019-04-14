package com.example.quantumchessapp.spiel;

import com.example.quantumchessapp.dao.Netzwerk;
import com.example.quantumchessapp.figuren.Bauer;
import com.example.quantumchessapp.figuren.Koenig;
import com.example.quantumchessapp.figuren.Koenigin;
import com.example.quantumchessapp.figuren.Laeufer;
import com.example.quantumchessapp.figuren.Spielfigur;
import com.example.quantumchessapp.figuren.Springer;
import com.example.quantumchessapp.figuren.Turm;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Schachbrett {

    private int m_Y, m_X;
    private HashMap<Position, Spielfigur> feldInhalt;

    public Schachbrett(int x, int y) {
        m_Y = y + 1;
        m_X = x + 1;
    }

    public int getY() {
        return m_Y;
    }

    public int getX() {
        return m_X;
    }

    // starte den geilen Scheiss
    public void init(HashMap<Position, Spielfigur> mapBelegung) {
        stelleFigurenAuf(mapBelegung);
    }

    // zeichne Spielbrett auf der Konsole
    private void stelleFigurenAuf(HashMap<Position, Spielfigur> mapBelegung) {
        // erstelle Brett
        feldInhalt = new HashMap<>();
        feldInhalt = mapBelegung;
        // erstelle Figuren
        Spielfigur bauer = new Bauer();
        Spielfigur turm = new Turm();
        Spielfigur springer = new Springer();
        Spielfigur laeufer = new Laeufer();
        Spielfigur koenigin = new Koenigin();
        Spielfigur koenig = new Koenig();
        // Aufbau des Spielbretts
        for (int i = 0; i < getX(); i++) {
            for (int j = 0; j < getY(); j++) {
                Position pos;
                if (i == 1 || i == 6) {
                    pos = new Position(i, j);
                    feldInhalt.put(pos, bauer); // ab in die Ha.ha.ha.ha.hashmap
                }
                // setze Turm
                if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7)) {
                    pos = new Position(i, j);
                    feldInhalt.put(pos, turm);
                }
                // setze Springer
                if ((i == 0 && j == 1) || (i == 0 && j == 6) || (i == 7 && j == 1) || (i == 7 && j == 6)) {
                    pos = new Position(i, j);
                    feldInhalt.put(pos, springer);
                }
                // setze Laeufer
                if ((i == 0 && j == 2) || (i == 0 && j == 5) || (i == 7 && j == 2) || (i == 7 && j == 5)) {
                    pos = new Position(i, j);
                    feldInhalt.put(pos, laeufer);
                }
                // setze Koenigin
                if ((i == 0 || i == 7) && (j == 3)) {
                    pos = new Position(i, j);
                    feldInhalt.put(pos, koenigin);
                }
                // setze Koenig
                if ((i == 0 || i == 7) && (j == 4)) {
                    pos = new Position(i, j);
                    feldInhalt.put(pos, koenig);
                }
            }
        }
    }

    // schiebe Figur von A nach B
    // public void bewegeFigur(Position alt, Position neu) {
    public HashMap<Position, Spielfigur> bewegeFigur(Spielfigur spf, Position alt, Position neu) {
        feldInhalt.remove(alt);
        feldInhalt.put(neu, spf);
        return feldInhalt;
    }

    // wer steht auf dem ausgewaehlten Feld?
    public Spielfigur getFigurAufFeld(Position p) {
        return feldInhalt.get(p);
    }

    // steht eine Figur auf dem ausgewaehlten Feld?
    public boolean feldIstBelegt(Position p) {
        return getFigurAufFeld(p) != null;
    }

    // Infos in JSON packen
    private JSONObject output(HashMap feldInhalt) {

        JSONObject out = new JSONObject();

        for (Object o : feldInhalt.keySet()) {
            Position p = (Position) o;
            try
            {
                out.put(p.getX() + "," + p.getY(), getFigurAufFeld(p).getClass().getSimpleName());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return out;
    }

    // schicke mal die Daten los
    public void senden() {

        System.out.println("Sende an Server...");
        Netzwerk n = new Netzwerk();
        try {
            n.sendeAnServer(output(feldInhalt));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
