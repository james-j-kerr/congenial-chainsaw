package mas.diseasespread;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import mas.diseasespread.app.Simulator;
import mas.diseasespread.data.FieldStats;
import mas.diseasespread.data.GUIParams;
import mas.diseasespread.data.SimulationParams;
import mas.diseasespread.environment.GridField;
import mas.diseasespread.views.components.LabelledCheckBox;
import mas.diseasespread.views.components.LabelledTextArea;

public final class App {

  private static final SimulationParams params = SimulationParams.getInstance();
  private JFrame frameMain;
  private LabelledTextArea simLength;
	private LabelledTextArea simSeed;
	private LabelledTextArea mapWidth;
	private LabelledTextArea mapDepth;
	private LabelledTextArea agentCreationProb;
	private LabelledTextArea agentZeroCreationProb;
	private LabelledTextArea infectiousness;
	private LabelledTextArea symptomaticProb;
	private LabelledTextArea maskWearingReduction;
	private LabelledTextArea agentCompliance;
	private LabelledCheckBox socialDistancing;
	private LabelledCheckBox maskMandate;
	private LabelledCheckBox quarantining;
	private JButton setUpButton;
	private JButton tickOnceButton;
	private JButton tickToEndButton;
	private JButton runIterationsButton;
	private JButton resetButton;
	private JButton quitButton;
	private String defaultSimLength = String.valueOf(params.RUNTIME);
	private String defaultSimSeed = String.valueOf(params.SEED);
	private String defaultSimSize = String.valueOf(params.WIDTH);
	private String defaultInfectiousness = String.valueOf(params.INFECTIVITY);
	private String defaultAsymptomaticProb = String.valueOf(params.ASYMPTOMATIC_RATE);
	private String defaultMaskReduction = String.valueOf(params.MASK_RISK_REDUCTION);
	private String defaultCompliance = String.valueOf(params.AGENT_COMPLIANCE);
	private String agentProb = String.valueOf(params.AGENT_DENSITY);
	private String agentZeroProb = String.valueOf(params.AGENT_ZERO_DENSITY);
	private Simulator simulator;

