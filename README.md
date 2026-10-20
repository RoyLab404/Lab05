# Lab 05: Exercise 8 - Flyweight khởi tạo muộn

| | |
|---|---|
| **Họ và tên** | Huỳnh Ngọc Khang |
| **MSSV** | 4651050116 |
| **Lớp** | CNTT46D |
| **Bài** | Lab 05 - Exercise 8 |

---

## 1. Đề bài

Exercise 8 yêu cầu sửa Flyweight của lá bài để mỗi lá chỉ được tạo khi cần. Bài cũng yêu cầu đo thời gian lấy đủ 52 lá rồi so sánh với cách khởi tạo sẵn.

## 2. Giải pháp

Em cài hai lớp dùng chung ý tưởng Flyweight, nhưng tạo lá ở thời điểm khác nhau:

| Lớp | Cách tạo | Thời điểm tạo 52 lá |
|---|---|---|
| `CardEager` | Khởi tạo sẵn | Khi lớp được nạp, trong khối `static` |
| `CardLazy` | Khởi tạo muộn | Tạo từng lá khi `get(rank, suit)` được gọi lần đầu |

Em dùng `EnumMap` lồng nhau để lưu các lá. Khóa của từng map là `Rank` hoặc `Suit`, đều là enum.

```mermaid
classDiagram
    class CardEager {
        -CARDS : Map~Rank, Map~Suit, CardEager~~$
        -aRank : Rank
        -aSuit : Suit
        -CardEager(Rank, Suit)
        +get(Rank, Suit) CardEager$
    }
    class CardLazy {
        -CARDS : Map~Rank, Map~Suit, CardLazy~~$
        -aRank : Rank
        -aSuit : Suit
        -CardLazy(Rank, Suit)
        +get(Rank, Suit) CardLazy$
    }
    class Rank {
        <<enumeration>>
    }
    class Suit {
        <<enumeration>>
    }
    CardEager --> Rank
    CardEager --> Suit
    CardLazy --> Rank
    CardLazy --> Suit
```

Mã nguồn của hai cách tạo lá và chương trình chạy nằm ở [CardEager.java](src/lab05/CardEager.java), [CardLazy.java](src/lab05/CardLazy.java) và [Main.java](src/lab05/Main.java).

## 3. Chương trình đo thời gian

`Main` duyệt các enum một lượt warm-up, rồi lấy đủ các tổ hợp `Rank` và `Suit` cho từng cách. Có 13 rank và 4 suit, nên mỗi lượt lấy 52 lá. Kết quả được in theo ms từ `System.currentTimeMillis()` và µs từ `System.nanoTime()`.

Chạy project bằng Java 25:

```sh
javac --release 25 -d bin src/e3/chapter3/*.java src/lab05/*.java
java -cp bin lab05.Main
```

## 4. Kết quả

Bảng dưới ghi lại 8 lần chạy riêng của `Main`. Mỗi lần, chương trình đo lazy trước rồi eager. Trung bình và trung vị được tính từ các số µs, sau đó làm tròn đến số nguyên gần nhất.

| Lần | Thứ tự chạy | Eager | Lazy |
|:---:|---|---:|---:|
| 1 | lazy → eager | 1,058 µs (1 ms) | 1,987 µs (2 ms) |
| 2 | lazy → eager | 442 µs (0 ms) | 1,517 µs (1 ms) |
| 3 | lazy → eager | 430 µs (0 ms) | 1,676 µs (2 ms) |
| 4 | lazy → eager | 488 µs (1 ms) | 1,685 µs (2 ms) |
| 5 | lazy → eager | 475 µs (0 ms) | 1,811 µs (2 ms) |
| 6 | lazy → eager | 486 µs (0 ms) | 1,824 µs (2 ms) |
| 7 | lazy → eager | 461 µs (0 ms) | 1,661 µs (2 ms) |
| 8 | lazy → eager | 463 µs (1 ms) | 1,422 µs (1 ms) |
| | **Trung bình** | **538 µs** | **1,698 µs** |
| | **Trung vị** | **469 µs** | **1,681 µs** |

Một lượt chạy `Main` trên JVM 25.0.2 cho kết quả:

```text
Warm-up (bỏ qua): 52 lá
Khởi tạo muộn (lazy): 52 lá, 2 ms (1987 µs)
Khởi tạo sẵn (eager): 52 lá, 1 ms (1058 µs)
```

## 5. Nhận xét và kết luận

Trong tám lượt đã chạy, eager có thời gian thấp hơn lazy. Tuy nhiên, `Main` luôn đo lazy trước, rồi eager, nên thứ tự và trạng thái JVM có thể ảnh hưởng kết quả.

Trong mỗi tiến trình, lazy tạo các lá khi vòng lặp yêu cầu chúng; eager tạo sẵn 52 lá khi lớp được nạp lần đầu. Bảng này cho thấy thời gian của eager thấp hơn trên máy chạy thử. Nếu chương trình chỉ dùng một phần bộ bài, lazy có thể tránh tạo những lá không cần đến.

Mỗi lượt đo rất ngắn nên kết quả còn thay đổi theo JVM và tải máy. `currentTimeMillis()` chỉ có độ phân giải đến ms, vì vậy số ms có thể không khớp chính xác với số µs. Các số liệu này chỉ để tham khảo.

## 6. Cấu trúc project

```text
Lab05
├── src
│   ├── e3/chapter3
│   │   ├── Rank.java
│   │   └── Suit.java
│   └── lab05
│       ├── CardEager.java
│       ├── CardLazy.java
│       └── Main.java
└── README.md
```

## Tham khảo

- [Đề bài Exercise 8](https://github.com/prmr/DesignBook/blob/master/exercises/e-chapter4.md).

Nguồn `Rank` và `Suit`: Hai file thuộc package `e3.chapter3` được sử dụng trực tiếp từ mã nguồn của cuốn sách *Introduction to Software Design with Java* do Martin P. Robillard biên soạn. Mã nguồn được công khai trên GitHub tại https://github.com/prmr/DesignBook, trong thư mục `code-e3/e3/chapter3`.
