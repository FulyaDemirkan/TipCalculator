package sheridan.demirkaf.demirkaf_assignment2;

public class TipCalculator {

    private float billAmount;
    private float tipPercentage;
    private boolean isRounded;

    private float calculatedTip;
    private float calculatedTotal;

    public float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    public float getTipPercentage() {
        return tipPercentage;
    }

    public void setTipPercentage(float tipPercentage) {
        this.tipPercentage = tipPercentage;
    }

    public boolean isRounded() {
        return isRounded;
    }

    public void setRounded(boolean rounded) {
        isRounded = rounded;
    }

    public float getCalculatedTip() {
        return calculatedTip;
    }

    public float getCalculatedTotal() {
        return calculatedTotal;
    }

    public float calculateTipAmount()
    {
        calculatedTip = billAmount * tipPercentage;
        if(isRounded)
        {
            calculatedTip = (float)Math.ceil(calculatedTip);
        }
        return calculatedTip;
    }

    public float calculateTotalAmount()
    {
        calculatedTip = calculateTipAmount();

        calculatedTotal =  billAmount + calculatedTip;

        return calculatedTotal;
    }
}
