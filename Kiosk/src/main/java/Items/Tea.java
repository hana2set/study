package Items;

import Items.superobject.DetailMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tea implements DetailMenu {
    CHAMOMILE("chamomile", 3000, "캐모마일"),
    EARLGREY("Earl Grey", 3800, "얼그레이"),
    GREEN("green", 3800, "녹차"),
    ROOIBOS("Rooibos", 300, "루이보스")
    ;

    private String name;
    private int price;
    private String desc;

}
