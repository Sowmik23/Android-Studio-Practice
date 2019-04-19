package edu.univdhaka.cse.cse2216.mycards

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class CardDetails: Activity() {

    lateinit var card: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)

        val cardNumberLabel = findViewById<TextView>(R.id.card_number)
        val cardTypeLabel = findViewById<TextView>(R.id.card_type)

        card = intent.extras?.get("card") as Card

        cardNumberLabel.text = card.number
        cardTypeLabel.text = card.type
    }
}
