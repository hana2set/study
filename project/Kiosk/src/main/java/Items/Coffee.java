package Items;

import Items.superobject.DetailMenu;
import lombok.*;

@Getter
@AllArgsConstructor
public enum Coffee implements DetailMenu {
    ESPRESSO("espresso", 3000, "에스프레소"),
    AMERICANO("americano", 3000, "아메리카노"),
    CAFELATTE("cafe latte", 3500, "카페라떼"),
    CAPPUCCINO("cappuccino", 3500, "카푸치노"),
    VANILLALATTE("vanilla latte", 3800, "아메리카노"),
    HAZELNUTLATTE("hazelnut latte", 3800, "헤이즐럿라떼")
    ;

    private String name;
    private int price;
    private String desc;

}
