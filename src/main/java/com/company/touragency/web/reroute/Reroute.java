package com.razkuuuuuuu.touragency.web.reroute;

public class Reroute {

    private TransitionType type;
    private String destination;
    public Reroute() {}
    public Reroute(TransitionType type, String destination) {
        this.type=type;
        this.destination=destination;
    }
    public TransitionType getType() {return type;}
    public String getDestination() {return destination;}

    public void setDestination(String destination) {
        this.destination=destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reroute)) return false;

        Reroute reroute = (Reroute) o;

        if (!type.equals(reroute.type)) return false;
        return destination.equals(reroute.destination);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }
}
