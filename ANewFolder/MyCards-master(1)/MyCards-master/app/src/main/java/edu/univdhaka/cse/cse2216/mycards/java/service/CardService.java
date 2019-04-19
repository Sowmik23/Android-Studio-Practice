package edu.univdhaka.cse.cse2216.mycards.java.service;

import android.content.Context;

import java.util.List;

import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;
import edu.univdhaka.cse.cse2216.mycards.java.repository.CardRepository;
import edu.univdhaka.cse.cse2216.mycards.java.repository.DatabaseCardRepository;

public class CardService {

    private CardRepository repository;

    public CardService(Context context) {
        repository = new DatabaseCardRepository(context);
    }

    public List<Card> listCards() {
        return repository.listCards();
    }

    public void addCard(Card card) {
        repository.addCard(card);
    }

    public void updateCard(Card card) {
        repository.updateCard(card);
    }

    public void deleteCard(Card card) {
        repository.deleteCard(card);
    }
}
