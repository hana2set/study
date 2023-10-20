import module.MenuInit;
import module.Order;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        // 2. 메뉴판 띄우기
        // 3. 메뉴 고르기
        // 3. 장바구니 등록
        // 4. 총액 계산
        // 5. 결제

        // 1. 메뉴판 초기화
        MenuInit init = new MenuInit();
        Order order = new Order();

        // http://patorjk.com/software/taag  Font Name: Bloody
        try (BufferedReader br = new BufferedReader(new FileReader("banner.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("");
        } catch (IOException e) {
            //banner.txt 없습니다
        }

        init.initMenuMap(order);
        order.printMenu();

        while (true) {
            order.getInputValue();
        }
    }
}
