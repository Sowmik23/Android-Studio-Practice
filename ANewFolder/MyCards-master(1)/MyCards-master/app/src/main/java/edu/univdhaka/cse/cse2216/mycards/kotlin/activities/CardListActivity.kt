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
import edu.univdhaka.cse.cse2216.mycards.kotlin.domains.Card
import kotlinx.android.synthetic.main.row_card_list.view.*

class CardListActivity : Activity() {

    private var cards = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        retrieveCards()

        prepareListView()
    }

    private fun retrieveCards() {
        // TODO: fetch card list from database/server
        cards.add(Card("Visa", "Standard Chartered Bank Ltd.", "1234 5678 9101 1123", "SHARAFAT MOSHARRAF", "12/21", 123))
        cards.add(Card("Master", "Eastern Bank Ltd.", "1092 1234 4485 2342", "SHARAFAT MOSHARRAF", "01/20", 456))
        cards.add(Card("Discover", "Eastern Bank Ltd.", "4608 123456 7890", "SHARAFAT MOSHARRAF", "08/19", 789))
        cards.add(Card("American Express", "City Bank Ltd.", "1234 567890 3942", "SHARAFAT MOSHARRAF", "12/18", 101))
    }

    private fun prepareListView() {
        val cardsRecyclerView: RecyclerView = findViewById(R.id.card_list)
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


    inner class CardListAdapter(private val cards : ArrayList<Card>) : RecyclerView.Adapter<CardListItemViewHolder>() {

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
