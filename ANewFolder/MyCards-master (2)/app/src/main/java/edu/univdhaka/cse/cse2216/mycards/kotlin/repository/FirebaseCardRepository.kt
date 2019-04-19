package edu.univdhaka.cse.cse2216.mycards.kotlin.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.univdhaka.cse.cse2216.mycards.kotlin.domain.Card
import java.util.*

/**
 * Detailed docs for using Firebase Firestore: https://firebase.google.com/docs/firestore/
 */
class FirebaseCardRepository : CardRepository {

    companion object {

        private const val DB_COLLECTION_NAME = "cards"
        private const val LOG_TAG = "MyCards"

    }


    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun listCards(): List<Card> {
        throw RuntimeException("Method not implemented. Call the async variation of this method - listCardsAsync()")
    }

    override fun listCardsAsync(onResult: (List<Card>) -> Unit) {
        db.collection(DB_COLLECTION_NAME)
                .get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    val cards = ArrayList<Card>()

                    for (document in queryDocumentSnapshots) {
                        cards.add(document.toObject(Card::class.java))
                    }

                    onResult(cards)
                }
                .addOnFailureListener { e -> Log.e(LOG_TAG, "Failed to retrieve cards.", e) }
    }

    override fun addCard(card: Card) {
        saveCard(card)
    }

    override fun updateCard(card: Card) {
        saveCard(card)
    }

    override fun deleteCard(card: Card) {
        db.collection(DB_COLLECTION_NAME)
                .document(card.number)
                .delete()
                .addOnSuccessListener { Log.i(LOG_TAG, "Card deleted successfully from FireStore: $card") }
                .addOnFailureListener { e -> Log.e(LOG_TAG, "Failed to delete card: $card", e) }
    }

    private fun saveCard(card: Card) {
        db.collection(DB_COLLECTION_NAME)
                .document(card.number)
                .set(card)
                .addOnSuccessListener { Log.i(LOG_TAG, "Card saved successfully to FireStore: $card") }
                .addOnFailureListener { e -> Log.e(LOG_TAG, "Failed to save card: $card", e) }
    }
}
