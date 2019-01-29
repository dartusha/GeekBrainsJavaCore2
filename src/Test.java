public class Test {
    private int sum=0;

    //testing
    public Test(){
      // String[][] arr={{"1","2","3","4"},{"test","test","5","6"}}; //тест кейс 1
        String[][] arr={{"1","2","3","4"},{"test","test","5","6"},{"7","8","9","0"},{"1","2","3","4"}}; //тест кейс 2
       // String[][] arr={{"1","2","3","4"},{"1","2","5","6"},{"7","8","9","0"},{"1","2","3","4"}}; //тест кейс 3
        MyException expt=new MyException(arr);
        this.sum=expt.getSum();
    }

    public int getSum() {
        return sum;
    }


    /*
    3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException
    и MyArrayDataException и вывести результат расчета.
     */
    public static void main(String[] args) {
        try {
            Test tst=new Test();
            System.out.println("Сумма равна: "+tst.getSum());
        }
        catch (MyArraySizeException e){
            System.out.println("Размерность массива должна быть 4*4!");
        }
        //тут добавила вывод значения, т.к. пользователю удобнее будет видеть, на чем упало
        catch (MyArrayDataException e) {
            System.out.println("В ячейке "+e.getX()+","+e.getY()+" содержится нечисловое значение "+e.getValue());
        }

    }

}
