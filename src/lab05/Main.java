package lab05;

import java.util.function.BiFunction;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public class Main {
	private static void doMotLuot(String ten, BiFunction<Rank, Suit, Object> layLa) {
		long batDauMs = System.currentTimeMillis();
		long batDauNs = System.nanoTime();
		int soLa = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				layLa.apply(rank, suit);
				soLa++;
			}
		}
		long thoiGianMs = System.currentTimeMillis() - batDauMs;
		long thoiGianUs = (System.nanoTime() - batDauNs) / 1_000;
		System.out.println(ten + ": " + soLa + " lá, " + thoiGianMs + " ms (" + thoiGianUs + " µs)");
	}

	public static void main(String[] args) {
		doMotLuot("Warm-up (bỏ qua)", (rank, suit) -> rank);
		doMotLuot("Khởi tạo muộn (lazy)", CardLazy::get);
		doMotLuot("Khởi tạo sẵn (eager)", CardEager::get);
	}
}
