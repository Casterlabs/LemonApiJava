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
public class LemonLicenseKey {
    private LemonLicenseStatus status;

    private String key;

    @JsonField("activation_limit")
    private int activationLimit;

    @JsonField("activation_usage")
    private int activationUsage;

    @JsonField("created_at")
    private String createdAt;

    @JsonField("expires_at")
    private String expiresAt;

}
