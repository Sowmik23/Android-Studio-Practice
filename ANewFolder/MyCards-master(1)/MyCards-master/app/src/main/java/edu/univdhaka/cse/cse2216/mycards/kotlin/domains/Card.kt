package edu.univdhaka.cse.cse2216.mycards.kotlin.domains

import java.io.Serializable

data class Card(
        val type: String,
        val bankName: String,
        val number: String,
        val cardholderName: String,
        val expiryDate: String,
        val cvv: Int
) : Serializable
