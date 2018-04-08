package net.luis_curso.app_alert;

/**
 * Created by sergiocuenca on 4/4/18.
 */

public class Icons {
    private int idIcon;
    private int url;

    public Icons(int url, int id){
        this.idIcon = id;
        this.url = url;
    }

    public Icons(int id){
        this.idIcon = id;
    }
    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }


}
