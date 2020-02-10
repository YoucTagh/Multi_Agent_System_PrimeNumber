package prime.utils;

import java.io.Serializable;
import java.math.BigInteger;

public class Pack implements Serializable {

    BigInteger number;
    BigInteger rangeFrom;
    BigInteger rangeTo;

    public Pack() {



    }

    public Pack(BigInteger number) {
        this.number = number;
    }

    public Pack(BigInteger number, BigInteger rangeFrom, BigInteger rangeTo) {
        this.number = number;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public BigInteger getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(BigInteger rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public BigInteger getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(BigInteger rangeTo) {
        this.rangeTo = rangeTo;
    }
}
