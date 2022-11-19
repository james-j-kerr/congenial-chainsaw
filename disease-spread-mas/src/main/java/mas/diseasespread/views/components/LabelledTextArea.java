package mas.diseasespread.views.components;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class LabelledTextArea extends JComponent {

  private JLabel label;
  private JTextArea textArea;

  public LabelledTextArea(String text, String value) {
    this.label = new JLabel(text);
    this.textArea = new JTextArea(value);
    this.setLayout(new BorderLayout());
    this.add(this.label, BorderLayout.WEST);
    this.add(this.textArea, BorderLayout.EAST);
    this.textArea.setBorder(
      new CompoundBorder(
        new EmptyBorder(10, 10, 10, 10),
        new EtchedBorder()));
  }

  public String getValue() {
    return textArea.getText().trim();
  }

  public void setValue(String value) {
    textArea.setText(value);
  }
}
