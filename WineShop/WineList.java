package WineShop;

import java.util.ArrayList;

public class WineList {
    ArrayList<Wine> wineList;
    public WineList() {
        wineList = new ArrayList<>();

        wineList.add(new Wine("Chateau Margaux", 500000, WineType.RED, WineCountry.FRANCE, "Chateau Margaux", "Cabernet Sauvignon", 13.5, 2015, 10));
        wineList.add(new Wine("Sassicaia", 300000, WineType.RED, WineCountry.ITALY, "Tenuta San Guido", "Cabernet Sauvignon", 14.0, 2016, 15));
        wineList.add(new Wine("Vega Sicilia Unico", 450000, WineType.RED, WineCountry.SPAIN, "Vega Sicilia", "Tempranillo", 14.5, 2014, 8));
        wineList.add(new Wine("Domaine de la Romanee-Conti", 1200000, WineType.RED, WineCountry.FRANCE, "DRC", "Pinot Noir", 13.0, 2013, 5));
        wineList.add(new Wine("Ornellaia", 350000, WineType.RED, WineCountry.ITALY, "Tenuta dell'Ornellaia", "Merlot", 14.0, 2017, 12));
        wineList.add(new Wine("Pingus", 800000, WineType.RED, WineCountry.SPAIN, "Dominio de Pingus", "Tempranillo", 15.0, 2012, 6));
    }
}
