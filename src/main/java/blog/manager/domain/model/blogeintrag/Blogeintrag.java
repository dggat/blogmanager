package blog.manager.domain.model.blogeintrag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Blogeintrag {

    private Integer blogeintragid;
    private String titel;
    private String text;
    private String erstellungsdatum;
    private String aenderungsdatum;
    @JsonIgnore
    private Integer wortid;
    @JsonIgnore
    private String redakteurEmail;

    public Blogeintrag(String titel, String text, String erstellungsdatum, String aenderungsdatum, String email) {
        this.titel = titel;
        this.text = text;
        this.erstellungsdatum = erstellungsdatum;
        this.redakteurEmail = email;
        this.erstellungsdatum = erstellungsdatum;
        this.erstellungsdatum = aenderungsdatum;
        this.aenderungsdatum = erstellungsdatum;
    }

    public Blogeintrag(String titel, String text, String erstellungsdatum, String email, Integer wortid) {
        this.titel = titel;
        this.text = text;
        this.erstellungsdatum = erstellungsdatum;
        this.redakteurEmail = email;
        this.erstellungsdatum = erstellungsdatum;
        this.aenderungsdatum = erstellungsdatum;
        this.wortid = wortid;

    }
}
