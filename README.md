# LemonApiJava
An api utility for LemonSqueezy.

## Usage
```java 
ActivateLicenseResponse alr = new LemonActivateLicenseRequest()
    .setInstanceName("Test")
    .setLicenseKey("my-license-key")
    .send();

ValidateLicenseResponse vlr = new LemonValidateLicenseRequest()
    .setInstanceId("Test")
    .setLicenseKey("my-license-key")
    .send();

DeactivateLicenseResponse dlr = new LemonDeactivateLicenseRequest()
    .setInstanceId("Test")
    .setLicenseKey("my-license-key")
    .send();
```

## Getting it
https://jitpack.io/#Casterlabs/LemonApiJava
