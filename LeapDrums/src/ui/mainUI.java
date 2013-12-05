package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;

public class mainUI {

	public JFrame frame;

	/**
	 * Create the application.
	 */
	public mainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 12));
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setLayout(new BorderLayout(0, 20));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(0, 20));
		
		JLabel lblLeapMotionDrums = new JLabel("Leap Motion Drums");
		lblLeapMotionDrums.setForeground(Color.WHITE);
		lblLeapMotionDrums.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 20));
		lblLeapMotionDrums.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblLeapMotionDrums, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel_2, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel_3, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(new GridLayout(0, 3, 10, 0));
		
		JButton kickBtn = new JButton("Kick");
		kickBtn.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 18));
		panel.add(kickBtn);
		
		JButton snareBtn = new JButton("Snare");
		snareBtn.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 18));
		panel.add(snareBtn);
		
		JButton hatBtn = new JButton("Hi-Hat");
		hatBtn.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 18));
		panel.add(hatBtn);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
