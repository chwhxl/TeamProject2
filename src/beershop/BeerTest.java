package beershop;

public class BeerTest {

	public static void main(String[] args) {
		BeerList.loadBeerData();
		for (Beer beer : BeerList.getBeerList()) {
			System.out.println(beer.getImgPath());
		}

	}
	
	

}
