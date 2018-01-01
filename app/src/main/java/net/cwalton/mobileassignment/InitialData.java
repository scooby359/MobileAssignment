package net.cwalton.mobileassignment;

/**
 * Created by scoob on 24/12/2017.
 * Contains initial data used to populate the Database on first run. No further use after that.
 */

public class InitialData {

    private final int NUM_COUNTRIES = 10;
    private final int NUM_CITIES = 50;
    private final String country = Location.LOC_TYPE_COUNTRY;
    private final String city = Location.LOC_TYPE_CITY;
    private final String favFalse = Location.LOC_FAV_FALSE;
    private final String notes = "";


    public Country[] getInitialCountries() {

        Country[] Countries = new Country[NUM_COUNTRIES];

        Countries[0] = new Country("United Kingdom", country, "https://en.wikipedia.org/wiki/United_kingdom", favFalse, notes, "Pound Sterling", "English", "London",null,"gb");
        Countries[1] = new Country("United States",	 country, "https://en.wikipedia.org/wiki/United_States", favFalse, notes, "US Dollar", "English", "Washington, D.C.",null,"us");
        Countries[2] = new Country("Germany",country,"https://en.wikipedia.org/wiki/Germany",favFalse, notes, "Euro","German","Berlin",null,"de");
        Countries[3] = new Country("France",country,"https://en.wikipedia.org/wiki/France",favFalse, notes, "Euro","French","Paris",null,"fr");
        Countries[4] = new Country("Spain",country,"https://en.wikipedia.org/wiki/Spain",favFalse, notes, "Euro","Spanish","Madrid",null,"es");
        Countries[5] = new Country("Australia",country,"https://en.wikipedia.org/wiki/Australia",favFalse, notes, "Australian Dollar","English","Canberra",null,"au");
        Countries[6] = new Country("Italy",country,"https://en.wikipedia.org/wiki/Italy",favFalse, notes, "Euro","Italian","Rome",null,"it");
        Countries[7] = new Country("Poland",country,"https://en.wikipedia.org/wiki/Poland",favFalse, notes, "Euro","Polish","Warsaw",null,"pl");
        Countries[8] = new Country("Belgium",country,"https://en.wikipedia.org/wiki/Belgium",favFalse, notes, "Euro","Dutch","Brussels",null,"be");
        Countries[9] = new Country("India",country,"https://en.wikipedia.org/wiki/India",favFalse, notes, "Indian Rupee","Hindi","New Delhi",null,"in");

        return Countries;
    }

