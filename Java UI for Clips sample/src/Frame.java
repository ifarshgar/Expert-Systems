import java.awt.Component;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;
import net.sf.clipsrules.jni.PrimitiveValue;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame {

	private JFrame frmClipsSampleUi;
	private static Environment clips;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			clips = new Environment();
			clips.load("a.clp");
			clips.reset();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} 
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					Frame window = new Frame();
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					for (Window w : JFrame.getWindows()) {
						SwingUtilities.updateComponentTreeUI(w);
					}
					window.frmClipsSampleUi.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private JTextArea textArea;
	private JComboBox<String> comboBox;
	
	private void initialize() {
		frmClipsSampleUi = new JFrame();
		frmClipsSampleUi.setTitle("Clips Sample UI");
		frmClipsSampleUi.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frmClipsSampleUi.setBounds(100, 100, 529, 331);
		frmClipsSampleUi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);

		JButton btnSeeResults = new JButton("See Results");
		btnSeeResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				String s = (String) comboBox.getSelectedItem();
				
				clips.reset();
				clips.assertString("(student (study " + s + "))");
				clips.run();
								
				MultifieldValue mv = (MultifieldValue) clips.eval("(find-all-facts ((?r result)) TRUE)");
				try {
					FactAddressValue fact = (FactAddressValue) mv.multifieldValue().get(0);
					PrimitiveValue pv = fact.getFactSlot("value");
					String ss = pv.toString();
					textArea.setText(ss);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frmClipsSampleUi.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
						.createParallelGroup(Alignment.LEADING).addGroup(
								groupLayout
										.createSequentialGroup().addGroup(groupLayout
												.createParallelGroup(Alignment.TRAILING)
												.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
														493, Short.MAX_VALUE)
												.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 493,
														Short.MAX_VALUE))
										.addContainerGap())
						.addGroup(Alignment.TRAILING,
								groupLayout.createSequentialGroup().addComponent(btnSeeResults).addGap(210)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSeeResults)
						.addContainerGap(29, Short.MAX_VALUE)));

		textArea = new JTextArea();
		textArea.setText("Welcome!\r\n\r\n(hint)\r\n1. select Yes or No\r\n2. click on the results button ");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(textArea,
				GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(textArea,
				GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE));
		panel.setLayout(gl_panel);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Yes", "No" }));
//		comboBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
		
		JLabel lblSelectInput = new JLabel("Assume you are a student, Do you study well? ");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(
						gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addGap(19).addComponent(lblSelectInput)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(comboBox,
												GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
										.addContainerGap(267, Short.MAX_VALUE)));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(lblSelectInput).addComponent(
						comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		frmClipsSampleUi.getContentPane().setLayout(groupLayout);
		frmClipsSampleUi
				.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { comboBox, btnSeeResults }));
	}
}
