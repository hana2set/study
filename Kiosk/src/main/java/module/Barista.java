package module;

import dto.Item;

import java.util.ArrayList;
import java.util.List;

public class Barista {
    static List<List<Item>> orderList = new ArrayList<>();
    public static boolean makeDrink(List<Item> menus) throws InterruptedException {

        orderList.add(menus);

        System.out.println(
                "주문이 완료되었습니다!\n" +
                "\n" +
                "대기번호는 [ "+ orderList.size() +" ] 번 입니다.\n" +
                "(3초후 메뉴판으로 돌아갑니다.)"
        );

        Thread.sleep(3000);

        System.out.println("");
        System.out.println("=======================================");

        return true;
    }
}
