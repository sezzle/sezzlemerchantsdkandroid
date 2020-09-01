Sezzle Android SDK
==================

Sezzle Android SDK allows you to offer Sezzle in your android app.

# Installation
Download via Gradle:
```groovy
implementation "com.sezzle:sezzlemerchantsdkandroid:1.0.2"
```
or Maven:
```xml
<dependency>
  <groupId>com.sezzle</groupId>
  <artifactId>sezzlemerchantsdkandroid</artifactId>
  <version>1.0.2</version>
</dependency>
```

## Usage Overview

Before you start the initialization for Sezzle SDK, you must have a merchant account with Sezzle.
You will need your `public API Key` from [Sezzle Merchant Dashboard](https://dashboard.sezzle.com/merchant).
You must set this key as follows:

```kotlin
Sezzle.Companion.initialize(new Sezzle.Configuration(
    "public key",
    Sezzle.Environment.PRODUCTION,
    Sezzle.LOG_LEVEL_DEBUG,
    Sezzle.Location.US
));
```
- `public key` comes from the [Sezzle Merchant Dashboard](https://dashboard.sezzle.com/merchant).
- `environment` can be set to `Sezzle.Environment.SANDBOX` for test.
- `log level` is available in the `Sezzle` object.
- `location` either US or CA is available in the `Sezzle.Location` object.

## Checkout
Checkout creation is the process in which a customer uses Sezzle to pay for a purchase in your app.
You should create an `Order` and a `Customer` and launch the Sezzle checkout using the `startCheckout` function.

```kotlin
    Sezzle.startCheckout(this@MainActivity, customerModel(), orderModel())
```
- Please look at the [Sezzle Docs](https://docs.sezzle.com/#sessions) for the structure of the Customer and Order objects.
- `activity` is the activity where you want to start the checkout.
- `Customer` is the object containing the customer's information.
- `Order` is the object containing the order information.

### Handling checkout events
1. Make sure your activity implements `Sezzle.CheckoutCallbacks`.
2. Override `onActivityResult` like so
```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (Sezzle.handleCheckoutData(this, requestCode, resultCode, data)) {
        return
    }

    super.onActivityResult(requestCode, resultCode, data)
}
```
3. Override the handler functions `onSezzleCheckoutError`, `onSezzleCheckoutCancelled` and `onSezzleCheckoutSuccess` like so
```kotlin
override fun onSezzleCheckoutError(message: String?) {
    Toast.makeText(this, "Checkout Error: $message", Toast.LENGTH_LONG).show()
}

override fun onSezzleCheckoutCancelled() {
    Toast.makeText(this, "Checkout Cancelled", Toast.LENGTH_LONG).show()
}

override fun onSezzleCheckoutSuccess(token: String) {
    Toast.makeText(this, "Checkout token: $token", Toast.LENGTH_LONG).show()
}
```

### Capture
Once the checkout is completed successfully, the token will be passed to `onSezzleCheckoutSuccess`. Send the token to your backend
and capture from there. For more information on this and other backend endpoints,
look at the [Sezzle Docs](https://docs.sezzle.com/#capture-amount-by-order).

## Sezzle Widget and Modal
Sezzle widget component and Modal components are used in product pages to educated users about Sezzle-payment.

### Show Sezzle widget with `SezzlePromotionButton`

To display the widget, the SDK provides a `SezzlePromotionButton` class. `SezzlePromotionButton` is implemented as follows:

```xml
    <com.sezzle.sezzlemerchantsdkandroid.SezzlePromotionButton
         android:id="@+id/promo"
         android:layout_width="wrap_content"
         android:layout_height="wrap_content"
         android:layout_centerHorizontal="true"
         app:sezzleTextSize="16sp"
         android:layout_below="@+id/modal_button"
         app:sezzleLogoType="SezzleLogoColorTextBlack" />
```
or
```kotlin
// Option1 - Load via findViewById
val sezzlePromo = findViewById<SezzlePromotionButton>(R.id.promo)
sezzlePromo.setLabel("or 4 interest-free payments with {sezzle_logo}")
```
or
```kotlin
// Option2 - Initialize
val sezzlePromotionButton2 = SezzlePromotionButton(this)
sezzlePromotionButton2.configWithLocalStyling(SezzleLogoType.SEZZLE_LOGO_BLACK_TEXT_BLACK)
sezzlePromotionButton2.setLabel("or 4 interest-free payments with {sezzle_logo}")
findViewById<RelativeLayout>(R.id.relative_layout).addView(sezzlePromotionButton2)
```

Configure the style of the SezzlePromotionButton
sezzleLogoType: SezzleLogoType,
        typeface: Typeface?,
        sezzleTextColor: Int,
        sezzleTextSize: Int
- `configWithLocalStyling` that will use the local styles. 
```kotlin
// You can custom with the SezzleLogoType, Typeface, TextSize, TextColor
sezzlePromotionButton2.configWithLocalStyling(
    SezzleLogoType.SEZZLE_LOGO_BLACK_TEXT_BLACK,
    ResourcesCompat.getFont(this, R.font.xyz),
    android.R.color.darker_gray,
    R.dimen.sezzle_promotion_size);
```

### Show Sezzle Modal
Show the modal by calling `showSiteModal`

```kotlin
Sezzle.showSiteModal(this@MainActivity, "2.0.1", "fr")
```
- Version and language support can be found at [Sezzle Modals](https://github.com/sezzle/sezzle-js/tree/master/modals)
- Use the [link] (https://media.sezzle.com/shopify-app/assets/sezzle-modal-2.0.0-en.html) to test the
version and language you want to pass in `showSiteModal` to make sure they are valid.
