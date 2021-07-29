package co.casterlabs.lemonapijava;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.lemonapijava.types.LemonLicenseStatus;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LemonHttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    public static Rson RSON = new Rson.Builder()
        .registerTypeResolver(new TypeResolver<LemonLicenseStatus>() {

            @Override
            public @Nullable LemonLicenseStatus resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                String val = value.getAsString().toUpperCase();

                return LemonLicenseStatus.valueOf(val);
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull LemonLicenseStatus value, @NonNull Class<?> type) {
                return new JsonString(value.name());
            }

        }, LemonLicenseStatus.class)
        .build();

    public static Response sendHttp(@Nullable RequestBody body, @Nullable String type, @NonNull String address) throws IOException {
        Request.Builder builder = new Request.Builder()
            .url(address)
            .addHeader("Accept", "application/json");

        if ((body != null) && (type != null)) {
            builder.method(type, body);
        }

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response;
    }

}
