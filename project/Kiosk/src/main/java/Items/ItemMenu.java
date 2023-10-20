package Items;

import Items.superobject.Menu;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ItemMenu implements Menu {
    COFFEE("Coffee", "커피"),
    TEA("Tea", "차"),
    ETC("Fruit Juice & Ade", "과일주스 및 에이드");

    String name;
    String desc;
}