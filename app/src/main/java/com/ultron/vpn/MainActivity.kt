
package com.ultron.vpn

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {

    private val green = Color.rgb(0, 255, 100)
    private val dark = Color.rgb(10, 10, 10)
    private var connected = false
    private var selectedServer = "Netherlands"
    private lateinit var status: TextView
    private lateinit var connectButton: TextView
    private lateinit var serverText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = dark
        window.navigationBarColor = dark
        showMainScreen()
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private fun text(
        value: String,
        size: Float,
        color: Int
    ): TextView {
        return TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            gravity = Gravity.CENTER
        }
    }

    private fun showMainScreen() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(22), dp(18), dp(22), dp(20))
            setBackgroundColor(dark)
        }

        val top = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val logo = text("ULTRON", 25f, green)
        logo.typeface = Typeface.DEFAULT_BOLD

        val menu = text("⋮", 34f, Color.WHITE)
        menu.setOnClickListener { showMenu() }

        top.addView(logo, LinearLayout.LayoutParams(0, dp(55), 1f))
        top.addView(menu, LinearLayout.LayoutParams(dp(45), dp(55)))
        root.addView(top)

        root.addView(
            text("SECURE CONNECTION", 12f, Color.GRAY),
            LinearLayout.LayoutParams(-1, dp(35))
        )

        status = text("DISCONNECTED", 16f, Color.LTGRAY)
        root.addView(status, LinearLayout.LayoutParams(-1, dp(45)))

        connectButton = text("CONNECT", 21f, Color.BLACK).apply {
            typeface = Typeface.DEFAULT_BOLD
            background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(green)
            }
            elevation = dp(12).toFloat()
            setOnClickListener { toggleConnection() }
        }

        val buttonHolder = FrameLayout(this).apply {
            gravity = Gravity.CENTER
        }
        buttonHolder.addView(
            connectButton,
            FrameLayout.LayoutParams(dp(190), dp(190), Gravity.CENTER)
        )

        root.addView(
            buttonHolder,
            LinearLayout.LayoutParams(-1, dp(225))
        )

        root.addView(
            text("YOUR SERVER", 12f, Color.GRAY),
            LinearLayout.LayoutParams(-1, dp(35))
        )

        serverText = text("🌍  $selectedServer     ›", 18f, Color.WHITE)
        serverText.background = panel()
        serverText.setOnClickListener { showServers() }

        root.addView(
            serverText,
            LinearLayout.LayoutParams(-1, dp(58)).apply {
                bottomMargin = dp(15)
            }
        )

        val promo = Button(this).apply {
            text = "PROMO CODE"
            textSize = 15f
            setTextColor(green)
            setBackgroundColor(Color.TRANSPARENT)
            setOnClickListener { showPromo() }
        }
        root.addView(promo)

        root.addView(
            text("ULTRON VPN  •  VERSION 1.0", 11f, Color.GRAY),
            LinearLayout.LayoutParams(-1, dp(55))
        )

        setContentView(root)
    }

    private fun panel(): GradientDrawable {
        return GradientDrawable().apply {
            setColor(Color.rgb(25, 25, 25))
            cornerRadius = dp(14).toFloat()
            setStroke(dp(1), Color.rgb(55, 55, 55))
        }
    }

    private fun toggleConnection() {
        if (connected) {
            connected = false
            status.text = "DISCONNECTED"
            status.setTextColor(Color.LTGRAY)
            connectButton.text = "CONNECT"
            Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show()
            return
        }

        status.text = "CONNECTING..."
        connectButton.text = "..."
        connectButton.isEnabled = false

        Handler(Looper.getMainLooper()).postDelayed({
            connected = true
            status.text = "CONNECTED (DEMO)"
            status.setTextColor(green)
            connectButton.text = "DISCONNECT"
            connectButton.isEnabled = true
            Toast.makeText(
                this,
                "Demo connection successful!",
                Toast.LENGTH_SHORT
            ).show()
        }, 2000)
    }

    private fun showServers() {
        val servers = arrayOf(
            "Netherlands",
            "Germany",
            "United States",
            "France",
            "United Kingdom"
        )

        AlertDialog.Builder(this)
            .setTitle("SELECT SERVER")
            .setItems(servers) { _, which ->
                selectedServer = servers[which]
                serverText.text = "🌍  $selectedServer     ›"
            }
            .setNegativeButton("CANCEL", null)
            .show()
    }

    private fun showPromo() {
        val input = EditText(this).apply {
            hint = "Enter promo code"
            setSingleLine(true)
        }

        AlertDialog.Builder(this)
            .setTitle("PROMO CODE")
            .setView(input)
            .setPositiveButton("ACTIVATE") { _, _ ->
                val code = input.text.toString().trim()
                Toast.makeText(
                    this,
                    if (code.isNotEmpty())
                        "Demo: code received"
                    else "Enter a code",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("CANCEL", null)
            .show()
    }

    private fun showMenu() {
        val options = arrayOf(
            "About ULTRON",
            "Help",
            "Buy PRO",
            "Settings"
        )

        AlertDialog.Builder(this)
            .setTitle("ULTRON MENU")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showMessage(
                        "About ULTRON",
                        "ULTRON VPN\nVersion 1.0\nYour personal VPN project."
                    )
                    1 -> showMessage(
                        "Help",
                        "Help is not available yet."
                    )
                    2 -> showMessage(
                        "ULTRON PRO",
                        "PRO is coming soon!"
                    )
                    3 -> showMessage(
                        "Settings",
                        "More settings are coming soon."
                    )
                }
            }
            .show()
    }

    private fun showMessage(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}

