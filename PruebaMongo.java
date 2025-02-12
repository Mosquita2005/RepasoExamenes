package pruebasMongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;


public class PruebaMongo {
																							{
  "_id": 1,
  "nombre": "Concesionario ABC",
  "vehiculos": [
    {
      "id": 1,
      "marca": "Toyota",
      "matricula": "1234ABC",
      "modelo": "Corolla"
    },
    {
      "id": 2,
      "marca": "Honda",
      "matricula": "5678XYZ",
      "modelo": "Civic"
    }
  ]
}
	MongoCollection miColeccion;
	MongoCollection miColeccion2;

	public PruebaMongo() {

		MongoClient mongoClient = new MongoClient("localhost", 27017);

		MongoDatabase database = mongoClient.getDatabase("maquinarefrescos");

		miColeccion = database.getCollection("garaje");
		miColeccion2 = database.getCollection("dispensadores");
		
	}
	public void leerDatos() {
	    MongoCursor<Document> cursorPrincipal = miColeccion.find().iterator();

	    System.out.println("\n VER DATOS \n");

	    while (cursorPrincipal.hasNext()) {
	        Document doc = cursorPrincipal.next();
	        String nombreConcesionario = doc.getString("nombre");
	        System.out.println("Concesionario: " + nombreConcesionario);

	        // Obtener el array de vehículos
	        List<Document> vehiculos = (List<Document>) doc.get("vehiculos");

	        // Iterar sobre el array de vehículos
	        for (Document vehiculo : vehiculos) {
	            Integer id = vehiculo.getInteger("id");
	            String marca = vehiculo.getString("marca");
	            String matricula = vehiculo.getString("matricula");
	            String modelo = vehiculo.getString("modelo");

	            System.out.println(id + " - " + marca + " - " + matricula + " - " + modelo);
	        }
	        System.out.println("=========================================");
	    }
	}



	public void insercionPrueba() {

		System.out.println("\n PRUEBA INSERCION \n Nombre Prueba y valor 500 \n ");		
		
		Document nuevo = new Document();
		nuevo.put("_id", "2");
		nuevo.put("nombre", "Concesionario DEF");
		ArrayList<Document>array= new ArrayList<Document>();
		Document v1= new Document();
		v1.put("id", 3);
		v1.put("marca", "ferrari");
		v1.put("matricula", "241191AF");
		v1.put("modelo", "sf24");

		
		
		Document v2= new Document();
		v2.put("id", 4);
		v2.put("marca", "haas");
		v2.put("matricula", "225321AF");
		v2.put("modelo", "vf24");
		
		array.add(v1);
		array.add(v2);
		nuevo.put("vehiculos", array);
		
		miColeccion.insertOne(nuevo);
		System.out.println("\n INSERCION CORRECTA \n");		
		
	}
	
	public void eliminar() {

			System.out.println("\n PRUEBA INSERCION CON FIND DE OTRA COLECCION ");		
		
		Document eliminar = new Document();
		eliminar.put("_id","1");
		
		Document vehiculo = new Document();
		vehiculo.put("id", 1);
		
		Document array= new Document("vehiculos",vehiculo);
		
		Document pull= new Document("$pull",array);
		
		miColeccion.updateOne(eliminar,pull);
		System.out.println("\n Eliminacion CORRECTA \n");	
		
	}

	public void pruebaBusqueda() {

		System.out.println("\n BUSQUEDA VALOR 500 \n");		
		
		Document searchQuery = new Document();
		searchQuery.put("valor", "500");

		MongoCursor resultado = miColeccion.find(searchQuery).iterator();

		while (resultado.hasNext()) {

			Document doc = (Document) resultado.next();
			System.out.println(doc.getString("nombre") + doc.getString("valor"));

		}

	}

}