    public City[] getInitialCities() {

        City[] Cities = new City[NUM_CITIES];

        Cities[0] = new City(	"London",	city,	"https://en.wikipedia.org/wiki/London",	favFalse, notes, "United Kingdom",	"8,787,892",	"Heathrow (LHR)");
        Cities[1] = new City(	"Birmingham",	city,	"https://en.wikipedia.org/wiki/Birmingham",	favFalse, notes, "United Kingdom",	"1,124,600",	"Birmingham (BHX)");
        Cities[2] = new City(	"Leeds",	city,	"https://en.wikipedia.org/wiki/City_of_Leeds",favFalse, notes, 	"United Kingdom",	"781,700",	"Leeds (LBA)");
        Cities[3] = new City(	"Glasgow",	city,	"https://en.wikipedia.org/wiki/Glasgow",favFalse, notes, 	"United Kingdom",	"615,070",	"Glasgow (GLA)");
        Cities[4] = new City(	"Manchester",	city,	"https://en.wikipedia.org/wiki/Manchester",favFalse, notes, 	"United Kingdom",	"541,300",	"Manchester (MAN)");
        Cities[5] = new City(	"Washington, DC",	city,	"https://en.wikipedia.org/wiki/Washington,_D.C.",favFalse, notes, 	"United States",	"681,170",	"Ronald Reagan Washington National Airport (DCA)");
        Cities[6] = new City(	"New York City",	city,	"https://en.wikipedia.org/wiki/New_York_City",favFalse, notes, 	"United States",	"8,537,673",	"John F. Kennedy International Airport (JFK)");
        Cities[7] = new City(	"Los Angeles",	city,	"https://en.wikipedia.org/wiki/Los_Angeles",favFalse, notes, 	"United States",	"3,976,322",	"Los Angeles International Airport (LAX)");
        Cities[8] = new City(	"Chicago",	city,	"https://en.wikipedia.org/wiki/Chicago",favFalse, notes, 	"United States",	"2,704,958",	"O'Hare International Airport (ORD)");
        Cities[9] = new City(	"Houston",	city,	"https://en.wikipedia.org/wiki/Houston",favFalse, notes, 	"United States",	"2,303,482",	"George Bush Intercontinental Airport (IAH)");
        Cities[10] = new City(	"Berlin",	city,	"https://en.wikipedia.org/wiki/Berlin",favFalse, notes, 	"Germany",	"3,520,031",	"Berlin Tegel Airport (TXL)");
        Cities[11] = new City(	"Hamburg",	city,	"https://en.wikipedia.org/wiki/Hamburg",favFalse, notes, 	"Germany",	"1,787,408",	"Hamburg Airport (HAM)");
        Cities[12] = new City(	"Munich",	city,	"https://en.wikipedia.org/wiki/Munich",favFalse, notes, 	"Germany",	"1,450,381",	"Munich Airport (MUC)");
        Cities[13] = new City(	"Cologne",	city,	"https://en.wikipedia.org/wiki/Cologne",favFalse, notes, 	"Germany",	"1,060,582",	"Cologne Bonn Airport (CGN)");
        Cities[14] = new City(	"Frankfurt",	city,	"https://en.wikipedia.org/wiki/Frankfurt_am_Main",favFalse, notes, 	"Germany",	"732,688",	"Frankfurt Airport (FRA)");
        Cities[15] = new City(	"Paris",	city,	"https://en.wikipedia.org/wiki/Paris",favFalse, notes, 	"France",	"2,229,621",	"Charles de Gaulle Airport (CDG)");
        Cities[16] = new City(	"Marseille",	city,	"https://en.wikipedia.org/wiki/Marseille",favFalse, notes, 	"France",	"855,393",	"Marseille Provence Airport (MRS)");
        Cities[17] = new City(	"Lyon",	city,	"https://en.wikipedia.org/wiki/Lyon",favFalse, notes, 	"France",	"500,715",	"Lyon–Saint-Exupéry Airport (LYS)");
        Cities[18] = new City(	"Toulouse",	city,	"https://en.wikipedia.org/wiki/Toulouse",favFalse, notes, 	"France",	"458,298",	"Toulouse–Blagnac Airport (TLS)");
        Cities[19] = new City(	"Nice",	city,	"https://en.wikipedia.org/wiki/Nice",favFalse, notes, 	"France",	"342,295",	"Nice Côte d'Azur Airport (NCE)");
        Cities[20] = new City(	"Madrid",	city,	"https://en.wikipedia.org/wiki/Madrid",favFalse, notes, 	"Spain",	"3,165,235",	"Adolfo Suárez Madrid–Barajas Airport (MAD)");
        Cities[21] = new City(	"Barcelona",	city,	"https://en.wikipedia.org/wiki/Barcelona",favFalse, notes, 	"Spain",	"1,602,386",	"Barcelona–El Prat Airport (BCN)");
        Cities[22] = new City(	"Valencia",	city,	"https://en.wikipedia.org/wiki/Valencia,_Spain",	favFalse, notes, "Spain",	"786,424",	"Valencia Airport (VLC)");
        Cities[23] = new City(	"Seville",	city,	"https://en.wikipedia.org/wiki/Seville",favFalse, notes, 	"Spain",	"696,676",	"Seville Airport (SVQ)");
        Cities[24] = new City(	"Zaragoza",	city,	"https://en.wikipedia.org/wiki/Zaragoza",favFalse, notes, 	"Spain",	"666,058",	"Zaragoza Airport (ZAZ)");
        Cities[25] = new City(	"Canberra",	city,	"https://en.wikipedia.org/wiki/Canberra",favFalse, notes, 	"Australia",	"435,019",	"Canberra Airport (CBR)");
        Cities[26] = new City(	"Sydney",	city,	"https://en.wikipedia.org/wiki/Sydney",favFalse, notes, 	"Australia",	"5,029,768",	"Sydney Airport (SYD)");
        Cities[27] = new City(	"Melbourne",	city,	"https://en.wikipedia.org/wiki/Melbourne",favFalse, notes, 	"Australia",	"4,725,316",	"Melbourne Airport (MEL)");
        Cities[28] = new City(	"Brisbane",	city,	"https://en.wikipedia.org/wiki/Brisbane",favFalse, notes, 	"Australia",	"2,360,241",	"Brisbane Airport (BNE)");
        Cities[29] = new City(	"Perth",	city,	"https://en.wikipedia.org/wiki/Perth",	favFalse, notes, "Australia",	"2,022,044",	"Perth Airport (PER)");
        Cities[30] = new City(	"Rome",	city,	"https://en.wikipedia.org/wiki/Rome",	favFalse, notes, "Italy",	"2,873,494",	"Leonardo da Vinci–Fiumicino Airport (FCO)");
        Cities[31] = new City(	"Milan",	city,	"https://en.wikipedia.org/wiki/Milan",	favFalse, notes, "Italy",	"1,351,562",	"Linate Airport (LIN)");
        Cities[32] = new City(	"Naples",	city,	"https://en.wikipedia.org/wiki/Naples",	favFalse, notes, "Italy",	"970,185",	"Naples International Airport (NAP)");
        Cities[33] = new City(	"Turin",	city,	"https://en.wikipedia.org/wiki/Turin",	favFalse, notes, "Italy",	"886,837",	"Turin Airport (TRN)");
        Cities[34] = new City(	"Palermo",	city,	"https://en.wikipedia.org/wiki/Palermo",favFalse, notes, 	"Italy",	"673,735",	"Falcone–Borsellino Airport (PMO)");
        Cities[35] = new City(	"Warsaw",	city,	"https://en.wikipedia.org/wiki/Warsaw",favFalse, notes, 	"Poland",	"1,753,977",	"Warsaw Chopin Airport (WAW)");
        Cities[36] = new City(	"Krakow",	city,	"https://en.wikipedia.org/wiki/Krak%C3%B3w",favFalse, notes, 	"Poland",	"765,320",	"John Paul II International Airport Kraków–Balice (KRK)");
        Cities[37] = new City(	"Wroclaw",	city,	"https://en.wikipedia.org/wiki/Wroc%C5%82aw",	favFalse, notes, "Poland",	"637,683",	"Copernicus Airport Wrocław (WRO)");
        Cities[38] = new City(	"Poznan",	city,	"https://en.wikipedia.org/wiki/Pozna%C5%84",	favFalse, notes, "Poland",	"540,372",	"Poznań–Ławica Airport (POZ)");
        Cities[39] = new City(	"Gdansk",	city,	"https://en.wikipedia.org/wiki/Gda%C5%84sk",favFalse, notes, 	"Poland",	"463,754",	"Gdańsk Lech Wałęsa Airport (GDN)");
        Cities[40] = new City(	"Antwerp",	city,	"https://en.wikipedia.org/wiki/Antwerp",favFalse, notes, 	"Belgium",	"510,610",	"Antwerp International Airport (ANR)");
        Cities[41] = new City(	"Ghent",	city,	"https://en.wikipedia.org/wiki/Ghent",	favFalse, notes, "Belgium",	"233,120",	"Brussels South Charleroi Airport (CRL)");
        Cities[42] = new City(	"Charleroi",	city,	"https://en.wikipedia.org/wiki/Charleroi",favFalse, notes, 	"Belgium",	"201,300",	"Brussels South Charleroi Airport (CRL)");
        Cities[43] = new City(	"Liege",	city,	"https://en.wikipedia.org/wiki/Li%C3%A8ge",favFalse, notes, 	"Belgium",	"186,805",	"Liège Airport (LGG)");
        Cities[44] = new City(	"Brussels",	city,	"https://en.wikipedia.org/wiki/City_of_Brussels",favFalse, notes, 	"Belgium",	"144,784",	"Brussels Airport (BRU)");
        Cities[45] = new City(	"New Delhi",	city,	"https://en.wikipedia.org/wiki/New_Delhi",	favFalse, notes, "India",	"257,803",	"Indira Gandhi International Airport (DEL)");
        Cities[46] = new City(	"Mumbai",	city,	"https://en.wikipedia.org/wiki/Mumbai",favFalse, notes, 	"India",	"11,978,450",	"Chhatrapati Shivaji International Airport (BOM)");
        Cities[47] = new City(	"Bangalore",	city,	"https://en.wikipedia.org/wiki/Bangalore",favFalse, notes, 	"India",	"4,301,326",	"Kempegowda International Airport (BLR)");
        Cities[48] = new City(	"Hyderabad",	city,	"https://en.wikipedia.org/wiki/Hyderabad,_India",favFalse, notes, 	"India",	"3,637,483",	"Rajiv Gandhi International Airport (HYD)");
        Cities[49] = new City(	"Ahmedabad",	city,	"https://en.wikipedia.org/wiki/Ahmedabad",	favFalse, notes, "India",	"3,520,085",	"Sardar Vallabhbhai Patel International Airport (AMD)");

        return Cities;
    }


    public int getNUM_COUNTRIES() {
        return NUM_COUNTRIES;
    }

    public int getNUM_CITIES() {
        return NUM_CITIES;
    }
}
