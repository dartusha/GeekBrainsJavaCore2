import java.util.HashMap;
/*
1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
Посчитать, сколько раз встречается каждое слово.
 */

public class Task1 {
    public Task1(String[] arr){
        HashMap<String, Integer> res =getUniqueCnt(arr);
        System.out.println(res);
    }
    
    public HashMap getUniqueCnt(String arr[]){
        HashMap<String, Integer> res = new HashMap<String, Integer>();
        for (String i:arr){
            int cnt=0;

            if (!(res.containsKey(i))){
                cnt=1;
            }
            else
              cnt=res.get(i)+1;

            res.put(i,cnt);

        }
        return res;
    }

}
