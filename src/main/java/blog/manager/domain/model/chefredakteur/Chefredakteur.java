package blog.manager.domain.model.chefredakteur;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import blog.manager.domain.model.nutzer.Nutzer;
import blog.manager.domain.model.redakteur.Redakteur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Chefredakteur extends Redakteur {

    private Integer id;
    private String telefonnummer;
    private String email;

    public Chefredakteur(Nutzer nutzer, Redakteur redakteur, String telefonnummer, String email){
        this.setEmail(nutzer.getEmail());
        this.setPasswort(nutzer.getPasswort());
        this.setGeschlecht(nutzer.getGeschlecht());
        this.setGeburtsdatum(nutzer.getGeburtsdatum());
        this.setBenutzername(nutzer.getBenutzername());
        this.setVorname(redakteur.getVorname());
        this.setNachname(redakteur.getNachname());
        this.setBiographie(redakteur.getBiographie());
        this.telefonnummer = telefonnummer;
        this.email = email;
    }


}
