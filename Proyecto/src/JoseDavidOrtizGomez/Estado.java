package JoseDavidOrtizGomez;

import core.game.StateObservation;
import ontology.Types;

public class Estado {

	public String indice;
	public int Izpelota; //0 izquierda, 1 centro, 2 derecha
	public int Drpelota;
	public int Cenpelota;
	public int IzBandeja;
	public int DrBandeja;
	public int CenBandeja;
	
	
	
	public Estado(StateObservation Ob) {
		//Si muere, le ponemos un estado propio
		if (Ob.getGameWinner() == Types.WINNER.PLAYER_LOSES) {
			
			this.Izpelota = -1;
			this.Drpelota = -1;
			this.Cenpelota = -1;
		
		}else{
			
		Entorno m=new Entorno(Ob);
		
		IzBandeja=m.getIzBandeja();
		DrBandeja=m.getDrBandeja();
		CenBandeja=m.getCenBandeja();
	    //sacamos las celdas que necesitamos del avatar
	    this.Izpelota = m.getPosPelota(IzBandeja,18);
	    this.Drpelota = m.getPosPelota(DrBandeja,18);
	    this.Cenpelota = m.getPosPelota(CenBandeja,18);
		indice = funcionHash(this);
		}
		
	}
	private String funcionHash(Estado estado) {
		return "" + estado.IzBandeja + estado.DrBandeja + estado.CenBandeja + estado.Izpelota  + estado.Cenpelota + estado.Drpelota;
	}
	
	public String getIndice() {
		return indice;
	}
	public int getIzpelota() {
		return Izpelota;
	}
	public void setIzpelota(int izpelota) {
		Izpelota = izpelota;
	}
	public int getDrpelota() {
		return Drpelota;
	}
	public void setDrpelota(int drpelota) {
		Drpelota = drpelota;
	}
	public int getCenpelota() {
		return Cenpelota;
	}
	public void setCenpelota(int cenpelota) {
		Cenpelota = cenpelota;
	}
	public int getIzBandeja() {
		return IzBandeja;
	}
	public void setIzBandeja(int izBandeja) {
		IzBandeja = izBandeja;
	}
	public int getDrBandeja() {
		return DrBandeja;
	}
	public void setDrBandeja(int drBandeja) {
		DrBandeja = drBandeja;
	}
	public int getCenBandeja() {
		return CenBandeja;
	}
	public void setCenBandeja(int cenBandeja) {
		CenBandeja = cenBandeja;
	}
	
}
