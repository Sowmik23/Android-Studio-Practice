package edu.univdhaka.cse.cse2216.mycards.kotlin.repository

import edu.univdhaka.cse.cse2216.mycards.kotlin.domain.Card

interface CardRepository {

    fun listCards(): List<Card>

    fun listCardsAsync(onResult: (List<Card>) -> Unit)

    fun addCard(card: Card)

    fun updateCard(card: Card)

    fun deleteCard(card: Card)

}
