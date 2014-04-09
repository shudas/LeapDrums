package leapPack;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EventListener;
import java.util.EventObject;


public class LeapHit {
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private boolean rhit;
	private boolean lhit;
	private int rinstrument;
	private int linstrument;
    
	public boolean getrHit() {
		return rhit;
	}
	public void setrHit(boolean hit) {
		// old value is the instrument
		pcs.firePropertyChange("rhit", rinstrument, hit);
		this.rhit = hit;
	}
	
	public int getrInstrument() {
		return rinstrument;
	}
	public void setrInstrument(int instrument) {
		pcs.firePropertyChange("rinstrument", this.rinstrument, instrument);
		this.rinstrument = instrument;
	}
	
	public boolean getlHit() {
		return lhit;
	}
	
	public void setlHit(boolean hit) {
		// old value is the instrument
		pcs.firePropertyChange("lhit", linstrument, hit);
		this.lhit = hit;
	}
	
	public int getlInstrument() {
		return linstrument;
	}
	
	public void setlInstrument(int instrument) {
		pcs.firePropertyChange("linstrument", this.linstrument, instrument);
		this.linstrument = instrument;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
    
}
