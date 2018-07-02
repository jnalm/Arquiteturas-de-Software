import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import java.awt.Color;

public class AppView {

	private JFrame frame;
	private static JTextField textField;
	private static JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppView window = new AppView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public AppView() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	AppESS connect = new AppESS();
	private void initialize() throws IOException {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton EXIT = new JButton("EXIT");
		EXIT.setBounds(318, 198, 71, 34);
		EXIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(EXIT);
		
		JLabel lblWelcomeToThe = new JLabel("ESS Ltd");
		lblWelcomeToThe.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 22));
		lblWelcomeToThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToThe.setBounds(74, 11, 297, 49);
		frame.getContentPane().add(lblWelcomeToThe);
		
		JButton btnSignUp = new JButton("SIGN UP");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AfterSignUp asp = new AfterSignUp();
				asp.main(null);
			}
		});
		btnSignUp.setBounds(45, 198, 88, 34);
		frame.getContentPane().add(btnSignUp);
		
		JLabel lblWelcomeToYour = new JLabel("Welcome to your Trade Market Tool");
		lblWelcomeToYour.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWelcomeToYour.setBounds(115, 58, 235, 14);
		frame.getContentPane().add(lblWelcomeToYour);
		
		JLabel label = new JLabel("Username");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		label.setBounds(56, 83, 144, 55);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Password");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		label_1.setBounds(56, 138, 144, 36);
		frame.getContentPane().add(label_1);
		
		setTextField(new JTextField());
		getTextField().setColumns(10);
		getTextField().setBounds(210, 101, 86, 20);
		frame.getContentPane().add(getTextField());
		
		setPasswordField(new JPasswordField());
		getPasswordField().setBounds(210, 147, 86, 20);
		frame.getContentPane().add(getPasswordField());
		
		JButton button = new JButton("Log In");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String passText = new String(getPasswordField().getPassword());
				connect.fazLogin(getTextField().getText(), passText);
				if(connect.isLogin() == true) {
					MenuPrincipal mp = null;
					try {
						mp = new MenuPrincipal(connect);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mp.main(null);
					connect.setLogin(false);
				} else {
					JOptionPane.showMessageDialog(null, "Username ou Password incorretos!");
				}
			}
		});
		button.setBounds(318, 124, 89, 23);
		frame.getContentPane().add(button);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setBounds(10, 183, 414, 2);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(10, 83, 414, 2);
		frame.getContentPane().add(separator_1);
		
		JLabel lblByJooNuno = new JLabel("by Jo\u00E3o Nuno Almeida - a75209 - 2017/2018");
		lblByJooNuno.setBounds(114, 247, 293, 14);
		frame.getContentPane().add(lblByJooNuno);
		
		JButton btnVisitante = new JButton("VISITANTE");
		btnVisitante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuVisitante mv = null;
				try {
					mv = new MenuVisitante(connect);
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mv.main(null);
			}
		});
		btnVisitante.setBounds(169, 198, 115, 34);
		frame.getContentPane().add(btnVisitante);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jo\u00E3oNuno\\Desktop\\stock-market-tradingnew.png"));
		lblNewLabel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(lblNewLabel);
	}
	
	protected void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public static JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public static JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
}
