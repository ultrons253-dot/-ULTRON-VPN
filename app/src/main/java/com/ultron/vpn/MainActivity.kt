
package com.ultron.vpn

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)
        layout.setBackgroundColor(Color.BLACK)

        val title = TextView(this)
        title.text = "ULTRON VPN"
        title.textSize = 32f
        title.setTextColor(Color.GREEN)
        title.gravity = Gravity.CENTER

        val status = TextView(this)
        status.text = "DISCONNECTED"
        status.textSize = 18f
        status.setTextColor(Color.WHITE)
        status.gravity = Gravity.CENTER

        val connect = Button(this)
        connect.text = "CONNECT"

        connect.setOnClickListener {
            status.text =
                if (status.text == "DISCONNECTED") {
                    "CONNECTED (DEMO)"
                } else {
                    "DISCONNECTED"
                }
        }

        layout.addView(title)
        layout.addView(status)
        layout.addView(connect)

        setContentView(layout)
    }
}
