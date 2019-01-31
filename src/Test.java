public class Test {

    /*
    3. В методе main() вызвать полученный метод, обработать возможные исключения MySizeArrayException
    и MyArrayDataException и вывести результат расчета.
     */
    public static void main(String[] args) {
        System.out.println("Задание 1");
        String[] arr={"на","дворе","трава","на", "траве","дрова","не","руби","дрова","на","траве","двора"};
        Task1 task1=new Task1(arr);
        System.out.println("");

        System.out.println("Задание 2");
        Task2 task2=new Task2();
        task2.add("Иванов","1111111");
        task2.add("Иванов","2222222");

        task2.add("Петров","3333333");

        task2.get("Иванов");
        task2.get("Петров");
    }

}
