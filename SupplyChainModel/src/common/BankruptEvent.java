package common;

class BankruptEvent {

	final double value;
	final boolean fromUpstream;
	
	BankruptEvent(double value, boolean toDownstream){
		this.value = value;
		this.fromUpstream = toDownstream;
	}

}
