import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Login {


    public Login() {
        JFrame frame = new JFrame("Login");
        JPanel panel = new JPanel();
        JLabel username = new JLabel("username:");

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(panel);

        panel.setLayout(null);
        username.setBounds(40, 45, 80, 25);
        panel.add(username);
        username.setVisible(true);

        JTextField input = new JTextField();
        input.setBounds(110, 45, 165, 25);
        panel.add(input);

        JLabel status = new JLabel("");
        status.setBounds(40, 70, 250, 25);


        panel.add(status);

        JButton button = new JButton("Create");
        button.setBounds(130, 100, 85, 25);
        panel.add(button);



        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InetAddress destination = InetAddress.getByName("255.255.255.255");
                    int port = 5555;
                    DatagramSocket socket = new DatagramSocket();
                    String str = input.getText();
                    String message = "";
                    boolean success = true;
                    if (str.isEmpty()) {
                        message = "- username should not be empty";
                        status.setForeground(Color.red);
                        success = false;
                    }
                    if (str.contains(" ")) {
                        message = "- username should not contain spacing";
                        status.setForeground(Color.red);
                        success = false;
                    }

                    if (success) {
                        message = "- success";
                        status.setForeground(Color.green);
                    }
                    status.setText(message);


                    Object[] objects = new Object[2];
                    objects[0] = 1;
                    objects[1] = str;

                    System.out.println(Arrays.toString(Arrays.toString(objects).getBytes()));

                    DatagramPacket packet = new DatagramPacket(str.getBytes(), str.length(), destination, port);
                    socket.send(packet);







                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });


        frame.setVisible(true);


    }

    private void sendUDP(String username) {

    }


    public static void main(String[] args) {
        new Login();
    }
}
