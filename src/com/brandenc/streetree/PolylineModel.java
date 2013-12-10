package com.brandenc.streetree;

/**
 * Created by bchaishorn on 10/31/13.
 */

//polyline model that we wish to save


public class PolylineModel {
    private long id;
    private PolylineModel poly;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PolylineModel getPolyline() {
        return poly;
    }

    public void setPolyline(PolylineModel poly) {
        this.poly = poly;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return poly.toString();
    }
}
