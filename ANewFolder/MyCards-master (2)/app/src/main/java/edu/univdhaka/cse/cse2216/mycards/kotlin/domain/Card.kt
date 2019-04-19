package edu.univdhaka.cse.cse2216.mycards.kotlin.domain

import java.io.Serializable

data class Card(
        var type: String = "",
        var bankName: String = "",
        var number: String = "",
        var cardholderName: String = "",
        var expiryDate: String = "",
        var cvv: Int = 0,
        var id: Int = 0
) : Serializable
