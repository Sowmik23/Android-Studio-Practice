package edu.univdhaka.cse.cse2216.mycards.java.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.univdhaka.cse.cse2216.mycards.java.domain.Card;

/**
 * Detailed docs for using Firebase Firestore: https://firebase.google.com/docs/firestore/
 */
public class FirebaseCardRepository implements CardRepository {

    private static final String DB_COLLECTION_NAME = "cards";
    private static final String LOG_TAG = "MyCards";

    private FirebaseFirestore db;

    public FirebaseCardRepository() {
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public List<Card> listCards() {
        throw new RuntimeException("Method not implemented. Call the async variation of this method - listCardsAsync()");
    }

    @Override
    public void listCardsAsync(final OnResultListener<List<Card>> resultListener) {
        db.collection(DB_COLLECTION_NAME)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<Card> cards = new ArrayList<>();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            cards.add(document.toObject(Card.class));
                        }

                        resultListener.onResult(cards);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Failed to retrieve cards.", e);
                    }
                });
    }

    @Override
    public void addCard(final Card card) {
        saveCard(card);
    }

    @Override
    public void updateCard(Card card) {
        saveCard(card);
    }

    @Override
    public void deleteCard(final Card card) {
        db.collection(DB_COLLECTION_NAME)
                .document(card.getNumber())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(LOG_TAG, "Card deleted successfully from FireStore: " + card);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Failed to delete card: " + card, e);
                    }
                });
    }

    private void saveCard(final Card card) {
        db.collection(DB_COLLECTION_NAME)
                .document(card.getNumber())
                .set(card)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(LOG_TAG, "Card saved successfully to FireStore: " + card);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Failed to save card: " + card, e);
                    }
                });
    }
}
