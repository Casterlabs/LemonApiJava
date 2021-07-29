package co.casterlabs.lemonapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.lemonapijava.LemonHttpUtil;
import co.casterlabs.lemonapijava.requests.LemonDeactivateLicenseRequest.DeactivateLicenseResponse;
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
public class LemonDeactivateLicenseRequest extends WebRequest<DeactivateLicenseResponse> {
    private String licenseKey;
    private String instanceId;

    @Override
    protected DeactivateLicenseResponse execute() throws ApiException, IOException {
        if (this.licenseKey == null) {
            throw new IllegalArgumentException("licenseKey is null.");
        } else if (this.instanceId == null) {
            throw new IllegalArgumentException("instanceId is null.");
        } else {
            RequestBody formBody = new FormBody.Builder()
                .addEncoded("license_key", this.licenseKey)
                .addEncoded("instance_id", this.instanceId)
                .build();

            try (Response response = LemonHttpUtil.sendHttp(formBody, "POST", "https://api.lemonsqueezy.com/v1/licenses/deactivate")) {
                if (response.isSuccessful()) {
                    JsonObject body = LemonHttpUtil.RSON.fromJson(response.body().string(), JsonObject.class);

                    if (!body.get("error").isJsonNull()) {
                        throw new ApiException(body.getString("error"));
                    } else {
                        DeactivateLicenseResponse result = LemonHttpUtil.RSON.fromJson(body, DeactivateLicenseResponse.class);

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
    public static class DeactivateLicenseResponse {
        private boolean deactivated;

        @JsonField("license_key")
        private LemonLicenseKey licenseKey;

    }

}
