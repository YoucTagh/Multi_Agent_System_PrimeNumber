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
import prime.utils.Pack;

import java.io.IOException;
import java.math.BigInteger;

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
                    BigInteger rangeFrom=null;
                    BigInteger rangeTo=null;
                    Pack pack = new Pack(numberToCheck);

                    if (i != calculAgents.length - 1) {
                        rangeFrom=range.multiply(new BigInteger("" + i));
                        rangeTo=range.multiply(new BigInteger("" + (i + 1)));
                    } else {
                        //To take all the remaining range to check
                        rangeFrom=range.multiply(new BigInteger("" + (calculAgents.length - 1)));
                        rangeTo = range.multiply(new BigInteger("" + (calculAgents.length)));
                    }

                    pack.setRangeFrom(rangeFrom);
                    pack.setRangeTo(rangeTo);
                    rqst.addReceiver(calculAgents[i]);
                    rqst.setConversationId("num_prim");
                    rqst.setReplyWith("rqst_Youc");
                    try {
                        rqst.setContentObject(pack);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myAgent.send(rqst);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("num_prim"),
                            MessageTemplate.MatchInReplyTo(rqst.getReplyWith()));
                }

                step = 1;
                break;

            case 1:
                ACLMessage reply =myAgent.receive(mt);
                if (reply != null) {

                    if(reply.getPerformative()==ACLMessage.DISCONFIRM){
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
