package graphicswindow;

class Location {
//    private Boolean solved = false;
    private Picture background; // the background space image (nebula)
    private Picture anomaly; // the anomaly zoom in
    private Picture zoomOut; // the zoom out graphic when leaving the nebula
    private Picture message; //"Comander: That seems to be the encryption key for the next part of the message!  I'll begin the decryption process for you."

    Location(String bkgrd, String anom, String zmOut, String msg) {

        background = new Picture(bkgrd);
        anomaly = new Picture(anom);
        zoomOut = new Picture(zmOut);
        message = new Picture(msg);

    }

    Picture getBackground() {
        return background;
    }

    Picture getAnomaly() {
        return anomaly;
    }

    Picture getZoomOut() {
        return zoomOut;
    }

    Picture getMessage() {
        return message;
    }
}