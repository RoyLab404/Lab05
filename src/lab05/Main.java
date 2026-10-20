package lab05;

import java.util.function.BiFunction;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public class Main {
	private static void measureCards(String label, BiFunction<Rank, Suit, Object> cardGetter) {
		long startMillis = System.currentTimeMillis();
		long startNanos = System.nanoTime();
		int cardCount = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				cardGetter.apply(rank, suit);
				cardCount++;
			}
		}
		long elapsedMillis = System.currentTimeMillis() - startMillis;
		long elapsedMicros = (System.nanoTime() - startNanos) / 1_000;
		System.out.println(label + ": " + cardCount + " lá, " + elapsedMillis + " ms (" + elapsedMicros + " µs)");
	}

	public static void main(String[] args) {
		int cardCount = Rank.values().length * Suit.values().length;
		System.out.println("Warm-up (bỏ qua): " + cardCount + " lá");
		measureCards("Khởi tạo muộn (lazy)", CardLazy::get);
		measureCards("Khởi tạo sẵn (eager)", CardEager::get);
	}
}
