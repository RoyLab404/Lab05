package lab05;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public class Main {
	public static void main(String[] args) {
		int soLaWarmUp = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				soLaWarmUp++;
			}
		}
		System.out.println("Warm-up (bỏ qua): " + soLaWarmUp + " lá");

		long batDauMs = System.currentTimeMillis();
		long batDauNs = System.nanoTime();
		int soLaLazy = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				CardLazy.get(rank, suit);
				soLaLazy++;
			}
		}
		long thoiGianLazyMs = System.currentTimeMillis() - batDauMs;
		long thoiGianLazyUs = (System.nanoTime() - batDauNs) / 1_000;

		batDauMs = System.currentTimeMillis();
		batDauNs = System.nanoTime();
		int soLaEager = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				CardEager.get(rank, suit);
				soLaEager++;
			}
		}
		long thoiGianEagerMs = System.currentTimeMillis() - batDauMs;
		long thoiGianEagerUs = (System.nanoTime() - batDauNs) / 1_000;

		System.out.println("Lazy: " + soLaLazy + " lá, " + thoiGianLazyMs + " ms (" + thoiGianLazyUs + " µs)");
		System.out.println("Eager: " + soLaEager + " lá, " + thoiGianEagerMs + " ms (" + thoiGianEagerUs + " µs)");
	}
}
