package blog.manager.domain.model.redakteur;

import blog.manager.domain.model.nutzer.Nutzer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Redakteur extends Nutzer {

    private int nutzerid;
    private Integer redakteurid;
    private String vorname;
    private String nachname;
    private String biographie;

    public Redakteur(String email, String passwort, String geschlect, String geburtsdatum, String benutzername, String vorname, String nachname, String biographie) {
        super(email, passwort, geschlect, geburtsdatum, benutzername);
        this.vorname = vorname;
        this.nachname = nachname;
        this.biographie = biographie;
    }

    public Redakteur(Nutzer nutzer, String vorname, String nachname, String biographie) {
        this.setEmail(nutzer.getEmail());
        this.setPasswort(nutzer.getPasswort());
        this.setGeschlecht(nutzer.getGeschlecht());
        this.setGeburtsdatum(nutzer.getGeburtsdatum());
        this.setBenutzername(nutzer.getBenutzername());
        this.setVorname(vorname);
        this.setNachname(nachname);
        this.setBiographie(biographie);
    }

    @Override
    public String toString(){
        super.toString();
        return vorname
                + "\n" +nachname
                + "\n" + biographie;
    }
}
