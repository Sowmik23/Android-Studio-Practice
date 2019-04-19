package edu.univdhaka.cse.cse2216.mycards.kotlin.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import edu.univdhaka.cse.cse2216.mycards.R
import edu.univdhaka.cse.cse2216.mycards.kotlin.service.CardService
import edu.univdhaka.cse.cse2216.mycards.kotlin.domain.Card

class CardDetailsActivity: Activity() {

    private lateinit var card: Card

    private lateinit var cardTypeLabel: TextView
    private lateinit var bankNameLabel: TextView
    private lateinit var cardNumberLabel: TextView
    private lateinit var cardholderNameLabel: TextView
    private lateinit var expiryDateLabel: TextView
    private lateinit var cvvLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.card_details)

        setContentView(R.layout.activity_card_details)

        bindWidgets()

        card = intent.extras?.get("card") as Card

        bindValuesToLabels()
    }

    private fun bindWidgets() {
        cardTypeLabel = findViewById(R.id.card_type_label)
        bankNameLabel = findViewById(R.id.bank_name_label)
        cardNumberLabel = findViewById(R.id.card_number_label)
        cardholderNameLabel = findViewById(R.id.cardholder_name_label)
        expiryDateLabel = findViewById(R.id.expiry_date_label)
        cvvLabel = findViewById(R.id.cvv_label)
    }

    private fun bindValuesToLabels() {
        cardTypeLabel.text = card.type
        bankNameLabel.text = card.bankName
        cardNumberLabel.text = card.number
        cardholderNameLabel.text = card.cardholderName
        expiryDateLabel.text = card.expiryDate
        cvvLabel.text = card.cvv.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_card_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                onEdit()
                true
            }
            R.id.action_delete -> {
                onDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onEdit() {
        startActivity(Intent(this, AddEditCardActivity::class.java).putExtra("card", card))
    }

    private fun onDelete() {
        AlertDialog.Builder(this)
                .setTitle(R.string.delete_card_confirmation_title)
                .setMessage(R.string.delete_card_confirmation_message)
                .setPositiveButton(R.string.delete){ _, _ -> deleteCard() }
                .setNegativeButton(R.string.cancel){ _, _ -> }
                .create()
                .show()
    }

    private fun deleteCard() {
        Log.d(getString(R.string.app_name), "Delete card: " + card.toString())

        val cardService = CardService(this)
        cardService.deleteCard(card)

        Toast.makeText(this, R.string.card_delete_success, Toast.LENGTH_SHORT).show()

        finish()
    }
}
