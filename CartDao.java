import java.util.ArrayList;
import java.util.List;

public class CartDao {
    // 添加商品到购物车
    public int addGood(Good good, int uid) {
        List<String> cartLines = new ArrayList<>();
        // 先读取原有购物车数据
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("carts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cartLines.add(line);
            }
        } catch (Exception e) {
            // 文件不存在时初始化（首次添加商品）
        }

        // 检查商品是否已在购物车，存在则数量+1，否则新增
        boolean exists = false;
        for (int i = 0; i < cartLines.size(); i++) {
            String[] parts = cartLines.get(i).split(",");
            int userId = Integer.parseInt(parts[0]);
            int gid = Integer.parseInt(parts[1]);
            if (userId == uid && gid == good.getGid()) {
                // 数量+1
                int num = Integer.parseInt(parts[5]) + 1;
                cartLines.set(i, userId + "," + gid + "," + good.getGood() + "," + good.getBrand() + "," + good.getPrice() + "," + num);
                exists = true;
                break;
            }
        }
        if (!exists) {
            // 新增商品记录
            cartLines.add(uid + "," + good.getGid() + "," + good.getGood() + "," + good.getBrand() + "," + good.getPrice() + ",1");
        }

        // 写入购物车数据
        CF.writeCarts(cartLines);
        return 1; // 简化返回，默认添加成功
    }

    // 查询商品列表（从 goods.txt 读取）
    public List<Good> getGoods(String name) {
        List<Good> goodsList = CF.readGoods();
        List<Good> result = new ArrayList<>();
        for (Good good : goodsList) {
            if (good.getGood().contains(name)) {
                result.add(good);
            }
        }
        return result;
    }

    // 查询购物车商品列表
    public List<CartGood> getCGoods(int uid) {
        return CF.readCarts(uid);
    }

    // 从购物车移除商品
    public int delGood(String goodName, int uid) {
        List<String> cartLines = new ArrayList<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("carts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                cartLines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 找到对应商品并移除
        for (int i = 0; i < cartLines.size(); i++) {
            String[] parts = cartLines.get(i).split(",");
            int userId = Integer.parseInt(parts[0]);
            String name = parts[2];
            if (userId == uid && name.equals(goodName)) {
                cartLines.remove(i);
                // 写入更新后的购物车数据
                CF.writeCarts(cartLines);
                return 1; // 移除成功
            }
        }
        return 0; // 未找到商品，移除失败
    }
}

