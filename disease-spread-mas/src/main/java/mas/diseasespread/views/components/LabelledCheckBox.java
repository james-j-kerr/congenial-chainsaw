package mas.diseasespread.views.components;

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class LabelledCheckBox extends JComponent {

  private JLabel label;
  private JCheckBox checkBox;

  public LabelledCheckBox(String text, boolean value) {
    this.label = new JLabel(text);
    this.checkBox = new JCheckBox();
    this.checkBox.setSelected(value);
    this.setLayout(new BorderLayout());
    this.add(this.label, BorderLayout.WEST);
    this.add(this.checkBox, BorderLayout.EAST);
    this.checkBox.setBorder(
      new CompoundBorder(
        new EmptyBorder(10, 10, 10, 10),
        new EtchedBorder()));
  }

  public boolean getValue() {
    return checkBox.isSelected();
  }
}
