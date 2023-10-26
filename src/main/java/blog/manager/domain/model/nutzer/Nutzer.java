package blog.manager.domain.model.nutzer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Nutzer {

    private int nutzerid;
    private String email;
    private String passwort;
    private String geschlecht;
    private String geburtsdatum;
    private String benutzername;

    public Nutzer(String email, String passwort, String geschlect, String geburtsdatum, String benutzername) {
        this.email = email;
        this.passwort = passwort;
        this.geschlecht = geschlect;
        this.geburtsdatum = geburtsdatum;
        this.benutzername = benutzername;
    }

    @Override
    public String toString(){
        return nutzerid
                +"\n"+ email
                +"\n" + passwort
                + "\n"+ geschlecht
                +"\n" +geburtsdatum
                + "\n" + benutzername;
    }
}
