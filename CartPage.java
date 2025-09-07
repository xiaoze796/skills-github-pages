import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CartPage {
    JFrame frame;
    JPanel top, center, bottom;
    JButton del, pay;
    JTable table;
    JScrollPane scrollpane;
    JTextField good_name;
    int uid; // 当前登录用户ID

    static MyTableModel model; // 表格模型

    public CartPage(int uid) {
        this.uid = uid;
        initUI();
    }

    private void initUI() {
        Font font = new Font("Serif", Font.BOLD, 20);
        frame = new JFrame("购物车");

        // 顶部移除按钮
        top = new JPanel(new BorderLayout());
        del = new JButton("移除");
        del.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "请先选择要移除的商品！");
                return;
            }
            String goodName = (String) table.getValueAt(selectedRow, 1);
            CartDao dao = new CartDao();
            int result = dao.delGood(goodName, uid);
            if (result > 0) {
                query(); // 刷新购物车列表
                JOptionPane.showMessageDialog(frame, "移除成功！");
            } else {
                JOptionPane.showMessageDialog(frame, "移除失败！");
            }
        });
        top.add(del, BorderLayout.EAST);

        // 中央购物车表格
        center = new JPanel(new BorderLayout());
        query(); // 初始化购物车数据

        table = new JTable(model);
        table.setFont(font);
        table.setRowHeight(30);

        scrollpane = new JScrollPane(table);
        center.add(scrollpane, BorderLayout.CENTER);

        // 底部支付按钮
        bottom = new JPanel(new BorderLayout());
        pay = new JButton("支付");
        pay.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "请先选择要支付的商品！");
                return;
            }
            String goodName = (String) table.getValueAt(selectedRow, 1);
            CartDao dao = new CartDao();
            int result = dao.delGood(goodName, uid);
            if (result > 0) {
                query(); // 刷新购物车列表
                new PayDialog(frame).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "支付失败！");
            }
        });
        bottom.add(pay, BorderLayout.EAST);

        // 组装界面
        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 查询购物车商品并更新表格
    public void query() {
        String[] headers = {"序号", "商品", "品牌", "价格", "数量"};
        CartDao dao = new CartDao();
        List<CartGood> cartGoodsList = dao.getCGoods(uid);

        List<Object[]> dataRows = new ArrayList<>();
        int index = 1;
        for (CartGood cartGood : cartGoodsList) {
            dataRows.add(new Object[]{
                    index++,
                    cartGood.getGood(),
                    cartGood.getBrand(),
                    cartGood.getPrice(),
                    cartGood.getNum()
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
