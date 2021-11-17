import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GUIEchoClient extends JFrame {
	JTextArea textArea;
	JTextField textField;
	DataOutputStream out;

	public GUIEchoClient(String serverIP, int port) throws IOException {

		Socket socket = new Socket(serverIP, port);
		out = new DataOutputStream(socket.getOutputStream());
		/*
		 * Run a thread behind to receive data
		 */
//		Thread t = new Thread(() -> {
//			receiveData(socket);
//		});
//		t.start();

		new Thread(()->receiveData(socket)).start();

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(Color.lightGray);
		JScrollPane sp = new JScrollPane(textArea);

		textField = new JTextField();
		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					sendMsg(textField.getText());
					textField.setText("");
				}

			}
		});

		container.add(sp, BorderLayout.CENTER);
		container.add(textField, BorderLayout.SOUTH);

		this.setTitle("Echo Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(320, 240));
		this.setVisible(true);
	}

	private void sendMsg(String msg) {
		try {
			out.writeInt(msg.length());
			out.write(msg.getBytes(), 0, msg.length());
		} catch (IOException e) {
			textArea.append("Unable to send message to the server!\n");
		}
	}

private void receiveData(Socket socket) {
	try {
		byte[] buffer = new byte[1024];
		DataInputStream in = new DataInputStream(socket.getInputStream());
		while (true) {
			int len = in.readInt();
			in.read(buffer, 0, len);
			SwingUtilities.invokeLater(() -> {
				textArea.append(new String(buffer, 0, len) + "\n");
			});
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	public static void main(String[] args) {
		try {
			if (args.length != 2)
				throw new NumberFormatException();
			new GUIEchoClient(args[0], Integer.parseInt(args[1]));
		} catch (NumberFormatException nf) {
			System.err.println("Invalid arguments!");
			System.err.println("Usage:\n\tjava GUIEchoClient server_ip server_port");
		} catch (IOException io) {
			System.err.printf("Unable to communicate with host - %s:%s", args[0], args[1]);
		}
	}

}
