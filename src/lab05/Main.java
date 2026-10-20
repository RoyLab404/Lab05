package lab05;

import java.util.function.IntSupplier;

import e3.chapter3.Rank;
import e3.chapter3.Suit;

public class Main {
	private static void doMotLuot(String ten, IntSupplier layCaBoBai) {
		long batDauMs = System.currentTimeMillis();
		long batDauNs = System.nanoTime();
		int soLa = layCaBoBai.getAsInt();
		long thoiGianMs = System.currentTimeMillis() - batDauMs;
		long thoiGianUs = (System.nanoTime() - batDauNs) / 1_000;
		System.out.println(ten + ": " + soLa + " lá, " + thoiGianMs + " ms (" + thoiGianUs + " µs)");
	}

	private static int layTatCaLazy() {
		int soLa = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				CardLazy.get(rank, suit);
				soLa++;
			}
		}
		return soLa;
	}

	private static int layTatCaEager() {
		int soLa = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				CardEager.get(rank, suit);
				soLa++;
			}
		}
		return soLa;
	}

	public static void main(String[] args) {
		int soLaWarmUp = 0;
		for (Rank rank : Rank.values()) {
			for (Suit suit : Suit.values()) {
				soLaWarmUp++;
			}
		}
		System.out.println("Warm-up (bỏ qua): " + soLaWarmUp + " lá");
		doMotLuot("Khởi tạo muộn (lazy)", Main::layTatCaLazy);
		doMotLuot("Khởi tạo sẵn (eager)", Main::layTatCaEager);
	}
}
