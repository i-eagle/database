package de.peter.fbdj_songs;

public class Comment { //Strukturaufbau eines Eintrages in der Datenbank
private long 	id;
private String 	titel;
private String 	interpret;
private String 	tonart;
private String 	liedtext;
private int 	favorit;
private int 	eingelesen;
private int 	haeufig_benutzt;



public long getId() {
  return id;
}
public void setId(long id) {
  this.id = id;
}
public String getTitel() {
  return titel;
}
public void setTitel(String comment) {
  titel = comment;
}
public String getInterpret() {
	  return interpret;
	}
public void setInterpret(String comment){
	interpret = comment;
}	
public String getTonart() {
		return tonart;
	}
public void setTonart(String comment) {
  	tonart = comment;
	}
public String getLiedtext(){
	return liedtext;
}
public void setLiedtext(String comment){
	liedtext = comment;
}
public int getFavorit() {
	return favorit;
	}
public void setFavorit(int comment) {
		favorit = comment;
	}		
public int getEingelesen() {
	return eingelesen;
	}
public void setEingelesen(int comment) {
		this.eingelesen = comment;
	}			
public int getHaeufig_benutzt() {
		return haeufig_benutzt;
	}
public void setHaeufig_benutzt(int comment) {
		this.haeufig_benutzt = comment;
	}
				


// Will be used by the ArrayAdapter in the ListView
@Override
public String toString() {
  return this.titel+"\n"+this.interpret;
}
} 