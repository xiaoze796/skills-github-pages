import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CF {
    // 读取商品数据（goods.txt）
    public static List<Good> readGoods() {
        List<Good> goodsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("goods.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int gid = Integer.parseInt(parts[0]);
                String goodName = parts[1];
                String brand = parts[2];
                Double price = Double.parseDouble(parts[3]);
                goodsList.add(new Good(gid, goodName, brand, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    // 读取用户数据（users.txt）
    public static List<User> readUsers() {
        List<User> usersList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int uid = Integer.parseInt(parts[0]);
                String name = parts[1];
                String pwd = parts[2];
                usersList.add(new User(uid, name, pwd));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    // 写入用户数据（users.txt）
    public static void writeUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                bw.write(user.getUid() + "," + user.getName() + "," + user.getPwd());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取购物车数据（carts.txt）
    public static List<CartGood> readCarts(int uid) {
        List<CartGood> cartsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("carts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int userId = Integer.parseInt(parts[0]);
                if (userId == uid) {
                    String goodName = parts[2];
                    String brand = parts[3];
                    Double price = Double.parseDouble(parts[4]);
                    int num = Integer.parseInt(parts[5]);
                    cartsList.add(new CartGood(goodName, brand, price, num));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartsList;
    }

    // 写入购物车数据（carts.txt）
    public static void writeCarts(List<String> cartLines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("carts.txt"))) {
            for (String line : cartLines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
