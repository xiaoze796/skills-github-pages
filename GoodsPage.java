import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsPage {
    JFrame frame;
    JPanel top, center, bottom;
    JTable table;
    JScrollPane scrollpane;
    JLabel lab1;
    JTextField good_name;
    JButton search, add_cart, view_cart;
    int uid; // 当前登录用户ID

    static MyTableModel model; // 表格模型

    public GoodsPage(int uid) {
        this.uid = uid;
        initUI();
    }

    private void initUI() {
        Font font = new Font("Serif", Font.BOLD, 20);
        frame = new JFrame("商品浏览页面");

        // 顶部搜索栏
        top = new JPanel(new BorderLayout());
        lab1 = new JLabel("搜索商品");
        good_name = new JTextField(10);
        search = new JButton("搜索");
        search.addActionListener(e -> query());

        add_cart = new JButton("加入购物车");
        add_cart.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "请先选择要添加的商品！");
                return;
            }
            // 获取选中商品数据
            int gid = (int) table.getValueAt(selectedRow, 0);
            String goodName = (String) table.getValueAt(selectedRow, 2);
            String brand = (String) table.getValueAt(selectedRow, 3);
            Double price = (Double) table.getValueAt(selectedRow, 4);
            Good good = new Good(gid, goodName, brand, price);

            CartDao dao = new CartDao();
            int result = dao.addGood(good, uid);
            if (result > 0) {
                query(); // 刷新商品列表
                JOptionPane.showMessageDialog(frame, "添加成功！");
            } else {
                JOptionPane.showMessageDialog(frame, "添加失败！");
            }
        });

        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        westPanel.add(lab1);
        westPanel.add(good_name);
        westPanel.add(search);
        eastPanel.add(add_cart);

        top.add(westPanel, BorderLayout.WEST);
        top.add(eastPanel, BorderLayout.EAST);

        // 中央商品表格
        center = new JPanel(new BorderLayout());
        query(); // 初始化商品数据

        table = new JTable(model);
        // 隐藏商品编号列（第一列）
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.setFont(font);
        table.setRowHeight(30);

        scrollpane = new JScrollPane(table);
        center.add(scrollpane, BorderLayout.CENTER);

        // 底部查看购物车按钮
        bottom = new JPanel(new BorderLayout());
        view_cart = new JButton("查看购物车");
        view_cart.addActionListener(e -> {
            new CartPage(uid);
        });
        bottom.add(view_cart, BorderLayout.EAST);

        // 组装界面
        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 查询商品并更新表格
    public void query() {
        String[] headers = {"ID", "编号", "商品", "品牌", "价格"};
        String name = good_name.getText();
        CartDao dao = new CartDao();
        List<Good> goodsList = dao.getGoods(name);

        List<Object[]> dataRows = new ArrayList<>();
        int index = 1;
        for (Good good : goodsList) {
            dataRows.add(new Object[]{
                    good.getGid(),
                    index++,
                    good.getGood(),
                    good.getBrand(),
                    good.getPrice()
            });
        }

        Object[][] datas = dataRows.toArray(new Object[dataRows.size()][]);
        if (model == null) {
            model = new MyTableModel(headers, datas);
        } else {
            model.updateDataAndRefresh(datas);
        }
    }

    public static void addAll(Container ctn, Component... cs) {
        for (Component c : cs) {
            ctn.add(c);
        }
    }
}
