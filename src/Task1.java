
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

public class Task1 {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];
    static float[] a1 = new float[size/2+1];
    static float[] a2 = new float[size/2+1];
   // Object lock1 = new Object();

    public Task1(){
    }
    public static float[] createArray(){
        for (int i = 0; i < size; i++) {
            arr[i]=1;
        }
        return arr;
    }

    public static void one(){
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.format("Время работы: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");
    }

    public static void t2_0() {
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);
        System.out.format("Время работы разбивки: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");
    }

    public static void t2_1() {
        long a = System.currentTimeMillis();
        for (int i = 0; i < a1.length; i++) {
            a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.format("Просчет подмассива 1: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");

        synchronized (arr) {
            a = System.currentTimeMillis();
            System.arraycopy(a1, 0, arr, 0, h);
            System.out.format("Склейка массива 1: %d милисекунд", System.currentTimeMillis() - a);
            System.out.println("");
        }
    }

    public static void t2_2() {
        long a = System.currentTimeMillis();
        for (int i = 0; i < a2.length; i++) {
            a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.format("Просчет подмассива 2: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");

        synchronized (arr) {
            a = System.currentTimeMillis();
            System.arraycopy(a2, 0, arr, h, h);
            System.out.format("Склейка массива 2: %d милисекунд", System.currentTimeMillis() - a);
            System.out.println("");

            for (int i = 0; i < 15; i++) {
                System.out.print(arr[arr.length-i-1]);
            }
            System.out.println("");
        }
    }

    public  synchronized void  t2_3() {
            long a = System.currentTimeMillis();

            System.arraycopy(a1, 0, arr, 0, h);
            System.arraycopy(a2, 0, arr, h, h);
            System.out.format("Склейка: %d милисекунд", System.currentTimeMillis() - a);

    }


//Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
  /*  public static void two(float[] arr){
        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        System.out.format("Время работы разбивки: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");

        a = System.currentTimeMillis();
        for (int i = 0; i < a1.length; i++) {
            a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        for (int i = 0; i < a2.length; i++) {
            a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.format("Просчет: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");

        a = System.currentTimeMillis();

        synchronized (arr) {
            System.arraycopy(a1, 0, arr, 0, h);
            System.arraycopy(a2, 0, arr, h, h);
        }
        System.out.format("Склейка: %d милисекунд",System.currentTimeMillis() - a);
        System.out.println("");
    }
    */

    public static void main(String[] args) {
        Task1 task1 = new Task1();
        System.out.println("Start");

        arr=createArray();
        one();
        for (int i = 0; i < 15; i++) {
            System.out.print(arr[arr.length-i-1]);
        }
        System.out.println("");

      //  two(createArray());

      //
        arr = new float[size];
        arr=createArray();

        t2_0();
        new Thread(() -> task1.t2_1()).start();
        new Thread(() -> task1.t2_2()).start();
      //  new Thread(() -> task1.t2_3()).start();

        //t2_3();

    }
}