package edu.univdhaka.cse.cse2216.mycards

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.cards_row.view.*

class CardListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val cardNumber = view.card_number
    val cardType = view.card_type
}
