import com.opencsv.CSVWriter;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static boolean isEmptyFile(File textFile) {
        try (BufferedReader inBuffer = new BufferedReader(new FileReader(textFile))) {
            return inBuffer.readLine() == null;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {

        final String[] products = {
                "Молоко",
                "Хлеб",
                "Сливочное масло",
                "Макароны",
                "Творог",
                "Мука"
        };

        final int[] prices = {80, 45, 120, 62, 88, 74};

        Scanner sc = new Scanner(System.in);
        //final File fileBasket = new File("basket.txt");
        final File fiLeLog = new File("log.csv");
        final File fileJsonBasket = new File ("basket.json");
        Basket basket;
        ClientLog clientLog = new ClientLog();

        if (!fiLeLog.exists() || isEmptyFile(fiLeLog)) {
            try (CSVWriter wr = new CSVWriter(new FileWriter(fiLeLog))){
                wr.writeNext(new String[]{"productNum", "amount"});
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

        if (fileJsonBasket.canRead() && !isEmptyFile(fileJsonBasket)) {
            try {
                //basket = Basket.loadFromTxtFile(fileBasket);
                basket = Basket.loadFromJson(fileJsonBasket);
            } catch (Exception e) {
                System.out.println("Неверный формат файла. Будет создана пустая корзина");
                basket = new Basket(products, prices);
            }
        } else {
            System.out.println("У вас пустая корзина");
            basket = new Basket(products, prices);
        }

        basket.printChoice();

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
                        basket.addToCart(numberProduct, count);
                        //basket.saveTxt(fileBasket);
                        basket.saveJson(fileJsonBasket);
                        clientLog.log(numberProduct, count);
                        clientLog.exportAsCSV(fiLeLog);
                    }
                }

            }
        }
    }
}

