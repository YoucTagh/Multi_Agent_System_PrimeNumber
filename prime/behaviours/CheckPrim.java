/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prime.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.math.BigInteger;

/**
 *
 * @author YoucTagh
 */
public class CheckPrim extends CyclicBehaviour {

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String[] cont = msg.getContent().split(",");

            BigInteger numToCheck = new BigInteger(cont[0]);
            BigInteger rangeFrom = new BigInteger(cont[1]);
            BigInteger rangeTo = new BigInteger(cont[2]);

            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            boolean answer = checkPrimInRange(numToCheck, rangeFrom, rangeTo);
            reply.setContent(answer + "");
            //System.out.println("Answer = " + answer);
            myAgent.send(reply);

        } else {
            block();
        }
    }

    private boolean checkPrimInRange(BigInteger numToCheck, BigInteger rangeFrom, BigInteger rangeTo) {
        System.out.println(numToCheck + "=> [" + rangeFrom + "," + rangeTo + "]");
        for (BigInteger i = rangeFrom; i.compareTo(rangeTo) == -1; i = i.add(BigInteger.ONE)) {
           
            if (i.compareTo(BigInteger.ZERO) == 0
                    || i.compareTo(BigInteger.ONE) == 0
                    || i.compareTo(numToCheck) == 0);
            else if (numToCheck.mod(i).compareTo(BigInteger.ZERO) == 0) {
                System.out.println(numToCheck + " Can be devided by : " + i);
                return false;
            }
        }
        return true;
    }
}
