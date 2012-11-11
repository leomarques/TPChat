import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;

import javax.swing.JTextField;

class Sender implements KeyListener {
	private PrintWriter mOut;
	private JTextField textField;

	public Sender(PrintWriter aOut, JTextField paramTextField) {
		mOut = aOut;
		textField = paramTextField;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == '\n') {
			mOut.println(textField.getText());
			mOut.flush();
			textField.setText("");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}