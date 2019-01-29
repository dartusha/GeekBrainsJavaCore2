public class MyException {
    private int sum;
    /*
    1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4,
    при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
     */

    public MyException(String[][] array) throws MyArraySizeException {
        if (array.length != 4) {
            throw new MyArraySizeException();
        }

       /*
        2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
        Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст
        вместо числа), должно быть брошено исключение MyArrayDataException – с детализацией, в какой именно
        ячейке лежат неверные данные.
        */

        sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                try {
                    int tmp = Integer.parseInt(array[i][j]);
                    sum+=tmp;
                } catch (Exception e) {
                    throw new MyArrayDataException(i,j,array[i][j]);
                }
            }
        }
    }

    public int getSum() {
        return sum;
    }
}