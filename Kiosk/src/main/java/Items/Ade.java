package Items;

import Items.superobject.DetailMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Ade implements DetailMenu {
    ORANGE("orange", 5500, "오렌지 에이드"),
    GRAPEFRUIT("grapefruit", 6000, "자몽 에이드"),
    LEMON("lemon", 6000, "레몬 에이드"),
    BLUELEMON("blue lemon", 6000, "블루레몬 에이드")
    ;

    private String name;
    private int price;
    private String desc;

}
