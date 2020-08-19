Sezzle Android SDK
==================

Sezzle Android SDK allows you to offer Sezzle in your android app.

# Installation

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





