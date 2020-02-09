package prime;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import prime.agents.CalculAgent;
import prime.agents.ControllerAgent;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        jade.core.Runtime rt = jade.core.Runtime.instance();

        Profile p = new ProfileImpl();

        AgentContainer ac = rt.createMainContainer(p);

        AgentController rmaAgent = null;


        try {
            rmaAgent = ac.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);

            String s = JOptionPane.showInputDialog(null, "how many Agent calculators you need ?  ");

            int numberOfAgentCalculators = (s == null || !s.matches("[0-9]*")) ? 0 : Integer.valueOf(s);

            for (int i = 0; i < numberOfAgentCalculators; i++) {
                AgentController calculator1 = ac.createNewAgent("c" + i, CalculAgent.class.getName(), new Object[0]);
                calculator1.start();
            }


            String numberToCheck = JOptionPane.showInputDialog(null, "now challenge your agents and give them a damn big number to check !!!  ");

            numberToCheck = (numberToCheck == null || !numberToCheck.matches("[0-9]*")) ? "" : numberToCheck;

            AgentController controller = ac.createNewAgent("controller", ControllerAgent.class.getName(), new Object[]{numberToCheck});

            rmaAgent.start();

            controller.start();


        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

    }


}
