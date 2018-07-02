import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AfterSignUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AfterSignUp frame = new AfterSignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	AppESS connect = new AppESS();
	public AfterSignUp() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(102, 208, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNome.setBounds(99, 32, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblUsername.setBounds(99, 57, 77, 14);
		contentPane.add(lblUsername);
		
		JLabel lblEmail = new JLabel("Password");
		lblEmail.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblEmail.setBounds(99, 82, 77, 14);
		contentPane.add(lblEmail);
		
		JLabel lblEmail_1 = new JLabel("Email");
		lblEmail_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblEmail_1.setBounds(99, 107, 46, 14);
		contentPane.add(lblEmail_1);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblDataDeNascimento.setBounds(99, 132, 121, 14);
		contentPane.add(lblDataDeNascimento);
		
		JLabel lblNewLabel_1 = new JLabel("Pa\u00EDs");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNewLabel_1.setBounds(99, 157, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Criar Conta");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String passText = new String(passwordField.getPassword());
				if(connect.criaConta(textField.getText(), textField_1.getText(), passText, textField_3.getText(), textField_4.getText(), textField_2.getText())) {
					JOptionPane.showMessageDialog(null, "Conta criada com sucesso");
					AppView av = null;
					try {
						av = new AppView();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					av.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Username já utilizado!");
				}
			}
		});
		btnNewButton_1.setBounds(245, 208, 114, 23);
		contentPane.add(btnNewButton_1);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty() && passwordField.getPassword().length != 0 && !textField_2.getText().isEmpty() && !textField_4.getText().isEmpty() && !textField_3.getText().isEmpty()) {
					btnNewButton_1.setEnabled(true);
				} else {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		textField.setBounds(184, 30, 161, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty() && passwordField.getPassword().length != 0 && !textField_2.getText().isEmpty() && !textField_4.getText().isEmpty() && !textField_3.getText().isEmpty()) {
					btnNewButton_1.setEnabled(true);
				} else {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		textField_1.setBounds(184, 55, 161, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty() && passwordField.getPassword().length != 0 && !textField_2.getText().isEmpty() && !textField_4.getText().isEmpty() && !textField_3.getText().isEmpty()) {
					btnNewButton_1.setEnabled(true);
				} else {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		passwordField.setBounds(184, 80, 161, 20);
		contentPane.add(passwordField);
		
		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty() && passwordField.getPassword().length != 0 && !textField_2.getText().isEmpty() && !textField_4.getText().isEmpty() && !textField_3.getText().isEmpty()) {
					btnNewButton_1.setEnabled(true);
				} else {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		textField_2.setBounds(184, 105, 161, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(103, 180, 1, 2);
		contentPane.add(separator);
		
		textField_4 = new JTextField();
		textField_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty() && passwordField.getPassword().length != 0 && !textField_2.getText().isEmpty() && !textField_4.getText().isEmpty() && !textField_3.getText().isEmpty()) {
					btnNewButton_1.setEnabled(true);
				} else {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		textField_4.setBounds(230, 130, 114, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(!textField.getText().isEmpty() && !textField_1.getText().isEmpty() && passwordField.getPassword().length != 0 && !textField_2.getText().isEmpty() && !textField_4.getText().isEmpty() && !textField_3.getText().isEmpty()) {
					btnNewButton_1.setEnabled(true);
				} else {
					btnNewButton_1.setEnabled(false);
				}
			}
		});
		textField_3.setBounds(184, 155, 161, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jo\u00E3oNuno\\Desktop\\stock-market-tradingnew.png"));
		lblNewLabel.setBounds(0, 0, 434, 261);
		contentPane.add(lblNewLabel);
	}
}
