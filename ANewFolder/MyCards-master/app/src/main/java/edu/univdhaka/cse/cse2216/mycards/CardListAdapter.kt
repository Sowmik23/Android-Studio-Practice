package edu.univdhaka.cse.cse2216.mycards

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class CardListAdapter(private val cards : ArrayList<Card>, private val context: Context)
    : RecyclerView.Adapter<CardListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cards_row, parent, false)

        return CardListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardListItemViewHolder, position: Int) {
        val card = cards[position]

        holder.cardNumber.text = card.number
        holder.cardType.text = card.type

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, CardDetails::class.java).putExtra("card", card))
        }
    }
}
