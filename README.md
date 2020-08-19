Sezzle Android SDK
==================

Sezzle Android SDK allows you to offer Sezzle in your android app.

# Installation

# Usage Overview

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
- `environment` can be set to `Sezzle.Environment.SANDBOX` for test.

# Checkout
Checkout creation is the process in which a customer uses Sezzle to pay for a purchase in your app.
You can create an `Order` object and a `Customer` object and launch the Sezzle checkout using the `startCheckout` function.

```kotlin
    private fun customerModel(): Customer {
        val address = Address("John Doe", "2632 Sezzle Avenue", "Unit 9", "SezzleCity", "MN", "51407", "US", "6157025272")
        return Customer(true, "john.doe@sezzle.com", "John", "Doe", "6157025272", "1993-02-24", address, address)
    }

    private fun orderModel(): Order {
        val amount = Amount(1000, "USD")
        val item = Item("widget", "sku123456", 1, amount)
        val discount = Discount("20% off", amount)
        val metadata = mapOf<String, String>("location_id" to "123", "store_name" to "Downtown Minneapolis", "store_manager" to "Jane Doe")
        return Order("CAPTURE", "ord_joe_1", "sezzle-store - #12749253509255", amount, true, listOf(item), listOf(discount), metadata, amount, amount, "2021-04-23T16:13:44Z")
    }

    Sezzle.startCheckout(this@MainActivity, customerModel(), orderModel())
```


