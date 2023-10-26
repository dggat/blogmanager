package blog.manager.domain.model.kommentar;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KommentarForAnwender {

    private Integer nutzerid;
    private Integer blogeintragid;
    private String erstellungsdatum;
    private String text;
    @JsonIgnore
    private Integer kommenatrid;

    @JsonIgnore
    private String email;

    public KommentarForAnwender(Integer blogeintragid, String email, String erstellungsdatum, String text) {
        this.blogeintragid = blogeintragid;
        this.email = email;
        this.erstellungsdatum = erstellungsdatum;
        this.text = text;
    }
}
