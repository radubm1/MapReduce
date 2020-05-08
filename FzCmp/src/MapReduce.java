import java.util.*;
class MapReduce {
	Map<String,String>Columns1=new HashMap<String,String>();
	Map<String,String>Columns2=new HashMap<String,String>();
	Map<Integer, Map<Date, HashMap<String, String>>> Rows=new HashMap<Integer, Map<Date, HashMap<String, String>>>();
	public void show() {
		for (Map.Entry<Integer, Map<Date, HashMap<String, String>>> ROWS:Rows.entrySet())
		{
			System.out.println(ROWS.getKey());
		    for(Map.Entry<Date, HashMap<String, String>> COLS:ROWS.getValue().entrySet())
		    {
				System.out.println(COLS.getKey());
				for(Map.Entry<String, String> COL:COLS.getValue().entrySet())
					System.out.println(COL);
		    }}
	}
	public void populate() {
		Columns1.put("nume",Main.furnizori.get(0).nume);
		Columns1.put("oras",Main.furnizori.get(0).oras);
		Columns1.put("denum",Main.componente.get(1).denumire);
		Columns1.put("cant",Integer.toString(Main.livrari.get(0).cantit));
		Columns1.put("UM",Main.componente.get(1).um);

		Columns2.put("nume",Main.furnizori.get(1).nume);
		Columns2.put("stare",Integer.toString(Main.furnizori.get(1).stare));
		Columns2.put("denum",Main.componente.get(2).denumire);
		Columns2.put("culoare",Main.componente.get(2).culoare);
		Columns2.put("cant",Integer.toString(Main.livrari.get(0).cantit));
		Columns2.put("UM",Main.componente.get(2).um);
		
		Map<Date, HashMap<String, String>> FColumns1= new HashMap<Date,HashMap<String,String>>();
		FColumns1.put(new Date(),(HashMap<String, String>) Columns1);
		Map<Date, HashMap<String, String>> FColumns2= new HashMap<Date,HashMap<String,String>>();
		FColumns2.put(new Date(),(HashMap<String, String>) Columns2);
	
		Rows.put(1,FColumns1);
		Rows.put(2,FColumns2);
		
		show();
	}
	
}


