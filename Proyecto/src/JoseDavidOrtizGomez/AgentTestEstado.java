package JoseDavidOrtizGomez;

import java.util.ArrayList;
import java.util.Random;

import JoseDavidOrtizGomez.Estado;
import JoseDavidOrtizGomez.TablaQ;
import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types;
import tools.ElapsedCpuTimer;

/**
 * Created with IntelliJ IDEA. User: ssamot Date: 14/11/13 Time: 21:45 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class AgentTestEstado extends AbstractPlayer {
	/**
	 * Random generator for the agent.
	 */
	protected Random randomGenerator;
	/**
	 * List of available actions for the agent
	 */
	protected ArrayList<Types.ACTIONS> actions;
	private static int contador = 0;

	/**
	 * Public constructor with state observation and time due.
	 * 
	 * @param so           state observation of the current game.
	 * @param elapsedTimer Timer for the controller creation.
	 */
	public AgentTestEstado(StateObservation so, ElapsedCpuTimer elapsedTimer) {
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

		Estado s = new Estado(stateObs); // Se crea e inicializa el estado s String
		String ident_s = s.getIndice();
		if (!TablaQ.comprobarEstado(ident_s)) {
			System.out.println(ident_s);
		}
		int accion = TablaQ.sacarMejor(ident_s);
		System.out.println("Est� en el estado-> " + ident_s + " <-- y coge la acci�n: " + accion);

		/*
		 * Entorno e = new Entorno(stateObs); e.verMapa(); contador++;
		 * //System.out.println("La j en posPelota es: "+j);
		 * 
		 * if (contador < 10) { return actions.get(1); }else return actions.get(3);
		 */

		 return actions.get(accion);

	}

}
