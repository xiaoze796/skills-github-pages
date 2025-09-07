import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class MainPage {
    JFrame frame;
    JPanel top, center, bottom;
    JLabel lab1, lab2;
    JTextField username, password;
    JButton login, register;

    static int id; // 当前登录用户ID，这里命名为 id 不太准确，实际是 uid，可按需求调整

    public MainPage() {
        frame = new JFrame("购物车系统登陆页面");

        top = new JPanel();
        lab1 = new JLabel("用户名");
        username = new JTextField(11);
        addAll(top, lab1, username);

        center = new JPanel();
        lab2 = new JLabel("密码");
        password = new JTextField(12);
        addAll(center, lab2, password);

        bottom = new JPanel();
        login = new JButton("登陆");
        login.addActionListener(e -> {
            String name = username.getText();
            String pwd = password.getText();
            if ("".equals(name)) {
                JOptionPane.showMessageDialog(frame, "用户名不能为空！");
                return;
            }
            if ("".equals(pwd)) {
                JOptionPane.showMessageDialog(frame, "密码不能为空！");
                return;
            }
            UserDao dao = new UserDao();
            int result = dao.login(name, pwd);
            if (result == 0) {
                JOptionPane.showMessageDialog(frame, "用户不存在！");
            } else if (result == 1) {
                JOptionPane.showMessageDialog(frame, "登陆成功！");
                id = dao.getId(name);
                frame.setVisible(false);
                new GoodsPage(id);
            } else {
                JOptionPane.showMessageDialog(frame, "密码错误!");
            }
        });
        register = new JButton("注册");
        register.addActionListener(e -> {
            String name = username.getText();
            String pwd = password.getText();
            if ("".equals(name)) {
                JOptionPane.showMessageDialog(frame, "用户名不能为空！");
                return;
            }
            if ("".equals(pwd)) {
                JOptionPane.showMessageDialog(frame, "密码不能为空！");
                return;
            }
            UserDao dao = new UserDao();
            int result = dao.reg(name, pwd);
            if (result == 1) {
                JOptionPane.showMessageDialog(frame, "注册成功！");
            } else {
                JOptionPane.showMessageDialog(frame, "注册失败！");
            }
        });
        addAll(bottom, login, register);

        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(330, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public static int getId() {
        return id;
    }

    public static void addAll(Container ctn, Component... cs) {
        for (Component c : cs) {
            ctn.add(c);
        }
    }

    public static void main(String[] args) {
        new MainPage();
    }
}
