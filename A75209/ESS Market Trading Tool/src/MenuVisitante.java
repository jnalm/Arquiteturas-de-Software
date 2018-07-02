import java.awt.BorderLayout;
import java.applet.Applet;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Handler;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Caret;

import java.awt.Font;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

public class MenuVisitante extends JFrame implements Menu {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String menuprincipal;
	private JPanel contentPane;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					AppESS connect = new AppESS();
					
					MenuVisitante mp = new MenuVisitante(connect);
					
					connect.setTm(connect.transformaTabela());
					Runnable exec = new GetTheStock(connect);
					
					new Thread(exec).start();
					MenuVisitante frame = new MenuVisitante(connect);
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
	private static AppESS connect;
	public MenuVisitante(AppESS connect) throws SQLException, IOException {
		this.connect = connect;
		connect.registerObserver(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_3 = new JButton("Back");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton_3.setBounds(892, 0, 92, 23);
		contentPane.add(btnNewButton_3);
		
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
		scrollPane.setBounds(0, 155, 964, 272);
		contentPane.add(scrollPane);
		
		table = new JTable(connect.getTm());
		scrollPane.setViewportView(table);
		table.setEnabled(false);
		
		textField_4 = new JTextField();
		textField_4.setBounds(10, 541, 322, 20);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Jo\u00E3oNuno\\Desktop\\stock.market.tradingnewnew.jpg"));
		lblNewLabel.setBounds(0, -262, 984, 1100);
		contentPane.add(lblNewLabel);
	}

	public String getMenuprincipal() {
		return menuprincipal;
	}

	public void setMenuprincipal(String menuprincipal) {
		this.menuprincipal = menuprincipal;
	}

	public App getConnect() {
		return connect;
	}

	public void setConnect(AppESS connect) {
		this.connect = connect;
	}

	public void update(TableModel tm, String change) throws SQLException, IOException {
		table.setModel(tm);
		if(change != null) {
			textField_4.setText(change);
		}
	}
}


