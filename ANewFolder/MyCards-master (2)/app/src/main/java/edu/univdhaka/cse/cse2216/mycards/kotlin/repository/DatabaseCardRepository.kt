package edu.univdhaka.cse.cse2216.mycards.kotlin.repository

import android.content.ContentValues
import android.content.Context
import edu.univdhaka.cse.cse2216.mycards.kotlin.domain.Card
import edu.univdhaka.cse.cse2216.mycards.kotlin.utils.DbHelper

class DatabaseCardRepository(private val context: Context) : CardRepository {

    override fun listCards(): List<Card> {
        val cards = ArrayList<Card>()

        DbHelper(context).readableDatabase.use { db ->
            db.query("cards", null, null, null, null, null, null).use { cursor ->

                while (cursor.moveToNext()) {
                    cards.add(
                            Card(
                                    cursor.getString(cursor.getColumnIndexOrThrow("type")),
                                    cursor.getString(cursor.getColumnIndexOrThrow("bank_name")),
                                    cursor.getString(cursor.getColumnIndexOrThrow("number")),
                                    cursor.getString(cursor.getColumnIndexOrThrow("cardholder_name")),
                                    cursor.getString(cursor.getColumnIndexOrThrow("expiry_date")),
                                    cursor.getInt(cursor.getColumnIndexOrThrow("cvv")),
                                    cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                            )
                    )
                }
            }
        }

        return cards
    }

    override fun listCardsAsync(resultListener: (List<Card>) -> Unit) {
        resultListener(listCards())
    }

    override fun addCard(card: Card) {
        val values = getContentValues(card)

        DbHelper(context).writableDatabase.use { db -> db.insertOrThrow("cards", null, values) }
    }

    override fun updateCard(card: Card) {
        val values = getContentValues(card)

        DbHelper(context).writableDatabase.use { db -> db.update("cards", values, "_id = ?", arrayOf(Integer.toString(card.id))) }
    }

    override fun deleteCard(card: Card) {
        DbHelper(context).writableDatabase.use { db -> db.delete("cards", "_id = ?", arrayOf(Integer.toString(card.id))) }
    }

    private fun getContentValues(card: Card): ContentValues {
        val values = ContentValues()
        values.put("number", card.number)
        values.put("type", card.type)
        values.put("bank_name", card.bankName)
        values.put("cardholder_name", card.cardholderName)
        values.put("expiry_date", card.expiryDate)
        values.put("cvv", card.cvv)

        return values
    }
}
