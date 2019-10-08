package au.edu.curtin.madgameassignment;

public class Settings
{
    //Changeable settings
    private int mapW;
    private int mapH;
    private int initialMoney;
    private int salary;

    //Unchangeable
    private static final int familySize        = 4;
    private static final int shopSize          = 6;
    private static final int serviceCost       = 2;
    private static final double tax            = 0.3;
    private static final int houseCost         = 100;
    private static final int commercialCost    = 500;
    private static final int roadCost          = 20;


    public Settings()
    {
        mapW = 50;
        mapH = 10;
        initialMoney = 1000;
        salary = 10;
    }

    public void setMapH(int mapH)
    {
        this.mapH = mapH;
    }

    public void setMapW(int mapW)
    {
        this.mapW = mapW;
    }

    public void setInitialMoney(int initialMoney)
    {
        this.initialMoney = initialMoney;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }


    public int getMapH()
    {
        return mapH;
    }

    public int getMapW()
    {
        return mapW;
    }

    public int getInitialMoney()
    {
        return initialMoney;
    }

    public int getSalary()
    {
        return salary;
    }


}
