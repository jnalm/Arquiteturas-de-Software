import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class AtivosCompra extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AtivosCompra frame = new AtivosCompra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public AtivosCompra() throws SQLException, IOException {
		AppESS connect = new AppESS();
		connect.getAtivos();
		String passText = new String(AppView.getPasswordField().getPassword());
		connect.fazLogin(AppView.getTextField().getText(), passText);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(436, 28, 138, 23);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 30, 290, 42);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setText("Montante Investido: " + connect.getPlafondInvestidoModalidade(connect.getUserLogin(), "COMPRA") + "�");
		
		JButton btnNewButton = new JButton("Fechar Compra");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int id = Integer.parseInt(textField.getText());
				connect.fechaCompra(id);
				table.setModel(connect.resultSetToTableModel(connect.getAtivosCompra()));
				textField.setText(null);
				lblNewLabel_1.setText("Montante Investido: " + connect.getPlafondInvestidoModalidade(connect.getUserLogin(), "COMPRA") + "�");
			}
		});
		btnNewButton.setBounds(436, 62, 138, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(340, 63, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 107, 564, 151);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(connect.resultSetToTableModel(connect.getAtivosCompra()));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jo\u00E3oNuno\\Desktop\\stock-market-tradingnew.png"));
		lblNewLabel.setBounds(0, 0, 584, 561);
		contentPane.add(lblNewLabel);
	}

}
