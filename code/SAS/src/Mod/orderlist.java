package Mod;

public class orderlist {


    private int sno;
    private String prod_id;
    private String prod_name;
    private int unit_price;
    private double amt;
    private int qty;

    public orderlist(int sno, String prod_id,String prod_name,int unit_price,int qty, double amt) {

        this.setSno(sno);
        this.setProd_id(prod_id);
        this.setProd_name(prod_name);
        this.setUnit_price(unit_price);
        this.setQty(qty);
        this.setAmt(amt);
    }


    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getProd_id() {
        return prod_id;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
