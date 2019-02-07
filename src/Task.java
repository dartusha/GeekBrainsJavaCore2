
/*
1. Необходимо написать два метода, которые делают следующее:
1) Создают одномерный длинный массив, например:
static final int size = 10000000;
static final int h = size / 2;
float[] arr = new float[size];
2) Заполняют этот массив единицами;
3) Засекают время выполнения: long a = System.currentTimeMillis();
4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
5) Проверяется время окончания метода System.currentTimeMillis();
6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
Отличие первого метода от второго:
Первый просто бежит по массиву и вычисляет значения.
Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.

Пример деления одного массива на два:
System.arraycopy(arr, 0, a1, 0, h);
System.arraycopy(arr, h, a2, 0, h);

Пример обратной склейки:
System.arraycopy(a1, 0, arr, 0, h);
System.arraycopy(a2, 0, arr, h, h);

Примечание:
System.arraycopy() копирует данные из одного массива в другой:
System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
По замерам времени:
Для первого метода надо считать время только на цикл расчета:
for (int i = 0; i < size; i++) {
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
}
Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
 */

import java.util.ArrayList;
import java.util.List;

public class Task {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];

    static float[] divArr1 = new float[h];
    static float[] divArr2 = new float[h];

    public Task(){
    }

    public static float[] createArray(){
        for (int i = 0; i < size; i++) {
            arr[i]=1;
        }
        return arr;
    }

    public static void calc(float[] calcArr, int counter){
        long a = System.currentTimeMillis();

        for (int i=0, j = i+counter*h ; i < h; i++, j = i+counter*h) {
            calcArr[i] = (float) ( calcArr[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
        }
        System.out.format("Расчет для массива %d. Время работы: %d милисекунд",(counter+1), System.currentTimeMillis() - a);
        System.out.println("");
    }

    public static void divide(float[] baseArr) {
        long a = System.currentTimeMillis();
        System.arraycopy(baseArr, 0, divArr1, 0, h);
        System.arraycopy(baseArr, h, divArr2, 0, h);

        System.out.format("Время работы разбивки: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");
    }

    public static float[] getBaseArr(){
        return arr;
    }

    public static float[] getDivArr1(){
        return divArr1;
    }

    public static float[] getDivArr2(){
        return divArr2;
    }

    public static float[] plus(float[] dvArr1,float[] dvArr2){
        long a = System.currentTimeMillis();
        float[] returnArr = new float[size];
        System.arraycopy(dvArr1, 0, returnArr, 0, h);
        System.arraycopy(dvArr2, 0, returnArr , h, h);
        System.out.format("Склейка массивов: %d милисекунд", System.currentTimeMillis() - a);
        System.out.println("");
        return returnArr;
    }

    public static void one(){
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.format("Время работы: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");
    }

    public static void test(){
        System.out.println("Тестовый прогон. Сравниваем значения на границе склейки:");
        for (int i = size/2-10; i < size/2+10; i++) {
            System.out.print(arr[i]);
        }
        System.out.println("");
    }


    public static void main(String[] args) throws InterruptedException {
        Task1 task1 = new Task1();
        System.out.println("Start");

        arr=createArray();
        one();

        test();


        arr = new float[size];
        arr=createArray();

        divide(getBaseArr());

        List<Thread> threads = new ArrayList<Thread>();

        threads.add(new Thread(() -> Task.calc(getDivArr1(),0)));
        threads.add(new Thread(() -> Task.calc(getDivArr2(),1)));

        for (Thread thread: threads) {
            thread.start();
        }

        for (Thread thread: threads) {
            thread.join();
        }

        arr = plus(getDivArr1(), getDivArr2());

        test();

    }
}