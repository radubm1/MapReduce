import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Furnizor{
	public String getNume() {
		return nume;
	}
	public String getOras() {
		return oras;
	}
	public int getStare() {
		return stare;
	}
	String nume;
	String oras;
	int stare;
	public Furnizor(String nume, String oras, int stare) {
		super();
		this.nume = nume;
		this.oras = oras;
		this.stare = stare;
	}
}

class Componenta{
	String denumire;
	String culoare;
	String um;
	public Componenta(String denumire, String culoare, String um) {
		super();
		this.denumire = denumire;
		this.culoare = culoare;
		this.um = um;
	}
}

class Livrare{
	public Furnizor getFz() {
		return fz;
	}
	public Componenta getCmp() {
		return cmp;
	}
	public int getCantit() {
		return cantit;
	}
	Furnizor fz;
	Componenta cmp;
	int cantit;
	public Livrare(Furnizor fz, Componenta cmp, int cantit) {
		super();
		this.fz = fz;
		this.cmp = cmp;
		this.cantit = cantit;
	}
}

class Conexiune{
static String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
static String connstr = "jdbc:ucanaccess://C:/Users/radub/Desktop/Hyp/BD/Suport BD/Seminar_BD.accdb";
Connection conn;
Statement stmt;
ResultSet rs;
ResultSet rs1;

public Conexiune() {
	super();
	deschide();
}
private void deschide() {
	try {
		Class.forName(driver);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		conn=DriverManager.getConnection(connstr);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
public void inchidere(){
	try {
		rs.close();
		rs1.close();
		stmt.close();
		conn.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}
public void importa(String sql1, String sql2){
	try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql1);
		while(rs.next()){
			Main.furnizori.add(new Furnizor(rs.getString("nume"),rs.getString("oras"),rs.getInt("stare")));
		}
		rs1 = stmt.executeQuery(sql2);
		while(rs1.next()){
			Main.componente.add(new Componenta(rs1.getString("denumire"),rs1.getString("culoare"),rs1.getString("um")));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}

public class Main {
	static List<Furnizor> furnizori = new ArrayList<Furnizor>();
	static List<Componenta> componente = new ArrayList<Componenta>();
	static List<Livrare> livrari = new ArrayList<Livrare>();
	static Conexiune con = new Conexiune();
	static MapReduce mr = new MapReduce();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		con.importa("SELECT * FROM Furnizori", "SELECT * FROM Componente");

		/*		
		Furnizor fz1= new Furnizor("Ion","Buc",2);
		Furnizor fz2= new Furnizor("Radu","Buc",1);
		Furnizor fz3= new Furnizor("Mihai","Bz",3);
		
		Componenta cmp1 = new Componenta("saiba","rosu","kg");
		Componenta cmp2 = new Componenta("surub","alb","kg");
		
		Livrare liv1 = new Livrare(fz1,cmp2,200);
		Livrare liv2 = new Livrare(fz2,cmp1,400);
		Livrare liv3 = new Livrare(fz3,cmp2,150);
		Livrare liv4 = new Livrare(fz1,cmp1,250);
		
		List<Livrare> lst = new ArrayList<Livrare>();
		lst.add(liv1); lst.add(liv2); lst.add(liv3); lst.add(liv4);
		
		for(Livrare l:lst){
			if (l.cantit>150){
				System.out.println(l.fz.nume);
				System.out.println(l.cmp.denumire);
				System.out.println(l.cantit);
			}
		}*/
		livrari.add(new Livrare(furnizori.get(0),componente.get(1),200));
		livrari.add(new Livrare(furnizori.get(1),componente.get(2),400));
		livrari.add(new Livrare(furnizori.get(2),componente.get(1),150));
		livrari.add(new Livrare(furnizori.get(0),componente.get(0),250));
		
		System.out.println("Sa se afiseze furnizorii care livreaza peste 150um:");
		livrari.stream().filter(l->l.cantit>150).forEach(l->System.out.println(l.fz.getNume()+'\t'+l.cantit));
		
		System.out.println("\nSa se afiseze cantitatea totala livrata de fiecare furnizor in parte:");
		Map<Furnizor, Integer> sum = livrari.stream().collect(Collectors.groupingBy(Livrare::getFz, Collectors.summingInt(Livrare::getCantit)));
		
		for(Furnizor f : sum.keySet())
			System.out.println(f.nume + '\t' + sum.get(f));
		
		System.out.println("\nSa se afiseze cantitatea totala livrata din fiecare culoare in parte:");
		Map<Componenta, Integer> sum1 = livrari.stream().collect(Collectors.groupingBy(Livrare::getCmp, Collectors.summingInt(Livrare::getCantit)));
		
		for(Componenta c : sum1.keySet())
			System.out.println(c.culoare + '\t' + sum1.get(c));
		
		con.inchidere();
		
		mr.populate();
	}

}
