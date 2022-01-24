package JoseDavidOrtizGomez;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.File;
import java.io.FileReader;

public class TablaQ {

	public static Map<String, double[]> tabla = new HashMap<String, double[]>();

	public static void meter(String estado, double[] a) {
		tabla.put(estado, a);
	}

	public static void meter(String estado) {
		double vI = 0;// Valor inicial de todos los q-valores
		double[] a = { vI, vI, vI, vI };// inicializamos los q-valores
		tabla.put(estado, a);
	}

	public static void meterRandom(String estado, Random rand) {
		double[] valores = new double[4];
		for (int i = 0; i < 4; i++) {
			if (estado == "-1") {
				if (i==0) {
					valores[i] = 1000;
				}else valores[i] = rand.nextDouble() * 0.5;
				
			}
			else valores[i] = rand.nextDouble() * 0.5;

		}
		tabla.put(estado, valores);
	}

	public static int sacarMejor(String s) {

		if ("-1".equals(s)) {
			System.out.println("SACAR MEJOR SACA 0 PORQUE ESTADO ES -1");
			
			return 0;
		}else {
			double[] t = tabla.get(s);// sacamos la tabla con los valores de las acciones del estado s
			int max = 0;

			
			if (t == null) {// si no se ha dado ese estado hacemos una accion aleatoria
				max = (int) Math.floor(Math.random() * 4);
				System.out.println("SacarMejor devuelve aleatorio");
				return max;
			}

			for (int i = 1; i < t.length; i++) {// buscamos el indice con mayor valor
				if (t[i] > t[max]) {
					
					max = i;
				}
			}

			return max;
		}
		
	}

	public static double[] sacar(String s) {
		return tabla.get(s);
	}

	public static boolean comprobarEstado(String s) {
		return tabla.containsKey(s);
	}

	public static void actualizar(String s, int i, double valor) {
		double[] t = tabla.get(s);
		t[i] = valor;
		tabla.put(s, t);
	}

	public static double recogerQvalor(String s, int i) {
		double[] t = tabla.get(s);
		return t[i];
	}

	public static void guardarTabla() {
		try {
			String ruta = "./TablaQ.txt";
			File archivo = new File(ruta);
			BufferedWriter bw;
			if (archivo.exists()) {
				bw = new BufferedWriter(new FileWriter(archivo));
				System.out.println("El fichero de texto ya estaba creado.");
			} else {
				bw = new BufferedWriter(new FileWriter(archivo));
				System.out.println("Acabo de crear el fichero de texto.");
			}

			for (Map.Entry<String, double[]> fila : tabla.entrySet()) {
				if (fila.getKey() == null) {
					continue;
				}
				bw.write(fila.getKey());
				System.out.println(fila.getKey());
				double[] QValores = fila.getValue();
				for (int i = 0; i < QValores.length; i++) {
					bw.write(";" + QValores[i]);
				}
				bw.write("\n");

			}

			bw.close();
		} catch (Exception ex) {
			System.out.println(ex + "error generar fichero");
		}
	}

	public static void leerFichero() throws IOException {

		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader("./TablaQ.txt"));
			String line = br.readLine();// Leemos una linea

			while (null != line) {// Mientras leamos una linea continuamos
				String[] fields = line.split(";");// Dividimos los datos separandolos por el ;

				String estado = fields[0];
				double QV[] = new double[4];
				for (int i = 1; i < fields.length; i++) {
					QV[i - 1] = Double.parseDouble(fields[i]);
				}
				meter(estado, QV);
				line = br.readLine();// leemos otra linea
			}

		} catch (Exception e) {
		} finally {
			if (null != br) {
				br.close();
			}
		}

	}

	public static void verTabla() {
		for (Map.Entry<String, double[]> fila : tabla.entrySet()) {
			System.out.println(fila.getKey());
			double[] QValores = fila.getValue();
			for (int i = 0; i < QValores.length; i++) {
				System.out.println(";" + QValores[i]);
			}
			System.out.println();

		}
	}
}
