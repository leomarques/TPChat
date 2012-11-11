import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGUI extends JFrame {
	private Panel panel;

	public ClientGUI() {
		setTitle("TPChat");
		setSize(640, 480);
		setLocationRelativeTo(null);

		panel = new Panel();
		add(panel);

		setVisible(true);
	}

	public JTextArea getTextArea() {
		return panel.textArea;
	}

	public JTextField getTextField() {
		return panel.textField;
	}
}

class Panel extends JPanel {
	public JTextArea textArea;
	public JTextField textField;

	Panel() {
		textArea = new JTextArea();
		textField = new JTextField();

		textArea.setEditable(false);

		setLayout(new BorderLayout());
		add(textArea, BorderLayout.PAGE_START);
		add(textField, BorderLayout.PAGE_END);
	}
}
