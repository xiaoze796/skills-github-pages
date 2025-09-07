import javax.swing.*;
import java.awt.*;

public class PayDialog extends JDialog {
    private JButton button;

    public PayDialog(JFrame parent) {
        super(parent, "支付成功", true);
        this.initUI();
    }

    private void initUI() {
        this.button = new JButton("确定");
        this.button.addActionListener((ac) -> {
            this.dispose();
        });
        this.add(this.button, "Center");
        this.setSize(220, 85);
        this.setLocationRelativeTo((Component)null);
    }
}

