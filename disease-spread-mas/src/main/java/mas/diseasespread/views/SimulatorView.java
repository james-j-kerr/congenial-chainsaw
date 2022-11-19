package mas.diseasespread.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import mas.diseasespread.data.GUIParams;

public class SimulatorView extends JFrame {

  private final String STEP_PREFIX = "Step #: ";
  private final String POP_PREFIX = "Population: ";
  private final JLabel stepLabel;
  private final JLabel popLabel;
  private final FieldView fieldView;
  private final HashMap<Class<?>, Color> colorMap;

  public SimulatorView(int height, int width) {

    this.setTitle("MAS Disease Spread");
    this.setLocation(GUIParams.SIM_X, GUIParams.SIM_Y);
    stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
    popLabel = new JLabel(POP_PREFIX, JLabel.LEFT);

    fieldView = new FieldView(height, width);
    colorMap = new HashMap<>();

    Container contents = getContentPane();
    contents.add(stepLabel, BorderLayout.NORTH);
    contents.add(fieldView, BorderLayout.CENTER);
    contents.add(popLabel, BorderLayout.SOUTH);

    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    this.pack();
    this.setVisible(true);
  }

  public void render(int step, Map<String, Color> blueprint) {
    this.setVisible(true);
    this.stepLabel.setText(STEP_PREFIX + step);
    this.fieldView.preparePaint();

    for (String key : blueprint.keySet()) {
      String[] splitKey = key.split(",");
      fieldView.drawMark(
        Integer.parseInt(splitKey[0]),
        Integer.parseInt(splitKey[1]),
        blueprint.get(key)
      );
    }

    this.popLabel.setText(String.valueOf(blueprint.size()));
    fieldView.repaint();
  }

  public void setColor(Class<?> clss, Color color) {
    colorMap.put(clss, color);
  }

  private class FieldView extends JPanel {

    private static final int SCALING = 6;
    private int width;
    private int height;
    private int scaleX;
    private int scaleY;
    private Dimension dimension;
    private Graphics graphics;
    private Image image;

    public FieldView(int height, int width) {
      this.height = height;
      this.width = width;
      dimension = new Dimension(0, 0);
    }

    @Override
    public Dimension getPreferredSize() {
      return new Dimension(
        width * SCALING,
        height * SCALING
      );
    }

    public void preparePaint() {
      if (!dimension.equals(getSize())) {
        dimension = getSize();
        image = this.createImage(dimension.width, dimension.height);
        graphics = image.getGraphics();

        scaleX = dimension.width / width;
        scaleY = dimension.height / height;
        if (scaleX < 1)
          scaleX = SCALING;
        if (scaleY < 1)
          scaleY = SCALING;
      }
    }

    public void drawMark(int x, int y, Color color) {
      graphics.setColor(color);
      graphics.fillRect(x * scaleX, y * scaleY, scaleX - 1, scaleY - 1);
    }

    public void paint(Graphics graphics) {
      if (image != null) {
        graphics.drawImage(image, 0, 0, null);
      }
    }

  }

}
