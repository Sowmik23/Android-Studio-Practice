package edu.univdhaka.cse.cse2216.mycards.kotlin.service

import android.content.Context

import edu.univdhaka.cse.cse2216.mycards.kotlin.domain.Card
import edu.univdhaka.cse.cse2216.mycards.kotlin.repository.CardRepository
import edu.univdhaka.cse.cse2216.mycards.kotlin.repository.DatabaseCardRepository
import edu.univdhaka.cse.cse2216.mycards.kotlin.repository.FirebaseCardRepository

class CardService(context: Context) {

    companion object {

        private val USE_SQLITE_REPOSITORY = true

    }


    private val repository: CardRepository

    init {
        repository = if (USE_SQLITE_REPOSITORY) DatabaseCardRepository(context) else FirebaseCardRepository()
    }

    fun listCards(): List<Card> {
        return repository.listCards()
    }

    fun listCardsAsync(onResult: (List<Card>) -> Unit) {
        repository.listCardsAsync(onResult)
    }

    fun addCard(card: Card) {
        repository.addCard(card)
    }

    fun updateCard(card: Card) {
        repository.updateCard(card)
    }

    fun deleteCard(card: Card) {
        repository.deleteCard(card)
    }
}
