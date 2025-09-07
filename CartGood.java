public class CartGood {
    private String good;   // 商品名称
    private String brand;  // 商品品牌
    private Double price;  // 价格
    private int num;       // 数量

    public CartGood(String good, String brand, Double price, int num) {
        this.good = good;
        this.brand = brand;
        this.price = price;
        this.num = num;
    }

    public String getGood() {
        return good;
    }

    public String getBrand() {
        return brand;
    }

    public Double getPrice() {
        return price;
    }

    public int getNum() {
        return num;
    }
}

