package module;

import Items.*;
import Items.superobject.DetailMenu;
import Items.superobject.Menu;
import constant.Color;
import constant.OrderLevel;
import dto.Item;
import error.WrongInputException;

import java.util.*;

public class Order {
    Map<Menu, List<DetailMenu>> menuMap;            //메뉴 - 상세 메뉴 맵
    ItemMenu[] itemMenus = ItemMenu.values();       //메뉴 목록
    OrderMenu[] orderMenus = OrderMenu.values();    //명령 목록

    OrderLevel orderLevel = OrderLevel.MAIN;        //주문 계층
    Menu detailMenu;                                //선택된 상세 메뉴
    Item selectItem = new Item();                   //선택된 상품
    List<Item> basket = new ArrayList<>();          //장바구니

    public void getInputValue() {
        try{
            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();
            boolean isWrongValue = false;

            System.out.println("");
            System.out.println("=======================================");
            switch (orderLevel) {
                case MAIN :
                    if (0 < num && num <= itemMenus.length) {
                        printMenuDetail(itemMenus[num-1]);
                        orderLevel = OrderLevel.DETAIL;
                    } else if(basket.size() > 0 && num <= itemMenus.length + orderMenus.length ) {
                        switch (orderMenus[num-itemMenus.length-1]){
                            case ORDER:
                                printBasket();
                                break;
                            case CANCEL:
                                printCancel();
                                break;
                            default:
                                System.out.println("키오스크를 종료합니다.");
                                System.exit(0);
                        }
                    } else {
                        isWrongValue = true;
                    }
                    break;

                case DETAIL :
                    if (0 < num && num <= menuMap.get(detailMenu).size()) {
                        selectItem.setMenu(menuMap.get(detailMenu).get(num-1));
                        printAddBasket(selectItem.getMenu());
                    } else {
                        isWrongValue = true;
                    }
                    break;

                case ADD:
                    if (num == 1) {
                        selectItem.setCount(selectItem.getCount() + 1);
                        if (detailMenu == ItemMenu.COFFEE
                                || detailMenu == ItemMenu.TEA) {
                            printCheckIce();
                        } else {
                            addBasket();
                            printMenu();
                        }
                    } else if (num == 2) {
                        printMenu();
                    } else if (num == 3) {
                        printCheckCount();
                    } else {
                        isWrongValue = true;
                    }
                    break;

                case CHECKCOUNT:
                    if ( 0 < num && num < 100) {
                        int totalCount = selectItem.getCount() + num;
                        if (num < 100) {
                            selectItem.setCount(totalCount);
                            if (detailMenu == ItemMenu.COFFEE
                                    || detailMenu == ItemMenu.TEA) {
                                printCheckIce();
                            } else {
                                addBasket();
                                printMenu();
                            }
                        } else {
                            System.out.println("수량이 초과되었습니다.");
                            System.out.println("");
                            printAddBasket(selectItem.getMenu());
                        }
                    } else {
                        isWrongValue = true;
                    }
                    break;

                case CHECKICE:
                    if (num == 1) {
                        selectItem.setHasIce(true);
                        addBasket();
                        printMenu();
                    } else if (num == 2) {
                        selectItem.setHasIce(false);
                        addBasket();
                        printMenu();
                    } else {
                        isWrongValue = true;
                    }
                    break;

                case CANCEL:
                    if (num == 1) {
                        cancel();
                    } else if (num == 2) {
                        printMenu();
                    } else {
                        isWrongValue = true;
                    }
                    break;

                case ORDER:
                    if (num == 1) {
                        Barista.makeDrink(basket);
                        basket = new ArrayList<>();
                        printMenu();
                    } else if (num == 2) {
                        printMenu();
                    } else {
                        isWrongValue = true;
                    }
                    break;
            }

            if (isWrongValue == true) {
                throw new WrongInputException("잘못된 입력값입니다.");
            }

        } catch(InputMismatchException e) {
            System.out.println("잘못된 입력값입니다.");
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public void printMenu() {
        orderLevel = OrderLevel.MAIN;

        System.out.println("[ 메뉴 ]");
        printMenuItem(itemMenus, 0);
        if (basket.size() > 0) {
            System.out.println("");
        System.out.println("[ 주문확인 ]");
            printMenuItem(orderMenus, itemMenus.length);
        }
    }

    private void addBasket() {
        long basketCount = basket.stream()
                .filter(item ->
                        item.getMenu() == selectItem.getMenu() && item.isHasIce() == selectItem.isHasIce()
                )
                .limit(1)
                .peek(item -> item.setCount(item.getCount() + selectItem.getCount()))
                .count();
        if (basketCount == 0) {
            basket.add(selectItem);
        }
        selectItem = new Item();
    }

    public void printMenuDetail(Menu menu) {
        orderLevel = OrderLevel.DETAIL;

        System.out.println("[ 상세 메뉴 목록 ]");
        printDetailMenuItem(menuMap.get(menu), 0);
        detailMenu = menu;

    }

    public void printAddBasket(DetailMenu menu) {
        orderLevel = OrderLevel.ADD;

        printDetailMenuItem(menu);
        System.out.println("위 메뉴를 장바구니에 추가하시겠습니까?");
        System.out.println("");
        System.out.println("1. 확인       2. 취소       3.수량 선택");
    }

    public void printCheckCount() {
        orderLevel = OrderLevel.CHECKCOUNT;

        System.out.println("주문할 수량을 입력해주세요.(1-99)");
    }

    public void printCheckIce() {
        orderLevel = OrderLevel.CHECKICE;

        System.out.println("얼음을 추가하시겠습니까? 추가요금이 500원 부가됩니다.");
        System.out.println("");
        System.out.println("1. 추가       2. 추가 없음");
    }

    public void printBasket() {
        orderLevel = OrderLevel.ORDER;

        System.out.println("아래와 같이 주문 하시겠습니까?");
        System.out.println(" [ 주문 목록 ] ");
        printBasketList(basket);
        System.out.println("");
        System.out.println(" [ 총액 ] ");
        printBasketTotal(basket);
        System.out.println("");
        System.out.println("1. 확인       2. 메뉴판");

    }

    public boolean order() {

        return true;
    }
    public boolean cancel() {
        basket = new ArrayList<>();
        return true;
    }

    public void printCancel() {
        orderLevel = OrderLevel.CANCEL;

        System.out.println("주문을 취소하시겠습니까?");
        System.out.println("");
        System.out.println("1. 확인       2. 취소");
    }

    public void printMenuItem(Menu[] menus, int startNo) {
        int maxTitlelength = Arrays.stream(menus).mapToInt(m -> m.getName().length()).max().getAsInt();
        int titleLength = 10 > maxTitlelength ? 10 : maxTitlelength;
        for (int i = 0; i < menus.length; i++) {
            System.out.println((startNo + i + 1) + ". " + String.format("%-" + titleLength + "s", menus[i].getName())+" | " + menus[i].getDesc());
        }
    }

    public void printDetailMenuItem(DetailMenu menu) {
        int maxTitlelength = menu.getName().length();
        int titleLength = 10 > maxTitlelength ? 10 : maxTitlelength;
            System.out.println(
                            String.format("%-" + titleLength + "s", menu.getName())
                            + " | ₩ " + String.format("%5d", menu.getPrice())
                            + " | " + menu.getDesc()
            );
    }

    public void printDetailMenuItem(List<DetailMenu> menus, int startNo) {
        int maxTitlelength = menus.stream().mapToInt(m -> m.getName().length()).max().getAsInt();
        int titleLength = 10 > maxTitlelength ? 10 : maxTitlelength;
        for (int i = 0; i < menus.size(); i++) {
            System.out.println(
                    (startNo + i + 1)
                            + ". " + String.format("%-" + titleLength + "s", menus.get(i).getName())
                            + " | ₩ " + String.format("%5d", menus.get(i).getPrice())
                            + " | " + menus.get(i).getDesc()
            );
        }
    }

    public void printBasketList(List<Item> basket) {
        int maxTitlelength = basket.stream().mapToInt(m -> m.getMenu().getName().length()).max().getAsInt();
        int totalPrice = basket.stream().mapToInt(m -> m.getMenu().getPrice()).max().getAsInt();
        int titleLength = 10 > maxTitlelength ? 10 : maxTitlelength;
        for (int i = 0; i < basket.size(); i++) {
            Item item = basket.get(i);
            String iceText =  item.isHasIce() == true
                    ? Color.ANSI_BLUE + "ICE" + Color.ANSI_RESET
                    : Color.ANSI_RED + "HOT" + Color.ANSI_RESET;
            System.out.println(
                    iceText
                            + " " + String.format("%-" + titleLength + "s", item.getMenu().getName())
                            + " | " + String.format("%2s", item.getCount()) + " 개"
                            + " | ₩ " + String.format("%5d", calculateMenuPrice(item))
                            + " | " + item.getMenu().getDesc()
            );
        }
    }

    public void printBasketTotal(List<Item> basket) {
        int totalPrice = basket.stream()
                .mapToInt(m -> (
                        calculateMenuPrice(m)
                )).sum();
        System.out.println("₩ " + totalPrice);
    }

    public int calculateMenuPrice(Item item) {
        return (item.getMenu().getPrice() + (item.isHasIce() ? 1 : 0) * 500) * item.getCount();
    }
}
