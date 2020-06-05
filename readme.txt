****
This is a low quality fast-written Vogel's approximation method (VAM).
This is one of the methods for obtaining the solution to the transport problem.
Solution is NOT optimized by potential method.
****
Это быстро написанный алгоритм метода Фогеля (или аппроксимасия Фогеля) -
один из методов получения начального решения транспортной задачи.
Решение НЕ оптимизировано с помощью метода потенциалов.
****

Input data is located in 'src/main/resources/matrix.txt'

Input:
1. The first line contains suppliers' cells
2. The second line contains demanders' cells
3. Other lines are table of costs/times/etc...

Ввод:
1. Первая строка - запасы
2. Вторая - потребности
3. После них идёт таблица стоимостей/времени

****
Output/Вывод:

> Task :Main.main()


	Table:                                  A       D
	13 	14 	12 	9 	11 	51	2
	16 	17 	14 	12 	15 	46	2
	18 	16 	15 	15 	16 	99	0
	0 	0 	0 	0 	0 	74	0
B 	74 	23 	90 	49 	34
D 	3 	2 	2 	3 	4

	Table:                                  A       D
	13 	14 	12 	9 	0 	17	3
	16 	17 	14 	12 	0 	46	2
	18 	16 	15 	15 	0 	99	0
	0 	0 	0 	0 	0 	74	0
B 	74 	23 	90 	49 	0
D 	3 	2 	2 	3 	-9999

	Table:                                  A       D
	0 	0 	0 	0 	0 	0	-9999
	16 	17 	14 	12 	0 	46	2
	18 	16 	15 	15 	0 	99	0
	0 	0 	0 	0 	0 	74	0
B 	74 	23 	90 	32 	0
D 	2 	1 	1 	3 	-9999

	Table:                                  A       D
	0 	0 	0 	0 	0 	0	-9999
	16 	17 	14 	0 	0 	14	2
	18 	16 	15 	0 	0 	99	1
	0 	0 	0 	0 	0 	74	0
B 	74 	23 	90 	0 	0
D 	2 	1 	1 	-9999 	-9999

	Table:                                  A       D
	0 	0 	0 	0 	0 	0	-9999
	0 	0 	0 	0 	0 	0	-9999
	18 	16 	15 	0 	0 	99	1
	0 	0 	0 	0 	0 	74	0
B 	74 	23 	76 	0 	0
D 	0 	0 	0 	-9999 	-9999

	Table:                                  A       D
	0 	0 	0 	0 	0 	0	-9999
	0 	0 	0 	0 	0 	0	-9999
	18 	16 	0 	0 	0 	23	2
	0 	0 	0 	0 	0 	74	0
B 	74 	23 	0 	0 	0
D 	0 	0 	-9999 	-9999 	-9999

	Table:                                  A       D
	0 	0 	0 	0 	0 	0	-9999
	0 	0 	0 	0 	0 	0	-9999
	18 	0 	0 	0 	0 	0	0
	0 	0 	0 	0 	0 	74	0
B 	74 	0 	0 	0 	0
D 	0 	-9999 	-9999 	-9999 	-9999

	Table:                                  A       D
	0 	0 	0 	0 	0 	0	-9999
	0 	0 	0 	0 	0 	0	-9999
	0 	0 	0 	0 	0 	0	-9999
	0 	0 	0 	0 	0 	74	0
B 	74 	0 	0 	0 	0
D 	0 	-9999 	-9999 	-9999 	-9999


	0 	0 	0 	17*9	34*11
	0 	0 	14*14	32*12	0
	0 	23*16	76*15	0 	0
	74*0	0 	0 	0 	0
RESULT: 2615


