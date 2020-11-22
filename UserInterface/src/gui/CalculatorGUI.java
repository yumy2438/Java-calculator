package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import conversionservice.ConversionService;

public class CalculatorGUI {

	private JFrame frame;
	private JTextField numb1_textField;
	private JTextField numb2_textField;
	private JTextField result_textField;
	private ResourceBundle gui_keys;
	private ConversionService service;
	private BigInteger numb1_bi;
	private BigInteger numb2_bi;
	/**
	 * Launch the application.
	 */
	public static void start_gui(ConversionService service, Locale locale) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalculatorGUI window = new CalculatorGUI(service, locale);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalculatorGUI(ConversionService service,Locale locale) {
		this.service = service;
		this.gui_keys = ResourceBundle.getBundle("GuiKeys", locale);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 534, 299);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel(gui_keys.getString("first_number"));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 46, 111, 22);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblIkinciSay = new JLabel(gui_keys.getString("second_number"));
		lblIkinciSay.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIkinciSay.setBounds(10, 78, 111, 22);
		frame.getContentPane().add(lblIkinciSay);
		
		JLabel lblSonu = new JLabel(gui_keys.getString("result"));
		lblSonu.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSonu.setBounds(10, 110, 111, 22);
		frame.getContentPane().add(lblSonu);
		
		numb1_textField = new JTextField();
		numb1_textField.setBounds(131, 49, 332, 19);
		frame.getContentPane().add(numb1_textField);
		numb1_textField.setColumns(10);
		
		numb2_textField = new JTextField();
		numb2_textField.setColumns(10);
		numb2_textField.setBounds(131, 81, 332, 19);
		frame.getContentPane().add(numb2_textField);
		
		result_textField = new JTextField();
		result_textField.setColumns(10);
		result_textField.setBounds(131, 113, 332, 19);
		frame.getContentPane().add(result_textField);
		
		JButton btnNewButton = new JButton(gui_keys.getString("add_button"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					set_numbers();
					BigInteger result_bi = numb1_bi.add(numb2_bi);
					String result = service.convert_number_to_text(result_bi);
					result_textField.setText(result);
				}
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(frame, gui_keys.getString("number_format_error_message"));
				}
			}	
		});
		btnNewButton.setBounds(109, 150, 88, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnkar = new JButton(gui_keys.getString("subtract_button"));
		btnkar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					set_numbers();
					BigInteger result_bi = numb1_bi.subtract(numb2_bi);
					String result = service.convert_number_to_text(result_bi);
					result_textField.setText(result);
				} 
				catch (Exception e1) 
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frame, gui_keys.getString("number_format_error_message"));
				}
				
			}
		});
		btnkar.setBounds(207, 150, 88, 21);
		frame.getContentPane().add(btnkar);
		
		JButton btnarp = new JButton(gui_keys.getString("multiply_button"));
		btnarp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					set_numbers();
					BigInteger result_bi = numb1_bi.multiply(numb2_bi);
					String result = service.convert_number_to_text(result_bi);
					result_textField.setText(result);
				}
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(frame, gui_keys.getString("number_format_error_message"));
				}
				
			}
		});
		btnarp.setBounds(305, 150, 88, 21);
		frame.getContentPane().add(btnarp);
		
		JButton btnBl = new JButton(gui_keys.getString("divide_button"));
		btnBl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					set_numbers();
				    BigInteger result_bi = numb1_bi.divide(numb2_bi);
				    String result = service.convert_number_to_text(result_bi);
					result_textField.setText(result);
				}
				catch (java.lang.ArithmeticException e2)
				{
					JOptionPane.showMessageDialog(frame, gui_keys.getString("division_by_zero_error_message"));
				}
				catch (Exception e2) 
				{
					JOptionPane.showMessageDialog(frame, gui_keys.getString("number_format_error_message"));
				}
			}
		});
		btnBl.setBounds(403, 150, 88, 21);
		frame.getContentPane().add(btnBl);
	}
	
	private void set_numbers() throws Exception
	{
		String numb1 = numb1_textField.getText();
		String numb2 = numb2_textField.getText();
		this.numb1_bi = (service.convert_text_to_number(numb1.trim().toLowerCase()));
	    this.numb2_bi = (service.convert_text_to_number(numb2.trim().toLowerCase()));
	}
}
