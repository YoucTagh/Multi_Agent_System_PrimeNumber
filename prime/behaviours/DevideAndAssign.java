/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prime.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.math.*;

/**
 *
 * @author YoucTagh
 */
public class DevideAndAssign extends Behaviour {

    private BigInteger numberToCheck;
    private AID[] calculAgents;
    private int countAnswer = 0;
    private MessageTemplate mt;
    private int step = 0;
    private boolean isPrime = true;
    public DevideAndAssign(BigInteger numberToCheck, AID[] calculAgents) {
        this.numberToCheck = numberToCheck;
        this.calculAgents = calculAgents;
    }

    @Override
    public void action() {

        if (numberToCheck.compareTo(BigInteger.valueOf(2)) == -1) {
            System.out.println("Need Integer > 1 .");
            step = 3;
            done();
        }
        switch (step) {
            case 0:
                BigInteger range = numberToCheck.divide(new BigInteger("" + calculAgents.length));
                for (int i = 0; i < calculAgents.length; ++i) {
                    ACLMessage rqst = new ACLMessage(ACLMessage.REQUEST);
                    if (i != calculAgents.length - 1) {
                        rqst.addReceiver(calculAgents[i]);
                        rqst.setContent(numberToCheck + "," + range.multiply(new BigInteger("" + i))
                                + "," + range.multiply(new BigInteger("" + (i + 1))));
                    } else {
                        //To take all the remaining range to check
                        rqst.addReceiver(calculAgents[calculAgents.length - 1]);
                        rqst.setContent(numberToCheck
                                + ","
                                + range.multiply(new BigInteger("" + (calculAgents.length - 1)))
                                + ","
                                + range.multiply(new BigInteger("" + calculAgents.length))
                        );
                    }
                    rqst.setConversationId("num_prim");
                    rqst.setReplyWith("rqst_Youc");
                    myAgent.send(rqst);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("num_prim"),
                            MessageTemplate.MatchInReplyTo(rqst.getReplyWith()));
                }

                step = 1;
                break;

            case 1:
                ACLMessage reply =myAgent.receive(mt);
                if (reply != null) {
                    if(reply.getContent().equals("false")){
                        isPrime = false;
                        step = 2;
                    }
                    countAnswer++;
                    System.out.println(countAnswer + "-" + calculAgents.length);
                    if (countAnswer == calculAgents.length) {
                        step = 2;
                    }
                }
                break;
            case 2:
                if (isPrime) {
                    System.out.println("Yeah Is Primal !!");
                } else {
                    System.out.println("No Not Primal !!");
                }
                step = 3;
                break;
        }
    }

    @Override
    public boolean done() {
        if (step == 3)
            myAgent.doDelete();
        return (step == 3);
    }


}
