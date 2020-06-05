public class Max {
    int type;       // 0 - store, 1 - customer. 0 is deltaI (array of rows deltas). 1 is deltaJ (array of cols deltas)
    int X;          // position of minimum value in row or column
    int value;      // value of max delta
    int valueAtMatr; // value referring to the value at col/row
    Max(int type) {
        this.type = type;
    }
}

/*
88 6 92
32 62 54 10 78
12 13 11 8 10
9 10 7 5 8
10 8 7 7 8
*/

/*
51 46 99
74 23 90 49 34
13 14 12 9 11
16 17 14 12 15
18 16 15 15 16
*/

/*
10 20 30
15 20 25
5 3 1
3 2 4
4 1 2
*/