    private App() {
      setUpButton = new JButton();
		  tickOnceButton = new JButton();
		  tickToEndButton = new JButton();
		  runIterationsButton = new JButton();
		  resetButton = new JButton();
		  quitButton = new JButton();

		  // sim params
		  simLength = new LabelledTextArea("Simulation Duration: ", this.defaultSimLength);
		  simSeed = new LabelledTextArea("Simulation Seed: ", this.defaultSimSeed);
		  mapWidth = new LabelledTextArea("Map Width: ", this.defaultSimSize);
		  mapDepth = new LabelledTextArea("Map Depth: ", this.defaultSimSize);

		  // agent params
		  agentCreationProb = new LabelledTextArea("Agent Prob.: ", this.agentProb);
		  agentZeroCreationProb = new LabelledTextArea("Agent Zero Prob.: ", this.agentZeroProb);

		  // disease params
		  infectiousness = new LabelledTextArea("Infectivity: ", this.defaultInfectiousness);
		  symptomaticProb = new LabelledTextArea("Asymptomatic Prob.: ", this.defaultAsymptomaticProb);
		  maskWearingReduction = new LabelledTextArea("Mask Risk Reduction: ", this.defaultMaskReduction);

		  // compliance params
		  agentCompliance = new LabelledTextArea("Agent Compliance: ", this.defaultCompliance);

		  // policy options
		  socialDistancing = new LabelledCheckBox("Social Distancing: ", false);
		  maskMandate = new LabelledCheckBox("Mask Mandate: ", false);
		  quarantining = new LabelledCheckBox("Quarantining: ", false);

		  setUpButton.setText("Set up simulation");
		  setUpButton.setToolTipText("Feed simulation parameters and set up simulation.");
		  setUpButton.setEnabled(true);
		  tickOnceButton.setText("Step Once");
		  tickOnceButton.setToolTipText("Run simulation for only one step.");
		  tickOnceButton.setEnabled(false);
		  tickToEndButton.setText("Run");
		  tickToEndButton.setToolTipText("Run simulation for the duration specified.");
		  tickToEndButton.setEnabled(false);
		  runIterationsButton.setText("Run for X");
		  runIterationsButton.setToolTipText("Run a number of iterations of simulation.");
		  runIterationsButton.setEnabled(false);
		  resetButton.setText("Reset");
		  resetButton.setToolTipText("Allow changing of the parameters.");
		  resetButton.setEnabled(false);
		  quitButton.setText("Quit");
		  quitButton.setToolTipText("Quit simulation.");

		  frameMain = new JFrame("Disease Simulation Setup");
		  frameMain.setLocation(GUIParams.SETUP_X, GUIParams.SETUP_Y);

		  JPanel simParamsBox = new JPanel();
		  JPanel commandBox = new JPanel();
		  JPanel agentBox = new JPanel();
		  JPanel entitiesBox = new JPanel();
		  JPanel policiesBox = new JPanel();
		  JPanel diseaseBox = new JPanel();
		  JPanel complianceBox = new JPanel();
		  JPanel lowerBox = new JPanel();
		  JPanel middleBox = new JPanel();
		  lowerBox.setBorder(BorderFactory.createEtchedBorder());
		  middleBox.setBorder(BorderFactory.createEtchedBorder());

		  // add borders
		  simParamsBox.setBorder(new TitledBorder("Simulation Parameters"));
		  agentBox.setBorder(new TitledBorder("Agents"));
		  policiesBox.setBorder(new TitledBorder("Policies"));
		  diseaseBox.setBorder(new TitledBorder("Disease"));
		  complianceBox.setBorder(new TitledBorder("Compliance"));

		  // set layout
		  frameMain.getContentPane().setLayout(new BorderLayout());
		  simParamsBox.setLayout(new GridLayout(2,2));
		  agentBox.setLayout(new GridLayout(2,1));
		  policiesBox.setLayout(new GridLayout(3,1));
		  commandBox.setLayout(new GridLayout(5,1));
		  entitiesBox.setLayout(new BorderLayout());
		  diseaseBox.setLayout(new GridLayout(3,1));
		  complianceBox.setLayout(new GridLayout(3,1));
		  middleBox.setLayout(new BorderLayout());
		  lowerBox.setLayout(new BorderLayout());

		  // add components to containers
		  commandBox.add(setUpButton);
		  commandBox.add(resetButton);
		  commandBox.add(tickOnceButton);
		  commandBox.add(tickToEndButton);
		  commandBox.add(runIterationsButton);
		  commandBox.add(quitButton);

		  simParamsBox.add(simLength);
		  simParamsBox.add(mapWidth);
		  simParamsBox.add(simSeed);
		  simParamsBox.add(mapDepth);

		  agentBox.add(agentCreationProb);
		  agentBox.add(agentZeroCreationProb);

		  policiesBox.add(socialDistancing);
		  policiesBox.add(maskMandate);
		  policiesBox.add(quarantining);

		  diseaseBox.add(infectiousness);
		  diseaseBox.add(this.symptomaticProb);
		  diseaseBox.add(this.maskWearingReduction);

		  complianceBox.add(this.agentCompliance);

		  entitiesBox.add(agentBox, BorderLayout.CENTER);
		  entitiesBox.add(policiesBox, BorderLayout.WEST);
		  middleBox.add(complianceBox, BorderLayout.CENTER);
		  middleBox.add(diseaseBox, BorderLayout.WEST);
		  lowerBox.add(entitiesBox, BorderLayout.NORTH);
		  lowerBox.add(middleBox, BorderLayout.CENTER);
		  lowerBox.add(commandBox, BorderLayout.SOUTH);

		  frameMain.getContentPane().add(simParamsBox, BorderLayout.NORTH);
		  frameMain.getContentPane().add(lowerBox, BorderLayout.SOUTH);

      frameMain.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      frameMain.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          exitApp();
        }
      });

      quitButton.addActionListener(e -> {
        exitApp();
      });

      resetButton.addActionListener(e -> {
        reset();
      });

      tickOnceButton.addActionListener(e -> {
        new Thread(() -> {
          tickOnce();
          frameMain.setVisible(true);
        }).start();
        frameMain.setVisible(false);
      });

      tickToEndButton.addActionListener(e -> {
        new Thread(() -> {
          tickToEnd();
          frameMain.setVisible(true);
        }).start();
        frameMain.setVisible(false);
      });

      setUpButton.addActionListener(e -> {
        new Thread(() -> {
          setup();
          frameMain.setVisible(true);
        }).start();
        frameMain.setVisible(false);
      });

      frameMain.pack();
      frameMain.setVisible(true);
    }

    private void exitApp() {
      int response = JOptionPane.showConfirmDialog(
        frameMain,
        "Do you really want to quit?",
        "Quit?",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
      if (response == JOptionPane.YES_OPTION) {
        System.exit(0);
      }
    }

    private void setup() {
      try {
        int runtime = Integer.parseInt(simLength.getValue());
        int seed = Integer.parseInt(simSeed.getValue());
        int width = Integer.parseInt(mapWidth.getValue());
        int depth = Integer.parseInt(mapDepth.getValue());
        double agentDensity = Double.parseDouble(agentCreationProb.getValue());
        double agentZeroDensity = Double.parseDouble(agentZeroCreationProb.getValue());
        boolean distancing = socialDistancing.getValue();
        boolean masking = maskMandate.getValue();
        boolean quarantine = quarantining.getValue();
        params.RUNTIME = runtime;
        params.SEED = seed;
        params.WIDTH = width;
        params.DEPTH = depth;
        params.AGENT_DENSITY = agentDensity;
        params.AGENT_ZERO_DENSITY = agentZeroDensity;
        params.SOCIAL_DISTANCING = distancing;
        params.MASKING = masking;
        params.QUARANTINING = quarantine;

        GridField field = new GridField(params.DEPTH, params.WIDTH);
        FieldStats fieldStats = new FieldStats();
        simulator = new Simulator(field, fieldStats);

        setUpButton.setEnabled(false);
        tickOnceButton.setEnabled(true);
        tickToEndButton.setEnabled(true);
        resetButton.setEnabled(true);

      } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(frameMain, "Problem setting up simulation. " + e.getMessage());
      }
    }

    private void tickOnce() {
      simulator.tick();
    }

    private void tickToEnd() {
      while (!simulator.hasFinished()) {
        simulator.tick();
      }
    }

    private void reset() {
      if (simulator == null) return;
      simulator.close();
      setUpButton.setEnabled(true);
      tickOnceButton.setEnabled(false);
      tickToEndButton.setEnabled(false);
    }

    public static void main(String[] args) {
      new App();
    }
}
