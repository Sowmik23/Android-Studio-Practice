package edu.univdhaka.cse.cse2216.mycards.kotlin.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import edu.univdhaka.cse.cse2216.mycards.R
import edu.univdhaka.cse.cse2216.mycards.kotlin.domain.Card
import edu.univdhaka.cse.cse2216.mycards.kotlin.service.CardService
import kotlinx.android.synthetic.main.row_card_list.view.*



class CardListActivity : Activity() {

    private var cards = ArrayList<Card>()

    private lateinit var cardsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_card_list)

        prepareListView()
    }

    override fun onStart() {
        super.onStart()

        retrieveCards()
    }

    private fun retrieveCards() {
        val cardService = CardService(this)
        cardService.listCardsAsync {
            cards = it as ArrayList<Card>

            val adapter = cardsRecyclerView.adapter as CardListAdapter
            adapter.cards = cards
            adapter.notifyDataSetChanged()
        }
    }

    private fun prepareListView() {
        cardsRecyclerView = findViewById(R.id.card_list)
        cardsRecyclerView.layoutManager = LinearLayoutManager(this)
        cardsRecyclerView.adapter = CardListAdapter(cards)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_card_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, AddEditCardActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    inner class CardListAdapter(var cards : ArrayList<Card>) : RecyclerView.Adapter<CardListItemViewHolder>() {

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_card_list, parent, false)

            return CardListItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: CardListItemViewHolder, position: Int) {
            val card = cards[position]

            holder.cardNumber.text = card.number
            holder.cardType.text = card.type
            holder.cardTypeLogo.setImageResource(
                    when (card.type) {
                        "Visa" -> R.mipmap.ic_visa
                        "Master" -> R.mipmap.ic_master
                        "Discover" -> R.mipmap.ic_discover
                        "American Express" -> R.mipmap.ic_amex
                        else -> R.mipmap.ic_launcher
                    }
            )

            holder.itemView.setOnClickListener {
                startActivity(Intent(applicationContext, CardDetailsActivity::class.java).putExtra("card", card))
            }
        }
    }


    class CardListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val cardNumber: TextView = view.card_number
        val cardType: TextView = view.card_type
        val cardTypeLogo: ImageView = view.card_type_logo
    }
}
