package edu.univdhaka.cse.cse2216.mycards.java.repository;

import java.util.List;

import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;

public interface CardRepository {

    List<Card> listCards();

    void addCard(Card card);

    void updateCard(Card card);

    void deleteCard(Card card);

}
