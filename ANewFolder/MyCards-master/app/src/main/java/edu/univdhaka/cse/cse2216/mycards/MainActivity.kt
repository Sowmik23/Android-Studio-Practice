package edu.univdhaka.cse.cse2216.mycards

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : Activity() {

    lateinit var listsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cards: ArrayList<Card> = ArrayList();
        cards.add(Card("1234 5678 9101 1123", "VISA"))
        cards.add(Card("1092 1234 4485 2342", "Master"))
        cards.add(Card("4608 123456 7890", "DCI"))

        listsRecyclerView = findViewById(R.id.card_list)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        listsRecyclerView.adapter = CardListAdapter(cards, this)
    }
}
