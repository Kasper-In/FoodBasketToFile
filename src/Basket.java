import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Basket {
    private String[] products;
    private int[] prices;
    private int[] countProduct;

    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.countProduct = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        countProduct[productNum - 1] += amount;
    }

    private int sumTotal(){
        int sum = 0;
        for (int j = 0; j < products.length; j++) {
            if (countProduct[j] != 0) {
                int sumProduct = prices[j] * countProduct[j];
                sum += sumProduct;
            }
        }
        return sum;
    }

    public void printCart(){
        System.out.println("Ваша корзина:");
        for (int i = 0; i < products.length; i++) {
            if (countProduct[i] != 0) {
                int sum = prices[i] * countProduct[i];
                System.out.println(products[i] + " (" + prices[i] + " руб/шт) - " + countProduct[i] + " шт (Итого: " + sum + " руб)");
            }
        }
        System.out.println("Итоговая сумма: " + sumTotal() + " руб");
    }

    public void saveTxt(File textFile) throws IOException {
        try (BufferedWriter outBuffer = new BufferedWriter(new FileWriter(textFile))) {
            for (int i = 0; i < products.length; i++){
                outBuffer.write(products[i] + ";" + prices[i] + ";" + countProduct[i]);
                outBuffer.newLine();
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        Basket basket = null;
        try (BufferedReader inBuffer = new BufferedReader(new FileReader(textFile))) {
            String[] fileInfo = inBuffer.lines().collect(Collectors.toList()).toArray(new String[0]);
            String[] fileProducts = new String[fileInfo.length];
            int[] filePrices = new int[fileInfo.length];
            int[] fileCount = new int[fileInfo.length];
            for (int i = 0; i < fileInfo.length; i++) {
                String[] fileLine = fileInfo[i].split(";");
                fileProducts[i] = fileLine[0];
                filePrices[i] = Integer.parseInt(fileLine[1]);
                fileCount[i] = Integer.parseInt(fileLine[2]);
            }
            basket = new Basket(fileProducts, filePrices);
            for (int j = 0; j < fileCount.length; j++) {
                basket.addToCart(j+1, fileCount[j]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return basket;
    }
}
