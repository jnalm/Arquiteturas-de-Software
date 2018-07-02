import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField txtIdDoAtivo;
	private JTextField textField;
	private float temp;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
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
	public MenuPrincipal() throws SQLException, IOException {
		AppESS connect = new AppESS();
		String passText = new String(AppView.getPasswordField().getPassword());
		connect.fazLogin(AppView.getTextField().getText(), passText);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Ver Ativos em Venda");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AtivosVenda av = null;
				try {
					av = new AtivosVenda();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				av.setVisible(true);
			}
		});
		btnNewButton.setBounds(0, 0, 172, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Ver Ativos em Compra");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AtivosCompra ac = null;
				try {
					ac = new AtivosCompra();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ac.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(182, 0, 172, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Ver Portfolio");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewPortfolio vp = new ViewPortfolio();
				vp.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(472, 0, 172, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Logout");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect.logout();
				dispose();
			}
		});
		btnNewButton_3.setBounds(892, 0, 92, 23);
		contentPane.add(btnNewButton_3);
		
		textField = new JTextField();
		textField.setBounds(423, 71, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Adicionar ao Portfolio");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect.addAtivo(textField.getText());
				textField.setText(null);
			}
		});
		btnNewButton_4.setBounds(519, 71, 164, 23);
		contentPane.add(btnNewButton_4);
		
		JButton btnAbrirCompra = new JButton("Abrir Compra");
		btnAbrirCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefineCompra dc = null;
				try {
					dc = new DefineCompra();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dc.setVisible(true);
			}
		});
		btnAbrirCompra.setBounds(279, 49, 122, 23);
		contentPane.add(btnAbrirCompra);
		
		JButton btnAbrirVenda = new JButton("Abrir Venda");
		btnAbrirVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefineVenda dv = null;
				try {
					dv = new DefineVenda();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dv.setVisible(true);
			}
		});
		btnAbrirVenda.setBounds(279, 86, 122, 23);
		contentPane.add(btnAbrirVenda);
		
		JButton btnRefresh = new JButton("Refresh Ativos");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					table.setModel(connect.transformaTabela());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRefresh.setBounds(10, 121, 126, 23);
		contentPane.add(btnRefresh);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(10, 30, 185, 42);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setText("PLAFOND: " + connect.getPlafond(connect.getUserLogin()) + "€");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(97, 77, 46, 14);
		contentPane.add(lblNewLabel_2);
		lblNewLabel_2.setText("( " + connect.getPlafondInvestido(connect.getUserLogin()) + " )");
		
		JButton button = new JButton("Refresh Plafond");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNewLabel_1.setText("PLAFOND: " + connect.getPlafond(connect.getUserLogin()) + "€");
				lblNewLabel_2.setText("( " + connect.getPlafondInvestido(connect.getUserLogin()) + " )");
			}
		});
		button.setBounds(10, 98, 126, 23);
		contentPane.add(button);
		
		JButton btnNewButton_5 = new JButton("Adicionar Fundos");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float fundos = Float.parseFloat(textField_1.getText());
				connect.addFundos(fundos);
				lblNewLabel_1.setText("PLAFOND: " + connect.getPlafond(connect.getUserLogin()) + "€");
				textField_1.setText(null);
			}
		});
		btnNewButton_5.setBounds(772, 527, 138, 23);
		contentPane.add(btnNewButton_5);
		
		textField_1 = new JTextField();
		textField_1.setBounds(676, 527, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(703, 57, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_6 = new JButton("Adicionar à Watchlist");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect.addWatchlist(textField_2.getText());
				textField_2.setText(null);
			}
		});
		btnNewButton_6.setBounds(799, 56, 164, 23);
		contentPane.add(btnNewButton_6);
		
		JButton button_1 = new JButton("Remover da Watchlist");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				connect.removeWatchlist(textField_3.getText());
				textField_3.setText(null);
			}
		});
		button_1.setBounds(799, 86, 164, 23);
		contentPane.add(button_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(703, 87, 86, 20);
		contentPane.add(textField_3);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 144, 964, 272);
		contentPane.add(scrollPane);
		
		table = new JTable(connect.transformaTabela());
		scrollPane.setViewportView(table);
		table.setEnabled(false);
				
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jo\u00E3oNuno\\Desktop\\stock.market.tradingnewnew.jpg"));
		lblNewLabel.setBounds(0, -262, 984, 1100);
		contentPane.add(lblNewLabel);
	}
}
