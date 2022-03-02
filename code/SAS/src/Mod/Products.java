package Mod;

// Primary object to load data to table view
public class Products {

    public String ProductID;
    public String ProductName;
    public int Quantity;


    // constructor
    public Products(String ProductID, String ProductName, int Quantity, int ProductPrice) {

        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Quantity = Quantity;

    }

    // defining accessors and mutators
    public String getProductID()
    {
        return ProductID;
    }
    public void setProductID(String ProductID)
    {
        this.ProductID = ProductID;
    }

    public String getProductName()
    {
        return ProductName;
    }

    public void setProductName(String ProductName)
    {
        this.ProductName = ProductName;
    }
    public int getQuantity()

    {
        return Quantity;
    }

    public void setQuantity(int Quantity) {

        this.Quantity = Quantity;
    }

}