package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MyStatusBar extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int STATUS_BAR_HEIGHT = 20;
	private JLabel length;
	private JLabel caretPosition;
	private JLabel dateAndTime;

	public MyStatusBar() {
		setLayout(new GridLayout(1, 3));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setPreferredSize(new Dimension(getWidth(), STATUS_BAR_HEIGHT));

		initStatusBar();
	}

	private void initStatusBar() {
		length = new JLabel(" length: 0");
		length.setPreferredSize(new Dimension((int) (getWidth() / 3.0), getHeight()));
		length.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		caretPosition = new JLabel("Ln: 0   Col: 0   Sel: 0");
		caretPosition.setPreferredSize(new Dimension((int) (getWidth() / 3.0), getHeight()));
		caretPosition.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		caretPosition.setHorizontalAlignment(SwingConstants.CENTER);

		add(length);
		add(caretPosition);
		initDate();
	}

	private void initDate() {
		dateAndTime = new JLabel();
		dateAndTime.setPreferredSize(new Dimension((int) (getWidth() / 3.0), getHeight()));
		dateAndTime.setHorizontalAlignment(SwingConstants.RIGHT);

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
		Timer timer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar calendar = Calendar.getInstance();
				dateAndTime.setText(format.format(calendar.getTime()));
			}
		});

		timer.setRepeats(true);
		timer.setInitialDelay(0);
		timer.start();
		add(dateAndTime);
	}
	
	public void setDefaultValues(){
		length.setText(" length: 0");
		caretPosition.setText("Ln: 0   Col: 0   Sel: 0");
	}
	
	public void setLengthText(String text) {
		this.length.setText(text);
	}
	
	public void setCaretPositionText(String text) {
		this.caretPosition.setText(text);
	}
}
