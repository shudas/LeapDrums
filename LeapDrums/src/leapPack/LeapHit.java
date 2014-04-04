package leapPack;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EventListener;
import java.util.EventObject;


public class LeapHit {
	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	private boolean hit;
	private int instrument;
    
	public boolean getHit() {
		return hit;
	}
	public void setHit(boolean hit) {
		// old value is the instrument
		pcs.firePropertyChange("hit", instrument, hit);
		this.hit = hit;
	}
	
	public int getInstrument() {
		return instrument;
	}
	public void setInstrument(int instrument) {
		pcs.firePropertyChange("instrument", this.instrument, instrument);
		this.instrument = instrument;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
    
}
