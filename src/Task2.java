/*
2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона по фамилии.
Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
тогда при запросе такой фамилии должны выводиться все телефоны.

Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в телефонную запись добавлять еще дополнительные поля (имя, отчество, адрес),
 делать взаимодействие с пользователем через консоль и т.д.). Консоль желательно не использовать (в том числе Scanner), тестировать просто из метода main(),
  прописывая add() и get().
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Task2 {
    Map<String, HashSet<String>> res = new HashMap<>();

    public Task2() {

    }

    public void add(String surname, String phone){
        //по-хорошему бы наверное телефон должен бы быть ключом, т.к. он уникален. Но т.к. искать по фамилии...
        if (res.containsKey(surname)){
            res.get(surname).add(phone);
        }
        //такой записи еще нет
        else {
            HashSet<String> phoneHashSet = new HashSet<>();
            phoneHashSet.add(phone);
            res.put(surname, phoneHashSet);
        }
    }

    public void get(String surname){
            System.out.println("Фамилия: "+ surname+", телефон: "+res.get(surname));
    }

}
