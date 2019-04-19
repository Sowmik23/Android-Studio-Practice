package edu.univdhaka.cse.cse2216.mycards.java.service;

import android.content.Context;

import java.util.List;

import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;
import edu.univdhaka.cse.cse2216.mycards.java.repository.CardRepository;
import edu.univdhaka.cse.cse2216.mycards.java.repository.DatabaseCardRepository;
import edu.univdhaka.cse.cse2216.mycards.java.repository.FirebaseCardRepository;

public class CardService {

    private static final boolean USE_SQLITE_REPOSITORY = false;

    private CardRepository repository;

    public CardService(Context context) {
        repository = USE_SQLITE_REPOSITORY ? new DatabaseCardRepository(context) : new FirebaseCardRepository();
    }

    public List<Card> listCards() {
        return repository.listCards();
    }

    public void listCardsAsync(CardRepository.OnResultListener<List<Card>> resultListener) {
        repository.listCardsAsync(resultListener);
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
