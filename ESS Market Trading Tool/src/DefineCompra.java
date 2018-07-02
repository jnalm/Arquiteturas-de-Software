import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class DefineCompra extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DefineCompra frame = new DefineCompra();
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
	AppESS connect = new AppESS();
	String passText = new String(AppView.getPasswordField().getPassword());
	public DefineCompra() throws SQLException, IOException, ClassNotFoundException {
		Negocio n = new Negocio();
		connect.fazLogin(AppView.getTextField().getText(), passText);
		n.setLogin(connect.getUserLogin());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 250);
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
		btnBack.setBounds(57, 162, 104, 23);
		contentPane.add(btnBack);
		
		float plafond = n.getPlafond();
		int pl = Math.round(plafond);
		int half = pl / 2;
		
		JLabel lblNewLabel_1 = new JLabel("" + half);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(297, 48, 77, 14);
		contentPane.add(lblNewLabel_1);
		
		JSlider slider = new JSlider(1, pl, half);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblNewLabel_1.setText("" + ((JSlider) e.getSource()).getValue());
			}
		});
		slider.setBounds(120, 48, 167, 26);
		contentPane.add(slider);
		
		JButton btnConfirmar = new JButton("CONFIRMAR");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ll = Integer.parseInt(textField_3.getText());
				int lp = Integer.parseInt(textField_1.getText());
				n.abrirCompra(textField.getText(), slider.getValue(), ll, lp);
				dispose();
			}
		});
		btnConfirmar.setBounds(225, 162, 104, 23);
		contentPane.add(btnConfirmar);
		
		JLabel lblIdDoAtivo = new JLabel("ID do Ativo");
		lblIdDoAtivo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIdDoAtivo.setBounds(23, 11, 85, 26);
		contentPane.add(lblIdDoAtivo);
		
		textField = new JTextField();
		textField.setBounds(120, 14, 67, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblMontanteLimiteDe = new JLabel("Montante Limite de Perda");
		lblMontanteLimiteDe.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMontanteLimiteDe.setBounds(20, 77, 167, 34);
		contentPane.add(lblMontanteLimiteDe);
		
		JLabel lblMontante = new JLabel("Montante");
		lblMontante.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMontante.setBounds(23, 48, 87, 14);
		contentPane.add(lblMontante);
		
		JLabel lblMontanteLimiteDe_1 = new JLabel("Montante Limite de Lucro");
		lblMontanteLimiteDe_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMontanteLimiteDe_1.setBounds(23, 122, 177, 14);
		contentPane.add(lblMontanteLimiteDe_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(197, 85, 55, 23);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(197, 120, 55, 23);
		contentPane.add(textField_3);
		
		JLabel label = new JLabel("\u20AC");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(364, 48, 46, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u20AC");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(261, 88, 46, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u20AC");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_2.setBounds(262, 123, 46, 14);
		contentPane.add(label_2);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jo\u00E3oNuno\\Desktop\\stock-market-tradingnew.png"));
		lblNewLabel.setBounds(0, 0, 584, 561);
		contentPane.add(lblNewLabel);
	}
}
