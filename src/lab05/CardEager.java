package lab05;

import java.util.EnumMap;
import java.util.Map;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public final class CardEager {
	private static final Map<Rank, Map<Suit, CardEager>> CARDS = new EnumMap<>(Rank.class);

	static {
		for (Rank rank : Rank.values()) {
			Map<Suit, CardEager> cardsBySuit = new EnumMap<>(Suit.class);
			for (Suit suit : Suit.values()) {
				cardsBySuit.put(suit, new CardEager(rank, suit));
			}
			CARDS.put(rank, cardsBySuit);
		}
	}

	private final Rank aRank;
	private final Suit aSuit;

	private CardEager(Rank pRank, Suit pSuit) {
		aRank = pRank;
		aSuit = pSuit;
	}

	public static CardEager get(Rank pRank, Suit pSuit) {
		assert pRank != null && pSuit != null;
		return CARDS.get(pRank).get(pSuit);
	}

	@Override
	public String toString() {
		return aRank + " of " + aSuit;
	}
}
