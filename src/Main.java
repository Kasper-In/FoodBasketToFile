import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        String[] products = {
                "Молоко",
                "Хлеб",
                "Сливочное масло",
                "Макароны",
                "Творог"
        };

        int[] prices = {80, 45, 120, 62, 88};

        Basket basket = new Basket(products, prices);

        File fileBasket = new File("basket.txt");

        Basket.loadFromTxtFile(fileBasket);

        System.out.println("Список продуктов для выбора:");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Введите номер продукта и его количество через пробел:");
            System.out.println("//для завершения нажмите end");
            String input = sc.nextLine();
            if (input.equals("end")) {
                basket.printCart();
                break;
            }
            String[] foodBasket = input.split(" ");
            if (foodBasket.length != 2) {
                System.out.println("Неверный ввод: введено не 2 значения. Попробуйте еще раз");
            } else {
                int numberProduct;
                try {
                    numberProduct = Integer.parseInt(foodBasket[0]);
                } catch (NumberFormatException exception) {
                    System.out.println("Вы ввели не число. Попробуйте еще раз");
                    continue;
                }
                if (numberProduct > products.length || numberProduct <= 0) {
                    System.out.println("Неверный номер продукта. Попробуйте еще раз");
                } else {
                    int count;
                    try {
                        count = Integer.parseInt(foodBasket[1]);
                    } catch (NumberFormatException exception) {
                        System.out.println("Вы ввели не число. Попробуйте еще раз");
                        continue;
                    }
                    if (count < 0) {
                        System.out.println("Введено отрицательное количество. Попробуйте еще раз");
                    } else {
                        basket.addToCart(numberProduct,count);
                        basket.saveTxt(fileBasket);
                    }
                }

            }
        }
    }
}

