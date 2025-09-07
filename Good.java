public class Good {
    private int gid;      // 商品编号
    private String good;  // 商品名称
    private String brand; // 商品品牌
    private Double price; // 价格

    public Good(int gid, String good, String brand, Double price) {
        this.gid = gid;
        this.good = good;
        this.brand = brand;
        this.price = price;
    }

    public int getGid() {
        return gid;
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
}
