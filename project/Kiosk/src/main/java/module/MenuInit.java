package module;

import Items.*;
import Items.superobject.DetailMenu;
import Items.superobject.Menu;

import java.util.*;

public class MenuInit {
    public void initMenuMap(Order order) {
        Map<Menu, List<DetailMenu>> menuMap = new HashMap<>();
        Arrays.stream(Coffee.values())
                .forEach(item -> {
                    if (menuMap.get(ItemMenu.COFFEE) == null) {
                        menuMap.put(ItemMenu.COFFEE, new ArrayList<>());
                    }
                    menuMap.get(ItemMenu.COFFEE).add(item);
                });

        Arrays.stream(Tea.values())
                .forEach(item -> {
                    if (menuMap.get(ItemMenu.TEA) == null) {
                        menuMap.put(ItemMenu.TEA, new ArrayList<>());
                    }
                    menuMap.get(ItemMenu.TEA).add(item);
                });

        Arrays.stream(Ade.values())
                .forEach(item -> {
                    if (menuMap.get(ItemMenu.ETC) == null) {
                        menuMap.put(ItemMenu.ETC, new ArrayList<>());
                    }
                    menuMap.get(ItemMenu.ETC).add(item);
                });

        order.menuMap = menuMap;
    }

}
