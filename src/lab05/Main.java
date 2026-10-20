package lab05;

import java.util.function.IntSupplier;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public class Main {
	private static void measureCards(String label, IntSupplier getAllCards) {
		long startMillis = System.currentTimeMillis();
		long startNanos = System.nanoTime();
		int cardCount = getAllCards.getAsInt();
		long elapsedMillis = System.currentTimeMillis() - startMillis;
		long elapsedMicros = (System.nanoTime() - startNanos) / 1_000;
		System.out.println(label + ": " + cardCount + " lá, " + elapsedMillis + " ms (" + elapsedMicros + " µs)");
	}

	private static int getAllLazyCards() {
		int cardCount = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				CardLazy.get(rank, suit);
				cardCount++;
			}
		}
		return cardCount;
	}

	private static int getAllEagerCards() {
		int cardCount = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				CardEager.get(rank, suit);
				cardCount++;
			}
		}
		return cardCount;
	}

	public static void main(String[] args) {
		int cardCount = Rank.values().length * Suit.values().length;
		System.out.println("Khởi động: " + cardCount + " lá");
		measureCards("Khởi tạo muộn (lazy)", Main::getAllLazyCards);
		measureCards("Khởi tạo sẵn (eager)", Main::getAllEagerCards);
	}
}
