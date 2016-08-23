
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class myclass extends JFrame {
	JSlider slid;
	JLabel label;

	public myclass() {
		super("JSlider");
		setLayout(new GridBagLayout());

		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 1;
		g.gridy = 0;

		slid = new JSlider(JSlider.VERTICAL, 0, 100, 20);
		slid.setMajorTickSpacing(50);
		slid.setPaintTicks(true);
		slid.setForeground(Color.RED);

		add(slid, g);

		label = new JLabel("Volume 20 %");
		g.gridx = 0;
		g.gridy = 1;
		add(label, g);

		slid.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				label.setText("Volume " +slid.getValue() + " %");
			}
		});
	}
	public static void main(String []  args){
		myclass m = new myclass();
		m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.setSize(500, 300);
		m.setVisible(true);
	}
}
