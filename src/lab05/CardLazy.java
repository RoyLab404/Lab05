package lab05;

import java.util.EnumMap;
import java.util.Map;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public final class CardLazy {
	private static final Map<Rank, Map<Suit, CardLazy>> CARDS = new EnumMap<>(Rank.class);

	private final Rank aRank;
	private final Suit aSuit;

	private CardLazy(Rank pRank, Suit pSuit) {
		assert pRank != null && pSuit != null;
		aRank = pRank;
		aSuit = pSuit;
	}

	public static CardLazy get(Rank pRank, Suit pSuit) {
		assert pRank != null && pSuit != null;

		Map<Suit, CardLazy> cardsBySuit = CARDS.get(pRank);
		if (cardsBySuit == null) {
			cardsBySuit = new EnumMap<>(Suit.class);
			CARDS.put(pRank, cardsBySuit);
		}

		CardLazy card = cardsBySuit.get(pSuit);
		if (card == null) {
			card = new CardLazy(pRank, pSuit);
			cardsBySuit.put(pSuit, card);
		}
		return card;
	}

	@Override
	public String toString() {
		return aRank + " of " + aSuit;
	}
}
