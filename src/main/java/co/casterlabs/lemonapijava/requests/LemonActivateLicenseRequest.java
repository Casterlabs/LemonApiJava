package co.casterlabs.lemonapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.lemonapijava.LemonHttpUtil;
import co.casterlabs.lemonapijava.requests.LemonActivateLicenseRequest.ActivateLicenseResponse;
import co.casterlabs.lemonapijava.types.LemonLicenseInstance;
import co.casterlabs.lemonapijava.types.LemonLicenseKey;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

@Data
@NonNull
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class LemonActivateLicenseRequest extends WebRequest<ActivateLicenseResponse> {
    private String licenseKey;
    private String instanceName;

    @Override
    protected ActivateLicenseResponse execute() throws ApiException, IOException {
        if (this.licenseKey == null) {
            throw new IllegalArgumentException("licenseKey is null.");
        } else if (this.instanceName == null) {
            throw new IllegalArgumentException("instanceName is null.");
        } else {
            RequestBody formBody = new FormBody.Builder()
                .addEncoded("license_key", this.licenseKey)
                .addEncoded("instance_name", this.instanceName)
                .build();

            try (Response response = LemonHttpUtil.sendHttp(formBody, "POST", "https://api.lemonsqueezy.com/v1/licenses/activate")) {
                if (response.isSuccessful()) {
                    JsonObject body = LemonHttpUtil.RSON.fromJson(response.body().string(), JsonObject.class);

                    if (!body.get("error").isJsonNull()) {
                        throw new ApiException(body.getString("error"));
                    } else {
                        ActivateLicenseResponse result = LemonHttpUtil.RSON.fromJson(body, ActivateLicenseResponse.class);

                        return result;
                    }
                } else {
                    throw new ApiException("Api error: " + response.code());
                }
            } catch (JsonParseException e) {
                throw new IOException(e);
            }
        }
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @JsonClass(exposeAll = true)
    public static class ActivateLicenseResponse {
        private boolean activated;

        @JsonField("license_key")
        private LemonLicenseKey licenseKey;

        private LemonLicenseInstance instance;

    }

}
