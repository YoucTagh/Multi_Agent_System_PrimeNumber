/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prime.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.math.BigInteger;
import java.util.ArrayList;
import prime.behaviours.DevideAndAssign;

/**
 *
 * @author YoucTagh
 *
 */
/*
Test With
    - 20988936657440586486151264256610222593863921
    - 123457
    - 123457² = 15241630849
 */
public class ControllerAgent extends Agent {

    private BigInteger numberToCheck;
    private AID[] calculAgents;
    private DFAgentDescription[] agents;

    @Override
    protected void setup() {
        System.out.println("Hi ! Controller_Agent " + getAID().getName() + " Ready.");
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            numberToCheck = new BigInteger((String) args[0]);
            System.out.println("Number to check : " + numberToCheck);
            addBehaviour(new Behaviour() {

                @Override
                public void action() {
                    DFAgentDescription template = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("number-primal");
                    template.addServices(sd);
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, template);
                        System.out.println("Found the following Calcul_Agents :");
                        calculAgents = new AID[result.length];
                        for (int i = 0; i < result.length; ++i) {
                            calculAgents[i] = result[i].getName();
                            System.out.println(calculAgents[i].getName());
                        }
                        agents = result;
                    } catch (FIPAException fe) {
                        fe.printStackTrace();
                    }
                    myAgent.addBehaviour(
                            new DevideAndAssign(numberToCheck, calculAgents));
                }

                @Override
                public boolean done() {
                    return true;
                }
            });
        } else {
            System.out.println("You need to give a number to check !!");
            
        }

    }

    @Override
    protected void takeDown() {
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }
}
