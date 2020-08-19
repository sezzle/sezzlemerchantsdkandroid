package com.sezzle.merchantsdkexample1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sezzle.sezzlemerchantsdkandroid.Sezzle
import com.sezzle.sezzlemerchantsdkandroid.model.*

class MainActivity : AppCompatActivity(), Sezzle.CheckoutCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Sezzle.startCheckout(this@MainActivity, customerModel(), orderModel())
        }
    }

    private fun customerModel(): Customer {
        val address = Address("Rishi Mukherjee", "2640 Nicollet Avenue", "Unit 403", "Minneapolis", "MN", "55408", "US", "6127026972")
        return Customer(true, "rishi.mukherjee@sezzle.com", "Rishi", "Mukherjee", "6127026972", "1993-02-24", address, address)
    }

    private fun orderModel(): Order {
        val amount = Amount(1000, "USD")
        val item = Item("widget", "sku123456", 1, amount)
        val discount = Discount("20% off", amount)
        val metadata = mapOf<String, String>("location_id" to "123", "store_name" to "Downtown Minneapolis", "store_manager" to "Jane Doe")
        return Order("CAPTURE", "ord_rishi_1", "sezzle-store - #12749253509255", amount, true, listOf(item), listOf(discount), metadata, amount, amount, "2021-04-23T16:13:44Z")
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