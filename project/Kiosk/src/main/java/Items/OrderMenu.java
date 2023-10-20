package Items;

import Items.superobject.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum OrderMenu implements Menu {
    ORDER("Order", "장바구니 확인 후 주문합니다."),
    CANCEL("Cancel", "취소")
//    EXIT("exit", "키오스그 끄기")
    ;

    String name;
    String desc;
}