package pruebasMongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.List;

import org.bson.Document;


public class PruebaMongo {

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
		nuevo.put("valor", "500");
		nuevo.put("nombre", "prueba");

		miColeccion.insertOne(nuevo);

		System.out.println("\n INSERCION CORRECTA \n");		
		
	}
	
	public void insercionPrueba2() {

		System.out.println("\n PRUEBA INSERCION CON FIND DE OTRA COLECCION ");		
		
		Document busqueda = new Document();
		busqueda.put("clave", "coca");
		
		Document proyeccion = new Document();
		proyeccion.put("_id", 0);
		
		MongoCursor resultado = miColeccion2.find(busqueda).projection(proyeccion).iterator();
		Document doc = (Document) resultado.next();

	
		Document nuevo = new Document();
		nuevo.put("valor", "50000");
		nuevo.put("nombre", "prueba insert find sin _id");
		nuevo.put("dispensadorIntruso", doc);

		miColeccion.insertOne(nuevo);

		System.out.println("\n INSERCION CORRECTA \n");		
		
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
