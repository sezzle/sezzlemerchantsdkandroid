package com.sezzle.merchantsdkexample1

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sezzle.sezzlemerchantsdkandroid.ModalActivity
import com.sezzle.sezzlemerchantsdkandroid.Sezzle
import com.sezzle.sezzlemerchantsdkandroid.SezzleLogoType
import com.sezzle.sezzlemerchantsdkandroid.SezzlePromotionButton
import com.sezzle.sezzlemerchantsdkandroid.model.*


class MainActivity : AppCompatActivity(), Sezzle.CheckoutCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.fab).setOnClickListener { view ->
            Sezzle.startCheckout(this@MainActivity, customerModel(), orderModel())
        }

        findViewById<Button>(R.id.modal_button).setOnClickListener { view ->
            Sezzle.showSiteModal(this@MainActivity, "2.0.0", "en")
        }

        val sezzlePromo = findViewById<SezzlePromotionButton>(R.id.promo)
        sezzlePromo.setLabel("or 4 interest-free payments with {sezzle_logo}")
        sezzlePromo.setOnClickListener { view ->
            Sezzle.showSiteModal(this@MainActivity, "2.0.1", "fr")
        }


        val sezzlePromotionButton2 = SezzlePromotionButton(this)
        val lp = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(0, 20, 0, 0)
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.BELOW, R.id.promo)
        val id = View.generateViewId()
        sezzlePromotionButton2.layoutParams = lp
        sezzlePromotionButton2.id = id
        sezzlePromotionButton2.configWithLocalStyling(SezzleLogoType.SEZZLE_LOGO_BLACK_TEXT_BLACK)
        sezzlePromotionButton2.setLabel("or 4 interest-free payments with {sezzle_logo}")
        findViewById<RelativeLayout>(R.id.relative_layout).addView(sezzlePromotionButton2)
    }

    private fun customerModel(): Customer {
        val address = Address(
            "John Doe",
            "2610 Joes Avenue",
            "Unit 81",
            "Minneapolis",
            "MN",
            "55308",
            "US",
            "6127024309"
        )
        return Customer(
            true,
            "rishi.mukherjee@sezzle.com",
            "Rishi",
            "Mukherjee",
            "6127024309",
            "1993-02-24",
            address,
            address
        )
    }

    private fun orderModel(): Order {
        val amount = Amount(1000, "USD")
        val item = Item("widget", "sku123456", 1, amount)
        val discount = Discount("20% off", amount)
        val metadata = mapOf<String, String>(
            "location_id" to "123",
            "store_name" to "Downtown Minneapolis",
            "store_manager" to "Jane Doe"
        )
        return Order(
            "AUTH", "ord_rishi_1", "sezzle-store - #12749253509255", amount, true, listOf(
                item
            ), listOf(discount), metadata, amount, amount, "2021-04-23T16:13:44Z"
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Sezzle.handleCheckoutData(this, requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    // - Sezzle.CheckoutCallbacks
    override fun onSezzleCheckoutError(message: String?) {
        Toast.makeText(this, "Checkout Error: $message", Toast.LENGTH_LONG).show()
    }

    override fun onSezzleCheckoutCancelled() {
        Toast.makeText(this, "Checkout Cancelled", Toast.LENGTH_LONG).show()
    }

    override fun onSezzleCheckoutSuccess(token: String) {
        Toast.makeText(this, "Checkout token: $token", Toast.LENGTH_LONG).show()
    }
}