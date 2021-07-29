package co.casterlabs.lemonapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonClass(exposeAll = true)
public class LemonLicenseInstance {
    private String id;

    private String name;

    @JsonField("created_at")
    private String createdAt;

}
