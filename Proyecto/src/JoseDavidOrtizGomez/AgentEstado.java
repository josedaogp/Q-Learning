package JoseDavidOrtizGomez;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import JoseDavidOrtizGomez.Entorno;
import JoseDavidOrtizGomez.TablaQ;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;

/**
 * Created with IntelliJ IDEA. User: ssamot Date: 14/11/13 Time: 21:45 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class AgentEstado extends AbstractPlayer {

	protected Random randomGenerator;

	protected ArrayList<Types.ACTIONS> actions;
	public TablaQ tablaQ;

	public static int n = 0;

	public static double alpha = 0.55;
	public static final float gamma = 0.55f;
	public static final double k = 100;

	private static Random rand = new Random(100);

	/*
	 * Public constructor with state observation and time due.
	 * 
	 * @param so state observation of the current game.
	 * 
	 * @param elapsedTimer Timer for the controller creation.
	 */
	public AgentEstado(StateObservation so, ElapsedCpuTimer elapsedTimer) {

		randomGenerator = new Random();
		actions = so.getAvailableActions();
		actions.add(Types.ACTIONS.ACTION_NIL);

	}

	/**
	 * Picks an action. This function is called every game step to request an action
	 * from the player.
	 * 
	 * @param stateObs     Observation of the current state.
	 * @param elapsedTimer Timer when the action returned is due.
	 * @return An action for the current state
	 * @throws InterruptedException
	 */

	public Types.ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {

		int accion;

		Estado s = new Estado(stateObs); // Se crea e inicializa el estado s
		String ident_s = s.getIndice(); // identificador de s
		boolean existe = TablaQ.comprobarEstado(ident_s);
		if (!existe) { // Si no existe el estado, se mete.
			TablaQ.meter(ident_s);
		}

		// ESCOGER LA ACCI�N SEG�N LA POL�TICA DEL AGENTE
		double epsilon = k / (k + n); // n es el tiempo o iteraciones
		double aleatorio = rand.nextDouble();

		if (aleatorio < epsilon) { // si el numero aleatorio es menor que epsilon, se ejecuta una accion aleatoria
			System.out.println("ALEATORIO");
			boolean repetir = false;

			if ("-1".equals(ident_s)) {
				System.out.println("Se le da la accion 0 al estado -1");
				accion = 0;
			} else {

				repetir = false;
				accion = (int) Math.floor(Math.random() * 4);
			}

			// Devolver accion aleatoria. Se puede generar un entero entre 0 y 3 y al final
			// del metodo act devolverlo
		} else { // Se elige la acci�n de mayor valor Q en la tablaQ
			System.out.println("Tabla Q");
			accion = TablaQ.sacarMejor(ident_s);
		}

		// VER ESTADO SIGUIENTE Y RECOMPENSA
		StateObservation Futuro = stateObs.copy();
		Futuro.advance(actions.get(accion));
		Estado sPrima = new Estado(Futuro);
		String ident_sPrima = sPrima.getIndice();

		// PARA LAS ESTADISTICAS, SI LA PARTIDA VA A ACABAR, MIRA LOS TIMESTEPS QUE
		// LLEVA Y LOS METE EN LAS ESTADISTICAS
		if (Futuro.getGameWinner() == Types.WINNER.PLAYER_LOSES || Futuro.getGameWinner() == Types.WINNER.PLAYER_WINS) {
			Estadisticas.meterDato(stateObs.getGameScore());
			Estadisticas.numPartidas++;
			System.out.println("NUM PARTIDAS: " + Estadisticas.numPartidas);
		}

		// Si muere se va a un estado raro el cual no queremos controlar

		if (!TablaQ.comprobarEstado(ident_sPrima)) { // Si no est� el estado, se mete.
			if (Futuro.getGameWinner() == Types.WINNER.PLAYER_LOSES) {
				TablaQ.meter(ident_sPrima);

			} else
				TablaQ.meter(ident_sPrima);
		}

		// SISTEMA RECOMPENSAS
		float recompensa = 0;

		if (sPrima.getCenpelota() == 2 && accion == 2) { // la pelota esta a la derecha y toma la accion de la derecha
			recompensa++;
		} else if (sPrima.getCenpelota() == 0 && accion == 1) { // la pelota esta a la izquierda y va a la izquierda
			recompensa++;
		} else if (sPrima.getCenpelota() == 1 && accion == 3) { //la pelota esta en el centro y no hace nada
			recompensa++;
		} else
			recompensa -= 0.5;

		if (sPrima.getDrpelota() == 2 && accion == 2) { // la pelota esta a la derecha y toma la accion de la derecha
			recompensa++;
		} else if (sPrima.getDrpelota() == 0 && accion == 1) { // la pelota esta a la izquierda y va a la izquierda
			recompensa++;
		} else if (sPrima.getDrpelota() == 1 && accion == 2) { //la pelota esta en el centro y va a la derecha
			recompensa++;
		} else if (sPrima.getDrpelota() == 1 && accion == 3) {//la pelota esta en el centro y no hace nada
			recompensa += 0.5;
		} else
			recompensa -= 0.5;

		if (sPrima.getIzpelota() == 2 && accion == 2) { // la pelota esta a la derecha y toma la accion de la derecha
			recompensa++;
		} else if (sPrima.getIzpelota() == 0 && accion == 1) { // la pelota esta a la izquierda y va a la izquierda
			recompensa++;
		} else if (sPrima.getIzpelota() == 1 && accion == 1) {//la pelota esta en el centro y va a la izquierda
			recompensa++;
		} else if (sPrima.getIzpelota() == 1 && accion == 3) {//la pelota esta en el centro y no hace nada
			recompensa += 0.5;
		} else
			recompensa -= 0.5;

		if (sPrima.getCenpelota() == -1 && accion == 0) {// para que saque
			System.out.println("Se le da el +10 para sacar");
			recompensa += 10;
		} else
			recompensa -= 0.5;
			
		// CALCULAR VALORES Q
		double Qactual = TablaQ.recogerQvalor(ident_s, accion);
		double Qfuturo = TablaQ.recogerQvalor(ident_sPrima, TablaQ.sacarMejor(ident_sPrima));
		// double v = Qactual + alpha *recompensa;
		double v = Qactual + alpha * (recompensa + gamma * Qfuturo - Qactual);

		/*
		 * System.out.println(); System.out.println("---DATOS A METER EN TABLA Q---");
		 * System.out.println("Estado: "+ ident_s); System.out.println("");
		 * System.out.println("Accion: "+accion); System.out.println("Valor q: "+v);
		 */
		TablaQ.actualizar(ident_s, accion, v);
		return actions.get(accion); // ACCION NO ALEATORIA
	}

}
