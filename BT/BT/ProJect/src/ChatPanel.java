import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatPanel extends JPanel {
Socket socket = null;
BufferedReader bf = null;
DataOutputStream os = null;
OutputThread t = null;
String sender;
String receiver;
JTextArea txtMessages;
	/**
	 * Create the panel.
	 * @param staffName 
	 * @wbp.parser.constructor
	 */
	public ChatPanel(Socket s, String sender, String receiver) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		JTextArea txtMessage = new JTextArea();
		scrollPane.setViewportView(txtMessage);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtMessage.getText().trim().length() == 0) return;
				try {
					os.writeBytes(txtMessage.getText());
					os.write(13); os.write(10);
					os.flush();
					txtMessages.append("\n" + sender + ":" + txtMessage.getText());
					txtMessage.setText("");
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBackground(SystemColor.activeCaption);
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		panel.add(btnNewButton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		add(scrollPane_1, BorderLayout.CENTER);
		
		 this.txtMessages = new JTextArea();
		scrollPane_1.setViewportView(txtMessages);
socket = s;
this.sender = sender;
this.receiver = receiver;
try {
	bf = new BufferedReader (new InputStreamReader(socket.getInputStream()));
	os = new DataOutputStream (socket.getOutputStream());
	t = new OutputThread(s,txtMessages, sender, receiver);
	t.start();
}
	catch(Exception e) {
	}
	}
public ChatPanel(Socket aStaffSocket, String sender2) {
		// TODO Auto-generated constructor stub
	}
public JTextArea getTxtMessages() {
	return this.txtMessages;
}
}
	


